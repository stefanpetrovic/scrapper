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

scrapperApp.factory("KeepAliveREST", function ($resource) {
    return $resource(
        "keepAlive"
    );
});

scrapperApp.factory("RecommenderConfigREST", function ($resource) {
    return $resource(
        "recommenderConfig"
    );
});

scrapperApp.factory("SmokingTestAnswersREST", function($resource) {
    return $resource(
        "smokingtestanswers"
    )
});