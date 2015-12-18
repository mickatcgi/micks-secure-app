angular.module('micks-todos', ['restangular', 'ngRoute'])
.config(["RestangularProvider",
    function(RestangularProvider) {
        RestangularProvider.setBaseUrl('/todoRest');
}])
/*************************************************************************************/
/* Routes switch between views at the ng-view definition                             */
/* Don't leave in the 'otherwise' because it defaults there for every other page     */
/*   even if it's not an angular page.                                               */
/*************************************************************************************/
.config(['$routeProvider', '$locationProvider',

    function($routeProvider, $locationProvider) {

        $routeProvider.when("/spaHome1", {
            templateUrl: "/assets/micks/partials/spaHome1.html"
        });

        $routeProvider.when('/spaShow/:id', {
            templateUrl: "/assets/micks/partials/spaShow.html"
        });

        $routeProvider.otherwise({
            // If using redirectTo: then you must use a route, not a fragment
            templateUrl: "/assets/micks/partials/spaBad.html"
        });

        // configure html5 to get links working on plnkr
        //$locationProvider.html5Mode(true);

}])
.controller("todoListController",
    ["$scope", "Restangular", "$log", '$location', '$routeParams', '$route',

    function ($scope, Restangular, $log, $location, $routeParams, $route) {

        $scope.name = "todoListController"
        $log.info("Loading controller --> " + $scope.name)

        $scope.allTodos = []
        $scope.count = -1

        /*************************************************************************************/
        $scope.loadAllTodos = function () {

            Restangular.all('/getAllTodos').getList().then(
                function(result) {
                    $scope.allTodos = result
                    $log.info("Name = " + $scope.name + " AllTodos length = "
                        + $scope.allTodos.length)
                },
                function(error) {
                    $log.error("Error in Restangular.all() call -> " + error.data.errorMessage)
                }
            )
        }

        /*************************************************************************************/
        $scope.show = function (todo) {
            $log.info("TodoList about to show todo = " + todo.id);
            var current_path = $location.path();
            var new_path = "/spaShow/" + todo.id;
            $log.info("TodoList about to navigate to url = " + new_path)
            $location.path(new_path);
        };

        $scope.loadAllTodos()

}])
.controller("todoController",
    ["$scope", "Restangular", "$log", '$location','$routeParams', '$route',

    function ($scope, Restangular, $log, $location, $routeParams, $route) {

        $scope.name = "todoController"
        $log.info("Loading controller --> " + $scope.name)

        $scope.oneTodo = null

        /*************************************************************************************/
        $scope.show = function() {

            $log.info("TodoController.show() $routeParams = " + JSON.stringify($routeParams));

            var todoId = $routeParams.id
            $log.info("Restangular.one() invoking show() REST call for todoId = " + todoId)

            Restangular.one('/getOneTodo', todoId).get().then(
                function(result) {
                    // Plain() method strips off extra restangular fluff from responses
                    $scope.oneTodo = result
                    $log.info("Restangular.one() show() returned -> " + angular.toJson($scope.oneTodo))
                },
                function(error) {
                    $log.error("Error in Restangular.one() call -> " + error.data.errorMessage)
                }
            )
            return
        }

        /*************************************************************************************/
        $scope.back = function () {
            $log.info("Returning BACK to todo list");
            var current_path = $location.path();
            var new_path = "/spaHome1";
            $location.path(new_path);
        };

        $scope.show()

}]);