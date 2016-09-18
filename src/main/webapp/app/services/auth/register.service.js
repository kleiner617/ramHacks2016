(function () {
    'use strict';

    angular
        .module('ramhacks2016App')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
