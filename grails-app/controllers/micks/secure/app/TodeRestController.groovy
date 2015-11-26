package micks.secure.app

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

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
        if (!todo.hasErrors()){
            def user = springSecurityService.currentUser

            // If you don't flush you don't get dateCreated/lastUpdated fields returned.
            def newTodo = todo.save(failOnError: true, flush: true)
            log.info("Successfully saved Todo: ${newTodo.toString()}")
            respond newTodo, status: 201
        } else {
            log.info("Unable to save Todo. It has errors: ${todo.toString()}")
            respond todo
        }
    }

}
