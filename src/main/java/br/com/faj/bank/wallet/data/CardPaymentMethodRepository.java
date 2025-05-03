package br.com.faj.bank.wallet.data;

import br.com.faj.bank.wallet.model.entity.CardPaymentMethodEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardPaymentMethodRepository extends CrudRepository<CardPaymentMethodEntity, Long> {

    @Query("select c from CardPaymentMethodEntity c where c.customer_id = :id")
    List<CardPaymentMethodEntity> findAllByCustomerId(Long id);

    @Query("select c from CardPaymentMethodEntity c where c.cardNumber = :cardNumber and c.customer_id = :customerId")
    CardPaymentMethodEntity findPaymentMethodByNumberAndCustomer(String cardNumber, Long customerId);

    @Query("select c from CardPaymentMethodEntity c where c.id = :id and c.customer_id = :customerId")
    CardPaymentMethodEntity findPaymentMethodByIdAndCustomer(Long id, Long customerId);

}
