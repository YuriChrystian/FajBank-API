# Checkout API

O módulo de Checkout gerencia transações de pagamento de faturas, permitindo o uso de diferentes métodos de pagamento.

## Endpoints

### Criar Transação

```http
POST /v1/checkout/create
```

Cria uma nova transação de pagamento para uma fatura específica.

#### Request Body
```json
{
  "invoice_id": 123,
  "payment_method_id": 456,
  "payment_type": "CREDIT_CARD"
}
```

#### Campos do Request
| Campo | Tipo | Descrição |
|-------|------|-----------|
| invoice_id | Long | ID da fatura a ser paga |
| payment_method_id | Long | ID do método de pagamento (obrigatório apenas para CREDIT_CARD) |
| payment_type | String | Tipo de pagamento: "CREDIT_CARD" ou "BALANCE_PAYMENT" |

#### Response Success (200 OK)
```json
{
  "transaction_id": 789,
  "status": "PENDING"
}
```

#### Response Error (400 Bad Request)
```json
{
  "message": "Invoice not found",
  "invoice_id": 123
}
```

## Tipos de Pagamento

- `CREDIT_CARD`: Pagamento via cartão de crédito
- `BALANCE_PAYMENT`: Pagamento usando saldo da carteira

## Status de Transação

- `PENDING`: Transação criada e aguardando processamento
- `APPROVED`: Transação processada com sucesso
- `REJECTED`: Transação rejeitada
- `CANCELLED`: Transação cancelada

## Regras de Negócio

### Validações Gerais
- A fatura (invoice_id) deve existir e pertencer ao usuário autenticado
- O tipo de pagamento deve ser válido (CREDIT_CARD ou BALANCE_PAYMENT)

### Pagamento com Cartão de Crédito
- O ID do cartão (payment_method_id) é obrigatório
- O cartão deve existir e pertencer ao usuário autenticado

### Pagamento com Saldo
- O usuário deve possuir uma carteira ativa
- O saldo disponível deve ser suficiente para cobrir o valor da fatura

## Possíveis Erros

| Mensagem | Descrição |
|----------|-----------|
| "Invoice ID cannot be null" | ID da fatura não informado |
| "Invoice not found" | Fatura não encontrada ou não pertence ao usuário |
| "Card ID cannot be null" | ID do cartão não informado (para pagamento com cartão) |
| "Card not found" | Cartão não encontrado ou não pertence ao usuário |
| "Wallet not found" | Carteira não encontrada |
| "Insufficient balance" | Saldo insuficiente para pagamento |
| "Unsupported payment method" | Método de pagamento não suportado |

## Exemplo de Uso

```bash
curl -X POST http://localhost:8080/v1/checkout/create \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "invoice_id": 123,
    "payment_method_id": 456,
    "payment_type": "CREDIT_CARD"
  }'
``` 