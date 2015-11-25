package micks.secure.app

import grails.plugin.springsecurity.annotation.Secured

class SecureController {

    @Secured('ROLE_ADMIN')
    def index() {
        if (isLoggedIn()) {
            String username = principal.username
            log.info("Principal username = ${username}")
            render 'ADMIN ROLE access only - only MICK with ROLE_ADMIN can view this page'
        } else {
            render 'Secure access only - user is not logged in'
        }
    }

    @Secured(['ROLE_USER', 'ROLE_ADMIN'])
    def userAction() {
        render 'USER ROLE access only - only logged in basic users can do this action (e.g. billybob)'
    }

    @Secured(['ROLE_ANONYMOUS', 'ROLE_USER', 'ROLE_ADMIN'])
    def anonymousAction() {
        render 'ANONYMOUS ROLE access only - anybody on the planet can do this action'
    }
}
