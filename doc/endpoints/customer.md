# Cliente

## Endpoints

### GET /v1/customer
Obtém dados do cliente autenticado.

**Requer autenticação:** Sim

### POST /v1/customer
Atualiza dados do cliente.

**Requer autenticação:** Sim

**Payload:**
```json
{
  "fields": [
    {
      "type": "FIRST_NAME",
      "description": "string"
    }
  ]
}
```

## Campos Editáveis
- `FIRST_NAME`: Nome do cliente
- `LAST_NAME`: Sobrenome do cliente
- `EMAIL`: Email do cliente 