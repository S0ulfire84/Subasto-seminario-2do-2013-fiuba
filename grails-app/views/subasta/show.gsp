
<%@ page import="subasto6.Subasta" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'subasta.label', default: 'Subasta')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.countdown.css')}" type="text/css">
	</head>
	<body>

<!-- @@@@@@@@@@@ CALIFICACION @@@@@@@@@@@@@@ -->
	<div class="modal fade" id="modalCalificacion">
	
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title">${ tieneQueCalificarComprador? "Calificar al comprador..." : "Calificar al vendedor..." }</h4>
      </div>
      <div class="modal-body">
        <g:if test="${ tieneQueCalificarVendedor }">
        	<p><strong>Felicitaciones, ganaste la subasta!</strong> El proximo paso es calificar al vendedor. Pon a continuacion tu calificacion del vendedor y una breve rese&ntilde;a y, si no se concret&oacute; la operaci&oacute;n, aclara por qu&eacute;.</p>
        	<br/>
        	<g:form id="${ subastaInstance.id }" class="formCalificarVendedor" name="formCalificarVendedor" action="calificarVendedor" method="post">
        		<div class="form-group">
	        		<label for="selectEstadoTransaccion">¿C&oacute;mo finaliz&oacute; la transacci&oacute;n?</label>
	        		<g:select class="form-control" id="selectEstadoTransaccion" name="selectEstadoTransaccion" from="${estadosPosiblesTransaccion}"  />
        		</div>
        		
        		<div class="form-group">
        			<label for="selectEstadoTransaccion">Rese&ntilde;a breve:</label>
        			<textarea class="form-control" name="comentario" rows="3" required></textarea>
        		</div>
        		
        		<div class="form-group">
				    <label for="puntaje">Calificacion del usuario:</label>
				    <g:field name="puntaje" type="number" min="-5" max="5" value="0" /><br/>
				    
				    <div style="margin-top:15px" class="well">0 es neutral. 5 es el mejor puntaje. -5 es el peor puntaje.</div>
				</div>
				
        		<g:actionSubmit id="botonRealCalificar" action="calificarVendedor" value="${message(code: 'default.button.calificar.label', default: 'Calificar')}" />
        		
        	</g:form>
        	
        </g:if>
        <g:else>
        	<p><strong>Felicitaciones, vendiste tu producto!</strong> El proximo paso es calificar al comprador. Pon a continuacion tu calificacion del comprador y una breve rese&ntilde;a y, si no se concret&oacute; la operaci&oacute;n, aclara por qu&eacute;.</p>
        	<br/>
        	<g:form id="${ subastaInstance.id }" class="formCalificarComprador" name="formCalificarComprador" action="calificarComprador" method="post">
        		<div class="form-group">
	        		<label for="selectEstadoTransaccion">¿C&oacute;mo finaliz&oacute; la transacci&oacute;n?</label>
	        		<g:select class="form-control" id="selectEstadoTransaccion" name="selectEstadoTransaccion" from="${estadosPosiblesTransaccion}"  />
        		</div>
        		
        		<div class="form-group">
        			<label for="selectEstadoTransaccion">Rese&ntilde;a breve:</label>
        			<textarea class="form-control" name="comentario" rows="3" required></textarea>
        		</div>
        		
        		<div class="form-group">
				    <label for="puntaje">Calificacion del usuario:</label>
				    <g:field name="puntaje" type="number" min="-5" max="5" value="0" /><br/>
				    
				    <div style="margin-top:15px" class="well">0 es neutral. 5 es el mejor puntaje. -5 es el peor puntaje.</div>
				</div>
				
        		<g:actionSubmit id="botonRealCalificar" action="calificarComprador" value="${message(code: 'default.button.calificar.label', default: 'Calificar')}" />
        		
        	</g:form>
        	
        </g:else>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
        <button type="button" id="botonCalificar" class="btn btn-primary">Calificar</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- @@@@ FIN CALIFICACION @@@@ -->



