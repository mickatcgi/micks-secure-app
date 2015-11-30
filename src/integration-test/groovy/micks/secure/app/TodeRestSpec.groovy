package micks.secure.app

import grails.util.Environment
import groovy.util.logging.Slf4j
import spock.lang.Shared
import spock.lang.Specification
import wslite.http.auth.HTTPBasicAuthorization
import wslite.rest.ContentType
import wslite.rest.RESTClient

/**
 * Integration/functional tests
 *      Hit the currently listening application database - could be test or dev
 *      Must be run with the application running and listening on a valid port
 * Unit tests hit the dev database (no listener needs to be active)
 *      Application does not need to be running
 * Runtime modes:
 *      Grails run-app runs the application in the dev database
 *      java -jar build/libs/micks-secure-app-0.1.jar runs the application in the test or prod database?
 */
@Slf4j
class TodeRestSpec extends Specification {

    @Shared
    def restClient =
            new RESTClient("http://localhost:9001/api/todes")

    void setup() {
        log.info("Testing in --> in ${Environment.current} environment")
        restClient.authorization = new HTTPBasicAuthorization("mick", "password")
        restClient.httpClient.sslTrustAllCerts = true
    }

    void "GET a list of posts as JSON"() {
        when: "I send a GET to the posts URL requesting JSON"
        def response = restClient.get(path: "/", accept: ContentType.JSON)

        then: "I get the expected posts as a JSON list"
        if (response?.json == null) {
            printf("Json for /api/todes/ index query returned null - no json returned\n")
        } else {
            response.json.each {
                todo -> printf("WsLite Response for /api/todes/ = %s\n", todo)
            }
        }

        response.json*.description[0..1] == [
                "Write some grails code 1",
                "Write some groovy code 2"]

    }
}
