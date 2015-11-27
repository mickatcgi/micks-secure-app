package micks.secure.app

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

import javax.servlet.http.HttpServletResponse

@Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_ANONYMOUS'])
class TodeRestController extends RestfulController {

    static responseFormats = ['json', 'xml']

    def springSecurityService

    TodeRestController() {
        super(Todo)
    }

    def index() {
        respond Todo.list()
    }

    def save(Todo todo) {
        todo.validate()
        if (!todo.hasErrors()){
            def user = springSecurityService.currentUser

            // If you don't flush you don't get dateCreated/lastUpdated fields returned.
            def newTodo = todo.save(failOnError: true, flush: true)
            log.info("Successfully saved Todo: ${newTodo.toString()}")
            respond newTodo, status: HttpServletResponse.SC_CREATED
        } else {
            // If a todo fails validation it will return an unprocessoble entry (422)
            // because it still has errors. To return the original todo you have to fix
            // it and revalidate it to remove the errors.
            logAllErrors(todo.errors)
            render(status: 418, text: todo.errors.fieldErrors.join(','))
        }
    }

    def update(Todo todo) {
        todo.validate()
        if (!todo.hasErrors()){
            def user = springSecurityService.currentUser

            // If you don't flush you don't get dateCreated/lastUpdated fields returned.
            def updatedTodo = todo.save(failOnError: true, flush: true)
            log.info("Successfully updated Todo: ${updatedTodo.toString()}")
            render(status: HttpServletResponse.SC_OK)
        } else {
            // If a todo fails validation it will return an unprocessoble entry (422)
            // because it still has errors. To return the original todo you have to fix
            // it and revalidate it to remove the errors.
            logAllErrors(todo.errors)
            render(status: 418, text: todo.errors.fieldErrors.join(','))
        }
    }

    private void logAllErrors(errorList) {
        errorList.eachWithIndex {
            error, index -> log.info("Validation error [${index}] = ${error}")
        }
    }

    /**
     * Save with validation failures to illustrate handing errors
     * @param todo
     * @return
     */
//    def save(Todo todo) {
//        todo.description = null // ERROR - fail validation
//        todo.validate()
//        if (!todo.hasErrors()){
//            def user = springSecurityService.currentUser
//
//            // If you don't flush you don't get dateCreated/lastUpdated fields returned.
//            def newTodo = todo.save(failOnError: true, flush: true)
//            log.info("Successfully saved Todo: ${newTodo.toString()}")
//            respond newTodo, status: 201
//        } else {
//            // If a todo fails validation it will return an unprocessoble entry (422)
//            // because it still has errors. To return the original todo you have to fix
//            // it and revalidate it to remove the errors.
//            todo.description = "Im now fixed..."
//            todo.validate()
//            log.info("Unable to save Todo. It has errors: ${todo.toString()}")
//            //respond todo, status: 418
//            //response.sendError(418, "You're hosed Mick")
//            render(status: 418, text: "You're buggered Mick")
//        }
//    }

}
