(function() {
    'use strict';

    angular
        .module('ramhacks2016App')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function HomeController ($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.currentMonth = null;
        vm.savings = 350;
        vm.numPoints = 350;
        vm.level = null;
        vm.pointsToLevel = null;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        determineLevel();

        function determineLevel(){
            if (vm.numPoints<200){
                vm.level = 1;
                vm.pointsToLevel = 200 - vm.numPoints;
            }
            else if (vm.numPoints < 500){
                vm.level = 2;
                vm.pointsToLevel = 500 - vm.numPoints;
            }
            else{
                vm.level = 3;
                vm.pointsToLevel = 0;
            }
        }

        getAccount();

        getCurrentMonth();

        function getCurrentMonth(){
            var monthNames = ["January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
            ];
            var d = new Date();
            vm.currentMonth = monthNames[d.getMonth()];

        }

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
