# Catálogo de Cursos

API REST independente para cadastrar instrutores e cursos, desenvolvida com Java 21 e Spring Boot.

## Requisitos

- Java 21
- PostgreSQL
- Git

O projeto usa o Maven Wrapper, portanto não exige uma instalação global do Maven.

## Banco de dados

Crie um banco PostgreSQL chamado `catalogocursos_dev`. Copie `.env.example` para `.env` e informe apenas no ambiente local os dados reais de conexão.

No PowerShell, carregue as variáveis antes de iniciar a aplicação:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/catalogocursos_dev"
$env:DB_USERNAME="seu_usuario"
$env:DB_PASSWORD="sua_senha"
.\mvnw.cmd spring-boot:run
```

O Liquibase cria as tabelas `instrutor` e `curso`, a restrição única de e-mail e a chave estrangeira obrigatória `curso.instrutor_id`. O Hibernate apenas valida o schema.
