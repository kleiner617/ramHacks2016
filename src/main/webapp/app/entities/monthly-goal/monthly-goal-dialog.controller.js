(function() {
    'use strict';

    angular
        .module('ramhacks2016App')
        .controller('MonthlyGoalDialogController', MonthlyGoalDialogController);

    MonthlyGoalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MonthlyGoal', 'Category'];

    function MonthlyGoalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MonthlyGoal, Category) {
        var vm = this;

        vm.months = ["January", "February", "March"];
        vm.monthlyGoal = entity;
        vm.clear = clear;
        vm.save = save;
        vm.categories = Category.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        vm.monthlyGoal.categories = [];
        $scope.categoriesdata = [ {id: "1", label: "food"}, {id: "2", label: "groceries"}, {id: "3", label: "entertainment"}];
        $scope.categoriessettings = {displayProp: 'label'};


        function save () {
            vm.isSaving = true;
            if (vm.monthlyGoal.id !== null) {
                MonthlyGoal.update(vm.monthlyGoal, onSaveSuccess, onSaveError);
            } else {
                MonthlyGoal.save(vm.monthlyGoal, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ramhacks2016App:monthlyGoalUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
