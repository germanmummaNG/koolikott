'use strict'

{
class controller extends Controller {
    $onInit() {
        this.$scope.isOpen = false
        this.$scope.userHasSelectedMaterials = false

        this.$rootScope.$watch('selectedMaterials.length', (newValue) => {
            this.$scope.userHasSelectedMaterials = newValue > 0
        }, false)

        this.$rootScope.$watch('selectedSingleMaterial', (newValue) => {
            this.$scope.userHasSelectedMaterials = newValue !== null && newValue !== undefined
        }, false)

        this.$scope.showAddPortfolioDialog = ($event) => {
            $event.preventDefault()

            const emptyPortfolio = this.createPortfolio()

            if (this.$scope.userHasSelectedMaterials || this.$rootScope.selectedSingleMaterial) {
                emptyPortfolio.chapters.push({
                    title: '',
                    subchapters: [],
                    materials: []
                })

                if (this.$rootScope.selectedMaterials && this.$rootScope.selectedMaterials.length > 0)
                    for (let i = 0; i < this.$rootScope.selectedMaterials.length; i++)
                        emptyPortfolio.chapters[0].contentRows.push({
                            learningObjects: [this.$rootScope.selectedMaterials[i]]
                        })

                else if(this.$rootScope.selectedSingleMaterial != null)
                    emptyPortfolio.chapters[0].contentRows = (emptyPortfolio.chapters[0].contentRows || []).concat({
                        learningObjects: [this.$rootScope.selectedSingleMaterial]
                    })

                this.toastService.showOnRouteChange('PORTFOLIO_ADD_MATERIAL_SUCCESS')
            }

            this.storageService.setEmptyPortfolio(emptyPortfolio)
            this.$mdDialog.show({
                templateUrl: 'views/addPortfolioDialog/addPortfolioDialog.html',
                controller: 'addPortfolioDialogController',
                fullscreen: false,
                locals: {
                    mode: 'ADD'
                }
            })
        }

        this.$scope.showAddMaterialDialog = () =>
            this.$mdDialog.show({
                templateUrl: 'addMaterialDialog.html',
                controller: 'addMaterialDialogController',
                controllerAs: '$ctrl',
                locals: { isEditMode: false }
            })

        /*this.$scope.copyPortfolio = () => {
            const portfolio = this.storageService.getPortfolio();
            if (!portfolio) console.log("copying failed")
            this.storageService.setEmptyPortfolio(portfolio)
            this.$mdDialog.show({
                templateUrl: 'views/addPortfolioDialog/addPortfolioDialog.html',
                controller: 'addPortfolioDialogController',
                fullscreen: false,
                locals: {
                    mode: 'COPY'
                }
            })
        }*/

        this.$scope.hasPermission = () =>
            this.authenticatedUserService.getUser() && !this.authenticatedUserService.isRestricted()

        this.$scope.setFabState = (state) => {
            if (!('ontouchstart' in window || window.DocumentTouch && document instanceof DocumentTouch))
                this.$scope.isOpen = state
        }
    }
}
controller.$inject = [
    '$scope',
    '$location',
    '$rootScope',
    '$route',
    '$mdDialog',
    'authenticatedUserService',
    'serverCallService',
    'storageService',
    'toastService'
]
component('dopMainFabButton', {
    templateUrl: 'directives/mainFabButton/mainFabButton.html',
    controller
})
}
