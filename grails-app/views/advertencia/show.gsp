
<%@ page import="subasto6.Advertencia" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'advertencia.label', default: 'Advertencia')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-advertencia" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-advertencia" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list advertencia">
			
				<g:if test="${advertenciaInstance?.comprador}">
				<li class="fieldcontain">
					<span id="comprador-label" class="property-label"><g:message code="advertencia.comprador.label" default="Comprador" /></span>
					
						<span class="property-value" aria-labelledby="comprador-label"><g:link controller="usuario" action="show" id="${advertenciaInstance?.comprador?.id}">${advertenciaInstance?.comprador?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${advertenciaInstance?.subasta}">
				<li class="fieldcontain">
					<span id="subasta-label" class="property-label"><g:message code="advertencia.subasta.label" default="Subasta" /></span>
					
						<span class="property-value" aria-labelledby="subasta-label"><g:link controller="subasta" action="show" id="${advertenciaInstance?.subasta?.id}">${advertenciaInstance?.subasta?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${advertenciaInstance?.transaccion}">
				<li class="fieldcontain">
					<span id="transaccion-label" class="property-label"><g:message code="advertencia.transaccion.label" default="Transaccion" /></span>
					
						<span class="property-value" aria-labelledby="transaccion-label"><g:link controller="transaccion" action="show" id="${advertenciaInstance?.transaccion?.id}">${advertenciaInstance?.transaccion?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${advertenciaInstance?.vendedor}">
				<li class="fieldcontain">
					<span id="vendedor-label" class="property-label"><g:message code="advertencia.vendedor.label" default="Vendedor" /></span>
					
						<span class="property-value" aria-labelledby="vendedor-label"><g:link controller="usuario" action="show" id="${advertenciaInstance?.vendedor?.id}">${advertenciaInstance?.vendedor?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
				
				<li class="fieldcontain">
					<span id="vendedor-label" class="property-label"><g:message code="advertencia.vendedor.label" default="Detalle" /></span>
					<span class="property-value" aria-labelledby="vendedor-label"> <%= advertenciaInstance.detalleAdvertencia()  %>  </span>
				</li>
			
			</ol>
			
			<g:form url="[resource:advertenciaInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${advertenciaInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
