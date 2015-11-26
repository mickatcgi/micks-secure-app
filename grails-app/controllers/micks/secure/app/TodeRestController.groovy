package micks.secure.app

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_ANONYMOUS'])
class TodeRestController {

    static responseFormats = ['json', 'xml']

    def springSecurityService

    def index() {
        respond Todo.list()
    }
}
