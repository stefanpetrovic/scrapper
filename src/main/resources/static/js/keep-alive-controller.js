scrapperApp.controller("KeepAliveController", function KeepAliveController($scope, $timeout, KeepAliveREST) {

    //28 minutes keep alive calls
    var keepAliveInterval = 1000 * 60 * 28;

    function keepAlive() {
        console.log("keep alive");
        KeepAliveREST.get({}, function() {}, function(error) {console.log(error);});
        $timeout(keepAlive, keepAliveInterval);
    }

    $timeout(keepAlive, keepAliveInterval);

});