package subasto6

class Advertencia {
	
	Subasta subasta
	Usuario comprador
	Usuario vendedor
	
	Transaccion transaccion

    static constraints = {
    }
	
	def String mensajeAdvertencia() {
		
		try {
			def grailsApplication = this.domainClass.grailsApplication
			
			def grailsLinkGenerator = grailsApplication.mainContext.grailsLinkGenerator
			
			return "<p>Se han detectado calificaciones contradictorias en la subasta "+subasta.toString()+"</p><p>"+
			detalleAdvertencia() +".</p> <p>Visitar la <a href='"+grailsLinkGenerator.link(action: 'show', id:this.id, controller:'advertencia')+"'>Advertencia</a> para mas detalles.</p>";
			
		} catch (MissingPropertyException e) {
			// Esto es porque los mocks de los tests parece que no pueden alcanzar la aplicacion de grails. Muestro un link vacio y listo. No me afecta.
			
			return "<p>Se han detectado calificaciones contradictorias en la subasta "+subasta.toString()+"</p><p>"+
			detalleAdvertencia() +".</p> <p>Visitar la <a href=''>Advertencia</a> para mas detalles.</p>";
		}
		
		
		
	}
	
	def String detalleAdvertencia() {
		return "El comprador <strong>"+comprador+"</strong> califico al vendedor <strong>"+vendedor+"</strong> con un puntaje de <strong>"+transaccion.calificacionVendedor.puntaje+"</strong> indicando que '<strong>"+transaccion.estadoVendedor+"'</strong> y comentando <strong>'"+transaccion.calificacionVendedor.comentario+"'</strong>"+
		", mientras que el vendedor <strong>"+vendedor+"</strong> califico al comprador <strong>"+comprador+"</strong> con un puntaje de <strong>"+transaccion.calificacionComprador.puntaje+"</strong>, indicando que <strong>'"+transaccion.estadoComprador+"'</strong> y comentando <strong>'"+transaccion.calificacionComprador.comentario+"'</strong>"
	}
}
