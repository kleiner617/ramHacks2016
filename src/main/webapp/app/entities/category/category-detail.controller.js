(function() {
    'use strict';

    angular
        .module('ramhacks2016App')
        .controller('CategoryDetailController', CategoryDetailController);

    CategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Category', 'Purchase', 'MonthlyGoal'];

    function CategoryDetailController($scope, $rootScope, $stateParams, previousState, entity, Category, Purchase, MonthlyGoal) {
        var vm = this;

        vm.category = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ramhacks2016App:categoryUpdate', function(event, result) {
            vm.category = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
