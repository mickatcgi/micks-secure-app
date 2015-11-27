package micks.secure.app

import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import groovy.time.TimeCategory
import spock.lang.Specification
import spock.lang.Stepwise

import javax.servlet.http.HttpServletResponse

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(TodeRestController)
@Mock([User, Todo, Role, UserRole])
@Stepwise
class TodeRestControllerSpec extends Specification {

    def setupSpec() {
        defineBeans {
            springSecurityService(SpringSecurityService)
        }
    }

    def cleanup() {
    }

    void "GET a list of TODO's as JSON"() {
        given: "A set of Todos"
        initializeUserAndTodos()

        when: "I invoke the GET list action"
        controller.index()

        then: "I get the expected Todos as a JSON list"
        def jsonContent = response.json
        def descriptions = response.json*.description
        println("JSON TEST RESULTS = ${jsonContent}")
        println("JSON TEST RESULTS = ${descriptions.size()} entries : ${descriptions}")
        descriptions.size() == Todo.count()
    }

    void "GET a list of TODO's as XML"() {
        given: "A set of Todos"
        initializeUserAndTodos()

        when: "I invoke the GET list action"
        response.format = 'xml'
        controller.index()

        then: "I get the expected Todos as an XML list"
        def xmlContent = response.xml
        def descriptions = response.xml.todo.description*.text()
        println("XML TEST RESULTS = ${xmlContent}")
        println("XML TEST RESULTS = ${descriptions.size()} entries : ${descriptions}")
        descriptions.size() == Todo.count()
    }

    void "POST a single new todo as JSON"() {
        given: "A set of Todos to update and a user Id"
        def (User testUser, Todo testTodo) = initializeUserAndTodos()

        when: "I invoke the save action with a JSON Todo request"
        String json = '{ "description": "New todo from POST unit test", "notes": "Hello Kitty", "user": { "id": ' +
                '"' + testUser.getId() + '" } }'
        request.json = json
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'POST'         // Force a POST otherwise we get a 405 method not allowed response
        println("About to POST JSON Todo = ${json}")
        controller.save()

        then: "I get a 201 JSON response with the id of the new POST"
        println("JSON POST Response errorMessage = ${response?.errorMessage}")  // Returned from sendError()
        println("JSON POST Response text = ${response?.text}")                  // Returned from render()
        response.status != HttpServletResponse.SC_METHOD_NOT_ALLOWED
        response.status == 201
        response.json.id != null
        println("JSON POST Response = ${response?.json}")

    }

    void "POST a single new bad todo as JSON which fails validation"() {
        given: "A set of Todos to update and a user Id"
        def (User testUser, Todo testTodo) = initializeUserAndTodos()

        when: "I invoke the save action with a JSON Todo request"
        String json = '{ "notes": "Theres no gubbins in this todo", "user": { "id": ' +
                '"' + testUser.getId() + '" } }'
        request.json = json
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'POST'         // Force a POST otherwise we get a 405 method not allowed response
        println("About to POST JSON Todo = ${json}")
        controller.save()

        then: "I get a 418 JSON response with an error message"
        println("JSON POST Response errorMessage = ${response?.errorMessage}")  // Returned from sendError()
        println("JSON POST Response text = ${response?.text}")                  // Returned from render()
        response.status != HttpServletResponse.SC_METHOD_NOT_ALLOWED
        response.status == 418
    }

    void "PUT a single existing Todo as JSON"() {
        given: "A set of Todos to update and a user Id"
        def (User testUser, Todo testTodo) = initializeUserAndTodos()

        when: "I invoke the update action with a JSON Todo request"
        String json = '{ "id": ' + testTodo.getId() +
                ', "description": "Updated todo from PUT unit test", "notes": "Hello Kitty" }'
        request.json = json
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'PUT'         // Force a PUT otherwise we get a 405 method not allowed response
        params.id = testTodo.id
        println("About to PUT JSON Todo = ${json}")
        controller.update()

        then: "I get a 201 JSON response with the id of the new PUT"
        response.status != HttpServletResponse.SC_METHOD_NOT_ALLOWED
        response.status == HttpServletResponse.SC_OK
        println("JSON PUT Response status = ${response?.status}")
        println("JSON PUT Response errorMessage = ${response?.errorMessage}")
    }

