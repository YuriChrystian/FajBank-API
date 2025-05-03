package br.com.faj.bank.infra.security;

import br.com.faj.bank.customer.data.CustomerRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityRequestFilter extends OncePerRequestFilter {

    private JwtService service;

    private CustomerRepository customerRepository;

    public SecurityRequestFilter(
            JwtService service,
            CustomerRepository customerRepository
    ) {
        this.service = service;
        this.customerRepository = customerRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = token.replace("Bearer ", "");
        String email = service.verify(token);
        UserDetails customer = customerRepository.findByEmail(email);

        if (customer != null) {
            var auth = new UsernamePasswordAuthenticationToken(customer, email, customer.getAuthorities());
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(auth);
        }


        filterChain.doFilter(request, response);
    }
}
