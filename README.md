# Desenvolvimento Web 3

Repositório da disciplina de Programação para Web 3 (IFSP). Contém anotações das aulas e o projeto prático `estanteweb`.

## Estrutura

- `docs/ResumoDesenvolvimentoWeb3.md`: Notas e resumos teóricos (aulas 1 a 9).
- `Projeto/estanteweb/`: Projeto prático em Spring Boot.

## Estante Web

Aplicação web para catálogo e gerenciamento de livros, sugestões e reservas.

### Stack

- Java 21
- Spring Boot 4 (Spring MVC)
- Apache FreeMarker (`.ftlh`)
- Spring JDBC (`JdbcTemplate`)
- MySQL 8
- Flyway

### Como rodar

1. Suba o container do MySQL:

```bash
docker run --name estanteweb-mysql \
  -e MYSQL_ALLOW_EMPTY_PASSWORD=yes \
  -e MYSQL_DATABASE=estanteweb \
  -p 3306:3306 \
  -d mysql:8
```

2. Inicie a aplicação:

```bash
cd Projeto/estanteweb
./mvnw spring-boot:run
```

Acesse em `http://localhost:8081`.

### Rotas

| Método | Rota | Descrição |
|---|---|---|
| GET | `/` | Página inicial |
| GET | `/livros` | Listagem de livros |
| GET | `/livros/novo` | Formulário de cadastro de livro |
| POST | `/livros` | Cadastro de livro (com validação e PRG) |
| GET | `/livros/busca` | Busca por título (`?titulo=...`) |
| GET | `/livros/total` | Contagem total de livros |
| GET | `/livros/valor-total` | Soma dos preços dos livros |
| GET | `/sugestoes` | Listagem de sugestões |
| GET | `/sugestoes/nova` | Formulário de sugestão |
| POST | `/sugestoes` | Salvar sugestão no banco |
| GET | `/contato` | Formulário de contato |
| POST | `/contato` | Envio de contato |
| GET | `/reserva` | Formulário de reserva |
| POST | `/reserva` | Processamento de reserva |

### Banco de dados

As migrações do Flyway (`Projeto/estanteweb/src/main/resources/db/migration/`) são executadas automaticamente na inicialização:

- `V1__criar_tabela_livro.sql`: DDL da tabela `livro`
- `V2__criar_tabela_sugestao.sql`: DDL da tabela `sugestao`
- `V3__inserir_livros_iniciais.sql`: Carga inicial de livros
