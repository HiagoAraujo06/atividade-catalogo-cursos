# Catálogo de Cursos

API REST independente para cadastrar instrutores e cursos, desenvolvida com Java 21 e Spring Boot.

## Integrantes

- Hiago Camillo Araújo Pereira
- João Vitor Garcia de Moraes
- Nicolas Ferreira Meiado

## Requisitos

- Java 21
- PostgreSQL
- Git

O projeto usa o Maven Wrapper, portanto não exige uma instalação global do Maven.

## Banco de dados

No pgAdmin 4, abra o Query Tool e execute sozinho o arquivo [`database/setup-pgadmin.sql`](database/setup-pgadmin.sql). Ele cria o banco `catalogocursos_dev`. Depois que a aplicação iniciar, reconecte o Query Tool a esse banco e execute [`database/verificar-pgadmin.sql`](database/verificar-pgadmin.sql) para conferir migrations, tabelas e restrições.

Copie `.env.example` para `.env` e informe apenas no ambiente local os dados reais de conexão. A senha nunca deve ser incluída em commits.

No PowerShell, carregue as variáveis antes de iniciar a aplicação:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/catalogocursos_dev"
$env:DB_USERNAME="seu_usuario"
$env:DB_PASSWORD="sua_senha"
.\mvnw.cmd spring-boot:run
```

O Liquibase cria as tabelas `instrutor` e `curso`, a restrição única de e-mail e a chave estrangeira obrigatória `curso.instrutor_id`. O Hibernate apenas valida o schema.

## Execução no IntelliJ IDEA

1. Abra esta pasta como projeto e aguarde o IntelliJ importar o Maven.
2. Em **Run > Edit Configurations**, selecione a configuração compartilhada `CatalogocursosApplication`.
3. Em **Environment variables**, mantenha `DB_URL` e `DB_USERNAME` já configurados e adicione `DB_PASSWORD` com a senha do PostgreSQL local.
4. Execute `CatalogocursosApplication`. Na primeira inicialização, o Liquibase monta o schema automaticamente.

Exemplo das variáveis, sem armazenar a senha no repositório:

```text
DB_URL=jdbc:postgresql://localhost:5432/catalogocursos_dev;DB_USERNAME=postgres;DB_PASSWORD=sua_senha_local
```

## Contrato REST

| Método | Endpoint | Resultado |
|---|---|---|
| `POST` | `/api/instrutores` | Cadastra um instrutor e retorna `201 Created` |
| `POST` | `/api/cursos` | Cadastra um curso para um instrutor existente e retorna `201 Created` |
| `GET` | `/api/cursos/{id}` | Consulta um curso com um resumo do instrutor |
| `GET` | `/api/instrutores/{id}/cursos` | Lista os cursos do instrutor em ordem de título |

Entradas inválidas retornam `400`, recursos inexistentes retornam `404` e e-mail duplicado retorna `409`. As respostas usam DTOs e não expõem diretamente as entidades JPA.

## Testes

Execute no Windows:

```powershell
.\mvnw.cmd clean test
```

Os testes cobrem a consistência da associação bidirecional, a rejeição de curso com instrutor inexistente, a validação HTTP e a listagem ordenada por instrutor.

## Respostas da entrega

- A entidade proprietária da relação é `Curso`, pois ela contém o mapeamento `@ManyToOne` e a coluna de associação.
- A chave estrangeira está na tabela `curso`, na coluna `instrutor_id`.
- As entidades não são retornadas diretamente para separar o contrato HTTP do modelo de persistência e impedir recursão no JSON da associação bidirecional.
