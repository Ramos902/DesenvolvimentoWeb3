# 📚 Programação para Web 3 (PRW3) & Git / GitHub (LSPW)

> **Documento de Registro e Base de Conhecimento das Aulas**  
> Material compilado a partir das aulas e slides do **Prof. Me. Danilo Bueno**.  
> Estruturado detalhadamente com conceitos teóricos, códigos práticos, diagramas de fluxo e pontos de revisão para facilitar a geração de resumos, estudos pré-prova e consultas rápidas.

---

## 🗺️ Mapa da Trilha de Aprendizagem

O curso desenvolve uma aplicação web do zero ao banco de dados com arquitetura profissional em Spring Boot:

```
[Aula 1: Fundamentos HTTP] ──▶ [Aula 2: MVC & Spring Boot] ──▶ [Aula 3: Comunicação HTTP & Rotas]
                                                                           │
                                                                           ▼
[Aula 6: Persistência & Flyway] ◀── [Aula 5: Formulários & PRG] ◀── [Aula 4: Templates FreeMarker]
             │
             ▼
[Aula 7: Consultas com JdbcTemplate] ──▶ [Aula 8: Git & GitHub] ──▶ [Aula 9: Fluxo Completo de Cadastro]
```

---

## 📑 Sumário

1. [Aula 1: Como a Web Funciona (Web e HTTP)](#aula-1-como-a-web-funciona-web-e-http)
2. [Aula 2: Organização de Aplicações Web (MVC e Spring Boot)](#aula-2-organização-de-aplicações-web-mvc-e-spring-boot)
3. [Aula 3: Comunicação HTTP (Métodos, Parâmetros e Status)](#aula-3-comunicação-http-métodos-parâmetros-e-status)
4. [Aula 4: Templates Dinâmicos com FreeMarker](#aula-4-templates-dinâmicos-com-freemarker)
5. [Aula 5: Formulários e Fluxo de Navegação (Formulário Grudento e PRG)](#aula-5-formulários-e-fluxo-de-navegação)
6. [Aula 6: Persistência de Dados (Docker, MySQL e Migrações com Flyway)](#aula-6-persistência-de-dados-docker-mysql-e-flyway)
7. [Aula 7: Consulta de Dados com Spring JDBC e JdbcTemplate](#aula-7-consulta-de-dados-com-spring-jdbc-e-jdbctemplate)
8. [Aula 8: Controle de Versão com Git e GitHub](#aula-8-controle-de-versão-com-git-e-github)
9. [Aula 9: Cadastro de Informações (Fluxo Completo de Inserção)](#aula-9-cadastro-de-informações-fluxo-completo-de-inserção)
10. [🎯 Tabela Resumo: Principais Anotações, Diretivas e Comandos](#-tabela-resumo-principais-anotações-diretivas-e-comandos)

---

## Aula 1: Como a Web Funciona (Web e HTTP)
🔗 **Slides:** [https://www.buenodc.com/class/PRW3/web-http/slides](https://www.buenodc.com/class/PRW3/web-http/slides)

### 1. O Modelo Cliente-Servidor
* **Definição de Papéis:**
  * **Cliente (User Agent):** É quem toma a iniciativa e faz requisições (ex.: navegador web, cURL, Postman, app mobile).
  * **Servidor:** Permanece continuamente em execução, escutando uma porta de rede e aguardando requisições para processá-las e devolver respostas.
  * *Atenção:* "Cliente" e "servidor" são papéis de software, não máquinas físicas fixas — um mesmo computador pode atuar como ambos (ex.: `localhost`).
* **Ciclo Requisição–Resposta:**
  ```
  [ Navegador (Cliente) ] ───── Requisição HTTP ─────▶ [ Servidor Web ]
                          ◀──── Resposta HTTP ────────
  ```
* **Características Fundamentais:**
  * O cliente **sempre** inicia a conversa. O servidor nunca envia nada sem ter sido requisitado.
  * Para **cada** requisição existe **exatamente uma** resposta.
  * O protocolo é **Stateless (Sem Estado):** cada par requisição/resposta é independente; o servidor não memoriza transações anteriores por padrão.

### 2. O Protocolo HTTP e HTTPS
* **HTTP (*HyperText Transfer Protocol*):** Protocolo da camada de aplicação baseado em texto que padroniza o formato das mensagens enviadas e recebidas.
* **HTTPS:** É o HTTP operando sobre uma camada de criptografia segura (**TLS/SSL**), garantindo confidencialidade e integridade contra interceptações.

### 3. Anatomia de uma Mensagem HTTP
* **Requisição HTTP:**
  ```http
  GET /noticias/hoje?categoria=tecnologia HTTP/1.1
  Host: www.exemplo.com
  User-Agent: Mozilla/5.0
  Accept: text/html
  ```
  * **Linha de Requisição:** Método (`GET`) + Caminho/Recurso (`/noticias/hoje`) + Versão (`HTTP/1.1`).
  * **Cabeçalhos (Headers):** Metadados chave-valor (`Host`, `User-Agent`, `Accept`).
  * **Linha em branco:** Separa obrigatoriamente os cabeçalhos do corpo.
  * **Corpo (Body):** Opcional (presente em POST, PUT; vazio em GET).
* **Resposta HTTP:**
  ```http
  HTTP/1.1 200 OK
  Content-Type: text/html; charset=UTF-8
  Content-Length: 1256

  <!DOCTYPE html>
  <html>...</html>
  ```
  * **Linha de Status:** Versão (`HTTP/1.1`) + Código de Status (`200`) + Texto de Justificativa (`OK`).
  * **Cabeçalhos:** `Content-Type`, `Content-Length`, `Date`, etc.
  * **Corpo (Payload):** O conteúdo do recurso retornado (HTML, JSON, imagem, etc.).

### 4. Anatomia de uma URL
```
https://www.exemplo.com:8080/produtos/detalhes?codigo=42#especificacoes
│───┘   │──────────────│└──┘│────────────────│└─────────┘└─────────────┘
Esquema     Host/Domínio Porta     Caminho    Query String    Fragmento
```
* **Query String:** Parâmetros iniciados por `?` e separados por `&` (`chave=valor`).

### 5. Classes de Códigos de Status HTTP
| Faixa | Categoria | Descrição e Exemplos Mais Comuns |
| :--- | :--- | :--- |
| **1xx** | Informacional | Requisição recebida, processo continuando (ex.: `100 Continue`). |
| **2xx** | Sucesso | Ação recebida, compreendida e aceita com êxito (ex.: `200 OK`, `201 Created`). |
| **3xx** | Redirecionamento | Ação adicional necessária para concluir (ex.: `301 Moved Permanently`, `302 Found`). |
| **4xx** | Erro do Cliente | Falha na requisição feita pelo cliente (ex.: `400 Bad Request`, `404 Not Found`, `405 Method Not Allowed`). |
| **5xx** | Erro do Servidor | O servidor falhou ao atender uma requisição válida (ex.: `500 Internal Server Error`). |

### 6. DevTools (Ferramentas do Desenvolvedor)
* A aba **Network (Rede)** no navegador (atalho `F12`):
  * Monitora todas as requisições disparadas pela página.
  * Permite inspecionar headers, payloads enviados, resposta recebida e tempo de carregamento.
  * Opção **Preserve log**: mantém o histórico mesmo após redirecionamentos de página.

---

## Aula 2: Organização de Aplicações Web (MVC e Spring Boot)
🔗 **Slides:** [https://www.buenodc.com/class/PRW3/mvc/slides](https://www.buenodc.com/class/PRW3/mvc/slides)

### 1. O Problema das Responsabilidades Misturadas
Sem organização, uma única função no servidor precisa:
1. Analisar a rota e o verbo HTTP.
2. Ler e converter parâmetros.
3. Consultar dados e aplicar regras de negócio.
4. Montar a string HTML de saída.
* **Consequência:** Código acoplado, ilegível, difícil de testar e quase impossível de manter.

### 2. O Padrão Arquitetural MVC
Separa a aplicação em 3 camadas com responsabilidades bem definidas:
* **Model (Modelo):** Os dados e as regras de negócio da aplicação. É a "cozinha" do sistema. Não sabe nada sobre HTTP nem sobre HTML.
* **View (Visão):** A interface apresentada ao usuário (HTML, templates). É o "salão / prato montado".
* **Controller (Controlador):** O orquestrador. É o "garçom": recebe o pedido (requisição HTTP), pede o prato à cozinha (Model) e entrega o resultado na mesa do cliente (View).

```
[ Navegador ] ─── 1. Requisição ───▶ [ Controller ] ─── 2. Consulta/Ação ───▶ [ Model ]
      ▲                                     │                                      │
      │                                4. Preenche                          3. Retorna Dados
      │                                     │                                      │
      │                                     ▼                                      ▼
      └───────── 5. HTML Final ◀───── [ View (Template) ] ◀────────────────────────┘
```

### 3. Spring Boot e o Spring Initializr
* **O que é um Framework:** Estrutura reutilizável pronta que resolve tarefas repetitivas de infraestrutura para que o desenvolvedor foque apenas nas regras do negócio.
* **Spring Boot:** Facilita o desenvolvimento Spring configurando servidores embutidos (Tomcat) e dependências automáticas (*starters*).
* **Spring Initializr (`start.spring.io`):** Gerador inicial de projetos configurando Maven/Gradle, dependências e versões.
* **Estrutura Básica de um Projeto:**
  * `pom.xml`: Gerenciador de dependências e plugins Maven.
  * `src/main/java/`: Código-fonte Java (controllers, models, repositories).
  * `src/main/resources/application.properties`: Configurações da aplicação.
  * `src/main/resources/templates/`: Arquivos de template de visualização.

### 4. Criando um Controller Básico
* Anotação `@RestController`: Marca a classe como um controlador onde o retorno dos métodos é enviado diretamente no corpo da resposta HTTP (em texto puro ou JSON).
* Anotação `@GetMapping("/caminho")`: Associa requisições HTTP GET na rota indicada ao método.
* **Whitelabel Error Page (404):** Quando o Spring Boot está rodando normalmente, mas nenhuma rota mapeada corresponde à URL digitada.

```java
@RestController
public class CardapioController {

    @GetMapping("/")
    public String inicio() {
        return "Bem-vindo à Cafeteria Central!";
    }

    @GetMapping("/cardapio")
    public String cardapio() {
        return "Espresso - R$ 6 | Coado - R$ 5";
    }
}
```

### 5. Separando Model e Repositório
* **Classe de Modelo (POJO):** Representa o dado puro.
  ```java
  public class Bebida {
      private String nome;
      private double preco;

      public Bebida(String nome, double preco) {
          this.nome = nome;
          this.preco = preco;
      }
      public String getNome() { return nome; }
      public double getPreco() { return preco; }
  }
  ```
* **Classe de Repositório (Repository):** Responsável por fornecer e gerenciar a coleção de dados.
  ```java
  public class BebidaRepository {
      private List<Bebida> bebidas = List.of(
          new Bebida("Espresso", 6.0),
          new Bebida("Coado", 5.0)
      );

      public List<Bebida> listarTodas() {
          return bebidas;
      }
  }
  ```

---

## Aula 3: Comunicação HTTP (Métodos, Parâmetros e Status)
🔗 **Slides:** [https://www.buenodc.com/class/PRW3/comunicacao-http/slides](https://www.buenodc.com/class/PRW3/comunicacao-http/slides)

### 1. Semântica dos Métodos: GET versus POST
* **GET:** Expressa intenção de **leitura**.
  * **Seguro:** Não deve alterar o estado do sistema no servidor.
  * **Idempotente:** Fazer a mesma requisição 1 ou 100 vezes produz o mesmo resultado.
  * **Cache e Prefetch:** Navegadores e robôs de busca assumem que GET é inofensivo e podem recarregar ou antecipar o acesso livremente.
* **POST:** Expressa intenção de **execução de ação ou escrita**.
  * **Não é seguro nem idempotente:** Repetir um POST pode duplicar um pedido ou cadastro.
  * Por isso, o navegador exibe o alerta: *"Deseja reenviar o formulário? (Confirm Form Resubmission)"* ao pressionar F5 em um POST.

### 2. Rotas e Verbos no Spring Boot
* `@GetMapping("/rota")`: Responde a requisições GET.
* `@PostMapping("/rota")`: Responde a requisições POST.
* **Erro 405 (Method Not Allowed):** Ocorre quando uma URL existe, mas foi requisitada com um verbo HTTP não mapeado (ex.: tentar acessar com GET uma rota mapeada apenas com `@PostMapping`).

### 3. Recebendo Parâmetros com `@RequestParam`
* Os parâmetros podem viajar na URL (**Query String**) ou no corpo de um envio de formulário.
* Anotação `@RequestParam`: Vincula os campos recebidos aos parâmetros do método Java.
  ```java
  @GetMapping("/buscar")
  public String buscar(
      @RequestParam(name = "termo", required = false, defaultValue = "") String termo,
      @RequestParam(name = "limite", defaultValue = "10") int limite
  ) {
      return "Buscando por: " + termo + " (limite: " + limite + ")";
  }
  ```

### 4. Lendo Cabeçalhos e Controlando Códigos de Status
* Inspecionando cabeçalhos recebidos com `@RequestHeader`:
  ```java
  @GetMapping("/agente")
  public String agente(@RequestHeader("User-Agent") String navegador) {
      return "Seu navegador é: " + navegador;
  }
  ```
* Definindo o status HTTP da resposta (usando `HttpServletResponse` ou `ResponseEntity`):
  ```java
  @GetMapping("/verificar")
  public String verificar(@RequestParam int idade, HttpServletResponse response) {
      if (idade < 18) {
          response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
          return "Acesso restrito para menores.";
      }
      return "Acesso liberado!";
  }
  ```

### 5. Formulários HTML e o Envio de Dados
* Tag `<form action="/url-destino" method="POST">`:
  * O atributo `name` de cada `<input>` define a chave enviada na requisição.
  * Se `method="GET"`, os valores vão codificados na URL: `/destino?campo1=valor1&campo2=valor2`.
  * Se `method="POST"`, os dados vão empacotados no corpo com `Content-Type: application/x-www-form-urlencoded`.

---

## Aula 4: Templates Dinâmicos com FreeMarker
🔗 **Slides:** [https://www.buenodc.com/class/PRW3/templates/slides](https://www.buenodc.com/class/PRW3/templates/slides)

### 1. O Problema da Concatenação Manual de HTML
Montar HTML concatenando Strings no Java gera:
* Falta de destaque de sintaxe no editor.
* Dificuldade de identificar tags não fechadas (erros só estouram em tempo de execução).
* Mistura indevida de apresentação visual com lógica de negócio.

### 2. Motores de Template (*Template Engines*)
* Um motor de template combina um arquivo base (estrutura fixa com marcadores) com dados dinâmicos vindos da aplicação, gerando o documento final (HTML).
* **Duas Filosofias:**
  * *Logic-less* (sem lógica): Substitui apenas chaves fixas (ex.: Mustache).
  * *Com diretivas*: Permite estruturas de controle (laços, decisões) direto no template (ex.: FreeMarker, Thymeleaf, JSP).
* **Por que FreeMarker (`.ftlh`) no curso:** Possui sintaxe com diretivas explícitas (`<#list>`, `<#if>`, `${var}`), tornando clara a fronteira entre o que é HTML padrão e o que é controle do template.

### 3. Configuração e a Diferença Crucial entre Controllers
* Dependência no `pom.xml`:
  ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-freemarker</artifactId>
  </dependency>
  ```
* Os arquivos ficam localizados em: `src/main/resources/templates/*.ftlh`.
* **A Grande Diferença:**
  * `@RestController`: O que o método retorna vai **direto para o corpo da resposta HTTP** (texto/JSON).
  * `@Controller`: O método retorna uma `String` com o **nome do template** a ser processado (sem a extensão `.ftlh`).

```java
@Controller
public class CardapioController {

    private BebidaRepository repositorio = new BebidaRepository();

    @GetMapping("/cardapio")
    public String exibirCardapio(Model model) {
        // Envia dados para o template através do objeto Model
        model.addAttribute("titulo", "Cardápio Especial");
        model.addAttribute("bebidas", repositorio.listarTodas());
        return "cardapio"; // busca templates/cardapio.ftlh
    }
}
```

### 4. Principais Recursos e Diretivas do FreeMarker (FTL)
* **Impressão de Variáveis:** `${titulo}` ou `${bebida.nome}`.
* **Comentários:** `<#-- Este comentário não aparece no HTML final gerado -->`.
* **Iteração com `<#list>`:**
  ```html
  <ul>
    <#list bebidas as b>
      <li>${b.nome} - R$ ${b.preco?string["0.00"]}</li>
    </#list>
  </ul>
  ```
* **Condicionais com `<#if>`:**
  ```html
  <#if bebidas?has_content>
    <p>Temos ${bebidas?size} opções disponíveis.</p>
  <#else>
    <p>Nenhuma bebida cadastrada no momento.</p>
  </#if>
  ```
* **Formatação Numérica:**
  * `${preco?string["0.00"]}`: Garante 2 casas decimais.
  * `${preco?string.currency}`: Formata na moeda configurada.
* **Reaproveitamento com `<#include>`:**
  ```html
  <#include "cabecalho.ftlh">
  <main>Conteúdo da página</main>
  <#include "rodape.ftlh">
  ```

---

## Aula 5: Formulários e Fluxo de Navegação
🔗 **Slides:** [https://www.buenodc.com/class/PRW3/formularios/slides](https://www.buenodc.com/class/PRW3/formularios/slides)

### 1. Problemas Comuns no Envio de Formulários
* **Confirmação Solta:** Devolver apenas uma mensagem crua (`"Reserva realizada com sucesso"`) deixa o usuário perdido sem menu nem navegação.
* **Perda de Dados em Erros:** Se o usuário errar um campo e a página for recarregada em branco, ele perde tudo o que já havia preenchido.

### 2. O Padrão "Formulário Grudento" (*Sticky Form*)
* Se a validação falhar, o controller reexibe o **mesmo template do formulário**, repassando via `Model` os dados que o usuário já havia digitado, junto com a mensagem de erro.
* Assim, o usuário apenas corrige o campo errado sem precisar redigitar tudo.
* **Validação no Cliente vs Servidor:**
  * Validação no cliente (`required`, `type="email"`, `min="1"`): Melhora a experiência do usuário.
  * Validação no servidor (Java): **Indispensável para a segurança**, pois qualquer pessoa pode burlar o navegador via DevTools ou cURL.

### 3. Tratamento de Variáveis Ausentes no FreeMarker
No FreeMarker, referenciar uma variável que não existe ou é nula lança um erro e aborta a renderização. Para contornar isso:
* **Operador `!` (Valor Padrão):**
  * `<input name="nome" value="${nome!}">` *(se `nome` for nulo, imprime vazio em vez de estourar erro)*.
  * `${(cliente.cidade)!"Não informada"}`.
* **Operador `??` (Teste de Existência Booleano):**
  * Retorna `true` se a variável existir e não for nula.
  ```html
  <#if erro??>
    <div class="alerta-erro">${erro}</div>
  </#if>
  ```

### 4. O Padrão PRG (*Post / Redirect / Get*)
* **O Problema do F5:** Quando o usuário envia um formulário via POST e o servidor responde direto com o HTML final (status 200), qualquer atualização (F5) reexecuta o POST, gerando pagamentos ou reservas duplicadas.
* **A Solução (PRG):**
  1. O cliente envia um **POST** com os dados.
  2. O servidor processa e grava os dados.
  3. O servidor responde com **HTTP 302 (Redirect)** apontando para uma URL de consulta (ex.: `/reserva/sucesso`).
  4. O navegador automaticamente realiza uma requisição **GET** limpa para a página de sucesso.

```
Navegador                       Servidor
   │                               │
   ├────── 1. POST /reserva ──────▶│ (Processa e grava)
   │                               │
   │◀───── 2. HTTP 302 Redirect ───┤ (Location: /reserva/sucesso)
   │                               │
   ├────── 3. GET /reserva/sucesso▶│
   │                               │
   │◀───── 4. 200 OK (HTML) ───────┤ (Exibe página de confirmação)
```

* **No Spring Boot:** O redirecionamento é feito retornando a string com prefixo `redirect:`:
  ```java
  @PostMapping("/reserva")
  public String processarReserva(
      Model model,
      @RequestParam String nome,
      @RequestParam String telefone
  ) {
      if (telefone.isBlank()) {
          // Validação falhou: reapresenta o formulário grudento
          model.addAttribute("erro", "Informe um telefone válido.");
          model.addAttribute("nome", nome);
          return "reserva-form";
      }

      // Sucesso: redireciona para a rota GET
      return "redirect:/reserva/sucesso";
  }
  ```

---

## Aula 6: Persistência de Dados (Docker, MySQL e Flyway)
🔗 **Slides:** [https://www.buenodc.com/class/PRW3/persistencia/slides](https://www.buenodc.com/class/PRW3/persistencia/slides)

### 1. Por que Precisamos de Persistência?
* Variáveis e listas em memória Java (`List.of(...)`, `ArrayList`) residem apenas na memória RAM do processo da JVM.
* Ao reiniciar a aplicação, atualizar o sistema (*deploy*) ou faltar energia, a memória é liberada e todos os dados são perdidos.
* **Solução:** Um processo separado (o **SGBD**) que grava os dados em disco de forma estruturada e permanente.

### 2. Subindo o Banco MySQL com Docker
O MySQL roda de forma isolada em um container Docker:
```bash
docker run --name cafeteria-mysql \
  -e MYSQL_ROOT_PASSWORD=cafeteria \
  -e MYSQL_DATABASE=cafeteria \
  -p 3306:3306 \
  -d mysql:8
```
* **Acessando o terminal do MySQL no container:**
  ```bash
  docker exec -it cafeteria-mysql mysql -uroot -pcafeteria cafeteria
  ```

### 3. Configurando o Spring Boot (`application.properties`)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cafeteria
spring.datasource.username=root
spring.datasource.password=cafeteria
```

### 4. Controle de Versão do Banco com Flyway
* **O Problema:** Como garantir que o banco de dados de desenvolvimento, testes e produção tenham exatamente a mesma estrutura de tabelas sem depender de comandos SQL manuais?
* **O que é o Flyway:** Ferramenta de migração de banco de dados que executa scripts SQL versionados automaticamente ao iniciar a aplicação.
* **Dependências (`pom.xml`):**
  ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-flyway</artifactId>
  </dependency>
  <dependency>
      <groupId>org.flywaydb</groupId>
      <artifactId>flyway-mysql</artifactId>
  </dependency>
  ```
* **Convenção de Nomenclatura dos Scripts:**  
  Devem ficar em: `src/main/resources/db/migration/`  
  Formato: `V{versao}__{descricao}.sql` *(atenção aos **dois underscores**)*
  * Exemplo: `V1__criar_tabela_produto.sql`
  ```sql
  CREATE TABLE produto (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      nome VARCHAR(255) NOT NULL,
      preco DECIMAL(10,2) NOT NULL
  );
  ```
  * Exemplo: `V2__criar_tabela_pedido.sql`

* **A Tabela `flyway_schema_history` e o Checksum:**
  * O Flyway cria uma tabela interna para registrar a data, versão e o **checksum** (hash criptográfico) de cada script executado.
  * **Regra de Ouro:** **Nunca edite um script de migração que já foi executado!** Se o hash do arquivo mudar, o Flyway abortará o início da aplicação para proteger a integridade do banco. Caso precise alterar uma tabela, crie uma **nova versão** (ex.: `V3__adicionar_coluna_categoria.sql`).

---

## Aula 7: Consulta de Dados com Spring JDBC e JdbcTemplate
🔗 **Slides:** [https://www.buenodc.com/class/PRW3/consulta-dados/slides](https://www.buenodc.com/class/PRW3/consulta-dados/slides)

### 1. As Camadas de Acesso a Dados
```
[ Código da Aplicação (Repositório) ]
                 ▼
[ JdbcTemplate (Abstração do Spring) ]
                 ▼
[ JDBC (API padrão Java: Connection, Statement, ResultSet) ]
                 ▼
[ Driver JDBC (mysql-connector-j) ]
                 ▼
[ Banco de Dados MySQL ]
```
* **JDBC puro:** Exige abrir e fechar conexões manualmente, capturar `SQLException` em blocos try/catch/finally e iterar `ResultSet`, sob alto risco de vazamento de conexões (*connection leaks*).
* **JdbcTemplate:** Abstrai todo o boilerplate (abertura, fechamento, gerenciamento de erros) e entrega métodos diretos de consulta e atualização.

### 2. Injetando o `JdbcTemplate` no Repositório
```java
@Repository
public class ProdutoRepository {

    private final JdbcTemplate jdbcTemplate;

    // Injeção de dependência via construtor
    public ProdutoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    ...
}
```

### 3. Executando Consultas SQL
* **Consulta de Valor Único (`queryForObject`):**
  ```java
  public int totalProdutos() {
      String sql = "SELECT COUNT(*) FROM produto";
      return jdbcTemplate.queryForObject(sql, Integer.class);
  }
  ```
* **Consulta de Múltiplas Linhas (`queryForList`):**
  Retorna uma lista de mapas, onde cada mapa representa uma linha (`chave = nome da coluna`, `valor = dado`):
  ```java
  List<Map<String, Object>> linhas = jdbcTemplate.queryForList("SELECT id, nome, preco FROM produto");
  ```
* **Consultas com Filtros e Parâmetros Posicionais (`?`):**
  * **Segurança:** Nunca concatene strings no SQL (`"WHERE preco <= " + preco`) para evitar ataques de **SQL Injection**.
  * Use placeholders `?`: o driver utiliza `PreparedStatement`, tratando os parâmetros com segurança.
  ```java
  String sql = "SELECT id, nome, preco FROM produto WHERE preco <= ?";
  List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql, precoMaximo);
  ```

### 4. Convertendo Resultados para Objetos de Domínio (`deMap`)
* **Tratamento de Tipos Numéricos:** No MySQL, colunas `DECIMAL` são mapeadas como `BigDecimal` e `BIGINT` como `Long`. Para evitar `ClassCastException`, faça o cast intermediário para `Number`:
```java
private Produto deMap(Map<String, Object> linha) {
    Long id = ((Number) linha.get("id")).longValue();
    String nome = (String) linha.get("nome");
    Double preco = ((Number) linha.get("preco")).doubleValue();
    return new Produto(id, nome, preco);
}

public List<Produto> todos() {
    String sql = "SELECT id, nome, preco FROM produto";
    List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql);
    
    List<Produto> produtos = new ArrayList<>();
    for (Map<String, Object> linha : linhas) {
        produtos.add(deMap(linha));
    }
    return produtos;
}
```

### 5. O Ciclo Completo da Consulta
```
Navegador ──▶ Controller ──▶ Repository ──▶ JdbcTemplate ──▶ MySQL
    ▲              │              │                 │          │
    │         Passa Model    Converte objetos  Executa SQL  Retorna Linhas
    │              │              │                 │          │
    └────── View ◀─┴──────────────┴─────────────────┴──────────┘
```

---

## Aula 8: Controle de Versão com Git e GitHub
🔗 **Slides:** [https://www.buenodc.com/class/LSPW/04-git-github](https://www.buenodc.com/class/LSPW/04-git-github)

### 1. Por que Usar Controle de Versão?
* Elimina a bagunça de arquivos duplicados (`projeto_v1`, `projeto_final`, `final_mesmo_agora_vai.zip`).
* Permite que múltiplos desenvolvedores trabalhem simultaneamente no mesmo código sem sobrescrever as alterações uns dos outros.
* Registra o histórico completo de quem fez, quando fez e por que fez cada alteração.

### 2. Git versus GitHub
* **Git:** Software livre de controle de versão distribuído que roda localmente no computador do desenvolvedor.
* **GitHub:** Plataforma em nuvem que hospeda repositórios remotos Git e fornece ferramentas de colaboração (pull requests, issues, code review).

### 3. As Três Áreas Locais do Git
```
[ Working Directory ] ─── git add ───▶ [ Staging Area ] ─── git commit ───▶ [ Repositório Local ]
 (Arquivos em edição)                   (Preparados p/ salvar)                (Histórico gravado)
                                                                                     │
                                                                                 git push
                                                                                     ▼
                                                                            [ GitHub (Remoto) ]
```

### 4. Configuração Inicial do Git (feita uma vez na máquina)
```bash
git config --global user.name "Seu Nome"
git config --global user.email "seu.email@exemplo.com"
```

### 5. Comandos Fundamentais do Dia a Dia
* **`git init`:** Transforma o diretório atual em um repositório Git local.
* **`git status`:** Mostra o estado atual dos arquivos (modificados, não rastreados ou preparados na *staging area*).
* **`git add <arquivo>` ou `git add .`:** Move as alterações do *Working Directory* para a *Staging Area*.
* **`git commit -m "Mensagem clara do que foi feito"`:** Salva uma foto definitiva das alterações no histórico do repositório local.
* **`git log`:** Exibe a linha do tempo dos commits realizados (hash, autor, data e mensagem).

### 6. Conectando com o Repositório Remoto no GitHub
```bash
# 1. Vincula o repositório local ao repositório remoto criado no GitHub
git remote add origin https://github.com/usuario/nome-do-repositorio.git

# 2. Envia os commits locais para o branch main na nuvem
# (-u configura o upstream para que os próximos comandos sejam apenas 'git push')
git push -u origin main
```

### 7. `git init` versus `git clone`
* **`git init`:** Usado ao iniciar um projeto novo a partir do zero na própria máquina.
* **`git clone <url>`:** Usado quando o repositório já existe (no trabalho ou projeto em grupo). Baixa o código completo, histórico de commits e já configura o ponteiro `origin` automaticamente.

---

## Aula 9: Cadastro de Informações (Fluxo Completo de Inserção)
🔗 **Slides:** [https://www.buenodc.com/class/PRW3/cadastro/slides](https://www.buenodc.com/class/PRW3/cadastro/slides)

### 1. O Desafio
Até a Aula 7, a aplicação lia do banco via `SELECT`, mas os dados eram inseridos apenas por scripts de migração do Flyway ou comandos manuais no terminal do MySQL. A Aula 9 fecha o ciclo, permitindo que o usuário final cadastre dados pelo navegador.

### 2. As Quatro Etapas do Cadastro
```
[ 1. GET /produtos/novo ] ──▶ Exibe o Formulário HTML vazio
                                       │
[ 2. POST /produtos     ] ──▶ Recebe os campos e Valida as regras no Servidor
                                       │
[ 3. Repository.salvar  ] ──▶ Executa o INSERT com jdbcTemplate.update(...)
                                       │
[ 4. Redirecionamento   ] ──▶ Responde com redirect:/produtos (Padrão PRG)
```

### 3. Passo a Passo do Código

#### Etapa 1: Rota e Template do Formulário (GET)
* **No Controller:**
  ```java
  @GetMapping("/produtos/novo")
  public String exibirFormulario() {
      return "produto-novo"; // templates/produto-novo.ftlh
  }
  ```
* **No Template (`produto-novo.ftlh`):**
  ```html
  <#if erro??>
    <p style="color: red;">${erro}</p>
  </#if>

  <form method="post" action="/produtos">
    <label>Nome do Produto:</label>
    <input type="text" name="nome" value="${nome!}">

    <label>Preço:</label>
    <input type="text" name="preco" value="${preco!}">

    <button type="submit">Cadastrar Produto</button>
  </form>
  ```

#### Etapa 2: Recebendo, Validando e Executando (POST)
* Validação rigorosa no servidor antes de tocar no banco de dados.
* Se houver dados inválidos, reexibe o formulário grudento sem redirecionar:
  ```java
  @PostMapping("/produtos")
  public String cadastrar(
      Model model,
      @RequestParam(defaultValue = "") String nome,
      @RequestParam(defaultValue = "0") double preco
  ) {
      // 1. Validação
      if (nome.isBlank()) {
          model.addAttribute("erro", "O nome do produto é obrigatório.");
          model.addAttribute("preco", preco);
          return "produto-novo";
      }
      if (preco <= 0) {
          model.addAttribute("erro", "O preço deve ser maior que zero.");
          model.addAttribute("nome", nome);
          return "produto-novo";
      }

      // 2. Persistência
      Produto novoProduto = new Produto(nome, preco);
      repositorio.salvar(novoProduto);

      // 3. Redirecionamento PRG
      return "redirect:/produtos";
  }
  ```

#### Etapa 3: Executando o INSERT no Repositório com `jdbcTemplate.update()`
* Para operações que modificam o banco (`INSERT`, `UPDATE`, `DELETE`), o `JdbcTemplate` disponibiliza o método `update()`, que executa o comando e devolve a quantidade de linhas afetadas:
  ```java
  public void salvar(Produto produto) {
      String sql = "INSERT INTO produto (nome, preco) VALUES (?, ?)";
      jdbcTemplate.update(sql, produto.getNome(), produto.getPreco());
  }
  ```
* **A coluna `id`:** Como o banco utiliza `BIGINT AUTO_INCREMENT PRIMARY KEY`, o MySQL gera automaticamente o identificador único para o registro; não precisamos passá-lo no `INSERT`.

---

## 🎯 Tabela Resumo: Principais Anotações, Diretivas e Comandos

### Anotações Spring Boot
| Anotação | Onde Usar | Finalidade |
| :--- | :--- | :--- |
| `@SpringBootApplication` | Classe Principal | Configuração automática, escaneamento de componentes e inicialização do Spring Boot. |
| `@Controller` | Classe Controller | Trata requisições web e retorna o **nome de um template** (`.ftlh`) a ser renderizado. |
| `@RestController` | Classe Controller | Trata requisições web e devolve os dados **diretamente no corpo da resposta** (texto/JSON). |
| `@GetMapping("/rota")` | Método do Controller | Mapeia requisições HTTP do tipo `GET` na rota especificada. |
| `@PostMapping("/rota")` | Método do Controller | Mapeia requisições HTTP do tipo `POST` na rota especificada. |
| `@RequestParam` | Parâmetro de Método | Captura parâmetros vindos da Query String ou do corpo de um formulário. |
| `@RequestHeader` | Parâmetro de Método | Inspeciona metadados dos cabeçalhos HTTP enviados pelo cliente. |
| `@Repository` | Classe de Dados | Marca a classe como repositório de persistência e tratamento de exceções de banco. |

### Diretivas e Operadores FreeMarker
| Sintaxe | Função | Exemplo |
| :--- | :--- | :--- |
| `${var}` | Interpolação de variável | `<h1>${produto.nome}</h1>` |
| `${var!}` / `${var!"padrão"}` | Tratamento de valor ausente/nulo | `<input value="${nome!}">` |
| `<#if var??>` | Verifica existência da variável | `<#if erro??>${erro}</#if>` |
| `<#list lista as item>` | Laço de repetição | `<#list produtos as p><li>${p.nome}</li></#list>` |
| `<#include "arquivo.ftlh">` | Inclusão de fragmento de template | `<#include "cabecalho.ftlh">` |
| `${num?string["0.00"]}` | Formatação de números decimais | `R$ ${p.preco?string["0.00"]}` |

### Comandos Essenciais do Git
| Comando | Função |
| :--- | :--- |
| `git init` | Inicializa um novo repositório local vazio. |
| `git clone <url>` | Clona um repositório remoto completo para a máquina local. |
| `git status` | Informa os arquivos modificados, staged e untracked. |
| `git add <arquivo>` / `git add .` | Adiciona modificações na *Staging Area*. |
| `git commit -m "msg"` | Grava uma nova versão no histórico do repositório local. |
| `git log` | Mostra a lista dos últimos commits efetuados. |
| `git remote add origin <url>` | Associa o repositório local a um repositório no GitHub. |
| `git push -u origin main` | Envia os commits locais para o servidor remoto no GitHub. |

---

*Documento gerado com base no conteúdo oficial das aulas de PRW3 e LSPW do Prof. Me. Danilo Bueno.*
