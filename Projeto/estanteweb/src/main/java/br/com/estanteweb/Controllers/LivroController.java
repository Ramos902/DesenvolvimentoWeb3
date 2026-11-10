package br.com.estanteweb.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.estanteweb.LivroRepository;
import br.com.estanteweb.Models.Livro;

@Controller
public class LivroController {

    // Missão 1.2: Injeção de dependência via Spring
    @Autowired
    private LivroRepository repositorio;

    @GetMapping({"/livros", "/livros/html"})
    public String listar(Model container){
        container.addAttribute("livros", repositorio.listarTodos());
        return "livros";
    }

    /*
     * Resposta Missão 1:
     * O formulário enviou um método HTTP POST, enquanto a rota existente de listagem está mapeada
     * exclusivamente para requisições do tipo GET (@GetMapping). No Spring MVC, o método HTTP faz parte
     * do roteamento da requisição.
     */
    @GetMapping("/livros/novo")
    public String novoLivro() {
        return "livro-novo";
    }

    /*
     * Resposta Missão 3:
     * A validação deve acontecer antes de salvar para evitar que dados inconsistentes sejam gravados
     * permanentemente no banco. Dados inválidos que chegassem ao banco violariam restrições (como NOT NULL)
     * causando erro 500 ou poluiriam a base de dados com informações incorretas (como valores negativos).
     */
    @PostMapping("/livros")
    public String salvarLivro(
            @RequestParam String titulo,
            @RequestParam String autor,
            @RequestParam int ano,
            @RequestParam double preco,
            Model model) {

        // Missão 3.1: Validação antes de gravar
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

        // Missão 2.2: Montar o objeto e gravar no banco
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
    public String total(){
        return "O Acervo tem " + repositorio.contarLivros() + " Livros";
    }

    @GetMapping("/livros/busca")
    @ResponseBody
    public ResponseEntity<String> listarAtePreco(@RequestParam(defaultValue = "1000000") double precoMaximo){
        List<Livro> filtrados = repositorio.listarAtePreco(precoMaximo);
        
        return ResponseEntity.ok()
                .header("X-Total-Encontrados", String.valueOf(filtrados.size()))
                .body(filtrados.toString());
    }

    // Missão 4.4 Desafio (opcional)
    @GetMapping("/livros/valor-total")
    @ResponseBody
    public String valorTotal(){
        return "Valor total do acervo: R$ " + repositorio.somarValorAcervo();
    }

    @GetMapping("/livros/quem-acessa")
    @ResponseBody
    public String quemAcessa(@RequestHeader("User-Agent") String userAgent){
        return userAgent;
    }
}
