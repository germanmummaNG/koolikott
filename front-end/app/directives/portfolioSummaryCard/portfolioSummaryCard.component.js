'use strict'

{
class controller extends Controller {
    $onInit() {

        const VISIBILITY_PUBLIC = 'PUBLIC'
        const VISIBILITY_PRIVATE = 'PRIVATE'
        const VISIBILITY_NOT_LISTED = 'NOT_LISTED'
        const licenceTypeMap = {
            'CCBY': ['by'],
            'CCBYSA': ['by', 'sa'],
            'CCBYND': ['by', 'nd'],
            'CCBYNC': ['by', 'nc'],
            'CCBYNCSA': ['by', 'nc', 'sa'],
            'CCBYNCND': ['by', 'nc', 'nd']
        };

        this.$scope.showEditModeButton = true
        this.deletePortfolio = this.deletePortfolio.bind(this)
        this.restorePortfolio = this.restorePortfolio.bind(this)

        this.eventService.notify('portfolio:reloadTaxonObject')

        // Main purpose of this watch is to handle situations
        // where portfolio is undefined at the moment of init()
        this.$scope.$watch('portfolio', (newValue, oldValue) => {
            if (newValue !== oldValue) {
                this.$rootScope.$broadcast('portfolioHistory:loadHistory');
                this.eventService.notify('portfolio:reloadTaxonObject')
            }
        })
        this.$scope.$watch('portfolio.taxon.id', (newValue, oldValue) => {
            if (newValue !== oldValue)
                $scope.portfolioSubject = this.taxonService.getSubject(this.portfolio.taxon)
        }, true)
        this.$scope.$watch(() => this.storageService.getPortfolio(), (currentValue, previousValue) => {
            if (currentValue !== previousValue)
                this.$scope.portfolio = currentValue
        }, true)

        this.$rootScope.$on('portfolioHistory:hide', this.showButtons.bind(this));
        this.$rootScope.$on('portfolio:autoSave', this.getHistoryType.bind(this));
        this.$rootScope.$on('portfolioHistory:hideDeleteButton', this.hideButtons.bind(this));

        this.$scope.portfolio = this.portfolio
        this.$scope.showlogselect = this.showlogselect
        this.$scope.pageUrl = this.$location.absUrl()

        this.$scope.isAutoSaving = false;
        this.$scope.showLogButton = true;
        this.$scope.showDeleteButton = true;
        this.$scope.showSendEmailButton = true;
        this.$scope.showRecommendButton = true;
        this.$scope.showReportImproperButton = true;

        this.$scope.canEdit = this.canEdit.bind(this)
        this.$scope.isAdmin = this.isAdmin.bind(this)
        this.$scope.isOwner= this.isOwner.bind(this)
        this.$scope.isAdminOrModerator = this.isAdminOrModerator.bind(this)
        this.$scope.isLoggedIn = this.isLoggedIn.bind(this)
        this.$scope.isRestricted = this.isRestricted.bind(this)
        this.$scope.editPortfolio = this.editPortfolio.bind(this)
        this.$scope.getPortfolioEducationalContexts = this.getPortfolioEducationalContexts.bind(this)
        this.$scope.showEditMetadataDialog = this.showEditMetadataDialog.bind(this)
        this.$scope.confirmPortfolioDeletion = this.confirmPortfolioDeletion.bind(this)
        this.$scope.getTargetGroups = this.getTargetGroups.bind(this)
        this.$scope.isAdminButtonsShowing = this.isAdminButtonsShowing.bind(this)
        this.$scope.setRecommendation = this.setRecommendation.bind(this)
        this.$scope.dotsAreShowing = this.dotsAreShowing.bind(this)
        this.$scope.restorePortfolio = this.restorePortfolio
        this.$scope.toggleFullScreen = this.toggleFullScreen.bind(this)

        this.$scope.getLicenseIconList = () => {
            if (this.portfolio && this.portfolio.licenseType) {
                return licenceTypeMap[this.portfolio.licenseType.name];
            }
        };
        this.$scope.getPortfolioVisibility = () => (this.storageService.getPortfolio() || {}).visibility

        this.$scope.makePublic = () => {
            this.storageService.getPortfolio().visibility = VISIBILITY_PUBLIC
            this.updatePortfolio()
            this.toastService.show('PORTFOLIO_HAS_BEEN_MADE_PUBLIC')
        }

        this.$scope.makeNotListed = () => {
            this.storageService.getPortfolio().visibility = VISIBILITY_NOT_LISTED
            this.updatePortfolio()
        }

        this.$scope.makePrivate = () => {
            this.storageService.getPortfolio().visibility = VISIBILITY_PRIVATE
            this.updatePortfolio()
        }

        if (this.$rootScope.openMetadataDialog) {
            this.showEditMetadataDialog()
            this.$rootScope.openMetadataDialog = null
        }
    }
    $doCheck() {
        if (this.$scope.portfolio !== this.portfolio)
            this.$scope.portfolio = this.portfolio
    }

    showButtons(){
        this.$scope.showEditModeButton = true
        this.$scope.showLogButton = true;
        this.$scope.showDeleteButton = true;
        this.$scope.showSendEmailButton = true;
        this.$scope.showRecommendButton = true;
        this.$scope.showReportImproperButton = true;
    }

    showPortfolioHistoryDialog() {
        this.$scope.showEditModeButton = false;
        this.$scope.showLogButton = false;
        this.$rootScope.$broadcast('portfolioHistory:show');
    }

    hideButtons() {
        this.$scope.showDeleteButton = false;
        this.$scope.showSendEmailButton = false;
        this.$scope.showRecommendButton = false;
        this.$scope.showReportImproperButton = false;
    }

    canEdit() {
        return !this.authenticatedUserService.isRestricted() && (
            this.isOwner() ||
            this.authenticatedUserService.isAdmin() ||
            this.authenticatedUserService.isModerator()
        )
    }
    isOwner() {
        return !this.authenticatedUserService.isAuthenticated()
            ? false : this.portfolio && this.portfolio.creator
                ? this.portfolio.creator.id === this.authenticatedUserService.getUser().id : false
    }
    isAdmin() {
        return this.authenticatedUserService.isAdmin()
    }
    isAdminOrModerator() {
        return (
            this.authenticatedUserService.isAdmin() ||
            this.authenticatedUserService.isModerator()
        )
    }
    isLoggedIn() {
        return this.authenticatedUserService.isAuthenticated()
    }
    isRestricted() {
        return this.authenticatedUserService.isRestricted()
    }
    editPortfolio() {
        this.$location.url('/portfolio/edit?id=' + this.$route.current.params.id)
    }
    updatePortfolio() {
        this.updateChaptersStateFromEditors()
        this.serverCallService
            .makePost(`rest/portfolio/update`,
                this.storageService.getPortfolio())
            .then(({data: portfolio}) => {
                if (portfolio) {
                    this.storageService.setPortfolio(portfolio);
                    this.toastService.show('PORTFOLIO_SAVED');
                }
            })
            .catch(() => this.toastService.show('PORTFOLIO_SAVE_FAILED',15000))
    }

    getHistoryType(){
        this.$scope.isAutoSaving = true;
    }

     getPortfolioEducationalContexts() {
        if (!this.portfolio || !this.portfolio.taxons)
            return

        const educationalContexts = []

        this.portfolio.taxons.forEach(taxon => {
            const edCtx = this.taxonService.getEducationalContext(taxon)

            if (edCtx && !educationalContexts.includes(edCtx))
                educationalContexts.push(edCtx)
        })

        return educationalContexts
    }
    showEditMetadataDialog() {
        this.storageService.setPortfolio(this.portfolio)
        this.$mdDialog.show({
            templateUrl: 'views/addPortfolioDialog/addPortfolioDialog.html',
            controller: 'addPortfolioDialogController',
            locals: {
                mode: 'EDIT'
            },
        })
    }
    deletePortfolio() {
        this.serverCallService
            .makePost('rest/portfolio/delete', this.portfolio)
            .then(() => {
                this.toastService.show('PORTFOLIO_DELETED')
                this.portfolio.deleted = true
                this.$rootScope.learningObjectDeleted = true
                this.$location.url('/portfolio?id=' + this.$route.current.params.id)
                this.$rootScope.$broadcast('dashboard:adminCountsUpdated')
                this.$rootScope.$broadcast('portfolioHistory:closeLogBanner')
                this.$scope.showEditModeButton = false;
                this.$scope.showLogButton = false;
            })
    }

    confirmPortfolioDeletion() {
        this.dialogService.showDeleteConfirmationDialog(
            'PORTFOLIO_CONFIRM_DELETE_DIALOG_TITLE',
            'PORTFOLIO_CONFIRM_DELETE_DIALOG_CONTENT',
            this.deletePortfolio
        )
    }
    restorePortfolio() {
        this.serverCallService
            .makePost('rest/admin/deleted/restore', this.portfolio)
            .then(() => {
                this.toastService.show('PORTFOLIO_RESTORED')
                this.portfolio.deleted = false
                this.$rootScope.learningObjectDeleted = false
                this.$rootScope.$broadcast('dashboard:adminCountsUpdated')
                this.$rootScope.$broadcast('portfolioHistory:show')
                this.$scope.showEditModeButton = true;
                this.$scope.showLogButton = true;
            })
    }
    markReviewed() {
        if (this.portfolio && (
                this.authenticatedUserService.isAdmin() ||
                this.authenticatedUserService.isModerator()
            )
        )
            this.serverCallService
                .makePost('rest/admin/firstReview/setReviewed', this.portfolio)
                .then(() => {
                    this.$rootScope.learningObjectUnreviewed = false
                    this.$rootScope.$broadcast('dashboard:adminCountsUpdated')
                })
    }
    getTargetGroups() {
        return this.portfolio
            ? this.targetGroupService.getConcentratedLabelByTargetGroups(this.portfolio.targetGroups || [])
            : undefined
    }
    isAdminButtonsShowing() {
        return this.authenticatedUserService.isAdmin() && (
            this.$rootScope.learningObjectDeleted === true || this.$rootScope.learningObjectImproper === true
        )
    }
    dotsAreShowing () {
        return this.$rootScope.learningObjectDeleted === false || this.authenticatedUserService.isAdmin() || this.authenticatedUserService.isModerator();
    };
    setRecommendation(recommendation) {
        if (this.portfolio)
            this.portfolio.recommendation = recommendation
    }

    toggleFullScreen() {
        this.$rootScope.isFullScreen = !this.$rootScope.isFullScreen;
        toggleFullScreen();
        if (this.$rootScope.isFullScreen)
            this.toastService.show('YOU_CAN_LEAVE_PAGE_WITH_ESC', 15000, 'user-missing-id');
        else {
            this.toastService.hide()
        }
    }
}
controller.$inject = [
    '$rootScope',
    '$scope',
    '$location',
    '$mdDialog',
    'authenticatedUserService',
    '$route',
    'dialogService',
    'serverCallService',
    'toastService',
    'storageService',
    'targetGroupService',
    'taxonService',
    'taxonGroupingService',
    'eventService',
    'portfolioService',
    '$timeout'
]
component('dopPortfolioSummaryCard', {
    bindings: {
        portfolio: '=',
    },
    templateUrl: 'directives/portfolioSummaryCard/portfolioSummaryCard.html',
    controller
})
}
