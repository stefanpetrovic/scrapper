angular.module('scrapperApp').config(['$routeProvider', function($routeProvider) {

    $routeProvider.when('/', {
        templateUrl: 'views/management.html'
    });

    $routeProvider.when('/management', {
        templateUrl: 'views/management.html'
    });
}]);