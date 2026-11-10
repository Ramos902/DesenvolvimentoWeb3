# Desenvolvimento Web 3

Repositório da disciplina de Programação para Web 3 (IFSP). Contém anotações das aulas e o projeto prático `estanteweb`.

## Estrutura

- `docs/Resumos_Prova/ResumoDesenvolvimentoWeb3.md`: Notas e resumos teóricos (aulas 1 a 9).
- `Projeto/docker-compose.yml`: Orquestração dos containers da aplicação e do MySQL.
- `Projeto/estanteweb/`: Aplicação Spring Boot e Dockerfile.

## Estante Web

Aplicação web para catálogo e gerenciamento de livros, sugestões e reservas.

### Stack

- Java 21
- Spring Boot 4 (Spring MVC)
- Apache FreeMarker (`.ftlh`)
- Spring JDBC (`JdbcTemplate`)
- MySQL 8
- Flyway
- Docker & Docker Compose

### Como rodar

#### Opção 1: Com Docker Compose (recomendado)

Inicie os containers da aplicação e do MySQL a partir da pasta `Projeto/`:

```bash
cd Projeto
docker compose up -d --build
```

Acesse em `http://localhost:8081`.

Comandos úteis:
- Ver logs da aplicação: `docker compose logs -f app`
- Recompilar após alterar o código: `docker compose up -d --build app`
- Parar containers: `docker compose down`

#### Opção 2: Localmente (Spring Boot com MySQL no Docker)

Caso prefira rodar com o Spring Boot DevTools ativo na sua máquina:

1. Suba apenas o MySQL:
```bash
cd Projeto
docker compose up -d mysql
```

2. Execute a aplicação via Maven:
```bash
cd estanteweb
./mvnw spring-boot:run
```

### Rotas

| Método | Rota | Descrição |
|---|---|---|
| GET | `/` | Página inicial |
| GET | `/livros` | Listagem de livros (aceita `?ordem=titulo|autor|ano|preco`) |
| GET | `/livros/{id}` | Ficha detalhada do livro (com outros títulos do mesmo autor) |
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
