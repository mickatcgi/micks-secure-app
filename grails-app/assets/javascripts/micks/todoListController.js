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

}]);