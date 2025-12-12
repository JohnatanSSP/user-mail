# user-mail

![Build Status](https://img.shields.io/badge/build-passing-brightgreen) ![License: MIT](https://img.shields.io/badge/license-MIT-blue) ![Java 17](https://img.shields.io/badge/java-17-informational) ![Spring Boot](https://img.shields.io/badge/spring--boot-3.5.7-brightgreen)

Uma coleção de microserviços em Java com Spring Boot para gerenciamento de usuários e envio de e-mails (envio assíncrono via RabbitMQ). O repositório contém dois módulos principais: `User_InGress` (API de usuários) e `Emails_InGress` (consumo de mensagens e envio de e-mails). O objetivo é demonstrar integrações típicas (PostgreSQL, RabbitMQ, SMTP) e boas práticas com migrações (Flyway) e configuração via variáveis de ambiente.

---

## Sumário
- [Descrição](#user-mail)
- [Tecnologias](#tecnologias-utilizadas)
- [Pré-requisitos](#pré-requisitos)
- [Instalação e execução local](#instalação-e-execução-local)
- [Configuração de variáveis de ambiente](#configuração-de-variáveis-de-ambiente)
- [Uso da API / Endpoints principais](#uso-da-api--endpoints-principais)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Migrations e seed data](#migrations-e-seed-data)
- [Troubleshooting comum](#troubleshooting-comum)
- [Contribuição](#contribuição)
- [Licença](#licença)
- [Contato](#contato)

---

## Tecnologias utilizadas
- Java 17
- Spring Boot 3.5.7
- Maven (`mvnw` wrapper incluído)
- Spring Data JPA (Hibernate)
- Spring AMQP (RabbitMQ)
- Spring Mail (JavaMailSender)
- Flyway (migrations)
- PostgreSQL (runtime)
- Docker & Docker Compose (opcional para infra local)
- springdoc-openapi (Swagger UI)
- Lombok

---

## Pré-requisitos
- Java JDK 17+
- Maven (pode usar o wrapper `./mvnw` / `mvnw.cmd`)
- Docker & Docker Compose (opcional, recomendado para PostgreSQL e RabbitMQ)
- Ferramenta para testes de API: curl, Postman ou Insomnia

Verifique as versões:
```powershell
java -version
.\mvnw.cmd -v
docker --version
docker compose version
```

---

## Instalação e execução local
Siga estes passos para rodar o ambiente de desenvolvimento localmente.

1. Clone o repositório:
```powershell
git clone <URL_DO_REPOSITORIO>
cd user-mail
```

2. Configurar variáveis de ambiente
- Há arquivos de exemplo: `.env.example` na raiz e em cada módulo (`Emails_InGress/.env.example`, `User_InGress/.env.example`).
- Crie um `.env` a partir do exemplo e preencha credenciais sensíveis (DB, RabbitMQ, SMTP).
  - PowerShell:
  ```powershell
  Copy-Item .env.example .env
  # ou para um módulo específico
  Copy-Item .\Emails_InGress\.env.example .\Emails_InGress\.env
  ```
- Atenção: nunca commite o `.env`. Use o arquivo `.env.sanitized` para revisar sem expor segredos.

3. (Opcional) Levantar infraestrutura com Docker Compose
- Muitos módulos incluem `docker-compose.yml`. Exemplo genérico:
```powershell
# Levantar PostgreSQL e RabbitMQ para desenvolvimento
cd Emails_InGress
docker compose up -d
```

4. Rodar os serviços via Maven
- Exemplo para `Emails_InGress`:
```powershell
cd Emails_InGress
.\mvnw.cmd -DskipTests package
.\mvnw.cmd spring-boot:run
```
- Exemplo para `User_InGress`:
```powershell
cd User_InGress
.\mvnw.cmd -DskipTests package
.\mvnw.cmd spring-boot:run
```
- Para rodar com debug/relatório de condicionais:
```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--debug"
```

---

## Configuração de variáveis de ambiente
As aplicações usam placeholders em `application.yml`. Principais variáveis (veja `.env.example`):

- Banco de dados
  - `SPRING_DATASOURCE_URL` (ex: `jdbc:postgresql://localhost:5433/postgres`)
  - `SPRING_DATASOURCE_USERNAME`
  - `SPRING_DATASOURCE_PASSWORD`
  - `USER_DS_URL`, `USER_DS_USERNAME`, `USER_DS_PASSWORD` (para `User_InGress` se necessário)
- RabbitMQ
  - `RABBITMQ_ADDRESSES`, `RABBITMQ_USERNAME`, `RABBITMQ_PASSWORD`, `RABBITMQ_VHOST`, `RABBITMQ_PORT`
  - `USER_RABBITMQ_*` (variáveis de RabbitMQ específicas do módulo `User_InGress`)
- SMTP
  - `SMTP_USERNAME`, `SMTP_PASSWORD` (configuração `spring.mail.username`/`spring.mail.password`)
- Outros
  - `EMAILS_SERVICE_PORT`, `USER_SERVICE_PORT`, `FLYWAY_LOCATIONS`

Observações de segurança:
- Não inclua segredos no repositório.
- Use secrets de CI/CD para deploys (GitHub Actions Secrets, GitLab CI Variables, etc.).

---

## Uso da API / Endpoints principais
Base URLs padrão (local):
- `Emails_InGress`: `http://localhost:8080` (padrão)
- `User_InGress`: `http://localhost:8081` (padrão)

Endpoints exemplares (ver controllers para lista completa):

- Emails_InGress
  - `POST /api/emails` — criar e enviar e-mail
  - `GET  /api/emails/{id}` — obter detalhes/status de um e-mail
  - `GET  /api/emails` — listar e-mails

- User_InGress
  - `POST /user` — criar usuário
  - `GET  /all/users` — listar todos os usuários
  - `DELETE /user/{id}` — remover usuário
  - (Se aplicável) `POST /api/auth/login` — autenticação JWT

Exemplo `curl` para enviar e-mail (ajuste payload conforme DTO):
```bash
curl -X POST http://localhost:8080/api/emails \
  -H "Content-Type: application/json" \
  -d '{"userId":"22222222-2222-2222-2222-222222222222","emailTo":"user@example.com","emailSubject":"Teste","emailBody":"Conteúdo do e-mail"}'
```

Swagger/OpenAPI
- Se o módulo usar `springdoc-openapi`, a UI estará disponível em:
  - `http://localhost:8080/swagger-ui.html` ou `http://localhost:8080/swagger-ui/index.html`
  - `http://localhost:8080/v3/api-docs` (JSON)

---

## Estrutura do projeto (resumida)
```
user-mail/
├─ Emails_InGress/
│  ├─ src/main/java/johnatanSSP/Emails_InGress/
│  │  ├─ consumer/      # consumidores RabbitMQ
│  │  ├─ controller/    # controllers REST
│  │  ├─ domain/        # entidades JPA
│  │  ├─ dto/           # DTOs
│  │  ├─ repositorie/   # Spring Data repositories
│  │  └─ service/       # serviços e lógica de negócio
│  ├─ src/main/resources/application.yml
│  └─ src/main/resources/db/migration/  # Flyway
├─ User_InGress/
│  └─ ...
└─ .env.example
```

---

## Migrations e seed data
- As migrations Flyway estão em `Emails_InGress/src/main/resources/db/migration`.
- Observação importante: se você adicionou um `INSERT` diretamente em `V1_CREATE_TB_EMAIL`, essa migration pode falhar ao reaplicar (PK duplicada). Recomendações:
  - Para seeds, prefira criar uma nova migration `V2__seed_tb_email.sql` com `INSERT ... ON CONFLICT DO NOTHING` (Postgres) para ser idempotente.
  - Alternativamente, use um script de inicialização separado ou `data.sql` controlado por profile.

Exemplo idempotente (Postgres):
```sql
INSERT INTO tb_email (email_id, ...) VALUES ('1111-...', ...) ON CONFLICT (email_id) DO NOTHING;
```

---

## Troubleshooting comum
1. Erro: `Unable to determine Dialect without JDBC metadata`
   - Causa: `spring.datasource.url` não definido corretamente ou placeholder não resolvido.
   - Solução: definir `SPRING_DATASOURCE_URL` no `.env` ou fornecer default válido em `application.yml`.

2. Erro: `FATAL: password authentication failed for user "postgres"`
   - Causa: credenciais do Postgres incorretas.
   - Solução: ajustar `SPRING_DATASOURCE_USERNAME`/`PASSWORD` ou iniciar container Postgres com as mesmas credenciais.

3. Erro: `UnsatisfiedDependencyException` ao criar beans
   - Causa: algum bean necessário não foi criado (ex.: `EmailService` sem `@Service`, dependência ausente, ou component-scan não cobre o pacote).
   - Solução: confirme as anotações (`@Service`, `@Repository`, `@Component`), verifique `@SpringBootApplication(scanBasePackages = ...)` se necessário, e confira logs para o `Caused by:` específico.

4. Problemas no envio de e-mail (SMTP)
   - Verifique `spring.mail.username` e `spring.mail.password` e as configurações de segurança do provedor SMTP. Para Gmail, pode ser necessário usar App Passwords.

Dica: rodar a aplicação com `--debug` (argumento do Spring) fornece o condition evaluation report para facilitar diagnóstico.

---

## Testes
- Executar testes (se houver):
```powershell
.\mvnw.cmd test
```

---

## Contribuição
Contribuições são bem-vindas. Siga estas recomendações:
1. Fork o repositório
2. Crie uma branch com nome descritivo (`feature/`, `fix/`)
3. Adicione testes e documentação para mudanças
4. Abra um Pull Request descrevendo as alterações

Considere adicionar um `CONTRIBUTING.md` com padrões de commits e procedimentos de CI se o projeto crescer.

---

## Status do projeto
- Estado atual: em desenvolvimento / protótipo
- Badges: atualizar links CI (GitHub Actions) quando houver pipeline configurado.

---

## Licença
Este projeto está licenciado sob a licença MIT. Veja o arquivo `LICENSE` para detalhes.

---

## Contato
- Autor / Mantenedor: (adicione seu nome e e-mail)
- Repo: (adicione a URL do repositório)

---

Se quiser, posso:
- Adicionar `docker-compose.yml` prontos para levantar Postgres + RabbitMQ + app em um único comando;
- Criar `V2__seed_tb_email.sql` idempotente;
- Gerar `CONTRIBUTING.md` e `CODE_OF_CONDUCT.md`.
