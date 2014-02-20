package subasto6

import java.sql.Timestamp;

class SubastaPromocional extends Subasta {

	BigDecimal precioBaseOriginal = -1;
	Timestamp finalizacionOriginal = null;
	BigDecimal porcentajeDeGananciaEsperadaSobreElPrecioBase = 0.2;
	
    static constraints = {
		finalizacionOriginal nullable:true, blank:true
    }
	public String porcentajeDescuento() {
		return Math.round((categoria?.descuentoOptimo)*100)+"%"
	}
	
	def asignarPrecioPromocion() {
		if (precioBaseOriginal == -1) {
			precioBaseOriginal = precioBase;
			
			if (categoria != null) {
				precioBase *= (1-categoria.descuentoOptimo);
			}
		}
	}
	
	// A que valor espera el vendedor lograr venderlo finalmente
	def BigDecimal valorFinalEsperado() {
		return precioBaseOriginal + precioBaseOriginal * porcentajeDeGananciaEsperadaSobreElPrecioBase;
	}

	@Override
	public Boolean ofertar(double incremento, Usuario ofertante) {
		this.extenderTiempoSegunCategoria();
		return super.ofertar(incremento, ofertante);
	}

	@Override
	public Boolean ofertarAutomaticamente(double tope, double incremento,
			Usuario ofertante) {
		this.extenderTiempoSegunCategoria();
		return super.ofertarAutomaticamente(tope, incremento, ofertante);
	}
	
	def void extenderTiempoSegunCategoria() {
		if (this.categoria != null) {

			long tiempoFinalizacion = finalizacion.getTime();
			
			Timestamp tiempoExtendido = new Timestamp (tiempoFinalizacion + categoria.tiempoAExtenderEnSegundosPorOferta * 1000);

			if (finalizacionOriginal == null) finalizacionOriginal = finalizacion
			finalizacion = tiempoExtendido;
		}
	}

	
	
	@Override
	public void finalizarSubasta() {
		
		if ( hayUnGanador() ) {
			categoria.actualizarTiempoOfertaConNuevoDato( tiempoFinalizacionOriginal(), finalizacion, cantidadDeOfertasRealizadas() );
			categoria.actualizarDescuentoOptimoConNuevoDato(this.densidadDePrecioActual(), precioActual(), valorFinalEsperado() )
			categoria.actualizarCantidadDeOfertasPromedioConNuevoDato(cantidadDeOfertasRealizadas() )
		}
		super.finalizarSubasta();
	}
	
	def Timestamp tiempoFinalizacionOriginal() {
		return (finalizacionOriginal == null)? finalizacion : finalizacionOriginal;
	}
	
}
