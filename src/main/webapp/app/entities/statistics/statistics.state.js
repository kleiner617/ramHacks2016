/**
 * Created by owner on 9/17/16.
 */
(function() {
    'use strict';

    angular
        .module('ramhacks2016App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('statistics', {
                parent: 'entity',
                url: '/statistics',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Statistics'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/statistics/statistics.html',
                        controller: 'StatisticsController',
                        controllerAs: 'vm'
                    }
                }
            })
            ;
    }

})();
