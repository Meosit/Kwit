angular
    .module('app')
    .controller('RootController', RootController);

function RootController(AuthService, $state) {
    AuthService.prepareAuthInfo().then(function () {
        if (AuthService.isAuthorized) {
            $state.go('transactions');
        } else {
            $state.go('signIn');
        }
    });
}
