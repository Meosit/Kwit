angular
    .module('app')
    .controller('SignUpController', SignUpController);

function SignUpController(AuthService, CurrencyFactory) {
    var self = this;
    self.email = null;
    self.password = null;
    self.passwordConfirmation = null;
    self.salaryDay = null;
    self.salaryCurrencyCode = null;
    self.currencyCodes = [];
    self.signUp = signUp;

    refresh();

    function refresh() {
        CurrencyFactory.getAll().$promise.then(function (response) {
            self.currencyCodes = response.map(function (it) {
                return it.code + ''
            });
        });
    }

    function signUp() {
        AuthService.signUp({
            email: self.email,
            password: self.password,
            passwordConfirmation: self.passwordConfirmation,
            salaryDay: self.salaryDay,
            salaryCurrencyCode: self.salaryCurrencyCode
        });
    }
}