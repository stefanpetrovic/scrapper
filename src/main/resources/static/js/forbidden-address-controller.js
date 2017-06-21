scrapperApp.controller("ForbiddenAddressController", function ManagementController($scope, ForbiddenAddressREST, blockUI, $filter) {

    $scope.forbiddenAddresses = [];
    $scope.newAddress = '';

    $scope.removeAddress = function (id, index) {
        blockUI.start();
        ForbiddenAddressREST.remove(
            {
                id: id
            },
            function () {
                $scope.forbiddenAddresses.splice(index, 1);
                blockUI.stop();
            },
            function (error) {
                console.log(error);
            });
    };

    $scope.addAddress = function () {
        blockUI.start();
        ForbiddenAddressREST.save(
            {
                "newAddress": $scope.newAddress
            },
            {},
            function (success) {
                $scope.forbiddenAddresses.push(success.toJSON());
                $scope.newAddress = '';

                $scope.forbiddenAddresses = orderByAttr($scope.forbiddenAddresses, 'address');
                blockUI.stop();
            },
            function (error) {
                console.log(error);
            }
        );
    };

    var currentlyEditableElem = undefined;
    $scope.enableEdit = function (id) {

        if (id === currentlyEditableElem) {
            //do nothing for selecting same input twice
            return;
        }

        if (currentlyEditableElem) {
            angular.element("#" + currentlyEditableElem).prop("disabled", true);
        }

        currentlyEditableElem = id;
        angular.element("#" + currentlyEditableElem).prop("disabled", false);
    };

    $scope.editAddress = function (address) {
        ForbiddenAddressREST.put(
            {},
            address,
            function (success) {
                console.log(success);
            },
            function (error) {
                console.log(error);
            }
        );
    };

    $scope.isVisibleSave = function (id) {
        return currentlyEditableElem === id;
    };

    function init() {
        blockUI.start();
        ForbiddenAddressREST.query(
            {},
            function (success) {
                $scope.forbiddenAddresses = orderByAttr(success, 'address');
                blockUI.stop();
            },
            function (error) {
                console.log(error);
            }
        );
    }

    init();

    function orderByAttr(collection, attr) {
        return $filter('orderBy')(collection, attr);
    }
});