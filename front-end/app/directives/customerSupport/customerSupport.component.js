'use strict'

{
    class controller extends Controller {
        constructor(...args) {
            super(...args)

            this.$scope.validEmail = VALID_EMAIL
            this.$scope.customerSupport = {}
            this.$scope.backClickedWhileOther = false
            this.$scope.captchaSuccess = false
            this.$scope.captchaKey = ''
            this.getCaptchaKey()

            this.$scope.fileNames = []
            this.$scope.files = []

            this.getUserManualTitles()
            this.$rootScope.$on('logout:success', this.clearData.bind(this));

            this.$scope.$watch('customerSupport.subject', (selectedValue, previousValue) => {
                if (selectedValue && selectedValue !== previousValue) {
                    this.handleSelectChange(this.$scope.customerSupport.subject)
                    this.$scope.showUserManualsHelped = false
                }
            })
        }

        validateAttachments(){
            

        }

        // clickToRemove(chipFileToRemove){
        //     // let index = this.$scope.files.indexOf(chipDetails);
        //     this.$scope.files.forEach((chipFile,index) => {
        //         if (chipFile === chipFileToRemove)
        //             this.$scope.files.splice(index,1)
        //     });
        // }

        clearData() {
            this.$scope.customerSupport = {}
            this.$scope.showCustomerSupportInput = false
        }

        setResponse() {
            this.$translate('CUSTOMER_SUPPORT_WILL_SEND_RESPONSE').then((value) => {
                this.$scope.finalresponse = (value.replace('${email}', this.$scope.customerSupport.email));
            })
        }

        isUserModeratorOrAdmin() {
            return this.authenticatedUserService.isModeratorOrAdmin()
        }

        openNewTab() {
            this.$window.open(window.location.origin + '/usermanuals', '_blank')
            this.$scope.showUserManualsHelped = true
            this.$scope.allowDialogClose = false
        }

        getUserManualTitles() {
            this.userManualsAdminService.getUserManuals()
                .then(response => {
                    this.$scope.titles = response.data.map(um => {
                        return um.title
                    })
                    this.$scope.titles.push('Muu')
                })
        }

        saveCustomerSupportRequest() {

            this.$scope.isSaving = true
            this.setResponse()
            this.$scope.customerSupport.files = this.$scope.files


            this.serverCallService.makePost('/rest/admin/customerSupport', this.$scope.customerSupport)
                .then(response => {
                        this.$scope.isSaving = false
                        if (response.status === 200) {
                            this.$scope.showCustomerSupportInput = false
                            this.$scope.finalStep = true
                        } else {
                            this.$scope.showCustomerSupportDialog = false
                        }
                    }, () =>
                        this.$scope.isSaving = false
                )
        }

        getLoggedInUserData() {
            let user = this.authenticatedUserService.getUser();
            if (user) {
                const {name, surname} = user
                this.$scope.customerSupport.name = name + ' ' + surname
            }
        }

        showCustomerSupportInput() {
            this.$scope.showCustomerSupportInput = true
            this.$scope.userManualExists = false
            this.$scope.showUserManualsHelped = false
        }

        toggleCustomerSupportDialog() {
            this.getLoggedInUserData()
            this.$scope.showCustomerSupportDialog = true
        }

        isSendDisabled() {
            const {name, email, subject, message} = this.$scope.customerSupport;
            return !(name && email && subject && message && this.$scope.captchaSuccess)
        }

        back() {
            this.$scope.captchaSuccess = false
            if (this.$scope.customerSupport.subject === 'Muu') {
                this.$scope.customerSupport.subject = ''
                this.$scope.userManualExists = false
                this.$scope.showCustomerSupportInput = false
            } else {

                this.$scope.showCustomerSupportInput = false
                this.$scope.backClickedWhileOther = true
                this.handleSelectChange(this.$scope.customerSupport.subject)
            }
        }

        close() {
            this.$scope.showCustomerSupportInput = false
            this.$scope.showCustomerSupportDialog = false
            this.$scope.userManualExists = false
            this.$scope.finalStep = false
            this.$scope.showUserManualsHelped = false
            this.$scope.customerSupport = {}
        }

        handleSelectChange(subject) {
            this.$scope.userManualExists = subject !== 'Muu';
            this.$scope.showCustomerSupportInput = subject === 'Muu';
        }

        clickOutside() {
            if (!this.$scope.isSaving && this.$scope.allowDialogClose) {
                this.$scope.showCustomerSupportDialog = false
            }
            this.$scope.allowDialogClose = true
        }
        captchaSuccess() {
            this.$scope.captchaSuccess = true
        }
        getCaptchaKey() {
            this.serverCallService.makeGet('/rest/captcha')
                .then(({data}) => {
                    this.$scope.captchaKey = data
                })
        }
        getLanguage(){
            let language = this.translationService.getLanguage();
            if (language === 'est')
                return 'et'
            else if (language === 'rus')
                return 'ru'
            else
                return 'en'

        }
        putFilesIntoArray(files){
            this.$scope.files = files
            this.$scope.fileNames = files.map(f => f.name)

        }

        uploadFiles(files){
            this.$scope.files = files
            if (files && files.length){
                this.$scope.uploadingFile = true
                //     angular.forEach(
                //         files,function (file) {
                //             // this.fileUpload = this.fileUploadService
                //             //     .upload(file)
                //         }
                //     )
                // .then(function (response) {
                //     this.$timeout(function () {
                //         this.$scope.result = response.data;
                //     });
                // }, function (response) {
                //     if (response.status > 0) {
                //         this.$scope.errorMsg = response.status + ': ' + response.data;
                //     }
                // }, function (evt) {
                //     this.$scope.progress =
                //         Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
                // });
            }
        }
    }
    controller.$inject = [
        'userManualsAdminService',
        'serverCallService',
        'toastService',
        '$scope',
        'authenticatedUserService',
        '$location',
        '$translate',
        '$window',
        '$rootScope',
        'vcRecaptchaService',
        'translationService',
        '$parse',
        '$timeout'
    ]
    component('dopCustomerSupport', {
        templateUrl: 'directives/customerSupport/customerSupport.html',
        controller
    })
}
