package subasto6



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UsuarioController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	Usuario usuarioLogueado = null
	def beforeInterceptor = {
		if (session["usuario"] != null) {
			usuarioLogueado = session["usuario"]
		}
	}
	
	def miSubasto() {
		
		if (params.selectUsuario != null) {
			Usuario usuario = Usuario.findByUsername(params.selectUsuario)
			usuarioLogueado = usuario;
			session["usuario"] = usuarioLogueado
			
			redirect controller:"usuario", action:"miSubasto"
		}
		
		if (usuarioLogueado == null) redirect controller:"Subasta"
		
		[usuarios:Usuario.findAll(), usuarioLogueado:usuarioLogueado, subastasDelUsuario: usuarioLogueado.subastas(), tareasDelUsuario: usuarioLogueado.pendientesDeCalificar(), calificacionesDelUsuario:usuarioLogueado.calificaciones() ]
	}
	
    def index(Integer max) {
		
		if (params.selectUsuario != null) {
			Usuario usuario = Usuario.findByUsername(params.selectUsuario)
			usuarioLogueado = usuario;
			session["usuario"] = usuarioLogueado
			
			redirect controller:"usuario", action:"index"
		}
		
		
        params.max = Math.min(max ?: 10, 100)
        respond Usuario.list(params), model:[usuarioInstanceCount: Usuario.count(), usuarioLogueado: usuarioLogueado, usuarios: Usuario.findAll() ]
    }

    def show(Usuario usuarioInstance) {
        respond usuarioInstance
    }
	
	def login() {
		render view:"login"
	}
	
	def autenticando() {
		
		String user = params.user;
		String pass = params.pass;
		
		Usuario u = Usuario.findByUsername(user)
		
		if (u != null && u.password == pass) {
			session["usuario"] = u;
			redirect controller:"Subasta"
		} else {
			flash.message = "Datos incorrectos, por favor intente de nuevo"
			redirect action:"login"
		}
		
	}

    def create() {
        respond new Usuario(params)
    }

    @Transactional
    def save(Usuario usuarioInstance) {
        if (usuarioInstance == null) {
            notFound()
            return
        }

        if (usuarioInstance.hasErrors()) {
            respond usuarioInstance.errors, view:'create'
            return
        }

        usuarioInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'usuarioInstance.label', default: 'Usuario'), usuarioInstance.id])
                redirect usuarioInstance
            }
            '*' { respond usuarioInstance, [status: CREATED] }
        }
    }

    def edit(Usuario usuarioInstance) {
        respond usuarioInstance
    }

    @Transactional
    def update(Usuario usuarioInstance) {
        if (usuarioInstance == null) {
            notFound()
            return
        }

        if (usuarioInstance.hasErrors()) {
            respond usuarioInstance.errors, view:'edit'
            return
        }

        usuarioInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Usuario.label', default: 'Usuario'), usuarioInstance.id])
                redirect usuarioInstance
            }
            '*'{ respond usuarioInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Usuario usuarioInstance) {

        if (usuarioInstance == null) {
            notFound()
            return
        }

        usuarioInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Usuario.label', default: 'Usuario'), usuarioInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuarioInstance.label', default: 'Usuario'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
