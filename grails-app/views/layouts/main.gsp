<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		
		
		<!-- Bootstrap -->
    	<link href="${resource(dir: 'css', file: 'bootstrap.min.css')}" rel="stylesheet">
		
		<title><g:layoutTitle default="Grails"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
		
		
		
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
    	
		<g:layoutHead/>
		<r:layoutResources />
	</head>
	<body>
	
		<script src="${ resource(dir: 'js', file:'jquery-1.10.2.min.js') }"></script>
		
		
		<!-- 
		
		<script>$( document ).ready(function() {
	        alert( "document loaded" );
	    });</script>
		 -->
		

		<div class="pull-right" style="padding:15px">
			<g:if test="${ usuarioLogueado != null }">
			
			<div class="btn-group">
				<button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown">
				  Usuario: ${ usuarioLogueado.username } <span class="caret"></span>
				</button>
				
				<ul class="dropdown-menu" role="menu">
				    <li><g:link controller="usuario" action="miSubasto" absolute="true">Ir a Mi Subasto</g:link></li>
				</ul>
			
				<!-- <div class="dropdown-menu" role="menu">
				  <a data-toggle="dropdown" href="#"> <p style="color:black"><strong>Usuario: ${ usuarioLogueado.username } </strong><span class="badge">42</span></p> </a>
				  <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
				  	<li>Test 1</li>
				  	<li>Test 2</li>
				  </ul>
				</div>-->
				
				</div>
			</g:if>
			
			<g:if test="${ usuarios != null }">
				<form class="form-horizontal" role="form" id="formUsuario" method="get">
				<g:select id="selectUsuario" name="selectUsuario" from="${usuarios}" noSelection="${['null':'Cambiar al usuario...']}" />
				</form>
			
			</g:if>
			
			
		</div>
		
		<div id="grailsLogo" role="banner"><a href="/Subasto6"><img src="${resource(dir: 'images', file: 'logo.png')}" alt="Grails"/></a></div>
		
		
		<g:layoutBody/>
		<div class="footer" role="contentinfo"></div>
		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
		
	
		<g:javascript library="application"/>
	
		
		<!-- <g:javascript library="bootstrap"/> -->
		
		<r:layoutResources />
			
		<!-- <script src="${resource(dir: 'js', file: 'bootstrap.min.js')}"/> -->
		
		<script type="text/javascript">
			/*$( document ).ready(function() {

				
				
			});*/

			$("#selectUsuario").change(function() {
				$("#formUsuario").submit();
			});
		</script>
		
	</body>
</html>
