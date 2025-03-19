package br.edu.unifaj.fajbank.service;

import br.edu.unifaj.fajbank.data.customer.CustomerRepository;
import br.edu.unifaj.fajbank.data.customer.CustomerTable;
import br.edu.unifaj.fajbank.data.dto.LoginUserDto;
import br.edu.unifaj.fajbank.data.dto.RegisterUserDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final CustomerRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            CustomerRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerTable signup(RegisterUserDto input) {
        CustomerTable user = new CustomerTable();
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    public CustomerTable authenticate(LoginUserDto input) {
//        System.out.println("Authenticated user: authenticate");
//        Authentication auth = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        input.getEmail(),
//                        input.getPassword()
//                )
//        );
//         System.out.println("Authenticated user: " + auth.getPrincipal());

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
