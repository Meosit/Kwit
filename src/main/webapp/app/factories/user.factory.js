angular
    .module('app')
    .factory('UserFactory', UserFactory);

function UserFactory($resource) {
    return $resource('api/users/:id', {id: '@id'}, {
        getSalaryInfo: {
            method: 'GET',
            url: 'api/users/salary-info'
        },
        setSalaryInfo: {
            method: 'POST',
            url: 'api/users/salary-info'
        },
        updatePassword: {
            method: 'POST',
            url: 'api/users/change-password'
        }
    });
}
