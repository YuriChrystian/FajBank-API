package br.com.faj.bank.wallet.data;

import br.com.faj.bank.wallet.model.entity.CardPaymentMethodEntity;
import org.springframework.data.repository.CrudRepository;

public interface CardPaymentMethodRepository extends CrudRepository<CardPaymentMethodEntity, Long> {

}
