angular.module('scrapperApp').config(['$routeProvider', function ($routeProvider) {

    $routeProvider
        .when('/', {
            templateUrl: 'views/home.html'
        })
        .when('/forbidden-address', {
            templateUrl: 'views/forbidden-address.html'
        });

}]);
