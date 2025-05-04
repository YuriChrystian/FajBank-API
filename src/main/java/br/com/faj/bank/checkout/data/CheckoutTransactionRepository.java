package br.com.faj.bank.checkout.data;

import br.com.faj.bank.checkout.model.entity.CheckoutTransactionEntity;
import org.springframework.data.repository.CrudRepository;

public interface CheckoutTransactionRepository extends CrudRepository<CheckoutTransactionEntity, Long> { }
