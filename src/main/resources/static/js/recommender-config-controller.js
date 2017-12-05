scrapperApp.controller('RecommenderConfigController', function RecommenderConfigController($scope, RecommenderConfigREST) {

    $scope.config = {
        minPrice: 10000,
        maxPrice: 100000,
        minArea: 10,
        maxArea: 100,
        maxPriceOfSquareMeter: 1600
    };

    $scope.saveConfig = function() {
        RecommenderConfigREST.save({}, $scope.config, function(success) {
            $scope.config = success;
        });
    };

    function init() {
        RecommenderConfigREST.get({}, function(success) {
            if (!angular.equals({}, success.toJSON())) {
                $scope.config = success.toJSON();
            }
        });
    }

    init();
});