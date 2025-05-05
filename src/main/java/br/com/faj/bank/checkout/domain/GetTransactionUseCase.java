package br.com.faj.bank.checkout.domain;

import br.com.faj.bank.checkout.data.CheckoutTransactionRepository;
import br.com.faj.bank.checkout.model.domain.CheckoutTransactionDomain;
import br.com.faj.bank.core.SessionCustomer;
import br.com.faj.bank.invoice.data.InvoiceRepository;
import br.com.faj.bank.wallet.data.CardPaymentMethodRepository;
import br.com.faj.bank.wallet.data.WalletCustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class GetTransactionUseCase {

    private final CheckoutTransactionRepository repoistory;
    private final InvoiceRepository invoiceRepository;
    private final CardPaymentMethodRepository cardPaymentMethod;
    private final SessionCustomer session;

    public GetTransactionUseCase(
            CardPaymentMethodRepository cardPaymentMethodRepository,
            CheckoutTransactionRepository repoistory,
            InvoiceRepository invoiceRepository,
            SessionCustomer session
    ) {
        this.session = session;
        this.repoistory = repoistory;
        this.invoiceRepository = invoiceRepository;
        this.cardPaymentMethod = cardPaymentMethodRepository;
    }

    public CheckoutTransactionDomain get(Long transactionId) {
        var transaction = repoistory.findById(transactionId);

        if (transaction.isEmpty()) {
            throw new RuntimeException("Transaction not found");
        }

        var invoiceId = transaction.get().getInvoiceId();
        var paymentMethodId = transaction.get().getPaymentMethodId();

        var invoice = invoiceRepository.findByIdAndCustomerId(invoiceId, session.getCustomerId());
        var paymentMethod = cardPaymentMethod.findPaymentMethodByIdAndCustomer(paymentMethodId, session.getCustomerId());

        if (invoice == null) {
            throw new RuntimeException("Invoice not found");
        }

        if (paymentMethod == null) {
            throw new RuntimeException("Payment method not found");
        }

        return CheckoutTransactionDomain.of(transaction.get(), invoice, paymentMethod);
    }

}
