scrapperApp.controller('HomeController', function HomeController($scope, ProcessorREST) {

    $scope.test = "test";

    $scope.startProcess = function () {
        ProcessorREST.save(
            {},
            {},
            function () {
                alert("Process started");
            },
            function (error) {
                alert("error");
                console.log(error);
            }
        );
    }
});