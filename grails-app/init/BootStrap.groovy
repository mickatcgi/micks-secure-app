import groovy.time.TimeCategory
import micks.secure.app.Role
import micks.secure.app.Todo
import micks.secure.app.User
import micks.secure.app.UserRole

class BootStrap {

    def init = { servletContext ->

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

        Todo todo1 = new Todo(user: adminUser, description: "Write some grails code", priority: "High",
                folder: "Grails Coding", status: "In-progress", notes: "100 lines per day", dueDate: dueDate)
        Todo todo2 = new Todo(user: adminUser, description: "Write some groovy code", priority: "Medium",
                folder: "Groovy Coding", status: "In-progress", notes: "200 lines per day", dueDate: dueDate,
                completedDate: completedDate)

        todo1.save(failOnError: true)
        todo2.save(failOnError: true)

        Todo todo101 = new Todo(user: standardUser, description: "Write some ruby code", priority: "High",
                folder: "Ruby Coding", status: "In-progress", notes: "No bugs today", dueDate: dueDate)
        Todo todo102 = new Todo(user: standardUser, description: "Write some rails code", priority: "Low",
                folder: "Rails Coding", status: "In-progress", notes: "One bug allowed per day", dueDate: dueDate,
                completedDate: completedDate)

        todo101.save(failOnError: true)
        todo102.save(failOnError: true)
    }


    def destroy = {
    }
}
