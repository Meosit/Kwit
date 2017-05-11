angular
    .module('app')
    .controller('TransactionSaveModalController', TransactionSaveModalController);

function TransactionSaveModalController($mdDialog, WalletFactory, CategoryFactory, TransactionFactory, toast, transaction) {
    var self = this;

    self.updateMode = !!self.transaction;
    self.transaction = transaction;
    if (!self.updateMode) {
        self.transaction = {date: new Date()};
    }
    self.saveTransaction = saveTransaction;
    self.cancel = cancel;
    self.MIN_DATE_STRING = "1000-01-01";
    self.MAX_DATE_STRING = "9999-12-31";
    self.MIN_DATE = new Date(self.MIN_DATE_STRING);
    self.MAX_DATE = new Date(self.MAX_DATE_STRING);
    self.wallets = [];
    self.categories = [];
    self.type = 'OUTGO';
    self.types = ['INCOME', 'OUTGO'];

    refresh();

    function refresh() {
        WalletFactory.getAll().$promise.then(function (response) {
            self.wallets = response;
        });
        CategoryFactory.getAll().$promise.then(function (response) {
            self.categories = response
        })
    }

    function isNumeric(value) {
        return /^\d+(\.\d+)?$/.test(value);
    }

    function formatDate(date) {
        var year = date.getFullYear();
        var month = (1 + date.getMonth()).toString();
        month = month.length > 1 ? month : '0' + month;
        var day = date.getDate().toString();
        day = day.length > 1 ? day : '0' + day;
        return year + '-' + month + '-' + day;
    }

    function saveTransaction() {
        if (self.transaction.sum !== null && isNumeric(self.transaction.sum)) {
            self.transaction.sum = new Big(self.transaction.sum).toFixed(4);
            self.transaction.date = formatDate(self.transaction.date);
            if (self.updateMode) {
                TransactionFactory.update(self.transaction, function (response) {
                    if (typeof response.status === 'undefined' || response.status === null) {
                        $mdDialog.hide();
                        toast.show("<span style='color: lightgreen'>Transaction updated!</span>");
                    }
                });
            } else {
                TransactionFactory.save(self.transaction, function (response) {
                    if (typeof response.status === 'undefined' || response.status === null) {
                        $mdDialog.hide();
                        toast.show("<span style='color: lightgreen'>Transaction added!</span>");
                    }
                });
            }
        } else {
            toast.show("<span style='color: lightcoral'>Invalid sum value</span>")
        }
    }

    function saveWallet() {
        if (self.transaction.balance !== null && isNumeric(self.transaction.balance)) {
            self.transaction.balance = new Big(self.transaction.balance).toFixed(4);
            if (self.updateMode) {
                WalletFactory.update(self.transaction, function (response) {
                    if (typeof response.status === 'undefined' || response.status === null) {
                        $mdDialog.hide();
                        toast.show("<span style='color: lightgreen'>Wallet updated!</span>");
                    }
                });
            } else {
                WalletFactory.save(self.transaction, function (response) {
                    if (typeof response.status === 'undefined' || response.status === null) {
                        $mdDialog.hide();
                        toast.show("<span style='color: lightgreen'>Wallet added!</span>");
                    }
                });
            }
        } else {
            toast.show("<span style='color: lightcoral'>Invalid balance value</span>")
        }
    }

    function cancel() {
        $mdDialog.cancel();
    }
}