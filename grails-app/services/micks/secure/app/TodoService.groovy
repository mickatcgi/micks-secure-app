package micks.secure.app

import grails.transaction.Transactional

class TodoException extends RuntimeException {
    String message
    Todo todo
}

@Transactional
class TodoService {

    public Todo saveTodo(Todo todo) {

        log.info("In saveTodo() : ${todo?.description}")

        def user = User.findById(todo?.user?.id)
        if (!user) {
            throw new TodoException(message: "User id is invalid or not registered for this todo: ${todo?.description}")
        }

        Todo savedTodo
        if (todo.validate()) {

            if (todo.id != null) {
                log.info("UPDATE: TodoService validated successfully: ${todo?.id} : ${todo?.description}")
            } else {
                log.info("NEW: TodoService validated successfully: ${todo?.description}")
            }
            savedTodo = todo.save(failOnError: true, flush: true)
        } else {
            log.info("TodoService validation failed for: ${todo?.description}")
            throw new TodoException(message: "Todo failed validation", todo: todo)
        }

        return savedTodo
    }
}
