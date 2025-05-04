![Version](https://img.shields.io/badge/0.0.1-alpha)

# FajBank 🍎

FajBank é uma API REST desenvolvida em Java com Spring Boot que simula operações bancárias básicas. O projeto oferece funcionalidades como autenticação de usuários, gerenciamento de carteiras digitais, métodos de pagamento e timeline de atividades.

## Características Principais

- Autenticação segura com JWT
- Gerenciamento de carteira digital
- Registro e gerenciamento de métodos de pagamento
- Gerenciamento de faturas mensais
- Sistema de checkout para pagamentos
- Timeline de atividades do usuário
- Endpoints RESTful
- Criptografia de dados sensíveis
- Documentação interativa com OpenAPI/Swagger

## Documentação da API

A documentação completa dos endpoints está disponível em dois formatos:

### Swagger UI (Interativa)
- Local: http://localhost:8080/swagger-ui
- Permite testar os endpoints diretamente pelo navegador
- Documentação detalhada de todos os endpoints, payloads e respostas

### Documentação por Contexto
- [Autenticação](doc/endpoints/auth.md)
- [Cliente](doc/endpoints/customer.md)
- [Carteira Digital](doc/endpoints/wallet.md)
- [Faturas](doc/endpoints/invoice.md)
- [Timeline](doc/endpoints/timeline.md)
- [Checkout](doc/endpoints/checkout.md)

## Configuração do Projeto

Para executar o projeto localmente:

1. Clone o repositório
2. Configure as variáveis de ambiente necessárias
3. Execute o projeto usando Maven:
   ```bash
   mvn spring-boot:run
   ```
4. Acesse a documentação Swagger em http://localhost:8080/swagger-ui.html

## Segurança

Todos os endpoints (exceto /v1/signup e /v1/signin) requerem autenticação via token JWT.
O token deve ser enviado no header Authorization: `Bearer {token}`

## Exemplos de Uso

Você pode encontrar exemplos de requisições HTTP para todos os endpoints no diretório [curl](curl/). Os exemplos estão em formato `.http` e podem ser executados diretamente em IDEs como VSCode ou IntelliJ IDEA.

## Notas de Desenvolvimento

- [Timeline](doc/timeline-doc.md)
