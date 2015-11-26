package micks.secure.app

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_ANONYMOUS'])
class TodoRestController extends RestfulController {

    static responseFormats = ['json', 'xml']

    /**
     * Scaffolding which provides the full suite of REST actions for this controller - see URLMappings
     * for modified path (/api/todos). Note in the client app (e.g. Google Chrome Advanced REST client)
     * when you POST or PUT your data needs to be submitted in json format in order to get a json response.
     * If you submit in x-www-form-urlencoded you will get the HTML page (e.g. show page) returned, which
     * is not what you want. You also don't need the .json extension on the URL path.
     * e.g. this works: http://localhost:9001/api/todos/4 with a POST or PUT body.
     *
     * When adding a new To-do using POST don't forget to add the user: {id: xxx} json. Post with
     * Content-Type: application/json and post to http://localhost:9001/api/todos
     * {description: "Billybob woz ere", notes:"Hello from the kittsville minus json extension", user: {id: 2}}
     *
     * Now i just need to find out why Spring-security needs to be switched off to allow REST actions.
     */

    TodoRestController() {
        super(Todo)
    }

}
