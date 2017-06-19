scrapperApp.controller("ManagementController", function ManagementController($scope, ForbiddenAddressREST, blockUI) {

    $scope.forbiddenAddresses = [];
    $scope.newAddress = '';

    $scope.removeAddress = function (index) {
        blockUI.start();
        ForbiddenAddressREST.remove(
            {
                id: $scope.forbiddenAddresses[index].id
            },
            function(success) {
                blockUI.stop();
                $scope.forbiddenAddresses.splice(index, 1);
            },
            function(error) {});
    };

    $scope.addAddress = function() {
        blockUI.start();
        ForbiddenAddressREST.save(
            {
                "newAddress": $scope.newAddress
            },
            {},
            function (success) {
                $scope.forbiddenAddresses.push(success.toJSON());
                $scope.newAddress = '';
                blockUI.stop();
            },
            function(error) {}
            );
    };

    function init() {
        blockUI.start();
        ForbiddenAddressREST.query(
            {},
            function(success) {
                $scope.forbiddenAddresses = success;
                blockUI.stop();
            },
            function(error) {}
            );
    }

    init();

});