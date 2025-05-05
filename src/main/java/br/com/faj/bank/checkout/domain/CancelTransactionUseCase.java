package br.com.faj.bank.checkout.domain;

import br.com.faj.bank.checkout.data.CheckoutTransactionRepository;
import br.com.faj.bank.checkout.model.TransactionStatus;
import br.com.faj.bank.core.SessionCustomer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CancelTransactionUseCase {

    private final CheckoutTransactionRepository repository;
    private final SessionCustomer session;

    public CancelTransactionUseCase(
            CheckoutTransactionRepository repository,
            SessionCustomer session
    ) {
        this.repository = repository;
        this.session = session;
    }

    public void cancel(Long transactionId) {
        var transaction = repository.findById(transactionId);

        if (transaction.isEmpty()) {
            throw new RuntimeException("Transaction not found");
        }

        var entity = transaction.get();

        if (!entity.getCustomerId().equals(session.getCustomerId())) {
            throw new RuntimeException("Unauthorized to cancel this transaction");
        }

        if (entity.getStatus() == TransactionStatus.CANCELLED) {
            throw new RuntimeException("Transaction is already cancelled");
        }

        if (entity.getStatus() != TransactionStatus.PENDING) {
            throw new RuntimeException("Only pending transactions can be cancelled");
        }

        entity.setStatus(TransactionStatus.CANCELLED);
        entity.setUpdated(LocalDateTime.now());
        repository.save(entity);
    }
}