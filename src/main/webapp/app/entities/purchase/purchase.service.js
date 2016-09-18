(function() {
    'use strict';
    angular
        .module('ramhacks2016App')
        .factory('Purchase', Purchase);

    Purchase.$inject = ['$resource'];

    function Purchase ($resource) {
        var resourceUrl =  'api/purchases/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
