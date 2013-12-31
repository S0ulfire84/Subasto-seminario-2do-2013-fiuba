
<%@ page import="subasto6.Transaccion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'transaccion.label', default: 'Transaccion')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-transaccion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-transaccion" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="transaccion.calificacionComprador.label" default="Calificacion Comprador" /></th>
					
						<th><g:message code="transaccion.calificacionVendedor.label" default="Calificacion Vendedor" /></th>
					
						<th><g:message code="transaccion.comprador.label" default="Comprador" /></th>
					
						<g:sortableColumn property="estadoComprador" title="${message(code: 'transaccion.estadoComprador.label', default: 'Estado Comprador')}" />
					
						<g:sortableColumn property="estadoVendedor" title="${message(code: 'transaccion.estadoVendedor.label', default: 'Estado Vendedor')}" />
					
						<th><g:message code="transaccion.subasta.label" default="Subasta" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${transaccionInstanceList}" status="i" var="transaccionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${transaccionInstance.id}">${fieldValue(bean: transaccionInstance, field: "calificacionComprador")}</g:link></td>
					
						<td>${fieldValue(bean: transaccionInstance, field: "calificacionVendedor")}</td>
					
						<td>${fieldValue(bean: transaccionInstance, field: "comprador")}</td>
					
						<td>${fieldValue(bean: transaccionInstance, field: "estadoComprador")}</td>
					
						<td>${fieldValue(bean: transaccionInstance, field: "estadoVendedor")}</td>
					
						<td>${fieldValue(bean: transaccionInstance, field: "subasta")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${transaccionInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
