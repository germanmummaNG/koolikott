'use strict'

angular.module('koolikottApp')
.directive('dopChapterToolbar',
[
    'translationService', '$mdDialog', '$rootScope', 'storageService', 'serverCallService', '$filter', '$anchorScroll',
    function(translationService, $mdDialog, $rootScope, storageService, serverCallService, $filter, $anchorScroll) {
        return {
            scope: {
                chapter: '=',
                isSub: '='
            },
            templateUrl: 'directives/chapter/chapterToolbar/chapterToolbar.html',
            controller: function($scope) {
                $scope.isEditable = $rootScope.isEditPortfolioMode;

                $scope.addMaterial = function() {
                    var addMaterialScope = $scope.$new(true);

                    addMaterialScope.uploadMode = true;
                    addMaterialScope.material = {};
                    addMaterialScope.isChapterMaterial = true;
                    storageService.setMaterial(null);

                    $mdDialog.show({
                        templateUrl: 'addMaterialDialog.html',
                        controller: 'addMaterialDialogController',
                        scope: addMaterialScope
                    }).then(closeDialog);
                };

                function closeDialog(material) {
                    if (material) {
                        if(!$scope.chapter.contentRows) $scope.chapter.contentRows = [];
                        $scope.chapter.contentRows.push({learningObjects: [material]});
                    }
                }

                $scope.addNewSubChapter = function() {
                    var subChapters = $scope.chapter.subchapters;

                    subChapters.push({
                        title: $filter('translate')('PORTFOLIO_DEFAULT_NEW_SUBCHAPTER_TITLE'),
                        materials: [],
                        openCloseChapter: true
                    });
                };

                $scope.openMenu = function($mdOpenMenu, ev) {
                    $mdOpenMenu(ev);
                };

                $scope.openDetailedSearch = function () {
                    $rootScope.savedChapter = $scope.chapter;
                    $rootScope.$broadcast("detailedSearch:open");
                    $anchorScroll();
                    $rootScope.isPlaceholderVisible = true;

                    if($rootScope.isEditPortfolioPage) {
                        document.getElementById('input-0').focus();
                    }
                };
            }
        }
    }]);
