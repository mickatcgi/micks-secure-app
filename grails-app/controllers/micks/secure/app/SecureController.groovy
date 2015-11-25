package micks.secure.app

import grails.plugin.springsecurity.annotation.Secured

class SecureController {

    @Secured('ROLE_ADMIN')
    def index() {
        if (isLoggedIn()) {
            render 'Secure access only - only MICK with ROLE_ADMIN can view this page'
        } else {
            render 'Secure access only - user is not logged in'
        }
    }

    def doSomething() {
        render 'InSecure access only - anybody on the planet can do something'
    }
}
