# Carteira Digital

## Endpoints

### GET /v1/wallet/bff-mobile
Obtém saldo e métodos de pagamento cadastrados.

**Requer autenticação:** Sim

### GET /v1/wallet
Lista todos os métodos de pagamento.

**Requer autenticação:** Sim

### POST /v1/wallet/register-payment-method
Registra novo método de pagamento.

**Requer autenticação:** Sim

**Payload:**
```json
{
  "cardNumber": "string",
  "cardHolderName": "string",
  "expirationDate": "string",
  "cvv": "string"
}
```

### POST /v1/wallet/remove/{cardId}
Remove um método de pagamento específico.

**Requer autenticação:** Sim

## Validações
- O número do cartão deve ter 11 dígitos
- O CVV deve ter 3 dígitos
- A data de expiração deve estar no formato MM/YY
- O nome do titular deve conter pelo menos nome e sobrenome 