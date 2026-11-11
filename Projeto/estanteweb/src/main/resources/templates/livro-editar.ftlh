<h1>Editar Livro</h1>

<#if !livro??>
    <strong>Não há nenhum livro!</strong>
<#else>
    <form action="/livros/editar/${livro.id?c}" method="post">
        <label for="titulo">Título:</label>
        <input type="text" id="titulo" name="titulo" value="${livro.titulo}">
        <br>
        <label for="autor">Autor:</label>
        <input type="text" id="autor" name="autor" value="${livro.autor}">
        <br>
        <label for="ano">Ano:</label>
        <input type="number" id="ano" name="ano" value="${livro.ano?c}">
        <br>
        <label for="preco">Preço:</label>
        <input type="number" id="preco" name="preco" value="${livro.preco?c}" step="0.01">
        <br>
        <input type="submit" value="Salvar">
    </form>
</#if>