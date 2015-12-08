<html>
    <head>
        <title>SPA for Billybob</title>
        <meta name="layout" content="main"/>
        <r:require module="core"/>
    </head>
    <body>

        <div>
            <hr/>
            <h1>Mick's SPA displaying single page Todo</h1>
            <div class="micks_align">
                <label class="micks_label" for="id">Id:</label>
                <span class="micks_todo">${todo.id} (from controller singlePage action)</span>
            </div>
            <div class="micks_align">
                <label class="micks_label" for="description">Description:</label>
                <span class="micks_todo">${todo.description}</span>
            </div>
            <div class="micks_align">
                <label class="micks_label" for="notes">Notes:</label>
                <span class="micks_todo">${todo.notes}</span>
            </div>
            <div class="micks_align">
                <label class="micks_label" for="username">User:</label>
                <span class="micks_todo">${todo.user.username}</span>
            </div>

            <div class="property-list" ng-controller="singlePageController" ng-cloak>

                <h1>Mick's SPA Restangular Pulling ALL Todo from AngularJs {{name}}</h1>
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
                        <td>{{myTodo.description}}</td>
                        <td>{{myTodo.notes}}</td>
                        <td>{{myTodo.priority}}</td>
                        <td>{{myTodo.folder}}</td>
                        <td>{{myTodo.status}}</td>
                        <td>{{myTodo.dueDate}}</td>
                        <td>{{myTodo.completedDate}}</td>
                    </tr>
                </table>

                <hr/>
                <button type="button" class="btn btn-xs btn-primary" ng-click="randomTodo()">Random Todo</button>
                <p>Show id = {{oneTodo.myTodo.id}}</p>
                <p>Show full todo = {{oneTodo.myTodo}}</p>
                <p>Show random Todo [{{randomInt}}] = {{oneTodo.randomTodo}}</p>
                <hr/>
            </div>

            <f:display bean="todo"/>

        </div>

    </body>
</html>
