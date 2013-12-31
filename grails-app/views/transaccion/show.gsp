
<%@ page import="subasto6.Transaccion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'transaccion.label', default: 'Transaccion')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-transaccion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-transaccion" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list transaccion">
			
				<g:if test="${transaccionInstance?.calificacionComprador}">
				<li class="fieldcontain">
					<span id="calificacionComprador-label" class="property-label"><g:message code="transaccion.calificacionComprador.label" default="Calificacion Comprador" /></span>
					
						<span class="property-value" aria-labelledby="calificacionComprador-label"><g:link controller="calificacion" action="show" id="${transaccionInstance?.calificacionComprador?.id}">${transaccionInstance?.calificacionComprador?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${transaccionInstance?.calificacionVendedor}">
				<li class="fieldcontain">
					<span id="calificacionVendedor-label" class="property-label"><g:message code="transaccion.calificacionVendedor.label" default="Calificacion Vendedor" /></span>
					
						<span class="property-value" aria-labelledby="calificacionVendedor-label"><g:link controller="calificacion" action="show" id="${transaccionInstance?.calificacionVendedor?.id}">${transaccionInstance?.calificacionVendedor?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${transaccionInstance?.comprador}">
				<li class="fieldcontain">
					<span id="comprador-label" class="property-label"><g:message code="transaccion.comprador.label" default="Comprador" /></span>
					
						<span class="property-value" aria-labelledby="comprador-label"><g:link controller="usuario" action="show" id="${transaccionInstance?.comprador?.id}">${transaccionInstance?.comprador?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${transaccionInstance?.estadoComprador}">
				<li class="fieldcontain">
					<span id="estadoComprador-label" class="property-label"><g:message code="transaccion.estadoComprador.label" default="Estado Comprador" /></span>
					
						<span class="property-value" aria-labelledby="estadoComprador-label"><g:fieldValue bean="${transaccionInstance}" field="estadoComprador"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${transaccionInstance?.estadoVendedor}">
				<li class="fieldcontain">
					<span id="estadoVendedor-label" class="property-label"><g:message code="transaccion.estadoVendedor.label" default="Estado Vendedor" /></span>
					
						<span class="property-value" aria-labelledby="estadoVendedor-label"><g:fieldValue bean="${transaccionInstance}" field="estadoVendedor"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${transaccionInstance?.subasta}">
				<li class="fieldcontain">
					<span id="subasta-label" class="property-label"><g:message code="transaccion.subasta.label" default="Subasta" /></span>
					
						<span class="property-value" aria-labelledby="subasta-label"><g:link controller="subasta" action="show" id="${transaccionInstance?.subasta?.id}">${transaccionInstance?.subasta?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${transaccionInstance?.vendedor}">
				<li class="fieldcontain">
					<span id="vendedor-label" class="property-label"><g:message code="transaccion.vendedor.label" default="Vendedor" /></span>
					
						<span class="property-value" aria-labelledby="vendedor-label"><g:link controller="usuario" action="show" id="${transaccionInstance?.vendedor?.id}">${transaccionInstance?.vendedor?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:transaccionInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${transaccionInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
