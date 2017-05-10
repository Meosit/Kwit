angular
    .module('app')
    .controller('CategoryController', CategoryController);

function CategoryController(CategoryFactory, UserFactory, CurrencyFactory, $mdDialog, self) {
    self.categories = [];
    self.currencyCodes = [];
    self.statsCurrencyCode = 'USD';
    self.MIN_DATE_STRING = "1000-01-01";
    self.MAX_DATE_STRING = "9999-12-31";
    self.MIN_DATE = new Date(self.MIN_DATE_STRING);
    self.MAX_DATE = new Date(self.MAX_DATE_STRING);
    self.isAllTime = true;
    self.period = {start: new Date(), end: new Date()};
    self.isAllTimePeriod = isAllTimePeriod;
    self.isAfterPeriodStart = isAfterPeriodStart;
    self.normalizePeriod = normalizePeriod;
    self.addCategory = addCategory;
    self.editCategory = editCategory;
    self.deleteCategory = deleteCategory;
    self.refresh = refresh;
    refreshFirst();
    refresh();

    function refreshFirst() {
        UserFactory.getSalaryInfo().$promise.then(function (response) {
            self.statsCurrencyCode = (!response.salaryCurrencyCode)
                ? "BYN"
                : response.salaryCurrencyCode
        });

        CurrencyFactory.getAll().$promise.then(function (response) {
            self.currencyCodes = response.map(function (it) {
                return it.code + ''
            });
        });
    }

    function refresh() {
        if (self.isAllTime) {
            CategoryFactory.getStatsAllTime({
                categoryType: self.type,
                currencyCode: self.statsCurrencyCode
            }).$promise.then(function (result) {
                self.categories = mapCategories(result.categories);
                self.isAllTime = true;
            })
        } else {
            self.period = self.normalizePeriod(self.period);
            CategoryFactory.getStatsForPeriod({
                categoryType: self.type,
                currencyCode: self.statsCurrencyCode,
                fromDate: formatDate(self.period.start),
                toDate: formatDate(self.period.end)
            }).$promise.then(function (result) {
                self.categories = mapCategories(result.categories);
                self.isAllTime = false;
            });
        }
    }

    function mapCategories(categories) {
        return categories.map(function (it) {
            return {
                id: it.categoryId,
                type: self.type,
                name: it.categoryName,
                sum: new Big(it.sumForCategory).toFixed(2),
                items: it.countForCategory,
                percent: new Big(it.percentOfAll).toFixed(2)
            }
        })
    }

    function isAfterPeriodStart(date) {
        return date >= self.period.start
    }

    function isAllTimePeriod(period) {
        return (period.start <= self.MIN_DATE)
            && (period.end >= self.MAX_DATE);
    }

    function normalizePeriod(period) {
        if (period.start <= self.MIN_DATE) {
            period.start = self.MIN_DATE;
        }
        if (period.end >= self.MAX_DATE) {
            period.end = self.MAX_DATE;
        }
        return period;
    }

    function formatDate(date) {
        var year = date.getFullYear();
        var month = (1 + date.getMonth()).toString();
        month = month.length > 1 ? month : '0' + month;
        var day = date.getDate().toString();
        day = day.length > 1 ? day : '0' + day;
        return year + '-' + month + '-' + day;
    }

    function addCategory() {
        $mdDialog.show({
            controller: 'CategoryAddModalController as self',
            templateUrl: 'templates/categories/addModal.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true
        }).then(refresh, refresh);
    }

    function editCategory(category) {
        $mdDialog.show({
            controller: 'CategoryEditModalController as self',
            templateUrl: 'templates/categories/editModal.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true,
            locals: {
                category: angular.copy(category)
            }
        }).then(refresh, refresh);
    }

    function deleteCategory(category) {
        $mdDialog.show({
            controller: 'CategoryDeleteModalController as self',
            templateUrl: 'templates/categories/deleteModal.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true,
            locals: {
                category: angular.copy(category)
            }
        }).then(refresh, refresh);
    }

}


