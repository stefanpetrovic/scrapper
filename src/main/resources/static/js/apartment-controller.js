scrapperApp.controller('ApartmentController', function ApartmentController($scope, ApartmentREST) {

    $scope.apartments = [];
    $scope.filters = {
        showOnlyRecommended: false
    };
    $scope.pagination = {
        pageNum: 1,
        pageSize: 50,
        totalItems: 0
    };

    $scope.pageChanged = function() {
        fetchPage();
    };

    $scope.filter = function() {
        fetchPage();
    };

    $scope.updateApartment = function(apartment) {
        ApartmentREST.put(
            {},
            apartment,
            function (success) {

            },
            function(error) {
                console.log(error);
            }
        );
    };

    function fetchPage() {
        ApartmentREST.get(
            {
                onlyRecommended: $scope.filters.showOnlyRecommended,
                pageNum: $scope.pagination.pageNum - 1,
                pageSize: $scope.pagination.pageSize
            },
            function(success) {
                $scope.apartments = success.content;
                $scope.pagination.totalItems = success.totalElements;
            },
            function(error) {
                console.log(error);
            }
        );
    }

    function init() {
        fetchPage();
    }

    init();
});