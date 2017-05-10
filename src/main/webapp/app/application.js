angular
    .module('app', [
        'ui.router',
        'ui.router.redirect',
        'ngResource',
        'ngSanitize',
        'ngCookies',
        'ngAnimate',
        'ngMaterial',
        'angular-loading-bar',
        'md.data.table',
        'angular-toast'
    ]).config(function ($mdThemingProvider) {

    $mdThemingProvider.theme('default')
        .primaryPalette('blue-grey')
        .accentPalette('orange');
});