<#include "cabecalho.ftlh">

<h2>Erro ${status!500} - ${(error)!"Erro na Requisição"}</h2>

<p>Ocorreu um problema ao acessar: <code>${path!"desconhecido"}</code></p>

<#if message?? && message?has_content>
    <p><strong>Detalhe:</strong> ${message}</p>
</#if>

<hr>

<p>
    <a href="/livros">Voltar para a Estante</a> |
    <a href="/">Página Inicial</a>
</p>
