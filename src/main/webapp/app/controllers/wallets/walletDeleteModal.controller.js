angular
    .module('app')
    .controller('WalletDeleteModalController', WalletDeleteModalController);

function WalletDeleteModalController($mdDialog, toast, WalletFactory, wallet) {
    var self = this;

    self.wallet = wallet;
    self.newWallets = [];
    self.isSafeDelete = false;
    self.newWalletId = null;
    self.currencies = [];
    self.cancel = cancel;
    self.deleteWallet = deleteWallet;

    refresh();

    function refresh() {
        WalletFactory.getAll().$promise.then(function (result) {
            result = result.filter(function (it) {
                return (it.currency.id === self.wallet.currency.id) && (it.id !== self.wallet.id)
            });
            self.newWallets = result;
        })
    }

    function deleteWallet() {
        if (self.isSafeDelete) {
            if (self.newWalletId !== null) {
                WalletFactory.softDelete({
                    id: self.wallet.id,
                    newWallet: self.newWalletId
                }).$promise.then(function (response) {
                    if (typeof response.status === 'undefined' || response.status === null) {
                        $mdDialog.hide();
                        toast.show("<span style='color: lightgreen'>Wallet deleted.</span>");
                    }
                })
            } else {
                toast.show("<span style='color: lightcoral'>New Wallet is not selected.</span>");
            }
        } else {
            WalletFactory.delete({id: self.wallet.id}).$promise.then(function (response) {
                if (typeof response.status === 'undefined' || response.status === null) {
                    $mdDialog.hide();
                    toast.show("<span style='color: lightgreen'>Wallet deleted.</span>");
                }
            })
        }
    }

    function cancel() {
        $mdDialog.cancel();
    }
}