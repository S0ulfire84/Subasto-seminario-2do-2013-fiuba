
<%@ page import="subasto6.Subasta" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'subasta.label', default: 'Subasta')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-subasta" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-subasta" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="titulo" title="${message(code: 'subasta.titulo.label', default: 'Titulo')}" />
					
						<g:sortableColumn property="descripcion" title="${message(code: 'subasta.descripcion.label', default: 'Descripcion')}" />
					
						<g:sortableColumn property="precioBase" title="${message(code: 'subasta.precioBase.label', default: 'Precio Base')}" />
					
						<g:sortableColumn property="finalizacion" title="${message(code: 'subasta.finalizacion.label', default: 'Finalizacion')}" />
					
						<th><g:message code="subasta.transaccionDeCompra.label" default="Transaccion De Compra" /></th>
					
						<th><g:message code="subasta.vendedor.label" default="Vendedor" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${subastaInstanceList}" status="i" var="subastaInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${subastaInstance.id}">${fieldValue(bean: subastaInstance, field: "titulo")}</g:link></td>
					
						<td>${fieldValue(bean: subastaInstance, field: "descripcion")}</td>
					
						<td>${fieldValue(bean: subastaInstance, field: "precioBase")}</td>
					
						<td>${fieldValue(bean: subastaInstance, field: "finalizacion")}</td>
					
						<td>${fieldValue(bean: subastaInstance, field: "transaccionDeCompra")}</td>
					
						<td>${fieldValue(bean: subastaInstance, field: "vendedor")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${subastaInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
