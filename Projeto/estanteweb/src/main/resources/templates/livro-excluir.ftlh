<#include "cabecalho.ftlh">

<h2>Remover Livro</h2>

<p>Tem certeza que deseja remover o livro abaixo do acervo?</p>

<p><strong>Título:</strong> ${livro.titulo}</p>
<p><strong>Autor:</strong> ${livro.autor}</p>
<p><strong>Ano:</strong> ${livro.ano?c}</p>
<p><strong>Preço:</strong> R$ ${livro.preco?string["0.00"]}</p>

<form action="/livros/excluir/${livro.id?c}" method="post">
    <input type="submit" value="Sim, confirmar remoção">
    <a href="/livros/${livro.id?c}">Desistir e voltar</a>
</form>