<!-- Modal -->
<div class="modal fade" id="modalOfertar" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">Realizar una oferta...</h4>
      </div>
      <div class="modal-body">
      	<!-- FORMULARIO DE OFERTA -->
      	
      	<form class="form-horizontal" role="form" id="formularioDeOferta" method="post">
      	
      	Ofertar con el usuario:<g:select id="selectUsuario" name="selectUsuario" from="${usuarios}" select="${ usuarioLogueado }" />
      	
		  <div class="form-group">
		    <label for="inputOferta" class="col-sm-6 control-label">Valor a ofertar (en $ARS)</label>
		    <div class="col-sm-4">
		      <input type="number" min="${ subastaInstance.precioActual()+10 }" step="10" name="inputOferta" class="form-control" id="inputOferta" placeholder="en $ARS" value="${ subastaInstance.precioActual()+10 }" >
		    </div>
		  </div>
		  <div class="form-group">
		    <div class="col-sm-offset-6 col-sm-10">
		      <div class="checkbox">
		        <label>
		          <input type="checkbox" name="ofertaAutomatica" value="1" > Realizar una oferta automatica
		        </label>
		      </div>
		      <g:actionSubmit id="botonRealOfertar" action="oferta" value="${message(code: 'default.button.oferta.label', default: 'Oferta')}" />
		    </div>
		  </div>
		  <!-- <div class="form-group">
		    <div class="col-sm-offset-2 col-sm-10">
		      <button type="submit" class="btn btn-default">Sign in</button>
		    </div>
		  </div> -->
		</form>
      	
      	<!-- /FORMULARIO DE OFERTA -->
      </div>
      <div class="modal-footer">
		
        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
        <button type="button" class="btn btn-primary" id="botonOfertar">Ofertar</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->





<script src="${ resource(dir: 'js', file:'jquery.countdown.min.js') }"></script>
<script src="${ resource(dir: 'js', file:'jquery.countdown-es.js') }"></script>

<script>



	$( document ).ready(function() {

		$("#botonRealOfertar").hide();
	    $("#botonRealCalificar").hide();
		
		if ( "${subastaFinalizada}" == "false" ) {
			// La subasta no esta finalizada
			
		    //var newYear = new Date(); 
		    //newYear = new Date(newYear.getFullYear() + 1, 1 - 1, 1); 
		    
		    var tiempoFinalizacionSubasta = new Date(${subastaInstance.finalizacion.getTime()});
		    
		    $('#defaultCountdown').countdown({until: tiempoFinalizacionSubasta}); 
		     
		    $('#removeCountdown').toggle(function() { 
		            $(this).text('Re-attach'); 
		            $('#defaultCountdown').countdown('destroy'); 
		        }, 
		        function() { 
		            $(this).text('Remove'); 
		            $('#defaultCountdown').countdown({until: tiempoFinalizacionSubasta}); 
		        } 
		    );

		   // $('#modalCalificacion').modal('show')
		    
		} else {
			// La subasta termino
			
			if ( "${tieneQueCalificarComprador}" == "true" || "${tieneQueCalificarVendedor}" == "true" ) {

				$('#modalCalificacion').modal('show');

			}
			

		}
		
	    
 });
  
	$( "#botonOfertar" ).click(function() {
		  $("#botonRealOfertar").trigger( "click" );
	});

	
	$( "#botonCalificar" ).click(function() {
		$("#botonRealCalificar").trigger( "click" );
	});
	
</script>


	
	
		<a href="#show-subasta" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>

		
		<div id="show-subasta" class="content scaffold-show" role="main">
			
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			
				
				
			
				<ol class="breadcrumb">
				  <li><g:link controller="categoria" action="show" id="${subastaInstance?.categoria?.id}">${subastaInstance?.categoria?.encodeAsHTML()}</g:link></li>
				  <li class="active">${subastaInstance.titulo }</li>
				</ol>			
			
				<h1>${subastaInstance?.titulo }</h1>
				<div class="row">
					<div class="col-md-12">
						<div class="col-md-7">
						
							<div class="panel panel-default">
							  <div class="panel-heading">
							    <h3 class="panel-title">Descripcion</h3>
							  </div>
							  <div class="panel-body">
							    ${ subastaInstance?.descripcion }
							  </div>
							</div>
						  	
						</div>
						<div class="col-md-5">
							<div class="well">
								<dl class="dl-horizontal">
									<h1>Precio actual: <span style="color:orange;">${ subastaInstance.precioActual() }&#36; (ARS)</span></h1>
									<dt>Vendedor:</dt><dd>${ subastaInstance.vendedor }</dd>
									<dt>Ofertas realizadas:</dt><dd>${ subastaInstance.cantidadDeOfertasRealizadas() }</dd>
									<dt>Ganador actual:</dt><dd>${ subastaInstance.quienVaGanando() }</dd>
								</dl>
								
								<div id="defaultCountdown"></div>
								
								<g:if test="${!subastaFinalizada}">
									<p class="text-center" style="padding-top:15px"><button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#modalOfertar"><span class="glyphicon glyphicon-hand-up"></span> Ofertar</button></p>
								</g:if>
								
								
							</div>
						</div>
					</div>
				</div> <!-- /row -->
				
			
			
		</div>
		
	</body>
</html>
