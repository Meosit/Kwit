angular
    .module('app')
    .controller('ProfileController', ProfileController);

function ProfileController(UserFactory, CurrencyFactory, toast) {
    var self = this;

    self.setSalaryInfo = setSalaryInfo;
    self.updatePassword = updatePassword;
    self.salaryInfo = {};
    self.oldPassword = '';
    self.password = '';
    self.passwordConfirmation = '';
    self.currencyCodes = [];

    refresh();

    function refresh() {
        UserFactory.getSalaryInfo().$promise.then(function (response) {
            self.salaryInfo.salaryDay = response.salaryDay;
            self.salaryInfo.salaryCurrencyCode = response.salaryCurrencyCode;
        });
        CurrencyFactory.getAll().$promise.then(function (response) {
            self.currencyCodes = response.map(function (it) {
                return it.code + ''
            });
        });
    }

    function setSalaryInfo() {
        UserFactory.setSalaryInfo(self.salaryInfo, function (response) {
            if (typeof response.status === 'undefined' || response.status === null)
                toast.show("<span style='color: lightgreen'>Salary info updated!</span>");
            refresh();
        });
    }

    function updatePassword() {
        UserFactory.updatePassword({
            oldPassword: self.oldPassword,
            password: self.password,
            passwordConfirmation: self.passwordConfirmation
        }, function (response) {
            self.oldPassword = '';
            self.password = '';
            self.passwordConfirmation = '';
            if (typeof response.status === 'undefined' || response.status === null)
                toast.show("<span style='color: lightgreen'>Password changed.</span>");
        });
    }
}