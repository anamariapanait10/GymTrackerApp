package com.unibuc.gymtrackerapp.config.dev;

import com.unibuc.gymtrackerapp.domain.security.Authority;
import com.unibuc.gymtrackerapp.domain.security.User;
import com.unibuc.gymtrackerapp.repositories.AuthorityRepository;
import com.unibuc.gymtrackerapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@Profile("dev")
@Configuration
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private DataSource dataSource;

    private AuthorityRepository authorityRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    private void loadUserData() throws IOException, SQLException {
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
