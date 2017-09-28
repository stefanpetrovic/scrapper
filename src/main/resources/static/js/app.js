var scrapperApp = angular.module('scrapperApp', [
    'ngRoute',
    'ngResource',
    'blockUI',
    'ui.bootstrap',
    'ngTagsInput'
]);

scrapperApp.filter('htmlToPlaintext', function() {
        return function(text) {
            return  text ? String(text).replace(/<[^>]+>|&nbsp;/gm, '') : '';
        };
    }
);