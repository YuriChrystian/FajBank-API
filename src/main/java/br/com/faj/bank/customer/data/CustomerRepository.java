package br.com.faj.bank.customer.data;


import br.com.faj.bank.customer.model.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {

    UserDetails findByEmail(String email);

    List<CustomerEntity> findAllByOrderByIdDesc();

}
