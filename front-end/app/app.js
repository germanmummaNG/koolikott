'use strict';

let app = angular.module('koolikottApp', [
    'ngRoute',
    'ngMaterial',
    'pascalprecht.translate',
    'angular-click-outside',
    'duScroll',
    'ngFileUpload',
    'ui.bootstrap',
    'DOPconstants',
    'textAngular',
    'md.data.table',
    'infinite-scroll',
    'youtube-embed',
    '720kb.socialshare',
    'ngMessages',
    'angular-tour',
    'angularScreenfull',
    'angular-sortable-view',
    'monospaced.qrcode',
    'vcRecaptcha',
    'auto-tab',
    'ngCookies',
    'angularTrix',
]);

let provideProvider = null;

app.config([
    '$locationProvider', '$controllerProvider', '$compileProvider', '$filterProvider', '$provide', '$translateProvider', '$sceProvider', '$mdThemingProvider', '$httpProvider', '$mdDateLocaleProvider', '$anchorScrollProvider', '$qProvider',
    function ($locationProvider, $controllerProvider, $compileProvider, $filterProvider, $provide, $translateProvider, $sceProvider, $mdThemingProvider, $httpProvider, $mdDateLocaleProvider, $anchorScrollProvider, $qProvider) {
        $compileProvider.debugInfoEnabled(false);
        $httpProvider.useApplyAsync(true);
        provideProvider = $provide;

        // Fixes empty datepicker
        // https://github.com/angular/material/issues/10168
        $compileProvider.preAssignBindingsEnabled(true);

        configureTranslationService($translateProvider);
        configureTheme($mdThemingProvider);
        configureDateLocale($mdDateLocaleProvider);
        $sceProvider.enabled(false);

        if (!$httpProvider.defaults.headers.get) {
            $httpProvider.defaults.headers.get = {};
        }

        $provide.decorator('taOptions', ['$delegate', function (taOptions) {
            taOptions.forceTextAngularSanitize = true;

            taOptions.toolbar = [
                ['h2', 'h3', 'bold', 'italics', 'ul', 'ol', 'insertLink', 'pre']
            ];

            taOptions.classes = {
                focussed: 'focussed',
                toolbar: 'btn-toolbar',
                toolbarGroup: 'btn-group',
                toolbarButton: 'btn btn-textangular',
                toolbarButtonActive: 'active',
                disabled: 'disabled',
                textEditor: 'form-control',
                htmlEditor: 'form-control'
            };
            return taOptions; // whatever you return will be the taOptions
        }]);

        let isIE = (navigator.userAgent.match(/Trident/) || navigator.userAgent.match(/MSIE/));

        if (isIE) {
            // disable IE ajax request caching
            $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
            // extra
            $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
            $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
        }

        // disable logging of unhandled rejections as its broken in ui-router
        $qProvider.errorOnUnhandledRejections(false);

        $locationProvider.html5Mode(true);
        $anchorScrollProvider.disableAutoScrolling();
    }
]);

/**
 * orderByTranslated Filter
 * Sort ng-options or ng-repeat by translated values
 * @example
 *   ng-repeat="scheme in data.schemes | orderByTranslated:'storage__':'collectionName'"
 * @param  {Array|Object} array or hash
 * @param  {String} i18nKeyPrefix
 * @param  {String} objKey (needed if hash)
 * @return {Array}
 */
app.filter('orderByTranslated', ['$translate', '$filter', function ($translate, $filter) {
    return function (array, objKey, i18nKeyPrefix) {
        var result = [];
        var translated = [];
        if (!i18nKeyPrefix) {
            i18nKeyPrefix = '';
        }

        angular.forEach(array, function (value) {
            var i18nKeySuffix = objKey ? value[objKey] : value;
            translated.push({
                key: value,
                label: $translate.instant(i18nKeyPrefix + i18nKeySuffix)
            });
        });
        angular.forEach($filter('orderBy')(translated, 'label'), function (sortedObject) {
            result.push(sortedObject.key);
        });
        return result;
    };
}]);

function configureTranslationService($translateProvider) {
    $translateProvider.useUrlLoader('rest/translation');
    var language = localStorage.getItem("userPreferredLanguage");
    if (!language) {
        language = 'est';
    }

    $translateProvider.preferredLanguage(language);
    $translateProvider.useSanitizeValueStrategy('escaped');
}

// http://stackoverflow.com/questions/30123735/how-to-create-multiple-theme-in-material-angular
function configureTheme($mdThemingProvider) {
    var customBlueMap = $mdThemingProvider.extendPalette('light-blue', {
        'contrastDefaultColor': 'light',
        'contrastDarkColors': ['50'],
        '50': 'ffffff'
    });

    $mdThemingProvider.definePalette('customBlue', customBlueMap);
    $mdThemingProvider.theme('default')
        .primaryPalette('customBlue', {
            'default': '500',
            'hue-1': '50'
        })
        .accentPalette('purple', {
            'default': '500'
        });

    $mdThemingProvider.theme('input', 'default').primaryPalette('grey');
}

