/**
 * Created by owner on 9/17/16.
 */
(function() {
    'use strict';

    angular
        .module('ramhacks2016App')
        .controller('ProfileController', ProfileController);

    ProfileController.$inject = ['Principal', 'Auth'];

    function ProfileController (Principal, Auth) {
        /*var vm = this;

        vm.error = null;
        vm.save = save;
        vm.settingsAccount = null;
        vm.success = null;

        /!**
         * Store the "settings account" in a separate variable, and not in the shared "account" variable.
         *!/
        var copyAccount = function (account) {
            return {
                activated: account.activated,
                email: account.email,
                firstName: account.firstName,
                langKey: account.langKey,
                lastName: account.lastName,
                login: account.login
            };
        };

        Principal.identity().then(function(account) {
            vm.settingsAccount = copyAccount(account);
        });

        function save () {
            Auth.updateAccount(vm.settingsAccount).then(function() {
                vm.error = null;
                vm.success = 'OK';
                Principal.identity(true).then(function(account) {
                    vm.settingsAccount = copyAccount(account);
                });
            }).catch(function() {
                vm.success = null;
                vm.error = 'ERROR';
            });
        }*/
    }
})();
