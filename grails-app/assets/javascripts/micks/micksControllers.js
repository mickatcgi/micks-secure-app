angular.module('micks-todos', ['restangular'])
.config(["RestangularProvider",
    function(RestangularProvider) {
        RestangularProvider.setBaseUrl('/todoRest');
}])
.controller("todoListController",
    ["$scope", "Restangular", "$log",

    function ($scope, Restangular, $log) {

        $scope.name = "todoListController"
        $log.info("Loading controller --> " + $scope.name)

        $scope.allTodos = []
        $scope.count = -1

        $scope.loadAllTodos = function () {

            /*
            * Invoke the /api/todes index() method and return all todos by default
            */
            Restangular.all('/getAllTodos').getList().then(
                function(result) {
                    $scope.allTodos = result
                    $log.debug("Name = " + $scope.name + " AllTodos length = "
                        + $scope.allTodos.length)
                },
                function(error) {
                    $log.error("Error in Restangular.all() call -> " + error.data.errorMessage)
                }
            )
        }

        $scope.loadAllTodos()

}])
.controller("todoController",
    ["$scope", "Restangular", "$log",

    function ($scope, Restangular, $log) {

        $scope.name = "todoController"
        $log.info("Loading controller --> " + $scope.name)

        $scope.oneTodo = null

        $scope.show = function(todoId) {

            $log.info("Restangular.one() invoking show() REST call...")

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

        $scope.show(4)

}]);