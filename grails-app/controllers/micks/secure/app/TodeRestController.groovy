package micks.secure.app
import grails.plugin.springsecurity.annotation.Secured
import grails.web.RequestParameter
import micks.secure.app.commands.TodoCommand

@Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_ANONYMOUS'])
class TodeRestController {

    static responseFormats = ['json', 'xml']

    // Save() method is used for new and existing objects
    static allowedMethods = [save:['POST', 'PUT'], show:['GET'], singlePage:['GET']]

    TodoService todoService
    def springSecurityService

    def index() {
        log.info("Index rendering ALL todo's")
        respond Todo.list()
    }

    def save(TodoCommand todoCommand) {
        try {
            log.info("SAVE TODO (via Command) starting: ${todoCommand?.id} - ${todoCommand?.description}")
            boolean newTodo = todoCommand?.id == 0 ? true : false

            Todo realTodo = new Todo(todoCommand.properties)

            Todo savedTodo = todoService.saveTodo(realTodo)
            log.info("SAVED TODO complete: ${savedTodo?.description}")
            respond savedTodo, status: newTodo ? 201 : 200
        } catch (TodoException te) {
            log.info("SAVE TODO failed with : ${te.message}")
            render(status: 400, text: te.message)
        }
    }

    def singlePage(@RequestParameter('todeRestId') int todoId) {
        log.info("SinglePage rendering todo = ${todoId}")
        Todo todo = Todo.findById(todoId)
        //respond todo
        [ todo: todo ]
    }

    /**
     * I've configured URLMappings to send all PUT/Updates to save() and the unit tests work
     * fine, but Google Chrome REST client does not work - it fails unless it can PUT to this
     * update() method. So, for now leave update() as an active method.
     * @param todoCommand
     * @return
     */
    def update(TodoCommand todoCommand) {
        log.info("UPDATE TODO (via Command) starting: ${todoCommand?.id} - ${todoCommand?.description}")
        return save(todoCommand)
    }

    def show(@RequestParameter('id') int todoId) {
        log.info("Show rendering todo = ${todoId}")
        //render(status: 405, text: "Not implemented yet. Unable to show todo ${todoId}")
        Todo todo = Todo.findById(todoId)
        respond todo
    }

    def delete(@RequestParameter('id') int todoId) {
        log.info("Delete rendering todo = ${todoId}")
        render(status: 405, text: "Not implemented yet. Unable to delete todo ${todoId}")
    }

    def patch(@RequestParameter('id') int todoId) {
        log.info("Patch rendering todo = ${todoId}")
        render(status: 405, text: "Not implemented yet. Unable to patch todo ${todoId}")
    }


    private void logAllErrors(errorList) {
        errorList.eachWithIndex {
            error, index -> log.info("Validation error [${index}] = ${error}")
        }
    }

    /**
     * Save with validation failures to illustrate handing errors
     * @param todo
     * @return
     */
//    def save(Todo todo) {
//        todo.description = null // ERROR - fail validation
//        todo.validate()
//        if (!todo.hasErrors()){
//            def user = springSecurityService.currentUser
//
//            // If you don't flush you don't get dateCreated/lastUpdated fields returned.
//            def newTodo = todo.save(failOnError: true, flush: true)
//            log.info("Successfully saved Todo: ${newTodo.toString()}")
//            respond newTodo, status: 201
//        } else {
//            // If a todo fails validation it will return an unprocessoble entry (422)
//            // because it still has errors. To return the original todo you have to fix
//            // it and revalidate it to remove the errors.
//            todo.description = "Im now fixed..."
//            todo.validate()
//            log.info("Unable to save Todo. It has errors: ${todo.toString()}")
//            //respond todo, status: 418
//            //response.sendError(418, "You're hosed Mick")
//            render(status: 418, text: "You're buggered Mick")
//        }
//    }

}
