
<%@ page import="subasto6.Oferta" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'oferta.label', default: 'Oferta')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-oferta" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-oferta" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list oferta">
			
				<g:if test="${ofertaInstance?.fechaYHora}">
				<li class="fieldcontain">
					<span id="fechaYHora-label" class="property-label"><g:message code="oferta.fechaYHora.label" default="Fecha YH ora" /></span>
					
						<span class="property-value" aria-labelledby="fechaYHora-label"><g:fieldValue bean="${ofertaInstance}" field="fechaYHora"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ofertaInstance?.ofertante}">
				<li class="fieldcontain">
					<span id="ofertante-label" class="property-label"><g:message code="oferta.ofertante.label" default="Ofertante" /></span>
					
						<span class="property-value" aria-labelledby="ofertante-label"><g:link controller="usuario" action="show" id="${ofertaInstance?.ofertante?.id}">${ofertaInstance?.ofertante?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${ofertaInstance?.valor}">
				<li class="fieldcontain">
					<span id="valor-label" class="property-label"><g:message code="oferta.valor.label" default="Valor" /></span>
					
						<span class="property-value" aria-labelledby="valor-label"><g:fieldValue bean="${ofertaInstance}" field="valor"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:ofertaInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${ofertaInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
