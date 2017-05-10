angular
    .module('app')
    .controller('WalletSaveModalController', WalletSaveModalController);

function WalletSaveModalController($mdDialog, WalletFactory, CurrencyFactory, wallet, toast) {
    var self = this;

    self.wallet = wallet;
    self.saveWallet = saveWallet;
    self.cancel = cancel;
    self.updateMode = !!self.wallet;
    self.currencies = [];
    self.types = ['NORMAL', 'SAVING'];

    refresh();

    function refresh() {
        CurrencyFactory.getAll().$promise.then(function (response) {
            self.currencies = response;
        });
    }

    function isNumeric(value) {
        return /^\d+(\.\d+)?$/.test(value);
    }

    function saveWallet() {
        if (self.wallet.balance !== null && isNumeric(self.wallet.balance)) {
            self.wallet.balance = new Big(self.wallet.balance).toFixed(4);
            if (self.updateMode) {
                WalletFactory.update(self.wallet, function (response) {
                    if (typeof response.status === 'undefined' || response.status === null) {
                        $mdDialog.hide();
                        toast.show("<span style='color: lightgreen'>Wallet updated!</span>");
                    }
                });
            } else {
                WalletFactory.save(self.wallet, function (response) {
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