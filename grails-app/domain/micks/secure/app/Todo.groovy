package micks.secure.app

import groovy.transform.ToString

/**
 * Created by mick on 11/25/2015.
 */
@ToString(includeNames=true, includePackage=false)
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

    static belongsTo = [ user: User ]

    static constraints = {
        dueDate(blank: true, nullable: true)
        completedDate(blank: true, nullable: true)
    }

}
