package br.com.faj.bank.wallet.model.response;

import java.math.BigDecimal;
import java.util.List;

public record WalletCustomerResponse(
        BigDecimal balance,
        List<PaymentMethodResponse> paymentMethods
) { }
