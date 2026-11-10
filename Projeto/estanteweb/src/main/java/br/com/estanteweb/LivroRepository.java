package br.com.estanteweb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.estanteweb.Models.Livro;

/*
 * Resposta Missão 1 (IoC/DI):
 * Quem cria o LivroRepository e o LivroController agora é o próprio container IoC do Spring Boot,
 * através da injeção de dependências (@Repository, @Controller, @Autowired), em vez de instanciarmos manualmente com "new".
 * Percebemos isso rodando a aplicação pois ela funciona e atende requisições normalmente sem termos chamado "new" manualmente.
 */
@Repository
public class LivroRepository {

    @Autowired 
    private JdbcTemplate jdbcTemplate;

    /*
     * Resposta Missão 2 (Persistência):
     * O número mudar para 7 sem reiniciar a aplicação mostra que o dado agora é consultado
     * em tempo real diretamente do banco de dados relacional (MySQL), e não de uma lista em memória na JVM.
     */
    public int contarLivros() {
        String sql = "SELECT COUNT(*) FROM livro";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /*
     * Resposta Missão 3 (Separação de Responsabilidades):
     * O princípio da Separação de Responsabilidades / Encapsulamento (arquitetura em camadas MVC).
     * O Controller e a View dependem unicamente da assinatura e contrato público do Repository (List<Livro>),
     * tornando a mudança da fonte de dados (memória para banco) completamente transparente para eles.
     */
    public List<Livro> listarTodos() {
        String sql = "SELECT * FROM livro";
        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql);
        List<Livro> livros = new ArrayList<>();
        for (Map<String, Object> linha : linhas) {
            livros.add(Livro.deMap(linha));
        }
        return livros;
    }

    /*
     * Missão 1.1 (Navegação entre Recursos):
     * Busca um livro pelo seu identificador único. Retorna null se não encontrar nenhum registro.
     */
    public Livro buscarPorId(Long id) {
        String sql = "SELECT * FROM livro WHERE id = ?";
        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql, id);
        if (linhas.isEmpty()) {
            return null;
        }
        return Livro.deMap(linhas.get(0));
    }

    /*
     * Missão 4.2 (Navegação entre Recursos):
     * Lista livros ordenados por uma coluna específica (whitelist para prevenir SQL Injection).
     * Se a coluna não for permitida ou for nula, recai sobre a listagem padrão.
     */
    public List<Livro> listarOrdenadoPor(String coluna) {
        if (coluna == null) {
            return listarTodos();
        }
        String colunaLower = coluna.trim().toLowerCase();
        if (!List.of("titulo", "autor", "ano", "preco").contains(colunaLower)) {
            return listarTodos();
        }
        String sql = "SELECT * FROM livro ORDER BY " + colunaLower;
        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql);
        List<Livro> livros = new ArrayList<>();
        for (Map<String, Object> linha : linhas) {
            livros.add(Livro.deMap(linha));
        }
        return livros;
    }

    /*
     * Missão 4.4 Desafio (Navegação entre Recursos):
     * Retorna outros livros do mesmo autor, excluindo o livro atual que já está sendo exibido na ficha.
     */
    public List<Livro> listarDoMesmoAutor(String autor, Long idAtual) {
        String sql = "SELECT * FROM livro WHERE autor = ? AND id <> ?";
        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql, autor, idAtual);
        List<Livro> livros = new ArrayList<>();
        for (Map<String, Object> linha : linhas) {
            livros.add(Livro.deMap(linha));
        }
        return livros;
    }

    /*
     * Resposta Missão 4 (Prepared Statement):
     * O uso do "WHERE preco <= ?" com parâmetro posicional (PreparedStatement) previne ataques de
     * SQL Injection, garantindo que o parâmetro seja tratado estritamente como dado/valor literal e não
     * como comando SQL executável, além de delegar a correta formatação dos tipos de dados.
     */
    public List<Livro> listarAtePreco(double precoMaximo) {
        String sql = "SELECT * FROM livro WHERE preco <= ?";
        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql, precoMaximo);
        List<Livro> livros = new ArrayList<>();
        for (Map<String, Object> linha : linhas) {
            livros.add(Livro.deMap(linha));
        }
        return livros;
    }

    // Desafio: somar valor total do acervo
    public Double somarValorAcervo() {
        String sql = "SELECT SUM(preco) FROM livro";
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    /*
     * Resposta Missão 2 (Insert):
     * Quem atribuiu o valor do id foi o próprio banco de dados MySQL, com base na definição
     * AUTO_INCREMENT declarada na coluna id (chave primária) na criação da tabela.
     */
    public void salvar(Livro livro) {
        String sql = "INSERT INTO livro (titulo, autor, ano, preco) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, livro.getTitulo(), livro.getAutor(), livro.getAno(), livro.getPreco());
    }
}
