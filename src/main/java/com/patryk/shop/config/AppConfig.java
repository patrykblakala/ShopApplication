package com.patryk.shop.config;

import com.patryk.shop.domain.dao.Role;
import com.patryk.shop.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
        return args -> {
            initializaRole("ROLE_USER", roleRepository);
            initializaRole("ROLE_ADMIN", roleRepository);

        };
    }

    private void initializaRole(String name, RoleRepository roleRepository) {
        var role = roleRepository.findByName(name);
        if (role.isEmpty()) {
            roleRepository.save(new Role(null, name));
        }
    }
}
