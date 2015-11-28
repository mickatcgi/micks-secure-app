package micks.secure.app


import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(RestInterceptor)
class RestInterceptorSpec extends Specification {

    def setup() {
    }

    def cleanup() {

    }

    void "Test rest interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"rest")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}
