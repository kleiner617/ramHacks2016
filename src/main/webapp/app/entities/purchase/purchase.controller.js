(function() {
    'use strict';

    angular
        .module('ramhacks2016App')
        .controller('PurchaseController', PurchaseController);

    PurchaseController.$inject = ['$scope', '$state', 'Purchase'];

    function PurchaseController ($scope, $state, Purchase) {
        var vm = this;
        vm.purchase = [{"purchaseId":"1", "type":"entertainment", "purchaseDate": "09/12/2016", "amount": "6.57", "description":"movie tickets"},
            {"purchaseId":"1", "type":"gas", "purchaseDate": "09/12/2016", "amount": "16.57", "description":"BP"},
            {"purchaseId":"2", "type":"dining", "purchaseDate": "09/12/2016", "amount": "90.62", "description":"Mama Zu"},
            {"purchaseId":"3", "type":"groceries", "purchaseDate": "09/12/2016", "amount": "106.89", "description":"Kroger"},
            {"purchaseId":"4", "type":"entertainment", "purchaseDate": "09/12/2016", "amount": "23.7", "description":"The Flying Squierrels"},
            {"purchaseId":"5", "type":"education", "purchaseDate": "09/12/2016", "amount": "987.32", "description":"VCU"},
            {"purchaseId":"6", "type":"dining", "purchaseDate": "09/12/2016", "amount": "7.49", "description":"Noodles & Co"},
            {"purchaseId":"7", "type":"groceries", "purchaseDate": "09/12/2016", "amount": "14.16", "description":"Kroger"},
            {"purchaseId":"8", "type":"groceries", "purchaseDate": "09/12/2016", "amount": "23.49", "description":"Kroger"},
            {"purchaseId":"9", "type":"entertainment", "purchaseDate": "09/12/2016", "amount": "20.00", "description":"movie tickets"},
            {"purchaseId":"10", "type":"health and fitness", "purchaseDate": "09/12/2016", "amount": "30.00", "description":"ACAC"},
            {"purchaseId":"11", "type":"gas", "purchaseDate": "09/12/2016", "amount": "20.83", "description":"Exxon"},
            {"purchaseId":"12", "type":"rent", "purchaseDate": "09/12/2016", "amount": "673.53", "description":"Celeste Walker"},
            {"purchaseId":"13", "type":"bills", "purchaseDate": "09/12/2016", "amount": "109.73", "description":"Verizon"},
            {"purchaseId":"14", "type":"bills", "purchaseDate": "09/12/2016", "amount": "81.13", "description":"Utilities"},
            {"purchaseId":"15", "type":"groceries", "purchaseDate": "09/12/2016", "amount": "24.56", "description":"Martins"},
            {"purchaseId":"16", "type":"entertainment", "purchaseDate": "09/12/2016", "amount": "11.14", "description":"putt putt"},
            {"purchaseId":"17", "type":"dining", "purchaseDate": "09/12/2016", "amount": "46.57", "description":"Can Can"},
            {"purchaseId":"18", "type":"health and fitness", "purchaseDate": "09/12/2016", "amount": "30.00", "description":"ACAC"},
            {"purchaseId":"19", "type":"dining", "purchaseDate": "09/12/2016", "amount": "30.56", "description":"Lunch"},
            {"purchaseId":"20", "type":"entertainment", "purchaseDate": "09/12/2016", "amount": "20.11", "description":"The National"},
            {"purchaseId":"21", "type":"shopping", "purchaseDate": "09/12/2016", "amount": "90.78", "description":"Madewell"},
            {"purchaseId":"22", "type":"gas", "purchaseDate": "09/12/2016", "amount": "19.60", "description":"gas"},
            {"purchaseId":"31", "type":"food", "purchaseDate": "8/12/2016", "amount": "22.37", "description":"Subway"},
            {"purchaseId":"45", "type":"entertainment", "purchaseDate": "7/12/2016", "amount": "5.22", "description":"netflix"},
            {"purchaseId":"40", "type":"shopping", "purchaseDate": "2/12/2016", "amount": "5.65", "description":"Macy's"},
            {"purchaseId":"67", "type":"education", "purchaseDate": "1/11/2016", "amount": "39.99", "description":"Udemy"},
            {"purchaseId":"78", "type":"kids", "purchaseDate": "3/9/2016", "amount": "12.22", "description":"Pampers"},
            {"purchaseId":"55", "type":"food + dining", "purchaseDate": "3/9/2016", "amount": "12.22", "description":"Boka"}
        ];
        console.log(vm.purchase[0].amount);
        loadAll();

        function loadAll() {
            Purchase.query(function(result) {
                vm.purchases = result;
            });
        }
    }
})();
