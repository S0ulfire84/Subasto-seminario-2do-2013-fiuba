<%@ page import="subasto6.Oferta" %>



<div class="fieldcontain ${hasErrors(bean: ofertaInstance, field: 'fechaYHora', 'error')} required">
	<label for="fechaYHora">
		<g:message code="oferta.fechaYHora.label" default="Fecha YH ora" />
		<span class="required-indicator">*</span>
	</label>
	
</div>

<div class="fieldcontain ${hasErrors(bean: ofertaInstance, field: 'ofertante', 'error')} required">
	<label for="ofertante">
		<g:message code="oferta.ofertante.label" default="Ofertante" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="ofertante" name="ofertante.id" from="${subasto6.Usuario.list()}" optionKey="id" required="" value="${ofertaInstance?.ofertante?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ofertaInstance, field: 'valor', 'error')} required">
	<label for="valor">
		<g:message code="oferta.valor.label" default="Valor" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valor" value="${fieldValue(bean: ofertaInstance, field: 'valor')}" required=""/>
</div>

