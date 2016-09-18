(function() {
    'use strict';
    angular
        .module('ramhacks2016App')
        .factory('MonthlyGoal', MonthlyGoal);

    MonthlyGoal.$inject = ['$resource'];

    function MonthlyGoal ($resource) {
        var resourceUrl =  'api/monthly-goals/:id';

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
