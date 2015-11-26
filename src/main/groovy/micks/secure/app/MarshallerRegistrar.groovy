package micks.secure.app

import grails.converters.JSON
import grails.converters.XML

import javax.annotation.PostConstruct
import java.text.SimpleDateFormat

/**
 * Created by mick on 11/26/2015.
 */
class MarshallerRegistrar {

    @PostConstruct
    public void registerMarshallers() {

        def dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")

        JSON.registerObjectMarshaller(Todo) {
            Todo t ->
                return [id           : t.id,
                        description  : t.description,
                        notes        : t.notes,
                        user         : t.user.username,
                        priority     : t.priority,
                        folder       : t.folder,
                        status       : t.status,
                        dueDate      : t.dueDate,
                        completedDate: t.completedDate,
                        dateCreated  : t.dateCreated,
                        lastUpdated  : t.lastUpdated
                ]
        }

        XML.registerObjectMarshaller(Todo) {
            Todo t, converter ->
                converter.attribute("id", t.id.toString())
                converter.attribute("user", t.user.username)
                converter.build {
                    description t.description
                    notes t.notes
                    priority t.priority
                    folder t.folder
                    status t.status
                    dueDate t.dueDate == null ? "" : dateFormatter.format(t.dueDate)
                    completedDate t.completedDate == null ? "" : dateFormatter.format(t.completedDate)
                    dateCreated t.dateCreated == null ? "" : dateFormatter.format(t.dateCreated)
                    lastUpdated t.lastUpdated == null ? "" : dateFormatter.format(t.lastUpdated)
                }
        }

    }

}
