package micks.secure.app

import grails.transaction.Transactional

class TodoException extends RuntimeException {
    String message
    Todo todo
}

@Transactional
class TodoService {

    def mailService

    public TodoService() {
        // Constructor only exists because if it isn't here the mailService goes null after one
        // or two tries and is never accessible. This constructor makes sure it never goes null.
        // There must be some bug with service injection - unable to find an answer on Google.
        log.info("=====================================================================")
        log.info("Constructor of TodoService...initializing")
    }

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
            welcomeTextEmail()
        } else {
            log.info("TodoService validation failed for: ${todo?.description}")
            throw new TodoException(message: "Todo failed validation", todo: todo)
        }

        return savedTodo
    }

    def welcomeTextEmail() {
        String recipient = "mickster.gallagher@gmail.com"
        log.info("Sending a TEXT email now to '${recipient}'")
        try {
            mailService.sendMail {
                to recipient
                subject "Welcome to Hell...er, I mean Grails"
                text """
                Hi, ${recipient}. Great to have you on board
                The devil...er, I mean the Grails team.
                """
            }
        } catch (Exception e) {
            log.error("Sending email failed with error: ${e.getMessage()} at ${new Date()}")
        }
        return
    }
}
