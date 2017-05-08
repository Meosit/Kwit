angular
    .module('app')
    .controller('SignUpController', SignUpController);

function SignUpController(AuthService) {
    var self = this;
    self.email = null;
    self.password = null;
    self.passwordConfirmation = null;
    self.signUp = signUp;

    function signUp() {
        AuthService.signUp({
            email: self.email,
            password: self.password,
            passwordConfirmation: self.passwordConfirmation
        });
    }
}