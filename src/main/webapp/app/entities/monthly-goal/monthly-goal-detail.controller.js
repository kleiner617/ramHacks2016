(function() {
    'use strict';

    angular
        .module('ramhacks2016App')
        .controller('MonthlyGoalDetailController', MonthlyGoalDetailController);

    MonthlyGoalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MonthlyGoal', 'Category'];

    function MonthlyGoalDetailController($scope, $rootScope, $stateParams, previousState, entity, MonthlyGoal, Category) {
        var vm = this;

        vm.monthlyGoal = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ramhacks2016App:monthlyGoalUpdate', function(event, result) {
            vm.monthlyGoal = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
