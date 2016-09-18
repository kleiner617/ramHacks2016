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
        vm.imgSource = null;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        function determineLevel(){
            if (numPoints<200){
                vm.imgSource = "content/images/level-1-icon.png";
            }
            else if (numPoints < 500){
                vm.imgSource = "content/images/level-1-icon.png";
            }
            else{
                vm.imgSource = "content/images/level-1-icon.png";
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
