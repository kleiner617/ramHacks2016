(function() {
    'use strict';

    angular
        .module('ramhacks2016App')
        .controller('CategoryDialogController', CategoryDialogController);

    CategoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Category', 'Purchase', 'MonthlyGoal'];

    function CategoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Category, Purchase, MonthlyGoal) {
        var vm = this;

        vm.category = entity;
        vm.clear = clear;
        vm.save = save;
        vm.purchases = Purchase.query();
        vm.monthlygoals = MonthlyGoal.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.category.id !== null) {
                Category.update(vm.category, onSaveSuccess, onSaveError);
            } else {
                Category.save(vm.category, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ramhacks2016App:categoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
