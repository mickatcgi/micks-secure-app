package micks.secure.app

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_ANONYMOUS'])
class TodoRestController extends RestfulController {

    static responseFormats = ['json', 'xml']

    TodoRestController() {
        super(Todo)
    }

//    def index() {
//        log.info("TodoRestController Index() working...")
//
//        respond {name: "Hello Kitty on " + new Date().toLocaleString() }
//    }
//
//    def todos() {
//
//        def todoList = Todo.list()
//        respond todoList
//    }
}
