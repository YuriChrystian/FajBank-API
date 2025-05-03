package br.com.faj.bank.signup;

import br.com.faj.bank.customer.data.CustomerRepository;
import br.com.faj.bank.customer.model.CustomerRole;
import br.com.faj.bank.customer.model.entity.CustomerEntity;
import br.com.faj.bank.signup.model.request.SignUpRequest;
import br.com.faj.bank.signup.model.response.SignUpResponse;
import br.com.faj.bank.signup.model.response.SignUpStrategy;
import br.com.faj.bank.timeline.domain.RegisterCustomerTimelineUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/signup")
public class SignUpController {

    private final BCryptPasswordEncoder cryptPassword;
    private final CustomerRepository customerRepository;
    private final RegisterCustomerTimelineUseCase timeline;

    public SignUpController(
            RegisterCustomerTimelineUseCase timeline,
            BCryptPasswordEncoder cryptPassword,
            CustomerRepository customerRepository
    ) {
        this.timeline = timeline;
        this.cryptPassword = cryptPassword;
        this.customerRepository = customerRepository;
    }

    @PostMapping()
    public ResponseEntity<SignUpStrategy> signUp(
            @RequestBody SignUpRequest signUpRequest
    ) {

        if (customerRepository.findByEmail(signUpRequest.getEmail()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String password = cryptPassword.encode("{noop}"+signUpRequest.getPassword() );
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setEmail(signUpRequest.getEmail());
        customerEntity.setPassword(password);
        customerEntity.setFirstName(signUpRequest.getFirstName());
        customerEntity.setLastName(signUpRequest.getSecondName());
        customerEntity.setRole(CustomerRole.CUSTOMER.getRole());

        var customer = customerRepository.save(customerEntity);


        timeline.registerNewCustomer(customer.getId());

        return ResponseEntity.ok(new SignUpResponse("Criado com sucesso","OK"));
    }
}
