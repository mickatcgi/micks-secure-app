package micks.secure.app
import geb.spock.GebSpec
import spock.lang.Shared
import wslite.http.auth.HTTPBasicAuthorization
import wslite.rest.ContentType
import wslite.rest.RESTClient
/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
class TodoRestSpec extends GebSpec {

    @Shared
    def restClient =
            new RESTClient("http://localhost:9001/api/todos")

    void setup() {
        restClient.authorization = new HTTPBasicAuthorization("mick", "password")
        restClient.httpClient.sslTrustAllCerts = true
    }

    void "GET a list of posts as JSON"() {
        when: "I send a GET to the posts URL requesting JSON"
        def response = restClient.get(path: "/", accept: ContentType.JSON)

        then: "I get the expected posts as a JSON list"
        response.json*.description[0..1] == [
                "Write some grails code 1",
                "Write some groovy code 2"]

        if (response?.json == null) {
            printf("Json for /api/todos/ index query returned null - no json returned\n")
        } else {
            response.json.each {
                todo -> printf("WsLite Response for /api/todos/ = %s\n", todo)
            }
        }
    }
}