package micks.secure.app
import geb.spock.GebSpec
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import grails.util.Environment
import groovy.util.logging.Slf4j

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@Integration
@Rollback
@Slf4j
class TodeRestDefaultSpec extends GebSpec {

    def setup() {
        log.info("Testing in --> in ${Environment.current} environment")
    }

    def cleanup() {
    }

    void "test something"() {
        when:"The home page is visited"
            go '/'

        then:"The title is correct"
        printf("TodeRestDefaultSpec - functional testing app title = %s\n", $('title').text())
        	$('title').text() == "Welcome to Grails"
    }
}
