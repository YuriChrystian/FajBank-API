package br.com.faj.bank.infra.security;

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
public class SecurityConfiguration {

    private SecurityRequestFilter securityRequestFilter;
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfiguration(
            SecurityRequestFilter securityRequestFilter,
            CustomAccessDeniedHandler customAccessDeniedHandler
    ) {
        this.securityRequestFilter = securityRequestFilter;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize ->
                        authorize
                                // Auth endpoints
                                .requestMatchers(HttpMethod.POST, "/v1/signup").permitAll()
                                .requestMatchers(HttpMethod.POST, "/v1/signin").permitAll()
                                // Swagger endpoints
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/api-docs/**").permitAll()
                                // Health check endpoints
                                .requestMatchers(HttpMethod.GET, "/v1/customer/all").hasAuthority("ROLE_ADMIN")
                                .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedHandler(customAccessDeniedHandler)
                )
                .addFilterBefore(securityRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
