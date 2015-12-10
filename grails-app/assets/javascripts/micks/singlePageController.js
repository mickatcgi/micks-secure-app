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
            $scope.randomInt = -1
            $scope.randomTodo = {}
            $scope.count = -1

            /*
            * Invoke the /api/todes index() method and return all todos by default
            */
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

            /*
            * Invoke the /api/todes/8 show() method and return a single todo
            * Executed by default
            */
            $scope.show = function() {

                $log.info("Restangular.one() invoking show() REST call...")
                //var todo = Restangular.one('/', 8).get()
                //$log.info("Restangular.one() returned -> " + angular.toJson(todo))

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

            /*
            * Invoke the /api/todes/count count() method and return a count of all todos
            * Executed by default
            */
            $scope.todoCount = function() {
                $log.info("Restangular todoCount starting...")
            }

            /*
             * Invoke the /api/todes/id/randomTodo method and return a todo given the id
             */
            $scope.randomTodo = function() {
                $scope.randomInt = Math.floor((Math.random() * 10) + 1);
                $log.info("Restangular randomTodo looking for id = " + $scope.randomInt)

                 Restangular.one('/', $scope.randomInt).customGET("randomTodo").then(
                                    function(result) {
                                       // Plain() method strips off extra restangular fluff from responses
                                       if (typeof result === 'undefined') {
                                            $log.info("Restangular randomTodo call returned undefined bugger")
                                        } else {
                                            $log.info("RandomTodo result = " + angular.toJson(result.plain()))
                                            $scope.randomTodo['randomTodo'] = angular.toJson(result.plain())
                                            $log.info("Restangular.one() randomTodo returned defined -> "
                                                + $scope.randomTodo.randomTodo)
                                        }
                                    },
                                    function(error) {
                                        $log.error("Error in Restangular.one() calll -> " + error.data.errorMessage)
                                    }
                                )
                                return
            }

            $scope.todoCount
            $scope.show()

}]);