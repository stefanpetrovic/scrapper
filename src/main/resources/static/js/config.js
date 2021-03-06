angular.module('scrapperApp').config(['$routeProvider', function ($routeProvider) {

    $routeProvider
        .when('/', {
            templateUrl: 'views/home.html'
        })
        .when('/forbidden-address', {
            templateUrl: 'views/forbidden-address.html'
        })
        .when('/recommended-apartments', {
            templateUrl: 'views/recommended-apartments.html'
        })
        .when('/recommender-config', {
            templateUrl: 'views/recommender-config.html'
        })
        .when('/smoking-test-answers', {
            templateUrl: 'views/smoking-test-answers.html'
        });

}]);
