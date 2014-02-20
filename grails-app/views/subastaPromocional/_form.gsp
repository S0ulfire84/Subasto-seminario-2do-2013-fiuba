<%@ page import="subasto6.SubastaPromocional" %>



<div class="fieldcontain ${hasErrors(bean: subastaPromocionalInstance, field: 'titulo', 'error')} required">
	<label for="titulo">
		<g:message code="subastaPromocional.titulo.label" default="Titulo" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="titulo" required="" value="${subastaPromocionalInstance?.titulo}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: subastaPromocionalInstance, field: 'descripcion', 'error')} required">
	<label for="descripcion">
		<g:message code="subastaPromocional.descripcion.label" default="Descripcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="descripcion" required="" value="${subastaPromocionalInstance?.descripcion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: subastaPromocionalInstance, field: 'precioBase', 'error')} required">
	<label for="precioBase">
		<g:message code="subastaPromocional.precioBase.label" default="Precio Base" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="precioBase" value="${fieldValue(bean: subastaPromocionalInstance, field: 'precioBase')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: subastaPromocionalInstance, field: 'finalizacion', 'error')} required">
	<label for="finalizacion">
		<g:message code="subastaPromocional.finalizacion.label" default="Finalizacion" />
		<span class="required-indicator">*</span>
	</label>
	
</div>

<div class="fieldcontain ${hasErrors(bean: subastaPromocionalInstance, field: 'vendedor', 'error')} required">
	<label for="vendedor">
		<g:message code="subastaPromocional.vendedor.label" default="Vendedor" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="vendedor" name="vendedor.id" from="${subasto6.Usuario.list()}" optionKey="id" required="" value="${subastaPromocionalInstance?.vendedor?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: subastaPromocionalInstance, field: 'categoria', 'error')} required">
	<label for="categoria">
		<g:message code="subastaPromocional.categoria.label" default="Categoria" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="categoria" name="categoria.id" from="${subasto6.Categoria.list()}" optionKey="id" required="" value="${subastaPromocionalInstance?.categoria?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: subastaPromocionalInstance, field: 'finalizacionOriginal', 'error')} required">
	<label for="finalizacionOriginal">
		<g:message code="subastaPromocional.finalizacionOriginal.label" default="Finalizacion Original" />
		<span class="required-indicator">*</span>
	</label>
	
</div>

<div class="fieldcontain ${hasErrors(bean: subastaPromocionalInstance, field: 'ofertasRealizadas', 'error')} ">
	<label for="ofertasRealizadas">
		<g:message code="subastaPromocional.ofertasRealizadas.label" default="Ofertas Realizadas" />
		
	</label>
	<g:select name="ofertasRealizadas" from="${subasto6.Oferta.list()}" multiple="multiple" optionKey="id" size="5" value="${subastaPromocionalInstance?.ofertasRealizadas*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: subastaPromocionalInstance, field: 'porcentajeDeGananciaEsperadaSobreElPrecioBase', 'error')} required">
	<label for="porcentajeDeGananciaEsperadaSobreElPrecioBase">
		<g:message code="subastaPromocional.porcentajeDeGananciaEsperadaSobreElPrecioBase.label" default="Porcentaje De Ganancia Esperada Sobre El Precio Base" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeDeGananciaEsperadaSobreElPrecioBase" value="${fieldValue(bean: subastaPromocionalInstance, field: 'porcentajeDeGananciaEsperadaSobreElPrecioBase')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: subastaPromocionalInstance, field: 'precioBaseOriginal', 'error')} required">
	<label for="precioBaseOriginal">
		<g:message code="subastaPromocional.precioBaseOriginal.label" default="Precio Base Original" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="precioBaseOriginal" value="${fieldValue(bean: subastaPromocionalInstance, field: 'precioBaseOriginal')}" required=""/>
</div>

