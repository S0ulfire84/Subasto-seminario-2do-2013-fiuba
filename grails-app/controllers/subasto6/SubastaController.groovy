package subasto6



import static org.springframework.http.HttpStatus.*

import java.awt.GraphicsConfiguration.DefaultBufferCapabilities;
import java.awt.TexturePaintContext.Int;
import java.awt.event.ItemEvent;

import org.springframework.aop.aspectj.RuntimeTestWalker.ThisInstanceOfResidueTestVisitor;

import grails.transaction.Transactional

@Transactional(readOnly = true)
class SubastaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", oferta:"POST"]

	Usuario usuarioLogueado = null
	List usuarios
	
	def beforeInterceptor = {
		if (session["usuario"] != null) {
			usuarioLogueado = session["usuario"]
		}
	}
	
    def index(Integer max) {
        
		if (params.selectUsuario != null) {
			Usuario usuario = Usuario.findByUsername(params.selectUsuario)
			usuarioLogueado = usuario;
			session["usuario"] = usuarioLogueado
			
			redirect controller:"subasta", action:"index"
		}
		
		params.max = Math.min(max ?: 10, 100)
		
		Subasta.list(params).each {
			it.estaFinalizada();
		}
		
        respond Subasta.list(params), model:[subastaInstanceCount: Subasta.count(), usuarios:Usuario.findAll(), usuarioLogueado:usuarioLogueado ]
    }

    def show(Subasta subastaInstance) {
		
		if (params.selectUsuario != null) {
			Usuario usuario = Usuario.findByUsername(params.selectUsuario)
			usuarioLogueado = usuario;
			session["usuario"] = usuarioLogueado 
			
			redirect subastaInstance
		}
		
		usuarios = Usuario.findAll()//.minus(usuarioLogueado)
		
		def subastaFinalizada = subastaInstance.estaFinalizada()
		
		def estadosPosiblesTransaccion = Transaccion.estadosPosibles()
		
		def tieneQueCalificarComprador = subastaInstance.puedeCalificarComprador() && usuarioLogueado?.esVendedorDeLaSubasta(subastaInstance)
		def tieneQueCalificarVendedor  = subastaInstance.puedeCalificarVendedor() && usuarioLogueado?.esGanadorDeLaSubasta(subastaInstance)
		
		[estadosPosiblesTransaccion: estadosPosiblesTransaccion, tieneQueCalificarComprador:tieneQueCalificarComprador, tieneQueCalificarVendedor: tieneQueCalificarVendedor, subastaInstance:subastaInstance, usuarios : usuarios, subastaFinalizada: subastaFinalizada, usuarioLogueado: usuarioLogueado]
		
        //respond subastaInstance
		
    }
	
	def calificarVendedor(Subasta subastaInstance) {
		String estadoTransaccion = params.selectEstadoTransaccion;
		String comentario = params.comentario
		
		int calificacion = new BigInteger(params.puntaje).intValue()
		
		System.out.println ("calificando al vendedor!")
		
		subastaInstance.calificarVendedor(comentario, calificacion, estadoTransaccion)
		
		request.withFormat {
			form {
				flash.message = "Se ha calificado al vendedor"
				redirect subastaInstance
			}
		}

	}
	
	
	def calificarComprador(Subasta subastaInstance) {
		String estadoTransaccion = params.selectEstadoTransaccion;
		String comentario = params.comentario
		
		int calificacion = new BigInteger(params.puntaje).intValue()
				
		subastaInstance.calificarComprador(comentario, calificacion, estadoTransaccion)
		
		System.out.println ("calificando al comprador!")
		
		request.withFormat {
			form {
				flash.message = "Se ha calificado al comprador"
				redirect subastaInstance
			}
		}

	}
	
	
	def oferta(Subasta subastaInstance) {
		
		BigInteger oferta = new BigInteger(params.inputOferta);
		
		Usuario usuario = Usuario.findByUsername(params.selectUsuario)//Usuario.getAt(params.selectUsuario)//new BigInteger(params.selectUsuario);
		
		
		
		//Usuario user = Usuario.fi
		//Usuario lopez = Usuario.findByUsername("Pepe123")
		//subastaInstance.ofertar(25, lopez);
		//subastaInstance.save(flush:true)
		
		String mensaje;
		
		if (params.ofertaAutomatica == "1") {
			// El valor indicado es el valor tope de una oferta automatica
			mensaje = "Se ha realizado la oferta automatica por "+oferta.toString()+"\$ARS"
			subastaInstance.ofertarAutomaticamente(oferta, 10, usuario);
		} else {
			// El valor indicado es el valor de la oferta
			mensaje = "Se ha realizado la oferta por "+oferta.toString()+"\$ARS"
			subastaInstance.ofertarAValor(oferta, usuario)
		}
		
		request.withFormat {
			form {
				flash.message = mensaje// "Se ha ofertado exitosamente."//message(code: 'default.oferta.message', args: [message(code: 'subastaInstance.label', default: 'Subasta'), subastaInstance.id])
				redirect subastaInstance
			}
			'*' { respond subastaInstance }
		}
		
		//redirect subastaInstance
	}

    def create() {
        respond new Subasta(params)
    }

    @Transactional
    def save(Subasta subastaInstance) {
        if (subastaInstance == null) {
            notFound()
            return
        }

        if (subastaInstance.hasErrors()) {
            respond subastaInstance.errors, view:'create'
            return
        }

        subastaInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'subastaInstance.label', default: 'Subasta'), subastaInstance.id])
                redirect subastaInstance
            }
            '*' { respond subastaInstance, [status: CREATED] }
        }
    }

    def edit(Subasta subastaInstance) {
        respond subastaInstance
    }

    @Transactional
    def update(Subasta subastaInstance) {
        if (subastaInstance == null) {
            notFound()
            return
        }

        if (subastaInstance.hasErrors()) {
            respond subastaInstance.errors, view:'edit'
            return
        }

        subastaInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Subasta.label', default: 'Subasta'), subastaInstance.id])
                redirect subastaInstance
            }
            '*'{ respond subastaInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Subasta subastaInstance) {

        if (subastaInstance == null) {
            notFound()
            return
        }

        subastaInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Subasta.label', default: 'Subasta'), subastaInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'subastaInstance.label', default: 'Subasta'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
