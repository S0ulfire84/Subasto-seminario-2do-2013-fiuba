package subasto6

import java.sql.Timestamp

class Subasta {
	
	// Todos los atributos son requeridos
	String titulo; 
	String descripcion;
	
	BigDecimal precioBase;
	Date finalizacion = new Date(2100,1,1)
	Usuario vendedor;
	Categoria categoria;
	
	
	// Ofertas y OfertaAutomaticas que se hicieron en esta subasta
	List ofertasRealizadas = [];
	List ofertasAutomaticas = [];
	
	Transaccion transaccionDeCompra = null;
	Boolean subastaEstaMarcadaComoFinalizada = false;
	
	static hasMany = [ ofertasRealizadas:Oferta, ofertasAutomaticas:OfertaAutomatica ]
	
    static constraints = {
		titulo blank:false
		descripcion blank:false
		
		ofertasAutomaticas display:false
		
		precioBase blank:false
		finalizacion blank:false
		transaccionDeCompra display:false, nullable:true
		vendedor nullable:false
		categoria nullable:false
		subastaEstaMarcadaComoFinalizada display:false
    }
	
	
	
	public Subasta(String titulo, String descripcion, BigDecimal precioBase, Timestamp finalizacion, Usuario vendedor, Categoria categoria) {
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.precioBase = precioBase;
		this.finalizacion = finalizacion;
		this.vendedor = vendedor;
		this.categoria = categoria;
	}
	

	// Devuelve si la subasta esta finalizada
	def Boolean estaFinalizada() {
		
		Boolean finalizada = finalizacion.compareTo(new Timestamp( new Date().getTime() )) < 0; 
		
		if (finalizada && !subastaEstaMarcadaComoFinalizada) {
			finalizarSubasta();
		}
		
		return finalizada;
	}
	
	// Marca la subasta como finalizada y crea la transaccion de finalizacion
	def void finalizarSubasta() {
		subastaEstaMarcadaComoFinalizada = true;
		
		this.save flush:true
		
		// Si esta finalizada y hay un ganador pero no hay asignada una transaccion de compra, asignarle la transaccion de compra al ganador
		if ( hayUnGanador() && this.transaccionDeCompra == null ) {
			
			transaccionDeCompra = new Transaccion(comprador: (this.ofertaGanadoraActual()).ofertante, vendedor: vendedor, subasta:this );
			
			try {
				transaccionDeCompra.save flush:true;
			} catch (MissingMethodException e) {
				// Esto es porque los mocks de los tests parece que no pueden hacer save()
			}
		}
		
	}
	
	def Boolean calificarVendedor(String comentario, int puntaje, String estadoTransaccion) {
		if ( puedeCalificarVendedor() ) {
			return transaccionDeCompra.calificarVendedor(comentario, puntaje, estadoTransaccion); 
		}
		return false;
	}
	
	def Boolean calificarComprador(String comentario, int puntaje, String estadoTransaccion) {
		if ( puedeCalificarComprador() ) {
			return transaccionDeCompra.calificarComprador(comentario, puntaje, estadoTransaccion);
		}
		return false;
	}
	
	def Boolean hayUnGanador() {
		return this.quienVaGanando() != null;
	}
	
	// Devuelve si es posible realizar calificaciones ahora o no del comprador
	def Boolean puedeCalificarComprador() {
		return (this.estaFinalizada() && hayUnGanador() && this.transaccionDeCompra.noHayCalificacionDelComprador() );
	}
	
	// Devuelve si es posible realizar calificaciones ahora o no del vendedor
	def Boolean puedeCalificarVendedor() {
		return (this.estaFinalizada() && hayUnGanador() && this.transaccionDeCompra.noHayCalificacionDelVendedor() );
	}
	
	def cambiarFinalizacionA(Timestamp nuevaFinalizacion) {
		finalizacion = nuevaFinalizacion;
	}
	
	def int cantidadDeOfertasRealizadas() {
		return ofertasRealizadas.size();
	}
	
	// Devuelve el valor del precio actual
	def BigDecimal precioActual() {
		return (ofertasRealizadas.empty)? precioBase : ofertaGanadoraActual().valor;
	}
	
	// Devuelve la Oferta que venga ganando ahora
	def Oferta ofertaGanadoraActual() {
		return ofertasRealizadas.last();
	}
	
	// Incrementa la oferta en el valor indicado y devuelve si pudo realizarla
	def Boolean ofertar(double incremento, Usuario ofertante) {
		
		if (incremento <= 0) return false
		if ( this.estaFinalizada() ) return false
		
		Oferta o = new Oferta(precioActual()+incremento, ofertante)
		
		ofertasRealizadas << o;
		
		try {
			this.save flush:true
		} catch (MissingMethodException e) {
			// Esto es porque los mocks de los tests parece que no pueden hacer save()
		}
		
		notificarAOfertasAutomaticas();
		
		return true
	}
	
	
	// Oferta al valor absoluto indicado en lugar de incrementar la oferta
	def Boolean ofertarAValor(double valorDeLaOferta, Usuario ofertante) {
		
		if ( this.estaFinalizada() ) return false
		
		ofertar( valorDeLaOferta - precioActual(), ofertante )
		
		return true
	}
	
	def Boolean calificacionesCoherentesEntreSi() {
		
		return transaccionDeCompra.estadosSonCoherentesEntreSi()
	}
	
	
	def notificarAOfertasAutomaticas() {
		ofertasAutomaticas.each { it.notificarOfertaRealizada() }
	}
	
	
	def Boolean ofertarAutomaticamente(double tope, double incremento, Usuario ofertante) {
		ofertasAutomaticas << new OfertaAutomatica(tope, incremento, ofertante, this)
		
		notificarAOfertasAutomaticas()
		
		return true
	}
	
	// Devuelve el nombre de usuario de quien va ganando
	def String quienVaGanando() {
		return (ofertasRealizadas.empty)? null : ofertaGanadoraActual().ofertante.username;
	}
	
	def Boolean puedeVerCalificacionDeContraparte() {
		if (this.estaFinalizada() ) return transaccionDeCompra.puedeVerCalificacionDeContraparte()
		return false;
	}
	
	def String toString() {
		return this.titulo;
	}
	
}
