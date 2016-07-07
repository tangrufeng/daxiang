
var App = angular.module('app', ['ui.router', 'ngCookies', 'datePicker', 'angularFileUpload']);

App.run(['$rootScope', '$cookieStore', '$state', function ($rootScope, $cookieStore, $state) {
    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
        var $toState = toState.name;

        // if($toState != 'login') {
        //     return;
        // }

        // if(!$cookieStore.get('user')){
        //     event.preventDefault();
        //     $state.go("login", {'from': $toState, '_toParams': toParams});
        // } 
    })
}
])
.directive('title', ['$rootScope', '$timeout',
    function($rootScope, $timeout) {
        return {
            link: function() {

                var listener = function(event, toState) {

                    var title = 'Default Title';
                    if (toState.data && toState.data.pageTitle) 
                    {
                        title = toState.data.pageTitle;
                    }

                    $timeout(function() {
                        $rootScope.title = title;
                    }, 0, false);
                };

                $rootScope.$on('$stateChangeSuccess', listener);
            }
        };
    }
])
.directive('backButton', ['$rootScope', 
    function ($rootScope) {
        return {
            restrict: 'EA',
            template: '<a ng-if="back" href="#{{backUrl}}" class="back-btn">'+
                      '    <i class="iconfont icon-leftarrow"></i>'+
                      '</a>',
            link: function (scope, element) {
                var listener = function(event, toState) {
                    var backUrl = '';
                    scope.back = false;
                    if (toState.backUrl) 
                    {
                        backUrl = toState.backUrl;
                        scope.back = true; 
                    }
                    $rootScope.backUrl = backUrl;
                };

                $rootScope.$on('$stateChangeSuccess', listener);
            }
        }
    }
])
.directive('mpLeftMenu', ['$rootScope', 
    function ($rootScope) {
        return {
            restrict: 'AE',
            templateUrl: 'components/left/left_menu.html',
            link: function (scope, element) {
                $rootScope.$on('$stateChangeSuccess', function (event, toState) {
                    var nlan = ['orders', 'supplier', 'editsupplier', 'store', 'editstore', 'channel', 'editchannel'];
                    for(i in nlan) {
                        if(toState.name == nlan[i]) {
                            scope.mp_left_menu = true;
                            return;
                        } else {
                            scope.mp_left_menu = false;
                        }
                    }
                });
            }
        }
    }
])
.directive('header', ['$rootScope', function ($rootScope) {
    return {
        restrict: 'AE',
        templateUrl: 'components/header/headerMenu.html',
        link: function (scope, element) {
            scope.headerShow = false;
            $rootScope.$on('$stateChangeSuccess', function (event, toState) {
                if(toState.name == 'splogin' || toState.name == 'login') {
                    scope.headerShow = false;
                    return;
                } else {
                    scope.headerShow = true;
                }
            });
        }
    }
}])
.service('common', ['$rootScope', '$http', '$state',  function ($rootScope, $http, $state) {
    this.get = function (url, data) {
        return $http.get(url, {params: data}, 
            { 
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}, 
                transformRequest: function(obj) {
                    var str = [];
                    for(var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
                }
            })
            .then(function (res) {
                var data = res.data;
                    if(data.errCode) {
                        alert(data.errMsg);
                        if(data.errCode == '-99') {
                            $state.go('login');
                        } else if (data.errCode == '-98') {
                            $state.go('splogin');
                        }
                        return false;
                    }
                    return data;
                }, function (res) {
                    return res;
                })
    };

    this.post = function (url, data) {
        return $http.post(url, data, 
            { 
                headers: {'Content-Type': 'application/json'}
            })
            .then(function(res){
                    var data = res.data;
                    if(data.errCode) {
                        alert(data.errMsg);
                        if(data.errCode == '-99') {
                            $state.go('login');
                        } else if (data.errCode == '-98') {
                            $state.go('splogin');
                        }
                        return false;
                    }
                    return data;
                }, function(res){
                    return res;
                });
    };

    this.setMoney = function (money, num) {
        num = num > 2 && num <= 20 ? num : 2;
        money = parseFloat((money + '').replace(/[^\d\.]/g, '')).toFixed(num)+"";
        var s_left = money.split('.')[0].split('').reverse(), 
            s_right = money.split('.')[1],
            new_money = '';

        for(var i=0, len = s_left.length; i< len; i++) {
            new_money += s_left[i] + ((i + 1) % 3 == 0 && (i + 1) != len? ',': '');
        }
        return new_money.split("").reverse().join('') + '.' + s_right;
    };


}])
.filter('range', function(){
    return function(input, total){
        total = parseInt(total);
        for(var i =1; i <= total; i++) {
            input.push(i);
        }
        return input;
    }
})
.filter('changeType', function(){
    return function(type){
        return type  == 'CNY'? '外币兑换人民币':'人民币兑换外币'
    }
})
.filter('currencyText', function(){
    return function(code){
        var currencyObj = {
                USD: '美元', 
                JPY: '日元',
                AUD: '澳大利亚元',
                EUR: '欧元',
                HKD: '港币',
                AED: '阿联酋迪拉姆',
                DKK: '丹麦克朗',
                CHF: '瑞士法郎',
                CAD: '加拿大元',
                BRL: '巴西里亚尔',
                GBP: '英镑',
                INR: '印度卢比',
                IDR: '印尼卢比',
                ZAR: '南非兰特',
                TWD: '新台币',
                THB: '泰国铢',
                SGD: '新加坡元',
                SEK: '瑞典克朗',
                RUB: '卢布',
                PHP: '菲律宾比索',
                NZD: '新西兰元'
            }
        return currencyObj[code];
    }
})
.filter('status', function(){
    return function(i){
        var status =  ["待审核", "已受理", "拒绝", "取消", "完成"];
        return status[i+1];
    }
})
.config(['$compileProvider', function ($compileProvider) {
    $compileProvider.imgSrcSanitizationWhitelist(/^\s*(https?|local|data):/);
}])
