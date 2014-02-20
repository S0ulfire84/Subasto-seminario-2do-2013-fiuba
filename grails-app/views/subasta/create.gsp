<%@ page import="subasto6.Subasta" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'subasta.label', default: 'Subasta')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-subasta" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="create-subasta" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${subastaInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${subastaInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="save" >
				<fieldset class="form">
<%--					<f:all bean="subastaInstance"/>--%>


					<f:with bean="subastaInstance">
				        <f:field property="titulo"/>
				        
				        <f:field property="precioBase"/>
				        				        
				        <g:if test="${ usuarioLogueado != null }">
				        	<g:hiddenField name="vendedor" value="${ usuarioLogueado.id }" />
				        </g:if>
				        <g:else>
				        	<f:field property="vendedor"/>	
				        </g:else>
				        
				        <f:field property="categoria"/>
				        
				    </f:with>

				<div class="fieldcontain required">
					<label for='descripcion'>
						Descripcion<span class='required-indicator'>*</span>
					</label>
					<g:textArea name="descripcion" value="" rows="5" cols="40" required="true"/>
				</div>
				
				<div class="fieldcontain required">
				
				<label for='descripcion'>
					Fecha y hora de finalizacion:<span class='required-indicator'>*</span>
				</label>
				
					<g:datePicker name="finalizacion" value="${new Date()}" relativeYears="[0..1]"/>
				</div>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
