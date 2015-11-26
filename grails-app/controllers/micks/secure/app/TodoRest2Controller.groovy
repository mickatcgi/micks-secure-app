package micks.secure.app

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_ANONYMOUS'])
class TodoRest2Controller {

    static responseFormats = ['json', 'xml']

    def springSecurityService

    def index() {
        respond Todo.list()
    }
}
