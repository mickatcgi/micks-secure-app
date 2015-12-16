<html>
    <head>
        <title>SPA for Billybob</title>
        <meta name="layout" content="main"/>
        <r:require module="core"/>
    </head>
    <body>

        <div class="property-list" ng-controller="todoRestController" ng-cloak>
        <hr/>
            <h1>Mick's SPA displaying single page Todo</h1>
            <div class="micks_align">
                <label class="micks_label" for="id">Id:</label>
                <span class="micks_todo">${oneTodo.myTodo.id} (from controller singlePage action)</span>
            </div>
            <div class="micks_align">
                <label class="micks_label" for="description">Description:</label>
                <span class="micks_todo">${oneTodo.myTodo.description}</span>
            </div>
            <div class="micks_align">
                <label class="micks_label" for="notes">Notes:</label>
                <span class="micks_todo">${oneTodo.myTodo.notes}</span>
            </div>
            <div class="micks_align">
                <label class="micks_label" for="username">User:</label>
                <span class="micks_todo">${oneTodo.myTodo.user.username}</span>
            </div>
        </div>

    </body>
</html>
