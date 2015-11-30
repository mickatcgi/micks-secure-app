angular.module('micks-todos', ['restangular'])
.config(function(RestangularProvider) {
        RestangularProvider.setBaseUrl('/api');
})
.controller("singlePageController",
    ["$scope", "Restangular", "$log",

        function ($scope, Restangular, $log) {

            $scope.name = "singlePageController";
            $scope.allTodos = []
            Restangular.all('todes').getList().then(
                function(result) {
                    $scope.allTodos = result
                    $log.debug("Name = " + $scope.name + " AllTodos length = "
                        + $scope.allTodos.length)
                },
                function(error) {
                    $log.error("Error in REST calll -> " + error.data.errorMessage)
                }
            )

}]);