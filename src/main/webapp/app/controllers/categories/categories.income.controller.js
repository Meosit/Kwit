angular
    .module('app')
    .controller('CategoryIncomeListController', CategoryIncomeListController);

function CategoryIncomeListController($controller) {
    var self = this;
    self.type = 'INCOME';
    self.title = "Income Categories";

    $controller('CategoryController', {self: self});

}


