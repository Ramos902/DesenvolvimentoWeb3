<h1>Lista de Livros</h1>
<ul>
    <#if livros?size == 0>
        <strong>Não há nenhum livro!</strong>
    <#else>
        <#list livros as livro>
            <li>Livro: ${livro.titulo}</li>
            <li>Autor: ${livro.autor}</li>
            <li>Preço: ${livro.preco?c}</li>
            <br>
        </#list>
    </#if>
</ul>