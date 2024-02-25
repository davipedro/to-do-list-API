package br.com.todolistAPI.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "api/v1/root-admin/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/v1/tasks").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "api/v1/tasks").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "api/v1/tasks/title").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "api/v1/tasks/creation_date").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "api/v1/tasks/{taskId}").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "api/v1/tasks/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "api/v1/user/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/v1/user/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/v1/admin/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/v1/admin/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "api/v1/admin/admins").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "api/v1/root-admin/auth/admin-register").hasRole("ROOT")
                        .requestMatchers(HttpMethod.POST, "api/v1/root-admin/auth/login").permitAll()
                        .anyRequest().permitAll())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }
}
