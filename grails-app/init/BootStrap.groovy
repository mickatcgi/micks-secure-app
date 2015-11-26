import grails.converters.JSON
import grails.converters.XML
import groovy.time.TimeCategory
import micks.secure.app.Role
import micks.secure.app.Todo
import micks.secure.app.User
import micks.secure.app.UserRole

import java.text.SimpleDateFormat

class BootStrap {

    /**
     * Initialize everything...
     */
    def init = { servletContext ->
        def (User adminUser, User standardUser) = initUsers()
        initTodos(adminUser, standardUser)
        initMarshallers()
    }


    def destroy = {
    }

    /**
     * Initialize Spring-security users and roles to get us started...
     */
    def initUsers() {

        Role adminRole = new Role('ROLE_ADMIN').save()
        Role userRole = new Role('ROLE_USER').save()

        User adminUser = new User('mick', 'password').save()
        User standardUser = new User('billybob', 'password').save()

        UserRole.create(adminUser, adminRole, true)
        UserRole.create(standardUser, userRole, true)

        assert User.count() == 2
        assert Role.count() == 2
        assert UserRole.count() == 2

        return [adminUser, standardUser]
    }

    /**
     * Initialize default todos to get us started...
     */
    def initTodos(User adminUser, User standardUser) {

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

        return
    }

    /**
     * Initialize the marshalers responsible for parsing and returning JSON objects in REST calls
     */
    def initMarshallers() {

        def dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")

        JSON.registerObjectMarshaller(Todo) {
            Todo t ->
                return [id           : t.id,
                        description  : t.description,
                        notes        : t.notes,
                        user         : t.user.username,
                        priority     : t.priority,
                        folder       : t.folder,
                        status       : t.status,
                        dueDate      : t.dueDate,
                        completedDate: t.completedDate
                ]
        }

        XML.registerObjectMarshaller(Todo) {
            Todo t, converter ->
                converter.attribute("id", t.id.toString())
                converter.attribute("user", t.user.username)
                converter.build {
                    description t.description
                    notes t.notes
                    priority t.priority
                    folder t.folder
                    status t.status
                    dueDate t.dueDate == null ? "" : dateFormatter.format(t.dueDate)
                    completedDate t.completedDate == null ? "" : dateFormatter.format(t.completedDate)
                }
        }
    }
}
