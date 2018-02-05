scrapperApp.controller('SmokingTestAnswersController', function SmokingTestAnswersController($scope, SmokingTestAnswersREST) {

    $scope.answers = [];

    SmokingTestAnswersREST.query(
        {},
        function(success){
            $scope.answers = success;
        },
        function(error) {
            console.log(error);
            alert("Error fetching smoking test answers.");
        }
    );

});