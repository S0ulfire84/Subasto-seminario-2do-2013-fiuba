
<%@ page import="subasto6.Categoria" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'categoria.label', default: 'Categoria')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-categoria" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-categoria" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list categoria">
			
				<g:if test="${categoriaInstance?.descuentoOptimo}">
				<li class="fieldcontain">
					<span id="descuentoOptimo-label" class="property-label"><g:message code="categoria.descuentoOptimo.label" default="Descuento Optimo" /></span>
					
						<span class="property-value" aria-labelledby="descuentoOptimo-label"><g:fieldValue bean="${categoriaInstance}" field="descuentoOptimo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${categoriaInstance?.nombre}">
				<li class="fieldcontain">
					<span id="nombre-label" class="property-label"><g:message code="categoria.nombre.label" default="Nombre" /></span>
					
						<span class="property-value" aria-labelledby="nombre-label"><g:fieldValue bean="${categoriaInstance}" field="nombre"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${categoriaInstance?.tiempoAExtenderEnSegundosPorOferta}">
				<li class="fieldcontain">
					<span id="tiempoAExtenderEnSegundosPorOferta-label" class="property-label"><g:message code="categoria.tiempoAExtenderEnSegundosPorOferta.label" default="Tiempo a Extender En Segundos Por Oferta" /></span>
					
						<span class="property-value" aria-labelledby="tiempoAExtenderEnSegundosPorOferta-label"><g:fieldValue bean="${categoriaInstance}" field="tiempoAExtenderEnSegundosPorOferta"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:categoriaInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${categoriaInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
