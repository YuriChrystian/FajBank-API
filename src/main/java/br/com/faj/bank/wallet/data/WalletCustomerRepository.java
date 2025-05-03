package br.com.faj.bank.wallet.data;

import br.com.faj.bank.wallet.model.entity.WalletCustomerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface WalletCustomerRepository extends CrudRepository<WalletCustomerEntity, Long> {

    @Query("select w from WalletCustomerEntity w where w.customer_id = :id")
    WalletCustomerEntity findByCustomerId(@Param("id") Long id);

}
