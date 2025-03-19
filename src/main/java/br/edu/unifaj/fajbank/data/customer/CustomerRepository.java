package br.edu.unifaj.fajbank.data.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerTable, Integer> {
    Optional<CustomerTable> findByEmail(String email);
}
