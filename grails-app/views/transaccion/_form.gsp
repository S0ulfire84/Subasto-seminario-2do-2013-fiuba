<%@ page import="subasto6.Transaccion" %>



<div class="fieldcontain ${hasErrors(bean: transaccionInstance, field: 'calificacionComprador', 'error')} required">
	<label for="calificacionComprador">
		<g:message code="transaccion.calificacionComprador.label" default="Calificacion Comprador" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="calificacionComprador" name="calificacionComprador.id" from="${subasto6.Calificacion.list()}" optionKey="id" required="" value="${transaccionInstance?.calificacionComprador?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: transaccionInstance, field: 'calificacionVendedor', 'error')} required">
	<label for="calificacionVendedor">
		<g:message code="transaccion.calificacionVendedor.label" default="Calificacion Vendedor" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="calificacionVendedor" name="calificacionVendedor.id" from="${subasto6.Calificacion.list()}" optionKey="id" required="" value="${transaccionInstance?.calificacionVendedor?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: transaccionInstance, field: 'comprador', 'error')} required">
	<label for="comprador">
		<g:message code="transaccion.comprador.label" default="Comprador" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="comprador" name="comprador.id" from="${subasto6.Usuario.list()}" optionKey="id" required="" value="${transaccionInstance?.comprador?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: transaccionInstance, field: 'estadoComprador', 'error')} ">
	<label for="estadoComprador">
		<g:message code="transaccion.estadoComprador.label" default="Estado Comprador" />
		
	</label>
	<g:textField name="estadoComprador" value="${transaccionInstance?.estadoComprador}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: transaccionInstance, field: 'estadoVendedor', 'error')} ">
	<label for="estadoVendedor">
		<g:message code="transaccion.estadoVendedor.label" default="Estado Vendedor" />
		
	</label>
	<g:textField name="estadoVendedor" value="${transaccionInstance?.estadoVendedor}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: transaccionInstance, field: 'subasta', 'error')} required">
	<label for="subasta">
		<g:message code="transaccion.subasta.label" default="Subasta" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="subasta" name="subasta.id" from="${subasto6.Subasta.list()}" optionKey="id" required="" value="${transaccionInstance?.subasta?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: transaccionInstance, field: 'vendedor', 'error')} required">
	<label for="vendedor">
		<g:message code="transaccion.vendedor.label" default="Vendedor" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="vendedor" name="vendedor.id" from="${subasto6.Usuario.list()}" optionKey="id" required="" value="${transaccionInstance?.vendedor?.id}" class="many-to-one"/>
</div>

