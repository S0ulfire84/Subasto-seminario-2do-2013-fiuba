package subasto6



import grails.test.mixin.*
import spock.lang.*

@TestFor(TransaccionController)
@Mock(Transaccion)
class TransaccionControllerSpec extends Specification {

	static scaffold = true
	
    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.transaccionInstanceList
            model.transaccionInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.transaccionInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def transaccion = new Transaccion()
            transaccion.validate()
            controller.save(transaccion)

        then:"The create view is rendered again with the correct model"
            model.transaccionInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            transaccion = new Transaccion(params)

            controller.save(transaccion)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/transaccion/show/1'
            controller.flash.message != null
            Transaccion.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def transaccion = new Transaccion(params)
            controller.show(transaccion)

        then:"A model is populated containing the domain instance"
            model.transaccionInstance == transaccion
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def transaccion = new Transaccion(params)
            controller.edit(transaccion)

        then:"A model is populated containing the domain instance"
            model.transaccionInstance == transaccion
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            status == 404

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def transaccion = new Transaccion()
            transaccion.validate()
            controller.update(transaccion)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.transaccionInstance == transaccion

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            transaccion = new Transaccion(params).save(flush: true)
            controller.update(transaccion)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/transaccion/show/$transaccion.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A 404 is returned"
            status == 404

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def transaccion = new Transaccion(params).save(flush: true)

        then:"It exists"
            Transaccion.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(transaccion)

        then:"The instance is deleted"
            Transaccion.count() == 0
            response.redirectedUrl == '/transaccion/index'
            flash.message != null
    }
}
