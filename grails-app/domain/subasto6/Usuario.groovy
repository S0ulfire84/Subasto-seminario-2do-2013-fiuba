package subasto6

class Usuario {

	String nombre
	String apellido
	String username
	String password
	String email
	
    static constraints = {
    }

	public Usuario(String nombre, String apellido, String username, String password, String email) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	def List subastas() {
		return Subasta.findAllWhere(vendedor:this)
	}
	
	def List calificaciones() {
		List calificaciones = Calificacion.findAllWhere(usuarioCalificado:this)
		
		List calificacionesPermitidas = []
		
		calificaciones.each {
			// Solo puedo mostrarle al usuario la calificacion si el usuario ya hizo su correspondiente contracalificacion.
			
			// Para saber eso, tiene que ser cierto que ambas partes de una transaccion hicieron sus calificaciones.
			
			if ( it.transaccion.ambosCalificados() ) calificacionesPermitidas.add(it) 
		}
		
		return calificacionesPermitidas
	}
	
	def List pendientesDeCalificar() {
		def transaccionesTerminadasComoComprador = Transaccion.findAllWhere(comprador:this)
		def transaccionesTerminadasComoVendedor = Transaccion.findAllWhere(vendedor:this)
		
		List pendientes = [];
		
		transaccionesTerminadasComoComprador.each {
			if (it.noHayCalificacionDelVendedor() ) pendientes.add(it.subasta)
			System.out.println ("Agregando "+it.subasta.toString()+ " a la lista de "+ this.nombre+" como comprador")
		}
		
		transaccionesTerminadasComoVendedor.each {
			if (it.noHayCalificacionDelComprador() ) pendientes.add(it.subasta)
			System.out.println ("Agregando "+it.subasta.toString()+ " a la lista de "+ this.nombre+" como vendedor")
		}

		return pendientes
	}
	
	def Boolean esGanadorDeLaSubasta(Subasta s) {
		return (s.estaFinalizada() && (s.ofertaGanadoraActual().ofertante.id == this.id))
	}
	
	def Boolean esVendedorDeLaSubasta(Subasta s) {
		return s.vendedor.id == this.id;
	}
	
	def String toString() {
		return username
	}
	
	
}
