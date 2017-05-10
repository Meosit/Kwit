angular
    .module('app')
    .controller('CategoryEditModalController', CategoryEditModalController);

function CategoryEditModalController($mdDialog, CategoryFactory, category, toast) {
    var self = this;

    self.category = null;
    self.cancel = cancel;
    self.editCategory = editCategory;

    refresh();

    function refresh() {
        CategoryFactory.get({
            id: category.id
        }).$promise.then(function (response) {
            self.category = response;
        });
    }

    function editCategory() {
        CategoryFactory.update(self.category, function (response) {
            if (typeof response.status === 'undefined' || response.status === null) {
                $mdDialog.hide();
                toast.show("<span style='color: lightgreen'>Category updated!</span>");
            }
        });
    }

    function cancel() {
        $mdDialog.cancel();
    }
}