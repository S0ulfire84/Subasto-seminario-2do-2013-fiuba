package subasto6



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AdvertenciaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Advertencia.list(params), model:[advertenciaInstanceCount: Advertencia.count()]
    }

    def show(Advertencia advertenciaInstance) {
        respond advertenciaInstance
    }

    def create() {
        respond new Advertencia(params)
    }

    @Transactional
    def save(Advertencia advertenciaInstance) {
        if (advertenciaInstance == null) {
            notFound()
            return
        }

        if (advertenciaInstance.hasErrors()) {
            respond advertenciaInstance.errors, view:'create'
            return
        }

        advertenciaInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'advertenciaInstance.label', default: 'Advertencia'), advertenciaInstance.id])
                redirect advertenciaInstance
            }
            '*' { respond advertenciaInstance, [status: CREATED] }
        }
    }

    def edit(Advertencia advertenciaInstance) {
        respond advertenciaInstance
    }

    @Transactional
    def update(Advertencia advertenciaInstance) {
        if (advertenciaInstance == null) {
            notFound()
            return
        }

        if (advertenciaInstance.hasErrors()) {
            respond advertenciaInstance.errors, view:'edit'
            return
        }

        advertenciaInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Advertencia.label', default: 'Advertencia'), advertenciaInstance.id])
                redirect advertenciaInstance
            }
            '*'{ respond advertenciaInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Advertencia advertenciaInstance) {

        if (advertenciaInstance == null) {
            notFound()
            return
        }

        advertenciaInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Advertencia.label', default: 'Advertencia'), advertenciaInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'advertenciaInstance.label', default: 'Advertencia'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
