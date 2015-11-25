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

        Todo todo1 = new Todo(user: adminUser, description: "Write some grails code", priority: "High",
                folder: "Coding", status: "In-progress", notes: "100 lines per day")

        todo1.save(failOnError: true)
    }


    def destroy = {
    }
}
