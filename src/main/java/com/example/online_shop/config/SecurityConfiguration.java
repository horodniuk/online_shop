package com.example.online_shop.config;


import com.example.online_shop.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                 .cors(cors->cors.disable())
                 .authorizeHttpRequests((authz) -> authz
                            /*   .requestMatchers("/api/user").hasAuthority(Role.USER.getAuthority())
                               .requestMatchers("/api/admin").hasAuthority(Role.ADMIN.getAuthority())
                               .requestMatchers("/api/superadmin").hasAuthority(Role.SUPER_ADMIN.getAuthority())
                               .requestMatchers("/ping").permitAll()*/
                               .anyRequest().authenticated())
                .csrf().disable()
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
