package subasto6



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CalificacionController {
	
	static scaffold = true

    /*static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Calificacion.list(params), model:[calificacionInstanceCount: Calificacion.count()]
    }

    def show(Calificacion calificacionInstance) {
        respond calificacionInstance
    }

    def create() {
        respond new Calificacion(params)
    }

    @Transactional
    def save(Calificacion calificacionInstance) {
        if (calificacionInstance == null) {
            notFound()
            return
        }

        if (calificacionInstance.hasErrors()) {
            respond calificacionInstance.errors, view:'create'
            return
        }

        calificacionInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'calificacionInstance.label', default: 'Calificacion'), calificacionInstance.id])
                redirect calificacionInstance
            }
            '*' { respond calificacionInstance, [status: CREATED] }
        }
    }

    def edit(Calificacion calificacionInstance) {
        respond calificacionInstance
    }

    @Transactional
    def update(Calificacion calificacionInstance) {
        if (calificacionInstance == null) {
            notFound()
            return
        }

        if (calificacionInstance.hasErrors()) {
            respond calificacionInstance.errors, view:'edit'
            return
        }

        calificacionInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Calificacion.label', default: 'Calificacion'), calificacionInstance.id])
                redirect calificacionInstance
            }
            '*'{ respond calificacionInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Calificacion calificacionInstance) {

        if (calificacionInstance == null) {
            notFound()
            return
        }

        calificacionInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Calificacion.label', default: 'Calificacion'), calificacionInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'calificacionInstance.label', default: 'Calificacion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }*/
}
