import micks.secure.app.Role
import micks.secure.app.User
import micks.secure.app.UserRole

class BootStrap {

    def init = { servletContext ->

        Role adminRole = new Role('ROLE_ADMIN').save()
        Role userRole = new Role('ROLE_USER').save()

        User testUser = new User('mick', 'password').save()

        UserRole.create(testUser, adminRole, true)

        assert User.count() == 1
        assert Role.count() == 2
        assert UserRole.count() == 1
    }


    def destroy = {
    }
}
