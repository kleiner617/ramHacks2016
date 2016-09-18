(function() {
    'use strict';

    angular
        .module('ramhacks2016App')
        .controller('MonthlyGoalController', MonthlyGoalController);

    MonthlyGoalController.$inject = ['$scope', '$state', 'MonthlyGoal'];

    function MonthlyGoalController ($scope, $state, MonthlyGoal) {
        var vm = this;
        
        vm.monthlyGoals = [];

        loadAll();

        function loadAll() {
            MonthlyGoal.query(function(result) {
                vm.monthlyGoals = result;
            });
        }
    }
})();
