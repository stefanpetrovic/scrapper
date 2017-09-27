scrapperApp.controller('ApartmentController', function ApartmentController($scope, ApartmentREST) {

    $scope.pageSizes = [
        10, 20, 50, 100
    ];

    $scope.apartments = [];
    $scope.pagedApartments = [];
    $scope.filters = {
        showOnlyRecommended: false
    };
    $scope.pagination = {
        pageNum: 1,
        pageSize: $scope.pageSizes[0],
        totalItems: 0
    };

    $scope.orderByItem = 'createdDate';

    $scope.pageChanged = function() {
        fetchPage();
    };

    $scope.pageSizeChanged = function() {
        pageApartments();
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
        ApartmentREST.query(
            {
            },
            function(apartments) {
                $scope.apartments = apartments;
                $scope.pagination.totalItems = apartments.size;

                pageApartments();
            },
            function(error) {
                console.log(error);
            }
        );
    }

    function pageApartments() {
        $scope.pagedApartments = $scope.apartments.slice(($scope.pagination.pageNum - 1) * $scope.pagination.pageSize, $scope.pagination.pageSize);
    }
    function init() {
        fetchPage();
    }

    init();
});