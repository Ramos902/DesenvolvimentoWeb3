# Desenvolvimento Web 3 (PRW3 & LSPW)

Repositório da disciplina de **Programação para Web 3** e **Laboratório de Servidores para Web**, ministrada pelo Prof. Me. Danilo Bueno no IFSP.

O repositório reúne as anotações teóricas e práticas das aulas, além da aplicação web **Estante Web**, desenvolvida em Spring Boot com arquitetura MVC, templates FreeMarker, persistência via Spring JDBC e versionamento de banco com Flyway.

---

## 📁 Estrutura do Repositório

```text
DesenvolvimentoWeb3/
├── docs/
│   └── ResumoDesenvolvimentoWeb3.md   # Base de conhecimento e resumo detalhado das Aulas 1 a 9
├── Projeto/
│   └── estanteweb/                     # Aplicação Spring Boot desenvolvida no curso
│       ├── src/
│       │   ├── main/
│       │   │   ├── java/br/com/estanteweb/
│       │   │   │   ├── Controllers/   # Controllers Spring MVC (rotas, validações, PRG)
│       │   │   │   ├── Models/        # Modelos de domínio (Livro, Sugestao)
│       │   │   │   ├── LivroRepository.java
│       │   │   │   ├── SugestaoRepository.java
│       │   │   │   └── EstantewebApplication.java
│       │   │   └── resources/
│       │   │       ├── db/migration/  # Migrações versionadas do Flyway (V1, V2, V3)
│       │   │       ├── templates/     # Templates dinâmicos FreeMarker (.ftlh)
│       │   │       └── application.properties
│       │   └── test/                  # Testes da aplicação
│       ├── mvnw / mvnw.cmd            # Maven Wrapper
│       └── pom.xml                    # Configuração de build e dependências
├── .gitignore
├── .gitattributes
└── README.md
```

---

## 🚀 Aplicação: Estante Web

A **Estante Web** é um sistema web para catálogo e gerenciamento de livros, cadastro de sugestões de leitura, formulário de contato e simulação de reservas.

### Tecnologias

- **Linguagem:** Java 21
- **Framework:** Spring Boot 4.1.0 (Spring MVC, DevTools)
- **View Engine:** Apache FreeMarker (`.ftlh`)
- **Persistência:** Spring JDBC (`JdbcTemplate`)
- **Banco de Dados:** MySQL 8
- **Migrações:** Flyway
- **Gerenciador de Dependências:** Apache Maven (Wrapper incluso)

---

## 🛠️ Como Executar

### Pré-requisitos

- **Java JDK 21** ou superior instalado (`java -version`)
- **Docker** (recomendado para o MySQL) ou um servidor **MySQL local** na porta 3306

---

### 1. Inicializar o Banco de Dados (MySQL)

Utilize o Docker para instanciar o MySQL com o banco `estanteweb` já criado:

```bash
docker run --name estanteweb-mysql \
  -e MYSQL_ALLOW_EMPTY_PASSWORD=yes \
  -e MYSQL_DATABASE=estanteweb \
  -p 3306:3306 \
  -d mysql:8
```

> Se o seu MySQL local possuir senha para o usuário `root`, ajuste o arquivo `Projeto/estanteweb/src/main/resources/application.properties`:
> ```properties
> spring.datasource.password=sua_senha_aqui
> ```

---

### 2. Executar a Aplicação

Navegue até o diretório do projeto e execute com o Maven Wrapper:

```bash
cd Projeto/estanteweb
./mvnw spring-boot:run
```

Na inicialização, o **Flyway** aplicará automaticamente as migrações SQL (`V1`, `V2` e `V3`), criando as tabelas e inserindo os dados iniciais.

A aplicação estará acessível em: **[http://localhost:8081](http://localhost:8081)**

---

## 🌐 Rotas e Endpoints

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `GET` | `/` | Página inicial com mensagem de boas-vindas |
| `GET` | `/sobre` | Informações sobre o projeto |
| `GET` | `/livros` | Listagem dos livros cadastrados (com template FreeMarker) |
| `GET` | `/livros/novo` | Formulário para cadastro de novo livro |
| `POST` | `/livros` | Processamento do cadastro com validação de dados e padrão PRG |
| `GET` | `/livros/busca?titulo=...` | Busca e filtro de livros por título |
| `GET` | `/livros/total` | Retorna o total de livros cadastrados |
| `GET` | `/livros/valor-total` | Retorna o somatório dos preços de todos os livros |
| `GET` | `/livros/quem-acessa` | Demonstração do uso de sessão HTTP (`HttpSession`) |
| `GET` | `/sugestoes` | Listagem de sugestões de livros enviadas pelos usuários |
| `GET` | `/sugestoes/nova` | Formulário para envio de nova sugestão |
| `POST` | `/sugestoes` | Gravação de sugestão no banco de dados via `JdbcTemplate` |
| `GET` | `/contato` | Formulário de contato |
| `POST` | `/contato` | Processamento do contato com validação e padrão PRG |
| `GET` | `/contato/sucesso` | Página de confirmação de envio de contato |
| `GET` | `/reserva` | Formulário de reserva de livro |
| `POST` | `/reserva` | Processamento de reserva |

---

## 🗄️ Migrações de Banco (Flyway)

Os scripts de migração estão localizados em `Projeto/estanteweb/src/main/resources/db/migration/`:

1. `V1__criar_tabela_livro.sql`: Criação da tabela `livro` (campos: `id`, `titulo`, `autor`, `ano`, `preco`).
2. `V2__criar_tabela_sugestao.sql`: Criação da tabela `sugestao` (campos: `id`, `nome_cliente`, `livro_sugerido`).
3. `V3__inserir_livros_iniciais.sql`: Carga inicial com clássicos da literatura brasileira (*Dom Casmurro*, *O Cortiço*, *Grande Sertão: Veredas*, etc.).

---

## 📖 Caderno de Estudos e Anotações

Todo o embasamento teórico da disciplina está compilado em [docs/ResumoDesenvolvimentoWeb3.md](docs/ResumoDesenvolvimentoWeb3.md), formatado para visualização no GitHub ou em editores como o **Obsidian**.

### Conteúdo coberto:

- **Aula 1:** Fundamentos da Web e Protocolo HTTP (Cliente-Servidor, ciclo requisição-resposta, métodos, cabeçalhos, status codes)
- **Aula 2:** Arquitetura MVC e Introdução ao Spring Boot
- **Aula 3:** Comunicação HTTP no Spring (`@RequestParam`, `@PathVariable`, `@ResponseBody`, status HTTP)
- **Aula 4:** Templates Dinâmicos com FreeMarker (`.ftlh`, diretivas `<#list>`, `<#if>`, includes)
- **Aula 5:** Formulários, Validação, Formulário Grudento e Padrão PRG (Post-Redirect-Get)
- **Aula 6:** Persistência de Dados com Docker, MySQL e Versionamento com Flyway
- **Aula 7:** Consultas com Spring JDBC e `JdbcTemplate` (RowMapper, queries parametrizadas)
- **Aula 8:** Controle de Versão com Git e GitHub
- **Aula 9:** Fluxo Completo de Inserção de Dados (Repository, Controller, View)
