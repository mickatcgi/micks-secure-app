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
                <label class="micks_label" for="description">Description:</label>
                <span class="micks_todo">${todo.description} (from controller singlePage action)</span>
            </div>

            <div class="property-list" ng-controller="singlePageController" ng-cloak>

                <h1>Mick's SPA Restangular Pulling ALL Todo from AngularJs {{name}}</h1>
                <h2>Found {{allTodos.length}} todos</h2>
                <ul>
                    <li class="fieldcontain" ng-repeat="myTodo in allTodos">Todo = {{myTodo.description}}</li>
                </ul>

                <hr/>
                <p>{{oneTodo.id}}</p>
                <p>{{oneTodo}}</p>
            </div>

            <f:display bean="todo"/>

        </div>

    </body>
</html>
