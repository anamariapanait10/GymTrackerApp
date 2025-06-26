package com.unibuc.gymtrackerapp.services;

import com.unibuc.gymtrackerapp.domain.entity.security.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.*;
import java.util.UUID;

@Service
public class UserServiceProxy {
    private final WebClient webClient;

    private final DataSource dataSource;

    public UserServiceProxy(WebClient.Builder builder, DataSource dataSource) {
        this.webClient = builder.filter(new ServletBearerExchangeFilterFunction()).build(); // uses load-balanced builder
        this.dataSource = dataSource;
    }

    public Mono<UUID> getUserByEmail(String username) {
        return webClient
                .get()
                .uri("lb://GYMTRACKERAPP-USER/users/" + username) // Eureka service name
                .header(HttpHeaders.AUTHORIZATION)
                .retrieve()
                .bodyToMono(UUID.class);
    }

    public UUID getUserIdByUsernameFallback(String username) {
        String sql = "SELECT id FROM user WHERE username = ?";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    byte[] uuidBytes = rs.getBytes("id");
                    if (uuidBytes != null && uuidBytes.length == 16) {
                        ByteBuffer bb = ByteBuffer.wrap(uuidBytes);
                        long high = bb.getLong();
                        long low = bb.getLong();
                        return new UUID(high, low);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
