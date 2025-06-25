package com.unibuc.gymtrackerapp.config.dev;

import com.unibuc.gymtrackerapp.domain.security.Authority;
import com.unibuc.gymtrackerapp.domain.security.User;
import com.unibuc.gymtrackerapp.repositories.AuthorityRepository;
import com.unibuc.gymtrackerapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final DataSource dataSource;
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;

    private void loadUserData() throws SQLException {
        if (userRepository.count() == 0){
            Authority adminRole = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());

            User admin = User.builder()
                    .username("admin")
                    .authority(adminRole)
                    .build();

            userRepository.save(admin);
        }

        Resource sqlInitializationScript = new ClassPathResource("initial-data.sql");
        ScriptUtils.executeSqlScript(dataSource.getConnection(), sqlInitializationScript);
    }

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }
}
