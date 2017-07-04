scrapperApp.factory("ForbiddenAddressREST", function($resource) {
    return $resource(
        "forbiddenAddresses/:id",
        null,
        {
            'put': {
                method: 'PUT'
            }
        }
    );
});

scrapperApp.factory("ProcessorREST", function($resource) {
   return $resource(
       "processor"
   );
});


scrapperApp.factory("ApartmentREST", function($resource) {
    return $resource(
        "apartments",
        null,
        {
            'put': {
                method: 'PUT'
            }
        }
    );
});