angular
    .module('app')
    .controller('WalletSaveModalController', WalletSaveModalController);

function WalletSaveModalController($mdDialog, WalletFactory, CurrencyFactory, wallet) {
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

    function saveWallet() {
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
    }

    function cancel() {
        $mdDialog.cancel();
    }
}