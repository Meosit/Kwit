angular
    .module('app')
    .directive('noComplete', function ($compile) {
        return {
            restrict: 'A',
            compile: function (el) {
                el.removeAttr('no-complete');
                el.attr('readonly', true);
                el.attr('onfocus', "this.removeAttribute('readonly')")
            }
        }
    });