package subasto6



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TransaccionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Transaccion.list(params), model:[transaccionInstanceCount: Transaccion.count()]
    }

    def show(Transaccion transaccionInstance) {
        respond transaccionInstance
    }

    def create() {
        respond new Transaccion(params)
    }

    @Transactional
    def save(Transaccion transaccionInstance) {
        if (transaccionInstance == null) {
            notFound()
            return
        }

        if (transaccionInstance.hasErrors()) {
            respond transaccionInstance.errors, view:'create'
            return
        }

        transaccionInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'transaccionInstance.label', default: 'Transaccion'), transaccionInstance.id])
                redirect transaccionInstance
            }
            '*' { respond transaccionInstance, [status: CREATED] }
        }
    }

    def edit(Transaccion transaccionInstance) {
        respond transaccionInstance
    }

    @Transactional
    def update(Transaccion transaccionInstance) {
        if (transaccionInstance == null) {
            notFound()
            return
        }

        if (transaccionInstance.hasErrors()) {
            respond transaccionInstance.errors, view:'edit'
            return
        }

        transaccionInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Transaccion.label', default: 'Transaccion'), transaccionInstance.id])
                redirect transaccionInstance
            }
            '*'{ respond transaccionInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Transaccion transaccionInstance) {

        if (transaccionInstance == null) {
            notFound()
            return
        }

        transaccionInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Transaccion.label', default: 'Transaccion'), transaccionInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'transaccionInstance.label', default: 'Transaccion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
