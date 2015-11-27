package micks.secure.app
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(TodoService)
@Mock([Todo, User, Role, UserRole])
class TodoServiceSpec extends Specification {

    def cleanup() {
    }

    void "Valid Todos get saved"() {

        given: "A new todo in the DB and a user"
        def (adminUser, standardUser) = initializeUsers()
        Todo todo = new Todo(description: "Mock todo", notes: "What a mockery", user: standardUser)

        when: "A new todo is created by the service"
        Todo newTodo = service.saveTodo(todo)

        then: "The todo is returned"
        todo.description == "Mock todo"
        todo.id > 0
    }

    void "Valid Todos get updated"() {

        given: "A new todo in the DB and a user"
        def (adminUser, standardUser) = initializeUsers()
        Todo todo = new Todo(description: "Mock todo", notes: "What a mockery", user: standardUser)
        todo.save(failOnError: true)

        when: "A new todo is created by the service"
        todo.description = "Mock todo updated"
        Todo updatedTodo = service.saveTodo(todo)
        Todo savedTodo = Todo.findById(updatedTodo.id)

        then: "The todo is returned"
        savedTodo.description == "Mock todo updated"
        Todo.count() == 1
    }

    def private initializeUsers() {

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

        // Used for the individual Todo tests, which need a user and a todo for POST/PUT tests
        return [adminUser, standardUser]
    }

}
