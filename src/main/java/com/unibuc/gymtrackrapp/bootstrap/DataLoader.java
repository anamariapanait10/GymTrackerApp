package com.unibuc.gymtrackrapp.bootstrap;

import com.unibuc.gymtrackrapp.domain.security.Authority;
import com.unibuc.gymtrackrapp.domain.security.User;
import com.unibuc.gymtrackrapp.repositories.security.AuthorityRepository;
import com.unibuc.gymtrackrapp.repositories.security.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@AllArgsConstructor
@Component
@Profile("mysql")
public class DataLoader implements CommandLineRunner {
    @Autowired
    private DataSource dataSource;

    private AuthorityRepository authorityRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    private void loadUserData() throws IOException, SQLException {
        if (userRepository.count() == 0){
            Authority adminRole = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());
            Authority guestRole = authorityRepository.save(Authority.builder().role("ROLE_GUEST").build());

            User admin = User.builder()
                    .email("admin@email.com")
                    .password(passwordEncoder.encode("12345"))
                    .authority(adminRole)
                    .build();

            User guest = User.builder()
                    .email("guest@email.com")
                    .password(passwordEncoder.encode("12345"))
                    .authority(guestRole)
                    .build();

            userRepository.save(admin);
            userRepository.save(guest);
        }

        Resource sqlInitializationScript = new ClassPathResource("initial-data.sql");
        ScriptUtils.executeSqlScript(dataSource.getConnection(), sqlInitializationScript);
    }

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }
}
