angular.module('micks-todos', ['restangular'])
.config(["RestangularProvider",
    function(RestangularProvider) {
        RestangularProvider.setBaseUrl('/api/todes');
}])
.controller("singlePageController",
    ["$scope", "Restangular", "$log",

        function ($scope, Restangular, $log) {

            $scope.name = "singlePageController";
            $log.info("Loading controller --> " + $scope.name)

            $scope.allTodos = []
            $scope.oneTodo = {}
            $scope.randomTodo = {}
            $scope.count = -1

            Restangular.all('/').getList().then(
                function(result) {
                    $scope.allTodos = result
                    $log.debug("Name = " + $scope.name + " AllTodos length = "
                        + $scope.allTodos.length)
                },
                function(error) {
                    $log.error("Error in Restangular.all() call -> " + error.data.errorMessage)
                }
            )

            $scope.show = function() {

                $log.info("Restangular.one() invoking show() REST call...")
                //var todo = Restangular.one('/', 8).get()
                //$log.info("Restangular.one() returned -> " + angular.toJson(todo))

                Restangular.one('/', 8).get().then(
                    function(result) {
                       // Plain() method strips off extra restangular fluff from responses
                       $scope.oneTodo['myTodo'] = angular.toJson(result.plain())
                       $log.info("Restangular.one() returned -> " + angular.toJson($scope.oneTodo))
                    },
                    function(error) {
                        $log.error("Error in Restangular.one() calll -> " + error.data.errorMessage)
                    }
                )
                return
            }

            $scope.todoCount = function() {
                $log.info("Restangular todoCount starting...")
            }

            $scope.show()

}]);