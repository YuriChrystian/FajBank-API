package br.com.faj.bank;

import br.com.faj.bank.core.AppHelper;
import br.com.faj.bank.core.SessionCustomer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BankApplication {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SessionCustomer sessionCustomer() {
        return () -> AppHelper.getCustomer().getId();
    }

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }

}
