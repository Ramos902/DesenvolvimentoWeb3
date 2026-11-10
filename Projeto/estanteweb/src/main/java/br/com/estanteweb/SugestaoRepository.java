package br.com.estanteweb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.estanteweb.Models.Sugestao;

/*
 * Resposta Missão 4 (Desafio):
 * Na Semana 3, as sugestões eram armazenadas em uma lista em memória volátil (heap da JVM) e eram perdidas
 * quando a aplicação reiniciava. Com o banco de dados MySQL e o JdbcTemplate, os dados são persistidos
 * de forma durável em disco na tabela sugestao, sobrevivendo a reinicializações da aplicação.
 */
@Repository
public class SugestaoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Missão 4.2: Gravar sugestão no banco de dados
    public void salvar(Sugestao sugestao) {
        String sql = "INSERT INTO sugestao (nome_cliente, livro_sugerido) VALUES (?, ?)";
        jdbcTemplate.update(sql, sugestao.getNomeCliente(), sugestao.getLivroSugerido());
    }

    // Missão 4.4 (Desafio): Listagem de sugestões lendo diretamente do banco
    public List<Sugestao> listarTodas() {
        String sql = "SELECT * FROM sugestao";
        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql);
        List<Sugestao> sugestoes = new ArrayList<>();
        for (Map<String, Object> linha : linhas) {
            sugestoes.add(Sugestao.deMap(linha));
        }
        return sugestoes;
    }
}
