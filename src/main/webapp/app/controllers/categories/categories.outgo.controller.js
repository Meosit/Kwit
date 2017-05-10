angular
    .module('app')
    .controller('CategoryOutgoListController', CategoryOutgoListController);

function CategoryOutgoListController($controller) {
    var self = this;
    self.type = 'OUTGO';
    self.title = "Outgo Categories";

    $controller('CategoryController', {self: self});

}


