angular
    .module('app')
    .factory('CategoryFactory', CategoryFactory);

function CategoryFactory($resource) {
    return $resource('api/categories/:id', {id: '@id'}, {
        update: {method: 'PUT'},
        delete: {method: 'DELETE'},
        softDelete: {
            method: 'DELETE',
            url: 'api/categories/:id?newCategory=:newCategory',
            params: {
                newCategory: '@newCategory'
            }
        },
        getAll: {
            method: 'GET',
            url: 'api/categories/all',
            isArray: true
        },
        getStatsAllTime: {
            method: 'GET',
            url: 'api/categories/stats/:categoryType/:currencyCode',
            params: {
                categoryType: '@categoryType',
                currencyCode: '@currencyCode'
            }
        },
        getByType: {
            method: 'GET',
            url: 'api/categories/type/:categoryType',
            params: {
                categoryType: '@categoryType'
            },
            isArray: true
        },
        getStatsForPeriod: {
            method: 'GET',
            url: 'api/categories/stats/:categoryType/:currencyCode?from=:fromDate&to=:toDate',
            params: {
                categoryType: '@categoryType',
                currencyCode: '@currencyCode',
                fromDate: '@fromDate',
                toDate: '@toDate'
            }
        }
    });
}
