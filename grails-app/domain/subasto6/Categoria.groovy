package subasto6

import java.sql.Timestamp;

class Categoria {
	
	String nombre;
	int tiempoAExtenderEnSegundosPorOferta = 30 // por default cada vez que se oferta, el tiempo se extiende en 30 segundos
	BigDecimal descuentoOptimo = 0.1; // por default, el descuento es del 10%, o sea que sale un 90% del precio elegido por el vendedor 
	BigDecimal densidadPromedioDeOfertas = 10;
	BigDecimal cantidadDeOfertasPromedio = 3;
	
    public Categoria(String nombre) {
		super();
		this.nombre = nombre;
    }

	static constraints = {
		descuentoOptimo (nullable: false, min: 0.0, max: 1.0, scale: 2)
    }
	
	def void actualizarDensidadPromedioDeOfertasConNuevoDato(BigDecimal nuevaDensidad) {
		BigDecimal porcentajeVariacion = 0.2
		densidadPromedioDeOfertas = densidadPromedioDeOfertas * (1-porcentajeVariacion) + nuevaDensidad * porcentajeVariacion
		
		try {
			save flush:true
		} catch (MissingMethodException e) {
			// Esto es porque los mocks de los tests parece que no pueden hacer save()
		}
		
	}
	
	def actualizarCantidadDeOfertasPromedioConNuevoDato(int cantidadDeOfertas) {
		// Afecto la cantidad de segundos por oferta de la categoria en un 20% de lo sucedido en esta subasta
		BigDecimal porcentajeVariacion = 0.2
		
		cantidadDeOfertasPromedio = Math.round(cantidadDeOfertasPromedio * (1-porcentajeVariacion) + cantidadDeOfertas * porcentajeVariacion);
		
		try {
			save flush:true
		} catch (MissingMethodException e) {
			// Esto es porque los mocks de los tests parece que no pueden hacer save()
		}
	}
	
	def actualizarTiempoOfertaConNuevoDato(Timestamp finalizacionOriginal, Timestamp finalizacionExtendida, int cantidadDeOfertasRealizadas) {
		long segundosFinOriginal = Math.floor(finalizacionOriginal.getTime()/1000);
		long segundosFinExtendida = Math.floor(finalizacionExtendida.getTime()/1000);
		
		long segundosTotalesExtendidos = segundosFinExtendida - segundosFinOriginal;
		
		int cantidadDeSegundosPorOferta = segundosTotalesExtendidos/cantidadDeOfertasPromedio;
		
		// Afecto la cantidad de segundos por oferta de la categoria en un 20% de lo sucedido en esta subasta
		BigDecimal porcentajeVariacion = 0.2
		
		tiempoAExtenderEnSegundosPorOferta = Math.round(tiempoAExtenderEnSegundosPorOferta * (1-porcentajeVariacion) + cantidadDeSegundosPorOferta * porcentajeVariacion);
		
		try {
			save flush:true
		} catch (MissingMethodException e) {
			// Esto es porque los mocks de los tests parece que no pueden hacer save()
		}
		
		
	}
	
	def actualizarDescuentoOptimoConNuevoDato(BigDecimal densidadDeOfertasDelNuevoDato, BigDecimal precioVentaFinal, BigDecimal valorEsperadoVentaFinal) {
		
		if (precioVentaFinal < valorEsperadoVentaFinal) {
			// Solo necesito actualizar el descuento optimo si no se logro vender al valor esperado
			
			// Pueden pasar dos cosas:
			// 1- No logro vender al valor esperado pero tuvo buenas ofertas: estamos siendo demasiado generosos en el descuento y no llega a vender a buen valor
			// 2- No logro vender al valor esperado y no tuvo buenas ofertas: el descuento no es atractivo.
			
			// En cualquiera de los dos casos, el aumento o disminucion del descuento tiene que ser proporcional a la diferencia entre la densidad de ofertas promedio y la obtenida.
			// (si estuvo muy lejos del promedio, tiene que variar mas rapido)
			
			BigDecimal descuentoOptimoNuevoDato = descuentoOptimo + descuentoOptimo * (densidadPromedioDeOfertas - densidadDeOfertasDelNuevoDato)/Math.max(densidadDeOfertasDelNuevoDato, densidadPromedioDeOfertas)		

			
			// Aun asi, toda esa informacion se pondera a un 20% para que una subasta sola no afecte completamente el estimador de la categoria.
			
			BigDecimal porcentajeVariacion = 0.2
			descuentoOptimo = descuentoOptimo * (1-porcentajeVariacion) + porcentajeVariacion * descuentoOptimoNuevoDato;
			
			System.out.println("descuentoOptimoNuevoDato: "+descuentoOptimoNuevoDato);
			System.out.println("descuentoOptimo: "+descuentoOptimo);
			
			try {
			save flush:true
			} catch (MissingMethodException e) {
				// Esto es porque los mocks de los tests parece que no pueden hacer save()
			}
		}
		
	}
	
	def String toString() {
		return nombre;
	}
}
