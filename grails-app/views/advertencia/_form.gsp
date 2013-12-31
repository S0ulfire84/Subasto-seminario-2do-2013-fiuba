<%@ page import="subasto6.Advertencia" %>



<div class="fieldcontain ${hasErrors(bean: advertenciaInstance, field: 'comprador', 'error')} required">
	<label for="comprador">
		<g:message code="advertencia.comprador.label" default="Comprador" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="comprador" name="comprador.id" from="${subasto6.Usuario.list()}" optionKey="id" required="" value="${advertenciaInstance?.comprador?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: advertenciaInstance, field: 'subasta', 'error')} required">
	<label for="subasta">
		<g:message code="advertencia.subasta.label" default="Subasta" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="subasta" name="subasta.id" from="${subasto6.Subasta.list()}" optionKey="id" required="" value="${advertenciaInstance?.subasta?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: advertenciaInstance, field: 'transaccion', 'error')} required">
	<label for="transaccion">
		<g:message code="advertencia.transaccion.label" default="Transaccion" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="transaccion" name="transaccion.id" from="${subasto6.Transaccion.list()}" optionKey="id" required="" value="${advertenciaInstance?.transaccion?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: advertenciaInstance, field: 'vendedor', 'error')} required">
	<label for="vendedor">
		<g:message code="advertencia.vendedor.label" default="Vendedor" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="vendedor" name="vendedor.id" from="${subasto6.Usuario.list()}" optionKey="id" required="" value="${advertenciaInstance?.vendedor?.id}" class="many-to-one"/>
</div>

