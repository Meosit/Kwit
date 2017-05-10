angular
    .module('app')
    .controller('WalletListController', WalletListController);

function WalletListController($mdDialog, WalletFactory) {
    var self = this;

    self.forecast = null;
    self.wallets = [];
    self.isUpdatingChosen = false;
    self.addWallet = addWallet;
    self.deleteWallet = deleteWallet;
    self.editWallet = editWallet;

    refresh();

    function refresh() {
        WalletFactory.getAll().$promise.then(function (result) {
            result.forEach(function (it) {
                it.balance = new Big(it.balance).toFixed(2);
            });
            self.wallets = result;
        });
        WalletFactory.forecast().$promise.then(function (result) {
            self.forecast = result;
        })
    }

    function addWallet() {
        openEditModal();
    }

    function deleteWallet(wallet) {
        $mdDialog.show({
            controller: 'WalletDeleteModalController as self',
            templateUrl: 'templates/wallets/deleteModal.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true,
            locals: {
                wallet: angular.copy(wallet)
            }
        }).then(refresh, refresh);
    }

    function editWallet(wallet) {
        openEditModal(wallet);
    }

    function openEditModal(wallet) {
        $mdDialog.show({
            controller: 'WalletSaveModalController as self',
            templateUrl: 'templates/wallets/saveModal.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true,
            locals: {
                wallet: angular.copy(wallet)
            }
        }).then(refresh, refresh);
    }

}