function configureDateLocale($mdDateLocaleProvider) {
    $mdDateLocaleProvider.firstDayOfWeek = 1;

    $mdDateLocaleProvider.formatDate = function (date) {
        return date ? moment(date).format('DD.MM.YYYY') : '';
    };

    $mdDateLocaleProvider.parseDate = function (dateString) {
        var m = moment(dateString, ['DD.MM.YYYY', 'MM.YYYY', 'YYYY', 'DD-MM-YYYY', 'DD/MM/YYYY'], true);
        return m.isValid() ? m.toDate() : new Date(NaN)
    };
}

function configureTextAngular($provide, $translate) {
    $provide.decorator('taTools', ['$delegate', function (taTools) {
        taTools.bold.buttontext = '<md-icon>format_bold</md-icon>';
        taTools.bold.iconclass = '';
        taTools.bold.tooltiptext = $translate.instant('TEXTANGULAR_BOLD');
        taTools.italics.buttontext = '<md-icon>format_italic</md-icon>';
        taTools.italics.iconclass = '';
        taTools.italics.tooltiptext = $translate.instant('TEXTANGULAR_ITALICS');
        taTools.insertLink.buttontext = '<md-icon>insert_link</md-icon>';
        taTools.insertLink.iconclass = '';
        taTools.insertLink.tooltiptext = $translate.instant('TEXTANGULAR_INSERT_LINK');
        taTools.ul.buttontext = '<md-icon>format_list_bulleted</md-icon>';
        taTools.ul.iconclass = '';
        taTools.ul.tooltiptext = $translate.instant('TEXTANGULAR_UL');
        taTools.ol.buttontext = '<md-icon>format_list_numbered</md-icon>';
        taTools.ol.iconclass = '';
        taTools.ol.tooltiptext = $translate.instant('TEXTANGULAR_OL');
        taTools.pre.buttontext = '<md-icon>crop_16_9</md-icon>';
        taTools.pre.iconclass = '';
        taTools.pre.tooltiptext = $translate.instant('TEXTANGULAR_PRE');
        taTools.quote.buttontext = '<md-icon>format_quote</md-icon>';
        taTools.quote.iconclass = '';
        taTools.quote.tooltiptext = $translate.instant('TEXTANGULAR_QUOTE');
        return taTools;
    }]);
}

app.run(['$rootScope', 'metadataService', 'APP_VERSION', 'taxonService', 'FB_APP_ID', 'GOOGLE_SHARE_CLIENT_ID',
    function ($rootScope, metadataService, APP_VERSION, taxonService, FB_APP_ID, GOOGLE_SHARE_CLIENT_ID) {
        $rootScope.APP_VERSION = APP_VERSION;
        $rootScope.FB_APP_ID = FB_APP_ID;
        $rootScope.GOOGLE_SHARE_CLIENT_ID = GOOGLE_SHARE_CLIENT_ID;
        $rootScope.hasAppInitated = false;
        metadataService.loadEducationalContexts();
    }]);

function isViewMyProfilePage($location, user) {
    return user && $location.path().startsWith('/' + user.username);
}

function isDashboardPage(path) {
    return path.indexOf("/dashboard") !== -1;
}

function isViewMaterialPage(path) {
    return path === '/material';
}

function isViewPortfolioPage(path) {
    return path === '/portfolio';
}

function isEditPortfolioPage(path) {
    return path === '/portfolio/edit';
}

function isHomePage(path) {
    return path === '/';
}

function isProfileOrSendEmailPath(path) {
    return path === '/profile' || path === '/dashboard/sentEmails';
}

