package ru.otus.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/book/**").authenticated()
                        .requestMatchers("/api/comment/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/genre/**").authenticated()
                        .requestMatchers("/api/genre/**").hasAuthority("ROLE_MANAGER")
                        .requestMatchers(HttpMethod.GET, "/api/author/**").authenticated()
                        .requestMatchers("/api/author/**").hasAuthority("ROLE_MANAGER")
                        .requestMatchers("/api/user/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll);
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
//        return NoOpPasswordEncoder.getInstance();

    }

    @Bean
    DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }

    @Bean
    public UserDetailsManager userDetailsService(DataSource dataSource) {
        UserDetails user = User
                .builder()
                .username("user")
                .password("$2a$10$p6fZvEXrndwlIXrL1uJT3et2Hyb4YyqRsXwivJwct7pf8lYsCTc7q") // password
                .roles("USER")
                .build();
        UserDetails admin = User
                .builder()
                .username("admin")
                .password("$2a$10$am/tOvXSUuV6wgosthrFGO6n8caIgNobipmr0ZhJ3Xf3q1q5LDR42") // admin
                .roles("ADMIN")
                .build();
        UserDetails manager = User
                .builder()
                .username("manager")
                .password("$2a$10$am/tOvXSUuV6wgosthrFGO6n8caIgNobipmr0ZhJ3Xf3q1q5LDR42") // admin
                .roles("MANAGER")
                .build();
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.createUser(user);
        userDetailsManager.createUser(admin);
        userDetailsManager.createUser(manager);
        return userDetailsManager;
    }
}
