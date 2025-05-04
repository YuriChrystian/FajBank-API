# Autenticação

## Endpoints

### POST /v1/signup
Registro de novo usuário

**Payload:**
```json
{
  "email": "string",
  "password": "string",
  "first_name": "string",
  "second_name": "string"
}
```

### POST /v1/signin
Login de usuário

**Payload:**
```json
{
  "email": "string",
  "password": "string"
}
```

## Segurança
Estes são os únicos endpoints que não requerem autenticação. Após o login bem-sucedido, você receberá um token JWT que deve ser usado em todas as outras requisições. 