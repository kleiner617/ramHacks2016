(function() {
    'use strict';

    angular
        .module('ramhacks2016App', [
            'ngStorage',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            'angularjs-dropdown-multiselect',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar',

        ])
        .run(run);

    run.$inject = ['stateHandler'];

    function run(stateHandler) {
        stateHandler.initialize();
    }
})();
