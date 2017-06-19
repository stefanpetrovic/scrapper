scrapperApp.factory("ForbiddenAddressREST", function($resource) {
    return $resource(
        "forbiddenAddresses/:id"
    );
});