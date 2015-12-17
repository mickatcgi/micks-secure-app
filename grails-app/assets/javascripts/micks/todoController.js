angular.module('micks-todos', ['restangular'])
.config(["RestangularProvider",
    function(RestangularProvider) {
        RestangularProvider.setBaseUrl('/todoRest');
}])
.controller("todoController",
    ["$scope", "Restangular", "$log",

    function ($scope, Restangular, $log) {

        $scope.name = "todoController"
        $log.info("Loading controller --> " + $scope.name)

        $scope.oneTodo = {}

        $scope.show = function(todoId) {

            $log.info("Restangular.one() invoking show() REST call...")

            Restangular.one('/getOneTodo', todoId).get().then(
                function(result) {
                    // Plain() method strips off extra restangular fluff from responses
                    $scope.oneTodo['myTodo'] = angular.toJson(result.plain())
                    $log.info("Restangular.one() show() returned -> " + angular.toJson($scope.oneTodo))
                },
                function(error) {
                    $log.error("Error in Restangular.one() call -> " + error.data.errorMessage)
                }
            )
            return
        }

}]);