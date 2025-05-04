# Faturas

## Endpoints

### POST /v1/invoice
Cria uma nova fatura.

**Requer autenticação:** Sim

**Payload:**
```json
{
  "due_date": "2024-04-10",
  "description": "string",
  "amount": "100.00"
}
```

### GET /v1/invoice
Lista todas as faturas do cliente.

**Requer autenticação:** Sim

### GET /v1/invoice/{invoiceId}
Obtém detalhes de uma fatura específica.

**Requer autenticação:** Sim

### POST /v1/invoice/{invoiceId}/charges
Adiciona uma nova cobrança a uma fatura existente.

**Requer autenticação:** Sim

**Payload:**
```json
{
  "description": "string",
  "amount": "50.00"
}
```

## Estados da Fatura
- `PENDING`: Fatura pendente de pagamento
- `PAID`: Fatura paga
- `OVERDUE`: Fatura vencida

## Observações
- Uma fatura pode ter múltiplas cobranças
- O valor total da fatura é atualizado automaticamente ao adicionar novas cobranças
- A data de vencimento deve ser uma data futura 