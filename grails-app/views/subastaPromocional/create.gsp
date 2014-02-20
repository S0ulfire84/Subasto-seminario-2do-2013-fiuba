<%@ page import="subasto6.SubastaPromocional" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'subastaPromocional.label', default: 'SubastaPromocional')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
		
				
		
	</head>
	<body>
	
	
		<a href="#create-subastaPromocional" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="create-subastaPromocional" class="content scaffold-create" role="main">
			<h1>Crear una Subasta Promocional</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${subastaPromocionalInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${subastaPromocionalInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			
			<div style="" class="well" >
				<p>La subasta promocional le permite publicar una subasta a un precio base por debajo del precio base que ud. sugiera (<strong>precio base sugerido</strong>) como razonable para este articulo 
				mediante la aplicaci&oacute;n de un descuento estad&iacute;stico &oacute;ptimo calculado por nuestros servidores con el fin de que logre una mejor exposici&oacute;n de su producto.</p><br/>
				
				<p>Promocionando su producto a un precio con descuento &oacute;ptimo lograra que su producto alcance el valor que usted indica como <strong>porcentaje de ganancia esperado sobre el precio base.</strong></p><br/>
				
				<p>N&oacute;tese que el descuento depende directamente de la <strong>categor&iacute;a</strong> seleccionada, por lo que debe seleccionarla. Una mala elecci&oacute;n de la categoria har&aacute; que se apliquen estad&iacute;sticas de otro tipo de productos, produciendo una ganancia no &oacute;ptima.</p><br/>
			
				<p>Finalmente, otra caracter&iacute;stica de las subastas promocionales es que al ofertar, el tiempo restante es extendido en una cantidad de segundos determinadas como &oacute;ptima para los productos de esa categor&iacute;a.</p>
			</div>
			
			<g:form action="save" >
				<fieldset class="form">
<%--					<f:all bean="subastaInstance"/>--%>

					


				
				    
				    
				    <g:if test="${ usuarioLogueado != null }">
				        	<g:hiddenField name="vendedor" value="${ usuarioLogueado.id }" />
				        </g:if>
				        <g:else>
				        	<div class="fieldcontain required">
								<label for='vendedor'>
									Vendedor<span class='required-indicator'>*</span>
								</label>
								<g:select optionKey="id" name="vendedor" from="${vendedores}" style="width:166px" />
							</div>
				        </g:else>
				
				<div class="fieldcontain required">
					<label for='titulo'>
						Titulo<span class='required-indicator'>*</span>
					</label>
					<g:field type="text" name="titulo" required="" value="" />
				</div>
				
				<div class="fieldcontain required">
					<label for='categoria'>
						Categoria<span class='required-indicator'>*</span>
					</label>
					<g:select id="categoriaSelect" optionKey="id" name="categoria" from="${categorias}" style="width:166px;"/>
				</div>
				
				<div class="fieldcontain required">
					<label for='precioBase'>
						Precio base sugerido<span class='required-indicator'>*</span>
					</label>
					<g:field type="number" name="precioBase" required="" min="0" value="" id="precioBaseSugerido" /> $(ARS)
				</div>



				<div class="fieldcontain required">
					<label for='porcentajeDeGananciaEsperadaSobreElPrecioBase'>
						% de ganancia esperada sobre el precio base<span class='required-indicator'>*</span>
					</label>
					<g:field type="number" id="porcentajeGananciaEsperada" name="porcentajeDeGananciaEsperadaSobreElPrecioBase" min="0" max="100" required="" value="20" style="width:50px"/>%
				</div>

				<div class="fieldcontain required">
					<label for='descripcion'>
						Descripcion<span class='required-indicator'>*</span>
					</label>
					<g:textArea name="descripcion" value="" rows="5" cols="40" required="true"/>
				</div>
				
				<div class="fieldcontain required">
				
				<label for='finalizacion'>
					Fecha y hora de finalizacion:<span class='required-indicator'>*</span>
				</label>
				
					<g:datePicker name="finalizacion" value="${new Date()}" relativeYears="[0..1]"/>
					
				</div>
				
				<br/>
				<div class="well">
					De acuerdo a los parametros que ud. seleccion&oacute;, se aplicar&aacute; un descuento del <strong><span id="descuentoFinal">DESCUENTO</span></strong> al precio base sugerido, ofreciendose inicialmente
					por un valor de <strong>$<span id="precioBaseConDescuento">VALOR INICIAL</span>(ARS)</strong> y esperando obtenerse un valor de <strong>$<span id="valorFinalEsperado">VALOR FINAL</span>(ARS)</strong>.
					
				</div>
				
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:form>
			
			 
			
			 
		</div>
		
		<script type="text/javascript">

			 $( document ).ready(function() {
				 actualizarResultados();
			 });

			 $("#categoriaSelect").change(function() {
				 actualizarResultados();
		     });

			 $("#precioBaseSugerido").change(function() {
				 actualizarResultados();
		     });

			 $("#porcentajeGananciaEsperada").change(function() {
				 actualizarResultados();
		     });

		     function actualizarResultados() {
		    	 actualizarDescuento();
				 actualizarValorInicial();
				 actualizarValorFinal();
			 }

		     function actualizarValorInicial() {
		    	 var idCategoria = $("#categoriaSelect").val();
		    	 var precioBaseSugerido = $("#precioBaseSugerido").val();

				 var urlConsulta = "${createLink(controller:'subastaPromocional', action:'valorInicial')}"+"?idCat="+idCategoria+"&precioBaseSugerido="+precioBaseSugerido;
		    	 
				 $.get(urlConsulta, function(r) { $("#precioBaseConDescuento").html(r); } );
			 }

			 function actualizarValorFinal() {
		    	 var precioBaseSugerido = $("#precioBaseSugerido").val();
		    	 var porcentajeGananciaEsperada = $("#porcentajeGananciaEsperada").val();

				 var urlConsulta = "${createLink(controller:'subastaPromocional', action:'valorFinal')}"+"?porcentajeGananciaEsperada="+porcentajeGananciaEsperada+"&precioBaseSugerido="+precioBaseSugerido;
		    	 
				 $.get(urlConsulta, function(r) { $("#valorFinalEsperado").html(r); } );
			 }

			 function actualizarDescuento() {
				 var idCat = $("#categoriaSelect").val();
				 $.get("${createLink(controller:'categoria', action:'descuentoDeLaCategoria', id:idCat)}", function(r) { $("#descuentoFinal").html(r); } );
			 }
			
			</script>
		
	</body>
</html>
