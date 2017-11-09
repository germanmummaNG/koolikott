'use strict'

{
class controller extends Controller {
    navigateTo() {
        const { id } = this.learningObject

        if (this.isMaterial(this.learningObject)) {
            if (this.learningObject.type === '.Material'){
                this.storageService.setMaterial(this.learningObject)
            }
            this.$location.path('/material').search({ id })
        } else if (this.isPortfolio(this.learningObject.type)) {
            if (this.learningObject.type === '.Portfolio'){
                this.storageService.setPortfolio(this.learningObject)
            }
            this.$location.path('/portfolio').search({ id })
        }
    }
    getCorrectLanguageTitle() {
        const { titles, language } = this.learningObject || {}

        if (titles)
            return this.getUserDefinedLanguageString(
                titles,
                this.translationService.getLanguage(),
                language
            )
    }
}
controller.$inject = [
    '$location',
    'translationService',
    'storageService'
]
component('dopCardXs', {
    bindings: {
        learningObject: '='
    },
    templateUrl: 'directives/card/cardXS/cardXS.html',
    controller
})
}
