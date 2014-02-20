<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="layout" content="main"/>
<title>Insert title here</title>
</head>
<body>
	
	<script>
	  	$( document ).ready(function() {
			$("#botonRealLogin").hide();
	  	});
	  	$( "#botonLogin" ).click(function() {
			$("#botonRealLogin").trigger( "click" );
		});
	</script>

  <div class="body">
  
  	<div id="show-subasta" class="content scaffold-show" style="padding:15px" role="main">
  	
  		<h1>Iniciar Sesion</h1>
  		
  		<p>Por favor ingrese su nombre de usuario y clave para ingresar a Subasto.</p><br/>
  	
		<form class="form-horizontal" role="form" id="formularioDeLogin" method="post">
		  <div class="form-group">
		    <label for="inputUser" class="col-sm-4 control-label">Usuario</label>
		    <div class="col-sm-4">
		      <input type="text" name="user" class="form-control" id="inputUser" placeholder="Usuario" >
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="inputPass" class="col-sm-4 control-label">Clave</label>
		    <div class="col-sm-4">
		      <input type="password" name="pass" class="form-control" id="inputPass" placeholder="Clave" >
		    </div>
		  </div>
		  
		  <g:actionSubmit id="botonRealLogin" action="autenticando" value="${message(code: 'default.button.login.label', default: 'Login')}" />
		  
		  <div class="col-md-offset-7"><button type="button" class="btn btn-primary" id="botonLogin">Iniciar Sesion</button></div>
		  
		</form>

	</div>

  </div>
</body>
</html>