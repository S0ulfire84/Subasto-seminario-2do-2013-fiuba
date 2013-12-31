package subasto6

class OfertaAutomatica {

	double tope
	double incremento
	Usuario usuario
	Subasta subasta
	
    static constraints = {
    }
	
	def OfertaAutomatica(double topeMax, double incrementoEntreOfertas, Usuario ofertante, Subasta subastaEnLaQueOferta) {
		tope = topeMax
		incremento = incrementoEntreOfertas
		usuario = ofertante
		subasta = subastaEnLaQueOferta
	}
	
	def tengoMejorOfertaQueLaActual() {
		if (subasta.quienVaGanando() == usuario.username) return false
		
		return ((subasta.precioActual()+incremento) <= tope )
	}
	
	def notificarOfertaRealizada() {
		
		if (tengoMejorOfertaQueLaActual() ) {
			subasta.ofertar(incremento, usuario)
		}
	}
	
}
