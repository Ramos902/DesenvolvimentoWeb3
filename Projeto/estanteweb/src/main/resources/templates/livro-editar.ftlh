<#include "cabecalho.ftlh">

<h1>Editar Livro</h1>

<#if erro??>
    <p><strong>Erro:</strong> ${erro}</p>
</#if>

<#if !livro??>
    <strong>Livro não encontrado!</strong>
<#else>
    <form action="/livros/editar/${livro.id?c}" method="post">
        <label for="titulo">Título:</label>
        <input type="text" id="titulo" name="titulo" value="${(livro.titulo)!}">
        <br>
        <label for="autor">Autor:</label>
        <input type="text" id="autor" name="autor" value="${(livro.autor)!}">
        <br>
        <label for="ano">Ano:</label>
        <input type="number" id="ano" name="ano" value="${(livro.ano?c)!}">
        <br>
        <label for="preco">Preço:</label>
        <input type="number" id="preco" name="preco" value="${(livro.preco?c)!}" step="0.01">
        <br>
        <input type="submit" value="Salvar">
        <a href="/livros/${livro.id?c}">Cancelar</a>
    </form>
</#if>
