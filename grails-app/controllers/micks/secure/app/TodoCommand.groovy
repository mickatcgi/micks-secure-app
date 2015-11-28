package micks.secure.app

import grails.validation.Validateable
import groovy.transform.ToString

/**
 * Created by mick on 11/27/2015.
 */
@ToString(includeNames=true, includePackage=false)
class TodoCommand implements Validateable {

    int id
    String description
    User user
    String priority
    String folder
    String status
    String notes
    Date dueDate
    Date completedDate

    static constraints = {
        // Blank can be an empty string, different to null.
        // Web requests can send blank strings when data is not provided.
        description(blank: false, nullable: false)
        priority(blank: true, nullable: true)
        folder(blank: true, nullable: true)
        status(blank: true, nullable: true)
        notes(blank: true, nullable: true)
        dueDate(blank: true, nullable: true)
        completedDate(blank: true, nullable: true)
    }

    def getNotes() {
        return notes + " (validated on ${new Date()})"
    }

}
