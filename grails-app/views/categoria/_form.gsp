<%@ page import="subasto6.Categoria" %>



<div class="fieldcontain ${hasErrors(bean: categoriaInstance, field: 'densidadPromedioDeOfertas', 'error')} required">
	<label for="densidadPromedioDeOfertas">
		<g:message code="categoria.densidadPromedioDeOfertas.label" default="Densidad Promedio De Ofertas" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="densidadPromedioDeOfertas" value="${fieldValue(bean: categoriaInstance, field: 'densidadPromedioDeOfertas')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: categoriaInstance, field: 'descuentoOptimo', 'error')} required">
	<label for="descuentoOptimo">
		<g:message code="categoria.descuentoOptimo.label" default="Descuento Optimo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="descuentoOptimo" value="${fieldValue(bean: categoriaInstance, field: 'descuentoOptimo')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: categoriaInstance, field: 'nombre', 'error')} ">
	<label for="nombre">
		<g:message code="categoria.nombre.label" default="Nombre" />
		
	</label>
	<g:textField name="nombre" value="${categoriaInstance?.nombre}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: categoriaInstance, field: 'tiempoAExtenderEnSegundosPorOferta', 'error')} required">
	<label for="tiempoAExtenderEnSegundosPorOferta">
		<g:message code="categoria.tiempoAExtenderEnSegundosPorOferta.label" default="Tiempo AE xtender En Segundos Por Oferta" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="tiempoAExtenderEnSegundosPorOferta" type="number" value="${categoriaInstance.tiempoAExtenderEnSegundosPorOferta}" required=""/>
</div>

