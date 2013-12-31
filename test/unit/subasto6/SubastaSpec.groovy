package subasto6

import java.sql.Timestamp;

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Subasta)
class SubastaSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }
	
	def "Creacion de una subasta"() {
		when:
			Subasta s = new Subasta(precioBase:10);
		
		then:
			s.ofertasRealizadas == [];
			s.precioBase == 10;
			s.precioActual() == 10;
			s.ofertasAutomaticas.empty == true
	}
	
	def "Probando ofertar y subir el precio actual"() {
		when:
			Subasta s = new Subasta(precioBase:10);
			s.ofertar(10, new Usuario() );
		
		then:
			s.precioBase == 10;
			s.precioActual() == 20;
	}
	
	def "Probando ofertar menos que el precio actual"() {
		when:		
			Subasta s = new Subasta(precioBase:10);
			s.ofertar(-2, new Usuario() );
		
		then:
			s.precioBase == 10;
			s.precioActual() == 10;
	}
	
	def "Viendo quien va ganando actualmente"() {
		when:
			Subasta s = new Subasta(precioBase:10);
			Usuario jose = new Usuario(nombre:"Jose", apellido:"Perez", username:"Josepe");
			
		then:
			s.quienVaGanando() == null
			s.ofertar(10, jose)
			s.quienVaGanando() == "Josepe";
	}
	
	def "Probando con mas de un ofertante"() {
		when:
			Subasta s = new Subasta(precioBase:10);
			Usuario jose = new Usuario(nombre:"Jose", apellido:"Perez", username:"Josepe");
			Usuario pepe = new Usuario(nombre:"Pepe", apellido:"Gomez", username:"Pepito75");
			
		then:
			s.quienVaGanando() == null
			s.ofertar(10, jose)
			s.quienVaGanando() == "Josepe";
			
			s.ofertar(5, pepe)
			s.quienVaGanando() == "Pepito75";
			
			s.ofertar(5, pepe) //Pepe deberia poder ofertar si esta ganando?
			s.quienVaGanando() == "Pepito75";
			
			s.ofertar(10, jose);
			s.quienVaGanando() == "Josepe";
			
			s.precioActual() == 40;
	}
	
	def "Prueba basica de oferta automatica"() {
		when:
			Subasta s = new Subasta(precioBase:10);
			Usuario jose = new Usuario(nombre:"Jose", apellido:"Perez", username:"Josepe");
			
			s.ofertarAutomaticamente(20, 5, jose)
		then:
			s.quienVaGanando() == "Josepe"
			s.precioActual() == 15
			
	}
	
	
	
	def "Prueba con un ofertante automatico y uno normal"() {
		when:
			Subasta s = new Subasta(precioBase:10);
			Usuario jose = new Usuario(nombre:"Jose", apellido:"Perez", username:"Josepe");
			Usuario pepe = new Usuario(nombre:"Pepe", apellido:"Gomez", username:"Pepito75");
			s.ofertarAutomaticamente(50, 5, jose)
			s.ofertar(1, pepe)
		then:
			s.quienVaGanando() == "Josepe"
	}
	
	def "Dos ofertas automaticas se pelean entre si"() {
		when:
			Subasta s = new Subasta(precioBase:10);
			Usuario jose = new Usuario(nombre:"Jose", apellido:"Perez", username:"Josepe");
			Usuario pepe = new Usuario(nombre:"Pepe", apellido:"Gomez", username:"Pepito75");
			s.ofertarAutomaticamente(50, 5, jose)
			s.ofertarAutomaticamente(70, 5, pepe)
		then:
			//Pepe quiere ofertar hasta maximo 50, pero Jose oferto primero, entonces 
			//Jose hizo la oferta de 50. Como Pepe no quiere superar los 50, no oferta mas
			s.quienVaGanando() == "Pepito75"
			s.precioActual() == 50
	}
	
	def "Pepito no supera su maximo"() {
		when:
			Subasta s = new Subasta(precioBase:10);
			Usuario jose = new Usuario(nombre:"Jose", apellido:"Perez", username:"Josepe");
			Usuario pepe = new Usuario(nombre:"Pepe", apellido:"Gomez", username:"Pepito75");
			s.ofertarAutomaticamente(51, 5, jose)
			s.ofertarAutomaticamente(70, 5, pepe)
		then:
			// Pepe quiere ofertar hasta maximo 51, pero Jose oferto primero, entonces
			// Jose hizo la oferta de 50. Como Pepe tiene un incremento de 5 (solo oferta 5 pesos
			// mas cada vez), como no quiere superar los 51, no oferta mas
			s.quienVaGanando() == "Pepito75"
			s.precioActual() == 50
	}
	
	def "El mejor sigue ganando"() {
		when:
			Subasta s = new Subasta(precioBase:10);
			Usuario jose = new Usuario(nombre:"Jose", apellido:"Perez", username:"Josepe");
			Usuario pepe = new Usuario(nombre:"Pepe", apellido:"Gomez", username:"Pepito75");
			s.ofertarAutomaticamente(55, 5, jose)
			s.ofertarAutomaticamente(70, 5, pepe)
		then:
			// Ahora Pepe oferta hasta maximo 55 y su incremento es de 5. Va a hacer la oferta de 55
			// Al hacer la oferta de 55, Pepe va a hacer la oferta de 60 y va ganar, pero ahora con precio 60
			s.quienVaGanando() == "Pepito75"
			s.precioActual() == 60
	}
	
	def "Tres automaticos"() {
		when:
			Subasta s = new Subasta(precioBase:10);
			Usuario jose = new Usuario(nombre:"Jose", apellido:"Perez", username:"Josepe");
			Usuario pepe = new Usuario(nombre:"Pepe", apellido:"Gomez", username:"Pepito75");
			Usuario tito = new Usuario(nombre:"Tito", apellido:"Ramirez", username:"Titus");
			s.ofertarAutomaticamente(55, 5, jose)
			s.ofertarAutomaticamente(70, 5, pepe)
			s.ofertarAutomaticamente(100, 5, tito)
		then:
			// Ahora Pepe oferta hasta maximo 55 y su incremento es de 5. Va a hacer la oferta de 55
			// Al hacer la oferta de 55, Pepe va a hacer la oferta de 60 y va ganar, pero ahora con precio 60
			s.quienVaGanando() == "Titus"
			s.precioActual() == 75
	}
	
	def "Una oferta mejor gana, aunque hayan automaticas"() {
		when:
			Subasta s = new Subasta(precioBase:10);
			Usuario jose = new Usuario(nombre:"Jose", apellido:"Perez", username:"Josepe");
			Usuario pepe = new Usuario(nombre:"Pepe", apellido:"Gomez", username:"Pepito75");
			s.ofertarAutomaticamente(20, 5, jose)
			s.ofertarAValor(55, pepe)
		then:
			// Ahora Pepe oferta hasta maximo 55 y su incremento es de 5. Va a hacer la oferta de 55
			// Al hacer la oferta de 55, Pepe va a hacer la oferta de 60 y va ganar, pero ahora con precio 60
			s.quienVaGanando() == "Pepito75"
			s.precioActual() == 55
	}
	
	def "Subasta esta finalizada"() {
		when:
			long ahora = System.currentTimeMillis();
			
			Timestamp haceDiezSegundos = new Timestamp (ahora - 10 * 1000); //10 segundos antes
			Timestamp haceDiezMinutos = new Timestamp ( ahora - 10 * 60 * 1000); //10 minutos antes
			Timestamp haceDiezHoras = new Timestamp ( ahora - 10 * 60 * 60 * 1000); //10 horas antes
			Timestamp haceDiezDias = new Timestamp ( ahora - 10 * 24 * 60 * 60 * 1000); //10 dias antes
			
			Timestamp dentroDeDiezSegundos = new Timestamp ( ahora + 10 * 1000); //10 segundos antes
			Timestamp dentroDeDiezMinutos = new Timestamp ( ahora + 10 * 60 * 1000); //10 minutos antes
			Timestamp dentroDeDiezHoras = new Timestamp ( ahora + 10 * 60 * 60 * 1000); //10 horas antes
			Timestamp dentroDeDiezDias = new Timestamp ( ahora + 10 * 24 * 60 * 60 * 1000); //10 dias antes
			
			Subasta s1 = new Subasta(precioBase:10, finalizacion:haceDiezSegundos);
			Subasta s2 = new Subasta(precioBase:10, finalizacion:haceDiezMinutos);
			Subasta s3 = new Subasta(precioBase:10, finalizacion:haceDiezHoras);
			Subasta s4 = new Subasta(precioBase:10, finalizacion:haceDiezDias);
			
			Subasta s5 = new Subasta(precioBase:10, finalizacion:dentroDeDiezSegundos);
			Subasta s6 = new Subasta(precioBase:10, finalizacion:dentroDeDiezMinutos);
			Subasta s7 = new Subasta(precioBase:10, finalizacion:dentroDeDiezHoras);
			Subasta s8 = new Subasta(precioBase:10, finalizacion:dentroDeDiezDias);
		
		then:
			s1.estaFinalizada() == true;
			s2.estaFinalizada() == true;
			s3.estaFinalizada() == true;
			s4.estaFinalizada() == true;
			
			s5.estaFinalizada() == false;
			s6.estaFinalizada() == false;
			s7.estaFinalizada() == false;
			s8.estaFinalizada() == false;
	}
	
	def "Solo se puede ofertar si la subasta no finalizo"() {
		when:
			long ahora = System.currentTimeMillis();
		
			Timestamp haceDiezSegundos = new Timestamp (ahora - 10 * 1000); //10 segundos antes
			Timestamp dentroDeDiezSegundos = new Timestamp ( ahora + 10 * 1000); //10 segundos antes
			
			Usuario jose = new Usuario(nombre:"Jose", apellido:"Perez", username:"Josepe");
			
			Subasta s1 = new Subasta(precioBase:10, finalizacion:dentroDeDiezSegundos);
			Subasta s2 = new Subasta(precioBase:10, finalizacion:haceDiezSegundos);
			
			s1.ofertarAValor(20, jose);
			s2.ofertarAValor(20, jose);
				
		then:
		
			s1.hayUnGanador() == true;
			s2.hayUnGanador() == false;
	}
	
	def "Antes de terminar la subasta no se puede calificar y solo si hay un comprador"() {
		when:
			long ahora = System.currentTimeMillis();
		
			Timestamp haceDiezSegundos = new Timestamp (ahora - 10 * 1000); //10 segundos antes
			Timestamp dentroDeDiezSegundos = new Timestamp ( ahora + 10 * 1000); //10 segundos antes
			
			Usuario jose = new Usuario(nombre:"Jose", apellido:"Perez", username:"Josepe");
			
			Subasta s1 = new Subasta(precioBase:10, finalizacion:dentroDeDiezSegundos);
			Subasta s2 = new Subasta(precioBase:10, finalizacion:dentroDeDiezSegundos);
			
			Subasta s3 = new Subasta(precioBase:10, finalizacion:haceDiezSegundos);
			Subasta s4 = new Subasta(precioBase:10, finalizacion:dentroDeDiezSegundos);
			
			s2.ofertarAValor(20, jose);
			s4.ofertarAValor(20, jose);
			
			s4.cambiarFinalizacionA(haceDiezSegundos);
			
		then:
		
			s1.hayUnGanador() == false;
			s2.hayUnGanador() == true;
			s3.hayUnGanador() == false;
			s4.hayUnGanador() == true;
		
			s1.puedeCalificarVendedor() == false;
			s2.puedeCalificarVendedor() == false;
			s3.puedeCalificarVendedor() == false;
			s4.puedeCalificarVendedor() == true;
	}
	
	def "Si ya califico, no puede volver a calificar"() {
		when:
			long ahora = System.currentTimeMillis();
		
			Usuario jose = new Usuario(nombre:"Jose", apellido:"Perez", username:"Josepe");
			Usuario pepe = new Usuario(nombre:"Pepe", apellido:"Gomez", username:"Pepito75");
			
			Timestamp haceDiezSegundos = new Timestamp (ahora - 10 * 1000); //10 segundos antes
			Timestamp dentroDeDiezSegundos = new Timestamp ( ahora + 10 * 1000); //10 segundos antes
			
			Subasta s1 = new Subasta(vendedor:jose, precioBase:10, finalizacion:dentroDeDiezSegundos);
			Subasta s2 = new Subasta(vendedor:jose, precioBase:10, finalizacion:dentroDeDiezSegundos);
		
			s1.ofertarAValor(20, pepe)
			s2.ofertarAValor(20, pepe)
			
			s1.cambiarFinalizacionA(haceDiezSegundos)
			s2.cambiarFinalizacionA(haceDiezSegundos)
			
			Boolean resultadoCalificar = s1.calificarVendedor("vendedor poco confiable", -5, Transaccion.TRANSACCION_FALLIDA_ARTICULO_NO_COINCIDIA_CON_DESCRIPCION);
		then:
		
			resultadoCalificar == true;
		
			s1.estaFinalizada() == true;
			s1.hayUnGanador() == true;
			
			s1.puedeCalificarVendedor() == false;
			s1.puedeCalificarComprador() == true;
			
			s2.puedeCalificarVendedor() == true;
			s2.puedeCalificarComprador() == true;

	}
	
	def "Si las razones de las calificaciones difieren, enviar mail a administrador"() {
		when:
			long ahora = System.currentTimeMillis();
		
			Usuario jose = new Usuario(nombre:"Jose", apellido:"Perez", username:"Josepe");
			Usuario pepe = new Usuario(nombre:"Pepe", apellido:"Gomez", username:"Pepito75");
			
			Timestamp haceDiezSegundos = new Timestamp (ahora - 10 * 1000); //10 segundos antes
			Timestamp dentroDeDiezSegundos = new Timestamp ( ahora + 10 * 1000); //10 segundos antes
			
			Subasta s1 = new Subasta(vendedor:jose, precioBase:10, finalizacion:dentroDeDiezSegundos);
			Subasta s2 = new Subasta(vendedor:jose, precioBase:10, finalizacion:dentroDeDiezSegundos);
		
			s1.ofertarAValor(20, pepe)
			s2.ofertarAValor(20, pepe)
			
			s1.cambiarFinalizacionA(haceDiezSegundos)
			s2.cambiarFinalizacionA(haceDiezSegundos)
			
			Boolean resultadoCalificacion1 = s1.calificarVendedor("vendedor poco confiable", -5, Transaccion.TRANSACCION_FALLIDA_ARTICULO_NO_COINCIDIA_CON_DESCRIPCION);
			Boolean resultadoCalificacion2 = s1.calificarComprador("todo perfecto, muy responsable, la transaccion se concreto perfectamente", 5, Transaccion.TRANSACCION_EXITOSA_CONCRETADA)
			
			Boolean resultadoCalificacion3 = s2.calificarVendedor("muy buen vendedor. Muy recomendable", 5, Transaccion.TRANSACCION_EXITOSA_CONCRETADA);
			Boolean resultadoCalificacion4 = s2.calificarComprador("todo perfecto, muy responsable, la transaccion se concreto perfectamente", 5, Transaccion.TRANSACCION_EXITOSA_CONCRETADA)
			
		then:
		
			resultadoCalificacion1 == true;
			resultadoCalificacion2 == true;
			resultadoCalificacion3 == true;
			resultadoCalificacion4 == true;
			
			s1.calificacionesCoherentesEntreSi() == false
			s2.calificacionesCoherentesEntreSi() == true
	}
	

}
