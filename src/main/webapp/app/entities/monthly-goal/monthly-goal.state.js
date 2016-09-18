(function() {
    'use strict';

    angular
        .module('ramhacks2016App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('monthly-goal', {
            parent: 'entity',
            url: '/monthly-goal',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MonthlyGoals'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/monthly-goal/monthly-goals.html',
                    controller: 'MonthlyGoalController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('monthly-goal-detail', {
            parent: 'entity',
            url: '/monthly-goal/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MonthlyGoal'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/monthly-goal/monthly-goal-detail.html',
                    controller: 'MonthlyGoalDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'MonthlyGoal', function($stateParams, MonthlyGoal) {
                    return MonthlyGoal.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'monthly-goal',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('monthly-goal-detail.edit', {
            parent: 'monthly-goal-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/monthly-goal/monthly-goal-dialog.html',
                    controller: 'MonthlyGoalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MonthlyGoal', function(MonthlyGoal) {
                            return MonthlyGoal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('monthly-goal.new', {
            parent: 'monthly-goal',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/monthly-goal/monthly-goal-dialog.html',
                    controller: 'MonthlyGoalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                month: null,
                                percentage: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('monthly-goal', null, { reload: 'monthly-goal' });
                }, function() {
                    $state.go('monthly-goal');
                });
            }]
        })
        .state('monthly-goal.edit', {
            parent: 'monthly-goal',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/monthly-goal/monthly-goal-dialog.html',
                    controller: 'MonthlyGoalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MonthlyGoal', function(MonthlyGoal) {
                            return MonthlyGoal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('monthly-goal', null, { reload: 'monthly-goal' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('monthly-goal.delete', {
            parent: 'monthly-goal',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/monthly-goal/monthly-goal-delete-dialog.html',
                    controller: 'MonthlyGoalDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MonthlyGoal', function(MonthlyGoal) {
                            return MonthlyGoal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('monthly-goal', null, { reload: 'monthly-goal' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
