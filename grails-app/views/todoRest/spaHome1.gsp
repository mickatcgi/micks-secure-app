<html>
    <head>
        <title>SPA for Billybob</title>
        <meta name="layout" content="main"/>
        <r:require module="core"/>
    </head>
    <body>

        <div>
            <hr/>
            <div class="property-list" ng-controller="todoListController" ng-cloak>

                <h1>Mick's SPA spaHome1.gsp Pulling ALL Todo from AngularJs {{name}}</h1>
                <h2>Found {{allTodos.length}} todos</h2>
                <button type="button" class="btn btn-xs btn-primary">Primary</button>
                <button type="button" class="btn btn-xs btn-success">Success</button>
                <button type="button" class="btn btn-xs btn-info">Info</button>
                <button type="button" class="btn btn-xs btn-warning">Warning</button>
                <button type="button" class="btn btn-xs btn-danger">Danger</button>

                <table>
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Description</th>
                            <th>Notes</th>
                            <th>Priority</th>
                            <th>Folder</th>
                            <th>Status</th>
                            <th>Due</th>
                            <th>Completed</th>
                        </tr>
                    </thead>
                    <tr ng-repeat="myTodo in allTodos">
                        <td>{{myTodo.id}}</td>
                        <td><a href<g:link controller="todoRest" action="spaShow" id="{{myTodo.id}}">
                                {{myTodo.description}}
                            </g:link>
                        </td>
                        <td>{{myTodo.notes}}</td>
                        <td>{{myTodo.priority}}</td>
                        <td>{{myTodo.folder}}</td>
                        <td>{{myTodo.status}}</td>
                        <td>{{myTodo.dueDate}}</td>
                        <td>{{myTodo.completedDate}}</td>
                    </tr>
                </table>

                <hr/>
            </div>
        </div>

    </body>
</html>
