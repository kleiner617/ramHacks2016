(function() {
    'use strict';

    angular
        .module('ramhacks2016App')
        .controller('PurchaseDialogController', PurchaseDialogController);

    PurchaseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Purchase', 'Category'];

    function PurchaseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Purchase, Category) {
        var vm = this;

        vm.purchase = entity;
        vm.clear = clear;
        vm.save = save;
        vm.categories = Category.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.purchase.id !== null) {
                Purchase.update(vm.purchase, onSaveSuccess, onSaveError);
            } else {
                Purchase.save(vm.purchase, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ramhacks2016App:purchaseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
