package micks.secure.app

/**
 * Created by mick on 11/25/2015.
 */
class Todo {

    String description
    User user
    String priority
    String folder
    String status
    String notes
    Date dueDate
    Date completedDate

    Date    dateCreated
    Date    lastUpdated

    static constraints = {
        dueDate(blank: true, nullable: true)
        completedDate(blank: true, nullable: true)
    }

    @Override
    String toString() {
        return super.toString()
    }
}
