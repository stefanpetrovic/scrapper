scrapperApp.controller('RecommenderConfigController', function RecommenderConfigController($scope, RecommenderConfigREST) {

    $scope.processingModes = [
        'PRODAJA',
        'IZDAVANJE'
    ];

    $scope.configList = [];

    $scope.saveConfig = function(index) {
        RecommenderConfigREST.save({}, $scope.configList[index], function(success) {
        });
    };

    function init() {
        RecommenderConfigREST.query({}, function(success) {
            $scope.configList = success;
        });
    }

    init();
});