    def private initializeUserAndTodos() {

        //////////////////////////////////////////////////////////
        // Initialize Spring Security stuff...

        Role adminRole = new Role('ROLE_ADMIN').save()
        Role userRole = new Role('ROLE_USER').save()

        User adminUser = new User('mick', 'password').save()
        User standardUser = new User('billybob', 'password').save()

        UserRole.create(adminUser, adminRole, true)
        UserRole.create(standardUser, userRole, true)

        assert User.count() == 2
        assert Role.count() == 2
        assert UserRole.count() == 2

        //////////////////////////////////////////////////////////
        // Initialize test Todo stuff

        Date dueDate
        Date completedDate
        Date today = new Date()
        use(TimeCategory) {
            dueDate = today + 1.week - 4.days + 2.hours - 3.seconds
            completedDate = today - 1.week
        }

        Todo todo1 = new Todo(user: adminUser, description: "Write some grails code 1", priority: "High",
                folder: "Grails Coding", status: "In-progress", notes: "100 lines per day", dueDate: dueDate)
        Todo todo2 = new Todo(user: adminUser, description: "Write some groovy code 2", priority: "Medium",
                folder: "Groovy Coding", status: "In-progress", notes: "200 lines per day", dueDate: dueDate,
                completedDate: completedDate)
        Todo todo3 = new Todo(user: adminUser, description: "Write some grails code 3", priority: "High",
                folder: "Grails Coding", status: "In-progress", notes: "100 lines per day", dueDate: dueDate)
        Todo todo4 = new Todo(user: adminUser, description: "Write some groovy code 4", priority: "Medium",
                folder: "Groovy Coding", status: "In-progress", notes: "200 lines per day", dueDate: dueDate,
                completedDate: completedDate)
        Todo todo5 = new Todo(user: adminUser, description: "Write some grails code 5", priority: "High",
                folder: "Grails Coding", status: "In-progress", notes: "100 lines per day", dueDate: dueDate)
        Todo todo6 = new Todo(user: adminUser, description: "Write some groovy code 6", priority: "Medium",
                folder: "Groovy Coding", status: "In-progress", notes: "200 lines per day", dueDate: dueDate,
                completedDate: completedDate)
        Todo todo7 = new Todo(user: adminUser, description: "Write some grails code 7", priority: "High",
                folder: "Grails Coding", status: "In-progress", notes: "100 lines per day", dueDate: dueDate)
        Todo todo8 = new Todo(user: adminUser, description: "Write some groovy code 8", priority: "Medium",
                folder: "Groovy Coding", status: "In-progress", notes: "200 lines per day", dueDate: dueDate,
                completedDate: completedDate)

        todo1.save(failOnError: true)
        todo2.save(failOnError: true)
        todo3.save(failOnError: true)
        todo4.save(failOnError: true)
        todo5.save(failOnError: true)
        todo6.save(failOnError: true)
        todo7.save(failOnError: true)
        todo8.save(failOnError: true)

        Todo todo101 = new Todo(user: standardUser, description: "Write some ruby code 101", priority: "High",
                folder: "Ruby Coding", status: "In-progress", notes: "No bugs today", dueDate: dueDate)
        Todo todo102 = new Todo(user: standardUser, description: "Write some rails code 102", priority: "Low",
                folder: "Rails Coding", status: "In-progress", notes: "One bug allowed per day", dueDate: dueDate,
                completedDate: completedDate)

        todo101.save(failOnError: true)
        todo102.save(failOnError: true)

        assert Todo.count() == 10

        // Used for the individual Todo tests, which need a user and a todo for POST/PUT tests
        return [adminUser, todo1]
    }
}
