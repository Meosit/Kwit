angular
    .module('app')
    .controller('TransactionListController', TransactionListController);

function TransactionListController(TransactionFactory, $mdDialog, toast) {
    var self = this;

    self.transactions = [];
    self.pagination = {page: 1, limit: 40, total: 200};
    self.addTransaction = addTransaction;
    self.editTransaction = editTransaction;
    self.deleteTransaction = deleteTransaction;
    self.refresh = refresh;

    refresh(self.pagination.page);

    function refresh(page) {
        if (!page) {
            page = self.pagination.page;
        }
        TransactionFactory.get({page: page}).$promise.then(function (result) {
            self.transactions = result.content;
            self.pagination.page = result.number + 1;
            self.pagination.limit = result.size;
            self.pagination.total = result.totalElements;
        })
    }

    function addTransaction() {
        openSaveModal();
    }

    function editTransaction(transaction) {
        openSaveModal(transaction)
    }

    function deleteTransaction(transaction) {
        var confirm = $mdDialog.confirm()
            .title('Would you like to delete this transaction?')
            .ok('Yes')
            .cancel('No');

        $mdDialog.show(confirm).then(function () {
            TransactionFactory.delete({id: transaction.id}).$promise.then(function (response) {
                if (typeof response.status === 'undefined' || response.status === null) {
                    toast.show("<span style='color: lightgreen'>Transaction deleted!</span>");
                    refresh(self.pagination.page)
                }
            });
        }, function () {
        });
    }

    function openSaveModal(transaction) {
        $mdDialog.show({
            controller: 'TransactionSaveModalController as self',
            templateUrl: 'templates/transactions/saveModal.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true,
            locals: {
                transaction: angular.copy(transaction)
            }
        }).then(self.refresh, self.refresh);
    }

}