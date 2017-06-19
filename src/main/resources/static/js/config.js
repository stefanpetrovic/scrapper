angular.module('scrapperApp').config(['$routeProvider', function ($routeProvider) {

    $routeProvider
        .when('/', {
            templateUrl: 'views/home.html'
        })
        .when('/management', {
            templateUrl: 'views/management.html'
        });

}]);