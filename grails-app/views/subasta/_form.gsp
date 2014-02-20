<%@ page import="subasto6.Subasta" %>



<div class="fieldcontain ${hasErrors(bean: subastaInstance, field: 'titulo', 'error')} required">
	<label for="titulo">
		<g:message code="subasta.titulo.label" default="Titulo" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="titulo" required="" value="${subastaInstance?.titulo}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: subastaInstance, field: 'descripcion', 'error')} required">
	<label for="descripcion">
		<g:message code="subasta.descripcion.label" default="Descripcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="descripcion" required="" value="${subastaInstance?.descripcion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: subastaInstance, field: 'precioBase', 'error')} required">
	<label for="precioBase">
		<g:message code="subasta.precioBase.label" default="Precio Base" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="precioBase" value="${fieldValue(bean: subastaInstance, field: 'precioBase')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: subastaInstance, field: 'finalizacion', 'error')} required">
	<label for="finalizacion">
		<g:message code="subasta.finalizacion.label" default="Finalizacion" />
		<span class="required-indicator">*</span>
	</label>
	
</div>

<div class="fieldcontain ${hasErrors(bean: subastaInstance, field: 'vendedor', 'error')} required">
	<label for="vendedor">
		<g:message code="subasta.vendedor.label" default="Vendedor" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="vendedor" name="vendedor.id" from="${subasto6.Usuario.list()}" optionKey="id" required="" value="${subastaInstance?.vendedor?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: subastaInstance, field: 'categoria', 'error')} required">
	<label for="categoria">
		<g:message code="subasta.categoria.label" default="Categoria" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="categoria" name="categoria.id" from="${subasto6.Categoria.list()}" optionKey="id" required="" value="${subastaInstance?.categoria?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: subastaInstance, field: 'ofertasRealizadas', 'error')} ">
	<label for="ofertasRealizadas">
		<g:message code="subasta.ofertasRealizadas.label" default="Ofertas Realizadas" />
		
	</label>
	<g:select name="ofertasRealizadas" from="${subasto6.Oferta.list()}" multiple="multiple" optionKey="id" size="5" value="${subastaInstance?.ofertasRealizadas*.id}" class="many-to-many"/>
</div>

