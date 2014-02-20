
<%@ page import="subasto6.SubastaPromocional" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'subastaPromocional.label', default: 'SubastaPromocional')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-subastaPromocional" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-subastaPromocional" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="titulo" title="${message(code: 'subastaPromocional.titulo.label', default: 'Titulo')}" />
					
						<g:sortableColumn property="descripcion" title="${message(code: 'subastaPromocional.descripcion.label', default: 'Descripcion')}" />
					
						<g:sortableColumn property="precioBase" title="${message(code: 'subastaPromocional.precioBase.label', default: 'Precio Base')}" />
					
						<g:sortableColumn property="finalizacion" title="${message(code: 'subastaPromocional.finalizacion.label', default: 'Finalizacion')}" />
					
						<th><g:message code="subastaPromocional.transaccionDeCompra.label" default="Transaccion De Compra" /></th>
					
						<th><g:message code="subastaPromocional.vendedor.label" default="Vendedor" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${subastaPromocionalInstanceList}" status="i" var="subastaPromocionalInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${subastaPromocionalInstance.id}">${fieldValue(bean: subastaPromocionalInstance, field: "titulo")}</g:link></td>
					
						<td>${fieldValue(bean: subastaPromocionalInstance, field: "descripcion")}</td>
					
						<td>${fieldValue(bean: subastaPromocionalInstance, field: "precioBase")}</td>
					
						<td>${fieldValue(bean: subastaPromocionalInstance, field: "finalizacion")}</td>
					
						<td>${fieldValue(bean: subastaPromocionalInstance, field: "transaccionDeCompra")}</td>
					
						<td>${fieldValue(bean: subastaPromocionalInstance, field: "vendedor")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${subastaPromocionalInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
