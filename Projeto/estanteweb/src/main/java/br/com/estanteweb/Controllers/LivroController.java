package br.com.estanteweb.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import br.com.estanteweb.LivroRepository;
import br.com.estanteweb.Models.Livro;

@Controller
public class LivroController {

    @Autowired
    private LivroRepository repositorio;

    /*
     * Missão 4.1 (Navegação entre Recursos):
     * Permite ordenar a listagem via query string (?ordem=preco, ?ordem=titulo, etc.).
     * Se o parâmetro não for informado, exibe na ordem padrão.
     *
     * Reflexão Missão 4:
     * O valor da ordenação foi para a query string (?ordem=...) porque ele apenas ajusta
     * a forma de representação e exibição de um mesmo recurso (a coleção de livros).
     * Já o identificador do livro foi para o caminho (/livros/{id}) na Missão 1 porque ele identifica
     * um recurso único e específico dentro da hierarquia da aplicação.
     */
    @GetMapping({"/livros", "/livros/html"})
    public String listar(@RequestParam(required = false) String ordem, Model container) {
        if (ordem != null && !ordem.isBlank()) {
            container.addAttribute("livros", repositorio.listarOrdenadoPor(ordem));
        } else {
            container.addAttribute("livros", repositorio.listarTodos());
        }
        return "livros";
    }

    /*
     * Missões 1.3 e 2.2 (Navegação entre Recursos):
     * Ficha individual de um livro identificada pelo seu id no caminho da URL.
     * Se o livro não for encontrado, lança ResponseStatusException com status HTTP 404.
     *
     * Reflexão Missão 1:
     * O ID foi colocado no caminho da URL (/livros/{id}) porque identifica univocamente um recurso
     * na hierarquia da aplicação. Para o cliente da loja, a grande vantagem é ter uma URL canônica,
     * amigável e permanente, fácil de compartilhar com amigos, salvar nos favoritos e com melhor indexação (SEO).
     *
     * Reflexão Missão 2:
     * - /livros/999 devolve 404 (Not Found): a requisição é sintaticamente correta, mas o recurso não existe no banco.
     * - /livros/abc devolve 400 (Bad Request): a requisição é malformada sintaticamente (falha de conversão String->Long)
     *   e o erro ocorre antes mesmo de o corpo do método executar (durante o data binding do Spring).
     * Não faria sentido usar o mesmo código pois 400 indica erro na sintaxe do pedido, enquanto 404 indica
     * que o pedido está correto, mas o registro de negócio inexiste.
     */
    @GetMapping("/livros/{id}")
    public String exibirLivro(@PathVariable Long id, Model model) {
        Livro livro = repositorio.buscarPorId(id);
        if (livro == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado");
        }
        model.addAttribute("livro", livro);
        // Missão 4.4 Desafio: outros títulos do mesmo autor
        model.addAttribute("outrosLivros", repositorio.listarDoMesmoAutor(livro.getAutor(), id));
        return "livro";
    }

    @GetMapping("/livros/novo")
    public String novoLivro() {
        return "livro-novo";
    }

    @PostMapping("/livros")
    public String salvarLivro(
            @RequestParam String titulo,
            @RequestParam String autor,
            @RequestParam int ano,
            @RequestParam double preco,
            Model model) {

        if (titulo.isBlank() || autor.isBlank() || preco <= 0) {
            if (titulo.isBlank()) {
                model.addAttribute("erro", "O título não pode estar em branco.");
            } else if (autor.isBlank()) {
                model.addAttribute("erro", "O autor não pode estar em branco.");
            } else {
                model.addAttribute("erro", "O preço deve ser maior que zero.");
            }
            model.addAttribute("titulo", titulo);
            model.addAttribute("autor", autor);
            model.addAttribute("ano", ano);
            model.addAttribute("preco", preco);
            return "livro-novo";
        }

        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAno(ano);
        livro.setPreco(preco);

        repositorio.salvar(livro);

        return "redirect:/livros/html";
    }

    @GetMapping("/livros/total")
    @ResponseBody
    public String total() {
        return "O Acervo tem " + repositorio.contarLivros() + " Livros";
    }

    @GetMapping("/livros/busca")
    @ResponseBody
    public ResponseEntity<String> listarAtePreco(@RequestParam(defaultValue = "1000000") double precoMaximo) {
        List<Livro> filtrados = repositorio.listarAtePreco(precoMaximo);
        
        return ResponseEntity.ok()
                .header("X-Total-Encontrados", String.valueOf(filtrados.size()))
                .body(filtrados.toString());
    }

    @GetMapping("/livros/valor-total")
    @ResponseBody
    public String valorTotal() {
        return "Valor total do acervo: R$ " + repositorio.somarValorAcervo();
    }

    @GetMapping("/livros/quem-acessa")
    @ResponseBody
    public String quemAcessa(@RequestHeader("User-Agent") String userAgent) {
        return userAgent;
    }
}
