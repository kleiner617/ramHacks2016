/**
 * Created by owner on 9/17/16.
 */
(function() {
    'use strict';

    angular
        .module('ramhacks2016App')
        .controller('ProfileController', ProfileController);

    ProfileController.$inject = ['$scope', '$state', 'Principal', 'Auth'];

    function ProfileController ($scope, $state, Principal, Auth) {
        var vm = this;

        vm.error = null;
        vm.save = save;
        vm.profile = null;
        vm.success = null;

        /*vm.monthlyGoal.categories = [];
        $scope.categoriesdata = [ {id: "1", label: "food"}, {id: "2", label: "groceries"}, {id: "3", label: "entertainment"}];
        $scope.categoriessettings = {displayProp: 'label'};*/

        //TODO: Need to determine how many savings per month...

        /**
         * Store the "settings account" in a separate variable, and not in the shared "account" variable.
         */

        var copyAccount = function (account) {
            return {
                activated: account.activated,
                income: account.income,
                fixedExpense: account.fixedExpense,
                totalSavings: account.totalSavings,
                forObject: account.forObject,
                months: account.months,
                category: account.category
            };
        };

        Principal.identity().then(function(account) {
            console.log(account.login);
            vm.profile = copyAccount(account);
        });

        function determineMonthlySavings(){
            console.log(vm.account.totalSavings);
            console.log(vm.account);
        }

        function save () {
            console.log("In save");
            Auth.updateAccount(vm.profile).then(function() {
                vm.error = null;
                vm.success = 'OK';
                Principal.identity(true).then(function(account) {
                    vm.profile = copyAccount(account);
                });
            }).catch(function() {
                vm.success = null;
                vm.error = 'ERROR';
            });
            determineMonthlySavings();
        }
    }
})();
