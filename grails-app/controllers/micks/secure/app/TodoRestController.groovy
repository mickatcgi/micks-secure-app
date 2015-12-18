package micks.secure.app

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import grails.web.RequestParameter

@Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_ANONYMOUS'])
class TodoRestController extends RestfulController {

    static responseFormats = ['json', 'xml']
    static defaultAction = "index"

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
     * {description: "Billybob woz ere", notes:"Hello from the kittsville minus json extension", user: {id: 2}}*
     * Now i just need to find out why Spring-security needs to be switched off to allow REST actions.
     * After a stop/start the TodoRestController POST/PUT actions seem to work just fine with Spring
     * Security enabled and working for the UI pages.
     */

    TodoRestController() {
        super(Todo)
    }

//    def spaHome1() {
//        log.info("In TodoRestController spaHome1() servicing spaHome1.html")
//    }
//
//    def spaShow() {
//        log.info("In TodoRestController spaShow(id = ${params.id}) servicing spaShow.html")
//    }

    def getAllTodos() {
        List<Todo> todoList = Todo.list()
        Todo singleTodo = Todo.findById(1)
        log.info("In TodoRestController spaHome() servicing spaHome1 with ${todoList.size()} todos")
        //respond todoList, [model: [todoList : todoList]]
        //render(todoList as JSON)
        //todoList
        //[todo: singleTodo]
        //[todoList: todoList] // tries to render a view with the same name as a method
        respond Todo.list()
    }

    def getOneTodo(@RequestParameter('id') int todoId) {
        log.info("In TodoRestController getOneTodo(id = ${todoId}) rendering todo")
        Todo todo = Todo.findById(todoId)
        respond todo
    }


}
