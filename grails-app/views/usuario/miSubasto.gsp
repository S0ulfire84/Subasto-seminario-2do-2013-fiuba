<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="layout" content="main"/>
<title>Mi Subasto</title>
</head>
<body>
  <div class="body">
  	<g:if test="${ usuarioLogueado != null }">
  		<div style="margin:15px">
  			<h3>Bienvenido a Subasto, ${ usuarioLogueado.nombre }</h3>
  		
  			<h1>Mis Subastas</h1>
  			<g:each in="${ subastasDelUsuario }">
  				<g:link controller="subasta" action="show" id="${ it.id }"> ${ it.toString() }</g:link><br/>
  			</g:each>
  			<g:if test="${ subastasDelUsuario == null || subastasDelUsuario.empty }">No hay ninguna subasta publicada.</g:if>
  			
  			<h1>Mis calificaciones</h1>
  			<g:each in="${ calificacionesDelUsuario }">
  				<div class="well">
  					<p>
  						<em>
  					
		  					<g:link controller="usuario" action="show" id="${ it.usuarioCalificador.id }"> ${ it.usuarioCalificador.toString() }</g:link>
		  					
		  					 te calific&oacute; por la subasta 
		  					 
		  					 <g:link controller="subasta" action="show" id="${ it.transaccion.subasta.id }"> ${ it.transaccion.subasta.toString() }</g:link> 
		  					 
		  					 con un puntaje de <strong>${ it.puntaje }</strong>
  					 	</em>
  					 </p>
  					<blockquote><strong>Comentario:</strong> ${ it.comentario }</blockquote>
  				</div>
  				
  			</g:each>
  			<g:if test="${ calificacionesDelUsuario == null || calificacionesDelUsuario.empty }">No tenes ninguna calificacion.</g:if>
  			
  			 
  			<h1>Tareas pendientes</h1>
  			<g:each in="${ tareasDelUsuario }">
  				Tenes que calificar a tu contraparte en <g:link controller="subasta" action="show" id="${ it.id }">${ it.toString() }</g:link><br/>
  			</g:each>
  			<g:if test="${ tareasDelUsuario == null || tareasDelUsuario.empty }">No tenes tareas pendientes.</g:if>
  			
  		
  		</div>
  	</g:if>
  </div>
</body>
</html>