app.run(['$rootScope', '$location', 'authenticatedUserService', 'storageService', 'serverCallService', 'userLocatorService', 'userSessionService',
    function ($rootScope, $location, authenticatedUserService, storageService, serverCallService, userLocatorService, userSessionService) {
        $rootScope.$on('$routeChangeSuccess', function () {
            var editModeAllowed = ["/portfolio/edit", "/search/result", "/material"];

            var path = $location.path();
            var user = authenticatedUserService.getUser();
            var isViewMyProfile = isViewMyProfilePage($location, user);
            let isLoggedIn = authenticatedUserService.isAuthenticated();

            userLocatorService.stopTimer()
            userSessionService.stopTimer()

            if (isLoggedIn && !$rootScope.locationDialogIsOpen)
                userLocatorService.startTimer()

            if (isLoggedIn) {
                userSessionService.startTimer()
            }
            $rootScope.isProfile = isProfileOrSendEmailPath(path)
            $rootScope.isAdmin = authenticatedUserService.isAdmin();
            $rootScope.isViewPortfolioPage = isViewPortfolioPage(path);
            $rootScope.isEditPortfolioPage = isEditPortfolioPage(path);
            $rootScope.isViewMaterialPage = isViewMaterialPage(path);
            $rootScope.isViewAdminPanelPage = isDashboardPage(path);
            $rootScope.isViewHomePage = isHomePage(path);
            $rootScope.isViewMaterialOrPortfolioPage = !!($rootScope.isViewMaterialPage || $rootScope.isViewPortfolioPage);
            $rootScope.isUserTabOpen
            $rootScope.isAdminTabOpen
            $rootScope.isTaxonomyOpen

            if (isViewMyProfile && $location.path() === '/' + user.username) {
                $location.path('/' + user.username + '/portfolios');
            }

            if ($rootScope.justLoggedIn && $rootScope.isAdmin) {
                $rootScope.isUserTabOpen = false
                $rootScope.isAdminTabOpen = true
            }

            if ($rootScope.isTaxonomyOpen) {
                $rootScope.isUserTabOpen = false
                $rootScope.isAdminTabOpen = false
            }

            if ($rootScope.justLoggedIn) {
                $rootScope.$broadcast('tour:start:firstTime');
            } else {
                $rootScope.$broadcast('tour:close:pageSwitch');
            }

            $rootScope.justLoggedIn = false;

            if ($rootScope.isEditPortfolioPage) {
                $rootScope.isEditPortfolioMode = true;
                $rootScope.selectedMaterials = [];
                $rootScope.selectedSingleMaterial = null;
            } else if (editModeAllowed.indexOf(path) !== -1) {
                $rootScope.selectedSingleMaterial = null;
                $rootScope.selectedMaterials = [];
            } else {
                $rootScope.isEditPortfolioMode = false;
                storageService.setPortfolio(null);
                $rootScope.selectedMaterials = null;
            }

            if (window.innerWidth > BREAK_LG && ($rootScope.isViewPortfolioPage || $rootScope.isEditPortfolioPage)) {
                $rootScope.sideNavOpen = true;
            }

            $rootScope.hasAppInitated = true;

            window.onbeforeunload = function() {
                userLocatorService.saveUserLocation()
            }
        });
    }]);

app.run(['APP_VERSION', function (APP_VERSION) {
    var savedVersion = localStorage.getItem('version');
    if (!savedVersion || savedVersion !== APP_VERSION) {
        var userData = localStorage.getItem('authenticatedUser');
        localStorage.clear();
        localStorage.setItem('version', APP_VERSION);
        if (userData) {
            localStorage.setItem('authenticatedUser', userData);
        }
    }
}]);

app.run(['$rootScope', '$location', function ($rootScope, $location) {
    var history = [];

    $rootScope.$on('$routeChangeSuccess', function () {
        history.push($location.url());
    });

    $rootScope.back = function () {
        var prevUrl = history.length > 1 ? history.splice(-2)[0] : '/';
        $location.url(prevUrl);
    };
}]);

app.run(['$rootScope', 'authenticatedUserService', '$route', '$location', function ($rootScope, authenticatedUserService, $route, $location) {
    $rootScope.$on('$locationChangeStart', function (event, next) {
        for (var i in $route.routes) {
            if (next.indexOf(i) !== -1) {
                var permissions = $route.routes[i].permissions;

                if (permissions === undefined) continue;

                if (!authenticatedUserService.getUser()) {
                    $location.path('/');
                    return event.preventDefault();
                }

                if (permissions && permissions.indexOf(authenticatedUserService.getUser().role) == -1) {
                    $location.path('/');
                    return event.preventDefault();
                }
            }
        }
    });
}]);

app.run(['$templateCache', '$sce', '$templateRequest', function ($templateCache, $sce, $templateRequest) {
    var addMaterialDialog = $sce.getTrustedResourceUrl('views/addMaterialDialog/addMaterialDialog.html');
    $templateRequest(addMaterialDialog).then(function (template) {
        $templateCache.put('addMaterialDialog.html', template);
    }, function () {
        console.log("Failed to load addMaterialDialog.html template")
    });

    var detailedSearch = $sce.getTrustedResourceUrl('directives/detailedSearch/detailedSearch.html');
    $templateRequest(detailedSearch).then(function (template) {
        $templateCache.put('detailedSearch.html', template);
    }, function () {
        console.log("Failed to load detailedSearch.html template")
    });
}]);

app.run(['$translate', function ($translate) {
    configureTextAngular(provideProvider, $translate);
}]);
