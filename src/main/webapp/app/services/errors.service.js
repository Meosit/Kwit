angular
    .module('app')
    .service('ErrorsService', ErrorsService);

function ErrorsService(toast) {

    var self = {
        showErrors: showErrors
    };

    return self;

    function showErrors(errors) {
        errors.forEach(function (error) {
            toast.show("<span style='color: lightcoral'>" + error.title + ": " + error.message + "</span>", 10000);
        });
    }
}