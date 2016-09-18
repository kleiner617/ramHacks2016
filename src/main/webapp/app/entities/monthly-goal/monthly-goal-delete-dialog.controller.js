(function() {
    'use strict';

    angular
        .module('ramhacks2016App')
        .controller('MonthlyGoalDeleteController',MonthlyGoalDeleteController);

    MonthlyGoalDeleteController.$inject = ['$uibModalInstance', 'entity', 'MonthlyGoal'];

    function MonthlyGoalDeleteController($uibModalInstance, entity, MonthlyGoal) {
        var vm = this;

        vm.monthlyGoal = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MonthlyGoal.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
