package micks.secure.app
import grails.plugin.springsecurity.annotation.Secured
import grails.web.RequestParameter

@Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_ANONYMOUS'])
class TodeRestController {

    static responseFormats = ['json', 'xml']

    // Save() method is used for new and existing objects
    static allowedMethods = [save:['POST', 'PUT']]

    TodoService todoService
    def springSecurityService

    def index() {
        respond Todo.list()
    }

    def save(Todo todo) {
        try {
            log.info("SAVE TODO starting: ${todo?.description}")
            boolean newTodo = todo.id == null ? true : false
            Todo savedTodo = todoService.saveTodo(todo)
            log.info("SAVED TODO complete: ${savedTodo?.description}")
            respond savedTodo, status: newTodo ? 201 : 200
        } catch (TodoException te) {
            log.info("SAVE TODO failed with : ${te.message}")
            render(status: 400, text: te.message)
        }
    }

    def update(Todo todo) {
        log.info("UPDATE TODO starting: ${todo?.description}")
        return save(todo)
    }

    def show(@RequestParameter('id') int todoId) {
        log.info("Show rendering todo = ${todoId}")
        render(status: 405, text: "Not implemented yet. Unable to show todo ${todoId}")
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
