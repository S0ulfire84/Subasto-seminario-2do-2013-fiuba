package subasto6

import java.sql.Timestamp;

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(SubastaPromocional)
class SubastaPromocionalSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Funcionamiento basico"() {
		when:
			long ahora = System.currentTimeMillis();
			
			Usuario jose = new Usuario(nombre:"Jose", apellido:"Perez", username:"Josepe");
				
			Timestamp haceDiezSegundos = new Timestamp (ahora - 10 * 1000); //10 segundos antes
			
			Categoria zapatos = new Categoria("Zapatos")
			
			SubastaPromocional s = new SubastaPromocional(precioBase:10, vendedor:jose, finalizacion:haceDiezSegundos, categoria:zapatos);
			s.asignarPrecioPromocion()
		then:
			s.categoria != null
			s.precioActual() == 9
			s.porcentajeDescuento() == "10%"
			
    }
	
	void "Ofertar extiende el tiempo en 30 segundos"() {
		when:
			long ahora = System.currentTimeMillis();
			
			Usuario jose = new Usuario(nombre:"Jose", apellido:"Perez", username:"Josepe");
			Usuario pablo = new Usuario(nombre:"Pablo", apellido:"Rodriguez", username:"pablo123");
				
			Timestamp dentroDeDiezSegundos = new Timestamp (ahora + 10 * 1000); //dentro de 10 segundos
			Timestamp dentroDe39Segundos = new Timestamp (ahora + 39 * 1000);
			Timestamp dentroDe41Segundos = new Timestamp (ahora + 41 * 1000);
			
			Categoria zapatos = new Categoria("Zapatos")
			
			SubastaPromocional s = new SubastaPromocional(precioBase:10, vendedor:jose, finalizacion:dentroDeDiezSegundos, categoria:zapatos);
			s.asignarPrecioPromocion()
			
			Boolean resultadoOfertar = s.ofertar(5, pablo)
			
			
		then:
			s.categoria == zapatos;
			s.categoria.tiempoAExtenderEnSegundosPorOferta == 30
			s.estaFinalizada() == false;
			resultadoOfertar == true;
			
			s.finalizacion.compareTo(dentroDe39Segundos) > 0
			s.finalizacion.compareTo(dentroDe41Segundos) < 0
			
	}
	
	void "Al terminar una subasta con ofertas, actualiza el tiempo y el descuento realizado"() {
		when:
			long ahora = System.currentTimeMillis();
			
			Usuario jose = new Usuario(nombre:"Jose", apellido:"Perez", username:"Josepe");
			Usuario pablo = new Usuario(nombre:"Pablo", apellido:"Rodriguez", username:"pablo123");
			Usuario luis = new Usuario(nombre:"Luis", apellido:"Gutierrez", username:"luisito");
			
			Timestamp dentroDeDiezSegundos = new Timestamp (ahora + 10 * 1000);
			Timestamp haceDiezSegundos = new Timestamp (ahora - 10 * 1000);
						
			Categoria zapatos = new Categoria("Zapatos")
			
			SubastaPromocional s = new SubastaPromocional(precioBase:10, vendedor:jose, finalizacion:dentroDeDiezSegundos, categoria:zapatos);
			s.asignarPrecioPromocion()
			
			s.ofertarAValor(10, pablo)
			s.ofertarAValor(11, luis)
			
			s.cambiarFinalizacionA(haceDiezSegundos)
			
			
		then:
			//s.estaFinalizada() == true;
			
			// Hubo una oferta, el tiempo se extendio desde +10 seg a +40 seg pero al finalizarla la pisamos con -10seg, 
			// entonces el tiempo entre el original (+10) y el tiempo extendido (-10) es de -20 seg. Esto afecta al tiempo
			// de la categoria en un 20%, esto es un 80% de 30 seg y un 20% de -20 seg = 24 seg + -4 seg = 20 seg

			//zapatos.tiempoAExtenderEnSegundosPorOferta == 20;
			
			// Se puso el precio base en 10, pero al ser una Subasta promocional, el precio base es de 9. 
			// Se esperaba obtener un 20% sobre el valor base original, es decir, se puso a 10 pero y se esperaba que llegue a 12. 
			// La densidad promedio de ofertas por default es de 10. 
			// Al ejecutarse el remate, se hicieron dos ofertas, que llevaron el precio a un valor final de 11, no suficiente.
			
			// Se obtuvo una densidad de 11-9/2 = 1.
			// El descuento se altera proporcional a la diferencia entre el promedio y el obtenido.
			// El promedio es 10. El obtenido es 1, por lo tanto, es 0.1 + 0.1 * (10-1)/10 = 0.1 + 0.09 = 0.19.
			// Y este dato se toma solo en un 20% y se adosa al descuento optimo, que ahora seria de: 80% de 10 + 20% de 19 = 8+3.8 = 11.8
			// Ligeramente por arriba del valor de descuento que se ofrecia antes.
			s.precioBaseOriginal == 10
			Math.round(s.precioBase) == 9
			
			s.valorFinalEsperado() == 12
			
			zapatos.descuentoOptimo == 0.1
			zapatos.densidadPromedioDeOfertas == 10
			s.precioActual() == 11
			
			s.densidadDePrecioActual() == 1
			
			s.estaFinalizada() == true;
			
			s.densidadDePrecioActual() == 1
			 
			aIgualAB(zapatos.descuentoOptimo, 0.118) //zapatos.descuentoOptimo == 0.118 //Uso esto porque Grails no entiende correctamente como comparar decimales
	}
	
	def Boolean aIgualAB(BigDecimal a, BigDecimal b) {
		return Math.abs(a-b) < 0.0001
	}
	
}
