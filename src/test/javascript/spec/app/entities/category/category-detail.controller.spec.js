'use strict';

describe('Controller Tests', function() {

    describe('Category Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCategory, MockPurchase, MockMonthlyGoal;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCategory = jasmine.createSpy('MockCategory');
            MockPurchase = jasmine.createSpy('MockPurchase');
            MockMonthlyGoal = jasmine.createSpy('MockMonthlyGoal');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Category': MockCategory,
                'Purchase': MockPurchase,
                'MonthlyGoal': MockMonthlyGoal
            };
            createController = function() {
                $injector.get('$controller')("CategoryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ramhacks2016App:categoryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
