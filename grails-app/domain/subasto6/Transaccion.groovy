package subasto6

class Transaccion {

	static final String TRANSACCION_NO_CONCRETADA_AUN = "Operacion todavia no concretada";
	static final String TRANSACCION_EXITOSA_CONCRETADA = "Operacion concretada";
	static final String TRANSACCION_FALLIDA_NO_SE_CONTACTO_EL_VENDEDOR = "El vendedor no se contacto";
	static final String TRANSACCION_FALLIDA_NO_SE_CONTACTO_EL_COMPRADOR = "El comprador no se contacto";
	static final String TRANSACCION_FALLIDA_DECIDIO_NO_COMPRARLO = "El articulo coincidia con la descripcion pero el comprador vio el articulo y decidio no comprarlo";
	static final String TRANSACCION_FALLIDA_ARTICULO_NO_COINCIDIA_CON_DESCRIPCION = "La descripcion del producto no correspondia con el articulo real vendido";
	
	Usuario comprador;
	Usuario vendedor;
	Subasta subasta;
	String estadoComprador = Transaccion.TRANSACCION_NO_CONCRETADA_AUN;
	String estadoVendedor = Transaccion.TRANSACCION_NO_CONCRETADA_AUN;
	Calificacion calificacionVendedor = null;
	Calificacion calificacionComprador = null;
	
	
    static constraints = {
		calificacionVendedor nullable:true, blank:true
		calificacionComprador nullable:true, blank:true
		
    }
	
	def static List estadosPosibles() {
		return [Transaccion.TRANSACCION_EXITOSA_CONCRETADA, Transaccion.TRANSACCION_FALLIDA_NO_SE_CONTACTO_EL_VENDEDOR, Transaccion.TRANSACCION_FALLIDA_NO_SE_CONTACTO_EL_COMPRADOR, TRANSACCION_FALLIDA_DECIDIO_NO_COMPRARLO, TRANSACCION_FALLIDA_ARTICULO_NO_COINCIDIA_CON_DESCRIPCION]
	}
	
	def Boolean calificarComprador(String comentario, int puntajeDado, String estadoTransaccion) {
		
		if (noHayCalificacionDelComprador() && Transaccion.esValidoElEstado(estadoTransaccion) && Calificacion.esValidoElPuntaje(puntajeDado)) {
			
			estadoComprador = estadoTransaccion;
			
			System.out.println("calificarComprador()")
			
			calificacionComprador = new Calificacion(comentario:comentario, puntaje:puntajeDado, usuarioCalificador:vendedor, usuarioCalificado:comprador, transaccion:this);
			calificacionComprador.save flush:true
			
			this.save flush:true
			
			if (this.ambosCalificados()) {
				if ( !estadosSonCoherentesEntreSi() ) {
					enviarMailAAdministrador();
				}
			} 
			
			return true
		}  
		return false
	}
	
	def enviarMailAAdministrador() {
		
		Advertencia advertencia = new Advertencia(transaccion:this, subasta:this.subasta, comprador:comprador, vendedor:vendedor);
		advertencia.save flush:true
		
		System.out.println( "ADVERTENCIA DE DIFERENCIA: "+advertencia.mensajeAdvertencia() )
		
		/*sendMail {
			to "acsaksida@gmail.com"
			subject "[Subasto.com] Calificaciones conflictivas"
			body "advertencia.mensajeAdvertencia()"
		}
		System.out.println("mandando mail")
		*/
		
		
	}
	
	def Boolean estadosSonCoherentesEntreSi() {
		if (estadoComprador == estadoVendedor) return true 
		return false;
	}
	
	def Boolean puedeVerCalificacionDeContraparte() {
		return ambosCalificados()
	}
	
	def Boolean calificarVendedor(String comentario, int puntajeDado, String estadoTransaccion) {

		if (noHayCalificacionDelVendedor() && Transaccion.esValidoElEstado(estadoTransaccion) && Calificacion.esValidoElPuntaje(puntajeDado)) {
			
			estadoVendedor = estadoTransaccion;
			
			System.out.println("calificarVendedor()")
			
			calificacionVendedor = new Calificacion(comentario:comentario, puntaje:puntajeDado, usuarioCalificador:comprador, usuarioCalificado:vendedor, transaccion:this);
			calificacionVendedor.save flush:true
			
			if ( estadoComprador == Transaccion.TRANSACCION_NO_CONCRETADA_AUN ) calificacionComprador = null;
			
			//calificacionVendedor = new Calificacion(comentario:comentario, puntaje:puntajeDado, usuarioCalificador:vendedor, usuarioCalificado:comprador, transaccion:this);
			//calificacionComprador = null;
			//calificacionVendedor.save flush:true
			
			
			this.save flush:true
			
			if (this.ambosCalificados()) {
				if ( !estadosSonCoherentesEntreSi() ) {
					enviarMailAAdministrador();
				}
			}
			
			return true
		}
		return false
	}
	
	def Boolean hayCalificacionDelComprador() {return calificacionComprador != null}
	def Boolean hayCalificacionDelVendedor() {return calificacionVendedor != null}
	
	def Boolean noHayCalificacionDelComprador() {return !hayCalificacionDelComprador()}
	def Boolean noHayCalificacionDelVendedor() {return !hayCalificacionDelVendedor()}
	
	def Boolean ambosCalificados() {return (hayCalificacionDelComprador() && hayCalificacionDelVendedor()) }
	
	def static Boolean esValidoElEstado(String estadoTransicion) {
		if (estadoTransicion.equals(Transaccion.TRANSACCION_NO_CONCRETADA_AUN)) return true;
		if (estadoTransicion.equals(Transaccion.TRANSACCION_EXITOSA_CONCRETADA)) return true;
		if (estadoTransicion.equals(Transaccion.TRANSACCION_FALLIDA_NO_SE_CONTACTO_EL_VENDEDOR)) return true;
		if (estadoTransicion.equals(Transaccion.TRANSACCION_FALLIDA_NO_SE_CONTACTO_EL_COMPRADOR)) return true;
		if (estadoTransicion.equals(Transaccion.TRANSACCION_FALLIDA_DECIDIO_NO_COMPRARLO)) return true;
		if (estadoTransicion.equals(Transaccion.TRANSACCION_FALLIDA_ARTICULO_NO_COINCIDIA_CON_DESCRIPCION)) return true;
	}
	
}
