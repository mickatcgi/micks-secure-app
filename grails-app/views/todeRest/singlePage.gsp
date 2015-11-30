<html>
    <head>
        <title>SPA for Billybob</title>
        <meta name="layout" content="main"/>
        <r:require module="core"/>
    </head>
    <body>

        <div>
            <h1>Mick's SPA displaying single page Todo</h1>
            <f:display bean="todo"/>

            <div id="list-todo" ng-controller="singlePageController" ng-cloak>
                <h1>Mick's SPA Restangular Pulling ALL Todo from {{name}}</h1>
                <h2>Found {{allTodos.length}} todos</h2>
                <ul>
                    <li ng-repeat="myTodo in allTodos">Todo = {{myTodo.description}}</li>
                </ul>
            </div>
        </div>

    </body>
</html>
