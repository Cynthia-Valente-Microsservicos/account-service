# Account-Service

Microsserviço responsável pelo gerenciamento de contas de usuário. Armazena dados de conta no PostgreSQL com senha protegida por hash SHA-256, e é chamado pelo `auth-service` para registro e verificação de credenciais.

## Tecnologias

- Java 25
- Spring Boot 4.0.3
- Spring Cloud 2025.1.0 (OpenFeign)
- Spring Data JPA + PostgreSQL + Flyway
- Micrometer / Prometheus
- Lombok

## Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/accounts` | Cria uma nova conta |
| `DELETE` | `/accounts/{id}` | Remove uma conta |
| `GET` | `/accounts` | Lista todas as contas |
| `GET` | `/accounts/{id}` | Busca conta por ID |
| `POST` | `/accounts/login` | Valida credenciais (email + senha) |
| `GET` | `/accounts/health-check` | Health check |

### POST `/accounts`

**Request body:**
```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "senha123",
  "role": "USER"
}
```

**Response:** `201 Created` com header `Location: /accounts/{id}`

### POST `/accounts/login`

**Request body:**
```json
{
  "email": "joao@email.com",
  "password": "senha123"
}
```

**Response:** `200 OK` com `AccountOut`, ou `404 Not Found` se credenciais inválidas.

## Banco de Dados

Schema PostgreSQL: `accounts`

| Tabela | Colunas |
|--------|---------|
| `accounts.accounts` | id (UUID), name, email (UNIQUE), password_sha256, role |

- Senhas armazenadas como hash SHA-256 (Base64)
- Index em `(email, password_sha256)` para login eficiente

## Comunicação entre serviços

| Tipo | Serviço | Detalhe |
|------|---------|---------|
| Feign (entrada) | `auth-service` | Chama este serviço para criar contas e validar credenciais |

## Variáveis de ambiente

| Variável | Descrição | Padrão |
|----------|-----------|--------|
| `DATABASE_HOST` | Host do PostgreSQL | — |
| `DATABASE_PORT` | Porta do PostgreSQL | `5432` |
| `DATABASE_DB` | Nome do banco | `store` |
| `DATABASE_USERNAME` | Usuário do banco | `store` |
| `DATABASE_PASSWORD` | Senha do banco | — |

## Executando localmente

### Com Docker (recomendado)

```bash
docker compose up
```

### Build manual

```bash
mvn clean package -DskipTests
java -jar target/account-service-1.0.0.jar
```

O serviço sobe na porta `8080`.

## Métricas

Prometheus disponível em `/accounts/actuator/prometheus`.

## Dependências externas

- **`store:account:1.0.0`** — biblioteca de contratos (DTOs e Feign client)
- **PostgreSQL** — persistência de contas
