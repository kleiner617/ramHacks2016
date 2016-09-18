/**
 * Created by owner on 9/17/16.
 */
(function() {
    'use strict';

    angular
        .module('ramhacks2016App')
        .controller('StatisticsController', StatisticsController);

    StatisticsController.$inject = ['$scope', '$state'];

    function StatisticsController ($scope, $state) {
    // hist plot
        setTimeout(function(){
            var data = {
                labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                series: [
                    [5, 4, 7, 7, 5, 10, 3],
                    [4, 5, 9, 6, 4, 6, 4]
                ]
            };

            var options = {
                seriesBarDistance: 10
            };

            var responsiveOptions = [
                ['screen and (max-width: 640px)', {
                    seriesBarDistance: 5,
                    axisX: {
                        labelInterpolationFnc: function (value) {
                            return value[0];
                        }
                    }
                }]
            ];
            new Chartist.Bar('#statsGraph', data, options, responsiveOptions);
        }, 3000);
    }
})();
