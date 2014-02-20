package subasto6



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SubastaPromocionalController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	Usuario usuarioLogueado;
	
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond SubastaPromocional.list(params), model:[subastaPromocionalInstanceCount: SubastaPromocional.count()]
    }

    def show(SubastaPromocional subastaPromocionalInstance) {
        
		if (params.selectUsuario != null) {
			Usuario usuario = Usuario.findByUsername(params.selectUsuario)
			usuarioLogueado = usuario;
			session["usuario"] = usuarioLogueado 
			
			redirect subastaPromocionalInstance
		}
		
		def usuarios = Usuario.findAll()//.minus(usuarioLogueado)
		
		def subastaFinalizada = subastaPromocionalInstance.estaFinalizada()
		
		def estadosPosiblesTransaccion = Transaccion.estadosPosibles()
		
		def tieneQueCalificarComprador = subastaPromocionalInstance.puedeCalificarComprador() && usuarioLogueado?.esVendedorDeLaSubasta(subastaPromocionalInstance)
		def tieneQueCalificarVendedor  = subastaPromocionalInstance.puedeCalificarVendedor() && usuarioLogueado?.esGanadorDeLaSubasta(subastaPromocionalInstance)

		[estadosPosiblesTransaccion: estadosPosiblesTransaccion, tieneQueCalificarComprador:tieneQueCalificarComprador, tieneQueCalificarVendedor: tieneQueCalificarVendedor, subastaPromocionalInstance:subastaPromocionalInstance, usuarios : usuarios, subastaFinalizada: subastaFinalizada, usuarioLogueado: usuarioLogueado]

    }
	
	
	def calificarVendedor(Subasta subastaPromocionalInstance) {
		String estadoTransaccion = params.selectEstadoTransaccion;
		String comentario = params.comentario
		
		int calificacion = new BigInteger(params.puntaje).intValue()
		
		System.out.println ("calificando al vendedor!")
		
		subastaPromocionalInstance.calificarVendedor(comentario, calificacion, estadoTransaccion)
		
		request.withFormat {
			form {
				flash.message = "Se ha calificado al vendedor"
				redirect subastaPromocionalInstance
			}
		}

	}
	
	
	def calificarComprador(Subasta subastaPromocionalInstance) {
		String estadoTransaccion = params.selectEstadoTransaccion;
		String comentario = params.comentario
		
		int calificacion = new BigInteger(params.puntaje).intValue()
				
		subastaPromocionalInstance.calificarComprador(comentario, calificacion, estadoTransaccion)
		
		System.out.println ("calificando al comprador!")
		
		request.withFormat {
			form {
				flash.message = "Se ha calificado al comprador"
				redirect subastaPromocionalInstance
			}
		}

	}
	
	
	def oferta(Subasta subastaPromocionalInstance) {
		
		int of = params.inputOferta.toFloat().toInteger().intValue()
		
		BigInteger oferta = new BigInteger(of);
		
		Usuario usuario = Usuario.findByUsername(params.selectUsuario)//Usuario.getAt(params.selectUsuario)//new BigInteger(params.selectUsuario);
		
		
		String mensaje;
		
		if (params.ofertaAutomatica == "1") {
			// El valor indicado es el valor tope de una oferta automatica
			mensaje = "Se ha realizado la oferta automatica por "+oferta.toString()+"\$ARS"
			subastaPromocionalInstance.ofertarAutomaticamente(oferta, 10, usuario);
		} else {
			// El valor indicado es el valor de la oferta
			mensaje = "Se ha realizado la oferta por "+oferta.toString()+"\$ARS"
			subastaPromocionalInstance.ofertarAValor(oferta, usuario)
		}
		
		request.withFormat {
			form {
				flash.message = mensaje// "Se ha ofertado exitosamente."//message(code: 'default.oferta.message', args: [message(code: 'subastaPromocionalInstance.label', default: 'Subasta'), subastaPromocionalInstance.id])
				redirect subastaPromocionalInstance
			}
			'*' { respond subastaPromocionalInstance }
		}
		
	}

	

    def create() {
		[subastaPromocionalInstance: new SubastaPromocional(params), categorias:Categoria.findAll(), vendedores: Usuario.findAll() ];
    }
	
	def valorInicial() {
		long idCat = params.idCat.toFloat().toInteger().intValue()
		
		BigDecimal precioBaseSugerido = (params.precioBaseSugerido?:0.toFloat()).toBigDecimal();
		
		Categoria cat = Categoria.get(idCat);
		
		BigDecimal desc = cat.descuentoOptimo;
		
		def resultado = Math.round( precioBaseSugerido * (1-desc)  * 100) / 100
		
		render resultado
	}
	
	def valorFinal() {
		BigDecimal precioBaseSugerido = (params.precioBaseSugerido?:0.toFloat()).toBigDecimal();
		BigDecimal porcentajeGananciaEsperada = params.porcentajeGananciaEsperada.toFloat().toBigDecimal()/100;
		
		def resultado = Math.round( precioBaseSugerido * (1+porcentajeGananciaEsperada)  * 100) / 100
		
		render resultado
	}
	

    @Transactional
    def save(SubastaPromocional subastaPromocionalInstance) {
        if (subastaPromocionalInstance == null) {
            notFound()
            return
        }

        if (subastaPromocionalInstance.hasErrors()) {
            respond subastaPromocionalInstance.errors, view:'create'
            return
        }
		
		subastaPromocionalInstance.asignarPrecioPromocion()
        subastaPromocionalInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'subastaPromocionalInstance.label', default: 'SubastaPromocional'), subastaPromocionalInstance.id])
                redirect subastaPromocionalInstance
            }
            '*' { respond subastaPromocionalInstance, [status: CREATED] }
        }
    }

    def edit(SubastaPromocional subastaPromocionalInstance) {
        respond subastaPromocionalInstance
    }

    @Transactional
    def update(SubastaPromocional subastaPromocionalInstance) {
        if (subastaPromocionalInstance == null) {
            notFound()
            return
        }

        if (subastaPromocionalInstance.hasErrors()) {
            respond subastaPromocionalInstance.errors, view:'edit'
            return
        }

        subastaPromocionalInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'SubastaPromocional.label', default: 'SubastaPromocional'), subastaPromocionalInstance.id])
                redirect subastaPromocionalInstance
            }
            '*'{ respond subastaPromocionalInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(SubastaPromocional subastaPromocionalInstance) {

        if (subastaPromocionalInstance == null) {
            notFound()
            return
        }

        subastaPromocionalInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'SubastaPromocional.label', default: 'SubastaPromocional'), subastaPromocionalInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'subastaPromocionalInstance.label', default: 'SubastaPromocional'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
