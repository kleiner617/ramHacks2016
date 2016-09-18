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
        $stateProvider.state('profile', {
            parent: 'account',
            url: '/settings',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Profile'
            },
            /*
            views: {
                'content@': {
                    templateUrl: 'app/account/profile/profile.html',
                    controller: 'ProfileController',
                    controllerAs: 'vm'
                }
            }*/
        });
    }
})();
