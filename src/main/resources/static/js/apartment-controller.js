scrapperApp.controller('ApartmentController', function ApartmentController($scope, ApartmentREST, $filter) {

    $scope.pageSizes = [
        10, 20, 50, 100
    ];

    $scope.sortItems = [
        '-price', '-area', '-noOfRooms', '-createdDate'
    ];

    $scope.apartmentSizes = [
        10, 20, 30, 40, 50, 60, 70, 80, 90, 100
    ];

    $scope.prices = [
        10000, 15000, 20000, 25000, 30000, 35000, 40000, 45000, 50000, 55000, 60000, 65000, 70000, 75000, 80000, 85000, 90000
    ];

    $scope.apartments = [];
    $scope.filteredApartments = [];

    $scope.pagedApartments = [];

    $scope.filters = {
        showOnlyRecommended: true,
        sizeGreaterThan: $scope.apartmentSizes[0],
        sizeLessThan: $scope.apartmentSizes[9],
        priceGreaterThan: $scope.prices[0],
        priceLessThan: $scope.prices[16],
        tags: [],
        selectedSort: $scope.sortItems[3]
    };

    $scope.pagination = {
        pageNum: 1,
        pageSize: $scope.pageSizes[0],
        totalItems: 0
    };

    $scope.orderByItem = 'createdDate';

    $scope.filter = function() {
        $scope.filteredApartments = sort(filterByTags(filterByRecommended(filterByApartmentSize(filterByPrice($scope.apartments)))));
        $scope.pagination.totalItems = $scope.filteredApartments.length;
        $scope.pageApartments();
    };

    function filterByApartmentSize(apartments) {
        var result = [];
        apartments.forEach(function(item) {
            var apartmentSize = item.area;
            if (apartmentSize > $scope.filters.sizeGreaterThan && apartmentSize < $scope.filters.sizeLessThan) {
                result.push(item);
            }
        });

        return result;
    }

    function filterByRecommended(apartments) {
        if (!$scope.filters.showOnlyRecommended) {
            return apartments;
        }

        var result = [];

        apartments.forEach(function(item) {
            if (item.recommended) {
                result.push(item);
            }
        });

        return result;
    }

    function filterByPrice(apartments) {
        var result = [];
        apartments.forEach(function(item) {
            var price = item.price;
            if (price > $scope.filters.priceGreaterThan && price < $scope.filters.priceLessThan) {
                result.push(item);
            }
        });

        return result;
    }

    function filterByTags(apartments) {
        var result = [];
        apartments.forEach(function(item){
            var containsTag = false;
            $scope.filters.tags.forEach(function(tag){
                if ($filter('json')(item).indexOf(tag.text) !== -1) {
                    containsTag = true;
                }
            });

            if (!containsTag) {
                result.push(item);
            }
        });

        return result;
    }

    function sort(apartments) {
        return $filter('orderBy')(apartments, $scope.filters.selectedSort);
    }

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

                $scope.filter();
                $scope.pageApartments();
            },
            function(error) {
                console.log(error);
            }
        );
    }

    $scope.pageApartments = function() {
        $scope.pagedApartments = $scope.filteredApartments.slice(($scope.pagination.pageNum - 1) * $scope.pagination.pageSize, $scope.pagination.pageNum * $scope.pagination.pageSize);
    };

    function init() {
        fetchPage();
    }

    init();
});