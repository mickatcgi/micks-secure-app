package micks.secure.app

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
@Transactional(readOnly = true)
class TodoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {

        String username = principal.username
        params.max = Math.min(max ?: 10, 100)
        //params['user_id'] = username
        //params['fetch'] = [users: '"eager"']

        log.info("username = ${username} : params = ${params}")
        def results = Todo.list(params)
        displayResults(results)

        flash.message = "Todo lookup successful. Found ${results.size()} todos."

        respond results, model:[todoCount: Todo.count()]
    }

    def show(Todo todo) {
        respond todo
    }

    def create() {
        respond new Todo(params)
    }

    @Transactional
    def save(Todo todo) {
        if (todo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (todo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond todo.errors, view:'create'
            return
        }

        todo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'todo.label', default: 'Todo'), todo.id])
                redirect todo
            }
            '*' { respond todo, [status: CREATED] }
        }
    }

    def edit(Todo todo) {
        respond todo
    }

    @Transactional
    def update(Todo todo) {
        if (todo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (todo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond todo.errors, view:'edit'
            return
        }

        todo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'todo.label', default: 'Todo'), todo.id])
                redirect todo
            }
            '*'{ respond todo, [status: OK] }
        }
    }

    @Transactional
    def delete(Todo todo) {

        if (todo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        todo.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'todo.label', default: 'Todo'), todo.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'todo.label', default: 'Todo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    protected displayResults(todos) {
        todos.each {
            todo -> printf("TODO = %s\n", todo.toString())
        }
    }
}
