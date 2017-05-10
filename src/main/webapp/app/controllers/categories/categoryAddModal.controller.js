angular
    .module('app')
    .controller('CategoryAddModalController', CategoryAddModalController);

function CategoryAddModalController($mdDialog, CategoryFactory, toast) {
    var self = this;

    self.category = {name: null, type: null};
    self.types = ['INCOME', 'OUTGO'];
    self.cancel = cancel;
    self.addCategory = addCategory;

    function addCategory() {
        CategoryFactory.save(self.category, function (response) {
            if (typeof response.status === 'undefined' || response.status === null) {
                $mdDialog.hide();
                toast.show("<span style='color: lightgreen'>Category created!</span>");
            }
        });
    }

    function cancel() {
        $mdDialog.cancel();
    }
}