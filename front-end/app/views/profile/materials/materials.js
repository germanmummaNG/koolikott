define([
    'app',
    'directives/materialBox/materialBox',
    'services/serverCallService'
], function (app) {
    return ['$scope', '$route', 'authenticatedUserService', 'serverCallService',
        function ($scope, $route, authenticatedUserService, serverCallService) {
        function init() {
            getUserMaterials();
        }

        function getUserMaterials() {
            var params = {
                'username': authenticatedUserService.getUser().username
            }
            serverCallService.makeGet("rest/material/getByCreator", params, getMaterialsSuccess, getDataFailed)
        }

        function getMaterialsSuccess(data) {
            if(data) {
                $scope.materials = data
            }
        }

        function getDataFailed() {
            console.log("Failed to get data")
        }

        init();
    }];
});
