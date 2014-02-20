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
	
	void "Al terminar una subasta con ofertas menores al precio base sugerido, aumenta el descuento de la categoria"() {
		when:
			long ahora = System.currentTimeMillis();
			
			Usuario jose = new Usuario(nombre:"Jose", apellido:"Perez", username:"Josepe");
			Usuario pablo = new Usuario(nombre:"Pablo", apellido:"Rodriguez", username:"pablo123");
			Usuario luis = new Usuario(nombre:"Luis", apellido:"Gutierrez", username:"luisito");
			
			Timestamp dentroDeDiezSegundos = new Timestamp (ahora + 10 * 1000);
			Timestamp haceDiezSegundos = new Timestamp (ahora - 10 * 1000);
						
			Categoria zapatos = new Categoria("Zapatos")
			
			SubastaPromocional s = new SubastaPromocional(precioBase:3000, vendedor:jose, finalizacion:dentroDeDiezSegundos, categoria:zapatos);
			s.asignarPrecioPromocion()
			
			s.ofertarAValor(2800, pablo)
			s.ofertarAValor(2900, luis)
			
			s.cambiarFinalizacionA(haceDiezSegundos)
			
			
		then:
			// Hubo una oferta, el tiempo se extendio desde +10 seg a +40 seg pero al finalizarla la pisamos con -10seg, 
			// entonces el tiempo entre el original (+10) y el tiempo extendido (-10) es de -20 seg. Esto afecta al tiempo
			// de la categoria en un 20%, esto es un 80% de 30 seg y un 20% de -20 seg = 24 seg + -4 seg = 20 seg

			// El descuento default de la categoria es de un 10%. Esto es, se ofrece en 2700.
			// Se esperaba obtener un 20% sobre el valor base original, asi que se espera obtener 3600.
			
			// Hay dos ofertas, el precio final queda en 2900, menos que el precio base. 
			// Entonces el descuento esta siendo poco atractivo y debe subir respecto del valor anterior.
			
			zapatos.descuentoOptimo == 0.1
			
			s.estaFinalizada() == true; // este es el punto que la finaliza
			
			zapatos.descuentoOptimo > 0.1
	}
	
	void "Al terminar una subasta con ofertas mayores al precio base sugerido pero menores al precio de venta esperado, disminuye el descuento de la categoria"() {
		when:
			long ahora = System.currentTimeMillis();
			
			Usuario jose = new Usuario(nombre:"Jose", apellido:"Perez", username:"Josepe");
			Usuario pablo = new Usuario(nombre:"Pablo", apellido:"Rodriguez", username:"pablo123");
			Usuario luis = new Usuario(nombre:"Luis", apellido:"Gutierrez", username:"luisito");
			
			Timestamp dentroDeDiezSegundos = new Timestamp (ahora + 10 * 1000);
			Timestamp haceDiezSegundos = new Timestamp (ahora - 10 * 1000);
						
			Categoria zapatos = new Categoria("Zapatos")
			
			SubastaPromocional s = new SubastaPromocional(precioBase:3000, vendedor:jose, finalizacion:dentroDeDiezSegundos, categoria:zapatos);
			s.asignarPrecioPromocion()
			
			s.ofertarAValor(2800, pablo)
			s.ofertarAValor(3100, luis)
			
			s.cambiarFinalizacionA(haceDiezSegundos)
			
			
		then:
			// Hubo una oferta, el tiempo se extendio desde +10 seg a +40 seg pero al finalizarla la pisamos con -10seg, 
			// entonces el tiempo entre el original (+10) y el tiempo extendido (-10) es de -20 seg. Esto afecta al tiempo
			// de la categoria en un 20%, esto es un 80% de 30 seg y un 20% de -20 seg = 24 seg + -4 seg = 20 seg

			// El descuento default de la categoria es de un 10%. Esto es, se ofrece en 2700.
			// Se esperaba obtener un 20% sobre el valor base original, asi que se espera obtener 3600.
			
			// Hay dos ofertas, el precio final queda en 2900, menos que el precio base. 
			// Entonces el descuento esta siendo poco atractivo y debe subir respecto del valor anterior.
			
			zapatos.descuentoOptimo == 0.1
			
			s.estaFinalizada() == true; // este es el punto que la finaliza
			
			zapatos.descuentoOptimo < 0.1
	}
	
}
