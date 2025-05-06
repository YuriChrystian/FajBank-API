package br.com.faj.bank.checkout.data;

import br.com.faj.bank.checkout.model.entity.CheckoutTransactionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CheckoutTransactionRepository extends CrudRepository<CheckoutTransactionEntity, Long> {

    @Query("update CheckoutTransactionEntity c set c.status = :status where c.id = :id")
    CheckoutTransactionEntity updateStatusById(@Param("id") Long id, @Param("status") String status);

}
