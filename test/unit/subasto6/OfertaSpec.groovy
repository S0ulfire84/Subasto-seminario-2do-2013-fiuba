
package subasto6

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Oferta)
class OfertaSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Probando crear una oferta..."() {
		when:
			Usuario u = new Usuario(nombre:"Jose")//Mock();
			Oferta o = new Oferta(10, u)
		
		then:
			o.fechaYHora != null
			o.ofertante.nombre == "Jose"
			o.valor == 10
		
    }
	
}
