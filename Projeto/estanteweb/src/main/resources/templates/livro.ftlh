<#include "cabecalho.ftlh">

<h2>Ficha do Livro</h2>

<p><strong>Título:</strong> ${livro.titulo}</p>
<p><strong>Autor:</strong> ${livro.autor}</p>
<p><strong>Ano:</strong> ${livro.ano?c}</p>
<p><strong>Preço:</strong> R$ ${livro.preco?string["0.00"]}</p>

<p>
    <a href="/livros/editar/${livro.id?c}">Editar este livro</a> |
    <a href="/livros/excluir/${livro.id?c}">Remover este livro</a>
</p>

<#if outrosLivros?? && (outrosLivros?size > 0)>
    <h3>Outros títulos do mesmo autor:</h3>
    <ul>
        <#list outrosLivros as outro>
            <li>
                <a href="/livros/${outro.id?c}">${outro.titulo}</a> (${outro.ano?c}) - R$ ${outro.preco?string["0.00"]}
            </li>
        </#list>
    </ul>
</#if>

<p>
    <a href="/livros">Voltar para o acervo</a>
</p>
