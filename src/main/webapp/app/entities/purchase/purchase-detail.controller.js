(function() {
    'use strict';

    angular
        .module('ramhacks2016App')
        .controller('PurchaseDetailController', PurchaseDetailController);

    PurchaseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Purchase', 'Category'];

    function PurchaseDetailController($scope, $rootScope, $stateParams, previousState, entity, Purchase, Category) {
        var vm = this;

        vm.purchase = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ramhacks2016App:purchaseUpdate', function(event, result) {
            vm.purchase = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
