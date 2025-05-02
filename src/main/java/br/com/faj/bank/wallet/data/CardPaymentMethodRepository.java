package br.com.faj.bank.wallet.data;

import br.com.faj.bank.wallet.model.entity.CardPaymentMethodEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardPaymentMethodRepository extends CrudRepository<CardPaymentMethodEntity, Long> {

    @Query("select c from CardPaymentMethodEntity c where c.customer_id = :id")
    List<CardPaymentMethodEntity> findAllByCustomerId(Long id);

}
