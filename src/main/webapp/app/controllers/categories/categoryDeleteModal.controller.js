angular
    .module('app')
    .controller('CategoryDeleteModalController', CategoryDeleteModalController);

function CategoryDeleteModalController($mdDialog, toast, CategoryFactory, category) {
    var self = this;

    self.category = category;
    self.newCategories = [];
    self.isSafeDelete = false;
    self.newCategoryId = null;
    self.cancel = cancel;
    self.deleteCategory = deleteCategory;

    refresh();

    function refresh() {
        CategoryFactory.getByType({
            categoryType: self.category.type
        }).$promise.then(function (result) {
            result = result.filter(function (it) {
                return (it.id !== self.category.id)
            });
            self.newCategories = result;
        })
    }

    function deleteCategory() {
        if (self.isSafeDelete) {
            if (self.newCategoryId !== null) {
                CategoryFactory.softDelete({
                    id: self.category.id,
                    newCategory: self.newCategoryId
                }).$promise.then(function (response) {
                    if (typeof response.status === 'undefined' || response.status === null) {
                        $mdDialog.hide();
                        toast.show("<span style='color: lightgreen'>Category deleted.</span>");
                    }
                })
            } else {
                toast.show("<span style='color: lightcoral'>New Category is not selected.</span>");
            }
        } else {
            CategoryFactory.delete({id: self.category.id}).$promise.then(function (response) {
                if (typeof response.status === 'undefined' || response.status === null) {
                    $mdDialog.hide();
                    toast.show("<span style='color: lightgreen'>Category deleted.</span>");
                }
            })
        }
    }

    function cancel() {
        $mdDialog.cancel();
    }
}