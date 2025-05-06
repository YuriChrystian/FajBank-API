package br.com.faj.bank.checkout.domain;

import br.com.faj.bank.checkout.data.CheckoutTransactionRepository;
import br.com.faj.bank.checkout.model.TransactionStatus;
import br.com.faj.bank.core.SessionCustomer;
import br.com.faj.bank.invoice.data.InvoiceRepository;
import br.com.faj.bank.wallet.data.CardPaymentMethodRepository;
import br.com.faj.bank.wallet.data.WalletCustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class PayTransactionUseCase {

    private final CheckoutTransactionRepository checkoutTransactionRepository;
    private final CardPaymentMethodRepository cardPaymentMethodRepository;
    private final WalletCustomerRepository walletCustomerRepositor;
    private final InvoiceRepository invoiceRepository;
    private final SessionCustomer session;

    public PayTransactionUseCase(
            CheckoutTransactionRepository checkoutTransactionRepository,
            CardPaymentMethodRepository cardPaymentMethodRepository,
            WalletCustomerRepository walletCustomerRepository,
            InvoiceRepository invoiceRepository,
            SessionCustomer session
    ) {
        this.checkoutTransactionRepository = checkoutTransactionRepository;
        this.cardPaymentMethodRepository = cardPaymentMethodRepository;
        this.walletCustomerRepositor = walletCustomerRepository;
        this.invoiceRepository = invoiceRepository;
        this.session = session;
    }

    public TransactionStatus pay(Long transactionId) {
        var transaction = checkoutTransactionRepository.findById(transactionId);

        if (transaction.isEmpty()) {
            throw new IllegalArgumentException("Transaction not found");
        }

        var invoiceId = transaction.get().getInvoiceId();
        if (invoiceId == null) {
            throw new IllegalArgumentException("Invoice ID cannot be null");
        }

        var invoice = invoiceRepository.findByIdAndCustomerId(invoiceId, session.getCustomerId());
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice not found");
        }

        if (transaction.get().getPaymentMethodId() == 0) {
            var wallet = walletCustomerRepositor.findByCustomerId(session.getCustomerId());

            if (wallet.getBalance().compareTo(invoice.getTotalAmount()) < 0) {
                checkoutTransactionRepository.updateStatusById(transactionId, TransactionStatus.REJECTED.name());
                return TransactionStatus.REJECTED;
            }

            wallet.setBalance(wallet.getBalance().subtract(invoice.getTotalAmount()));
            walletCustomerRepositor.save(wallet);

            invoice.setStatus("PAID");
            invoiceRepository.save(invoice);

            checkoutTransactionRepository.updateStatusById(transactionId, TransactionStatus.APPROVED.name());
            return TransactionStatus.APPROVED;
        }

        var card = cardPaymentMethodRepository.findById(transaction.get().getPaymentMethodId());

        if (card.isEmpty()) {
            checkoutTransactionRepository.updateStatusById(transactionId, TransactionStatus.REJECTED.name());
            return TransactionStatus.REJECTED;
        }

        checkoutTransactionRepository.updateStatusById(transactionId, TransactionStatus.APPROVED.name());
        return TransactionStatus.APPROVED;
    }

}
