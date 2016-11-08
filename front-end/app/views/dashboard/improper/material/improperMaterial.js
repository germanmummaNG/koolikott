define([
    'app',
    'ngload!angular-material-data-table',
    'services/serverCallService',
    'services/userDataService',
    'views/dashboard/baseTable/baseTable'
], function(app) {
    app.controller('improperMaterialsController', ['$scope', 'serverCallService', '$controller', '$filter', 'userDataService',
        function($scope, serverCallService, $controller, $filter, userDataService) {
            var base = $controller('baseTableController', {
                $scope: $scope
            });

            serverCallService.makeGet("rest/impropers/materials", {}, getImproperMaterialsSuccess, base.getItemsFail);

            $scope.title = $filter('translate')('DASHBOARD_IMRPOPER_MATERIALS');

            $scope.bindTable = function() {
              base.buildTable('#improper-materials-table', 'views/dashboard/improper/improper.html');
            };

            $scope.getLearningObjectTitle = function(material)  {
            	return base.getCorrectLanguageTitle(material);
            };

            $scope.getLearningObjectUrl = function(learningObject) {
            	return "/material?materialId=" + learningObject.id;
            };

            function getImproperMaterialsSuccess(impropers) {
                if (impropers) {
                    base.getItemsSuccess(base.removeDeletedFromImpropers(impropers), 'byReportCount', true);
                } else {
                    base.getItemsFail();
                }
            }
        }
    ]);
});
