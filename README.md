![Version](https://img.shields.io/badge/0.0.1-alpha)

# FajBank 🍎

FajBank é uma API REST desenvolvida em Java com Spring Boot que simula operações bancárias básicas. O projeto oferece funcionalidades como autenticação de usuários, gerenciamento de carteiras digitais, métodos de pagamento e timeline de atividades.

## Características Principais

- Autenticação segura com JWT
- Gerenciamento de carteira digital
- Registro e gerenciamento de métodos de pagamento
- Timeline de atividades do usuário
- Endpoints RESTful
- Criptografia de dados sensíveis

## Endpoints Disponíveis

### Autenticação

- **POST /v1/signup**
  - Registro de novo usuário
  - Payload:
    ```json
    {
      "email": "string",
      "password": "string",
      "first_name": "string",
      "second_name": "string"
    }
    ```

- **POST /v1/signin**
  - Login de usuário
  - Payload:
    ```json
    {
      "email": "string",
      "password": "string"
    }
    ```

### Cliente

- **GET /v1/customer**
  - Obtém dados do cliente autenticado
  - Requer autenticação

- **POST /v1/customer**
  - Atualiza dados do cliente
  - Requer autenticação
  - Payload:
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

### Carteira Digital

- **GET /v1/wallet/bff-mobile**
  - Obtém saldo e métodos de pagamento
  - Requer autenticação

- **GET /v1/wallet**
  - Lista todos os métodos de pagamento
  - Requer autenticação

- **POST /v1/wallet/register-payment-method**
  - Registra novo método de pagamento
  - Requer autenticação
  - Payload:
    ```json
    {
      "cardNumber": "string",
      "cardHolderName": "string",
      "expirationDate": "string",
      "cvv": "string"
    }
    ```

- **POST /v1/wallet/remove/{cardId}**
  - Remove um método de pagamento
  - Requer autenticação

### Timeline

- **GET /v1/timeline**
  - Obtém histórico de atividades do usuário
  - Requer autenticação

## Configuração do Projeto

Para executar o projeto localmente:

1. Clone o repositório
2. Configure as variáveis de ambiente necessárias
3. Execute o projeto usando Maven:
   ```bash
   mvn spring-boot:run
   ```

## Notas de Desenvolvimento

- [Timeline](doc/timeline-doc.md)

## Segurança

Todos os endpoints (exceto /v1/signup e /v1/signin) requerem autenticação via token JWT.
O token deve ser enviado no header Authorization: `Bearer {token}`
