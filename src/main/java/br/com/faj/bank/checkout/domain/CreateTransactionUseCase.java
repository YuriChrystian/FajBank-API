package br.com.faj.bank.checkout.domain;

import br.com.faj.bank.checkout.data.CheckoutTransactionRepository;
import br.com.faj.bank.checkout.model.PaymentType;
import br.com.faj.bank.checkout.model.TransactionStatus;
import br.com.faj.bank.checkout.model.domain.CheckoutTransactionDomain;
import br.com.faj.bank.checkout.model.entity.CheckoutTransactionEntity;
import br.com.faj.bank.core.SessionCustomer;
import br.com.faj.bank.invoice.data.InvoiceRepository;
import br.com.faj.bank.wallet.data.CardPaymentMethodRepository;
import br.com.faj.bank.wallet.data.WalletCustomerRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CreateTransactionUseCase {

    private final CheckoutTransactionRepository repoistory;
    private final InvoiceRepository invoiceRepository;
    private final CardPaymentMethodRepository cardPaymentMethod;
    private final WalletCustomerRepository walletCustomer;
    private final SessionCustomer session;

    public CreateTransactionUseCase(
            CardPaymentMethodRepository cardPaymentMethodRepository,
            CheckoutTransactionRepository repoistory,
            WalletCustomerRepository walletCustomer,
            InvoiceRepository invoiceRepository,
            SessionCustomer session
    ) {
        this.session = session;
        this.repoistory = repoistory;
        this.invoiceRepository = invoiceRepository;
        this.walletCustomer = walletCustomer;
        this.cardPaymentMethod = cardPaymentMethodRepository;
    }

    public CheckoutTransactionDomain create(
            PaymentType paymentMethod,
            Long cardId,
            Long invoiceId
    ) {

        if (invoiceId == null) {
            throw new IllegalArgumentException("Invoice ID cannot be null");
        }

        var invoice = invoiceRepository.findByIdAndCustomerId(invoiceId, session.getCustomerId());

        if (invoice == null) {
            throw new IllegalArgumentException("Invoice not found");
        }

        var entity = new CheckoutTransactionEntity();
        entity.setCustomerId(session.getCustomerId());
        entity.setCreated(LocalDateTime.now());
        entity.setUpdated(LocalDateTime.now());
        entity.setInvoiceId(invoiceId);
        entity.setStatus(TransactionStatus.PENDING);


        switch (paymentMethod) {
            case BALANCE_PAYMENT:

                var wallet = walletCustomer.findByCustomerId(session.getCustomerId());

                if (wallet == null) {
                    throw new IllegalArgumentException("Wallet not found");
                }

                if (wallet.getBalance().compareTo(invoice.getTotalAmount()) < 0) {
                    throw new IllegalArgumentException("Insufficient balance");
                }

                entity.setPaymentMethodId(0L);
                break;
            case CREDIT_CARD:

                if (cardId == null) {
                    throw new IllegalArgumentException("Card ID cannot be null");
                }

                var card = cardPaymentMethod.findById(cardId);

                if (card.isEmpty()) {
                    throw new IllegalArgumentException("Card not found");
                }

                entity.setPaymentMethodId(cardId);
               break;
           default:
               throw new IllegalArgumentException("Unsupported payment method");
        }

        return CheckoutTransactionDomain.of(repoistory.save(entity));
    }
}
