angular.module('micks-todos', ['restangular'])
.config(["RestangularProvider",
    function(RestangularProvider) {
        RestangularProvider.setBaseUrl('/todoRest');
}])
.controller("todoRestController",
    ["$scope", "Restangular", "$log",

    function ($scope, Restangular, $log) {

        $scope.name = "todoRestController"
        $log.info("Loading controller --> " + $scope.name)

        $scope.allTodos = []
        $scope.oneTodo = {}
        $scope.randomInt = -1
        $scope.randomTodo = {}
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

        $scope.show = function() {

            $log.info("Restangular.one() invoking show() REST call...")

            Restangular.one('/', 8).get().then(
                function(result) {
                    // Plain() method strips off extra restangular fluff from responses
                    $scope.oneTodo['myTodo'] = angular.toJson(result.plain())
                    $log.info("Restangular.one() show() returned -> " + angular.toJson($scope.oneTodo))
                },
                function(error) {
                    $log.error("Error in Restangular.one() calll -> " + error.data.errorMessage)
                }
            )
            return
        }

        $scope.loadAllTodos()

}]);