(function(){
	
	var app = angular.module("crud", [ ]);
	
	app.controller('MainController', ['$http', '$scope', function($http, $scope){
		/*Управление вкладками*/
        this.tab = 1;
		this.setTab = function(newTab){
		this.tab = newTab;
		};
		this.isSet = function(arg){
		return this.tab === arg;
		};

        /*Изменение юзера*/
        this.setName = function(user){
            var newname = prompt('Enter new name', user.name);
            var pattern = new RegExp("^\\w{1,25}$");
            if (newname != null){
                if (pattern.test(newname)){
                    user.name = newname;
                    user.isChanged = 1;
                } else {
                    alert('Name should contain only alphanumeric characters, max length 25');
                }
            }
        };

        this.setAge = function(user){
            var newage = prompt('Enter new age', user.age);
            if (newage !=null){
                var pattern = new RegExp("^\\d{1,3}$");
                if (pattern.test(newage)){
                    newage = parseInt(newage, 10);
                    if (newage > 120 || newage < 0){
                        alert('User age should be a number < 120 && > 0');
                    } else {
                        user.age = newage;
                        user.isChanged = 1;
                    }
                } else {
                    alert('User age should be a number < 120 && > 0');
                }
            }
        };

        this.swap = function(user){
            var change = confirm('Set value to '+!user.admin+'?');
            if (change){
                user.admin = !user.admin;
                user.isChanged = 1;
            }
        };

		/*
		/!*Вывод целиком*!/
        $scope.usersarray = [];
		$http({
			method : "GET",
			url : "/test/usersarray.json"
		}).then(function succes(response) {
			$scope.usersarray = response.data;
		});
		*/

        //Добавление юзера (добавленный юзер получает ID и дату на сервере), результат пойдёт в первую строку таблицы
        this.addUser = function () {
            if ($scope.isadmin != true){
                $scope.isadmin = false;
            }
            var newUser = {
                id : null,
                name : $scope.addname,
                age : $scope.addage,
                admin : $scope.isadmin,
                createdDate : 0
            };
            $http({
                method : "POST",
                url : "/test/user/".concat(angular.toJson(newUser)),
                headers: {
                    "Accept": "application/json;charset=utf-8",
                    "Accept-Charset":"charset=utf-8"
                }
            }).then(function succes(response) {
                $scope.usersarray.unshift(response.data);
                $scope.usersarray.pop();
                $scope.usersarray[0].isChanged = 2;
            }, function (response){
                alert("server was murdered");
            });
        };

        /*Управление результирующей таблицей*/
        this.deleteUser = function(uid){
            var orly = confirm("Yuo sure?");
            if (orly){
                $http({
                    method : "DELETE",
                    url : "/test/user/".concat(uid),
                    headers: {
                        "Accept": "application/json;charset=utf-8",
                        "Accept-Charset":"charset=utf-8"
                    }
                }).then(function succes(response){
                        var delindex = $scope.usersarray.map(function (e) {
                            return e.id;
                        }).indexOf(uid);
                        if (delindex !=-1){
                            $scope.usersarray.splice(delindex, 1);
                        }
                }, function (response){
                    alert("server was murdered");
                });
            }
        };

        this.updateUser = function(user){
            user.isChanged = undefined;
            $http({
                method : "PUT",
                url : "/test/user/".concat(angular.toJson(user)),
                headers: {
                    "Accept": "application/json;charset=utf-8",
                    "Accept-Charset":"charset=utf-8"
                }
            }).then(function succes(response){}, function (response){
                user.isChanged = true;
                alert("server was murdered");
            });
        };

        /*Постраничный вывод server-side*/
        $scope.usersPerPage = 15;
        $scope.currentPage = 0;
        $scope.usersarray = [];

        $scope.curfindid = null;
        $scope.curfindname = null;
        $scope.curlike = null;
        $scope.curagestart = null;
        $scope.curageend = null;
        $scope.curdatestart = null;
        $scope.curdateend = null;
        $scope.curadm = null;

        $scope.flag = 1; //"default 1", "id 2", "name 3", "age 4" "date 5" "odmin 6"
        $scope.setFlag = function(newFlag){
            $scope.flag = newFlag;
            if (newFlag === 2) {
                $scope.curfindid = $scope.findid;
            }
            if (newFlag === 3) {
                $scope.curfindname = $scope.findname;
                $scope.curlike = $scope.like ? true : false;
            }
            if (newFlag === 4) {
                $scope.curagestart = $scope.agestart < $scope.ageend ? $scope.agestart : $scope.ageend;
                $scope.curageend = $scope.ageend > $scope.agestart ? $scope.ageend : $scope.agestart;
            }
            if (newFlag === 5) {
                var testst = new Date($scope.datestart).getTime();
                var testend = new Date($scope.dateend).getTime();
                $scope.curdatestart = testst < testend ? testst : testend;
                $scope.curdateend = testend > testst ? testend : testst;
            }
            if (newFlag === 6){
                $scope.curadm = $scope.adm ? true : false;
            }
            //костыль чтобы вотч отреагировал на смену поиска
            $scope.currentPage = -10;
        };

        //Панелька перехода по страницам почти-как-у-гугла
        $scope.range = function() {
            var rangeSize = 11; //максимум стр на панельке
            if (rangeSize > $scope.pageCount()) {
                rangeSize = $scope.pageCount()
            }
            var ret = [];
            var start;

            start = $scope.currentPage;
            if ( start > $scope.pageCount()-rangeSize ) {
                start = $scope.pageCount()-rangeSize + 5; //выровнить в конце
            }
            // -5 чтобы текущая страница была в центре панельки
            for (var i=start - 5; i < start+rangeSize; i++) {
                if (i >= 0 && ret.length <= 10) {
                    if (i < $scope.pageCount()) {
                        ret.push(i);
                    }
                }
            }
            return ret;
        };

        this.prevPage = function() {
            if ($scope.currentPage > 0) {
                $scope.currentPage--;
            }
        };

        this.setPage  = function(n) {
            if (n >= 0 && n < $scope.pageCount()) {
                $scope.currentPage = n;
            }
        };

        this.prevPageDisabled = function() {
            return $scope.currentPage === 0 ? "disabled" : "";
        };

        this.nextPage = function() {
            if ($scope.currentPage < $scope.pageCount() - 1) {
                $scope.currentPage++;
            }
        };

        this.nextPageDisabled = function() {
            return $scope.currentPage === $scope.pageCount() - 1 ? "disabled" : "";
        };

        $scope.pageCount = function() {
            return $scope.total < 1 ? 1 : Math.ceil($scope.total/$scope.usersPerPage);
        };

        /*=================================================================*/
        $scope.$watch("currentPage", function(newValue, oldValue) {
            //refresh then searching by user property
            if ($scope.currentPage < 0) {
                console.log("search switched");
                $scope.currentPage = 0;
            } else {
                if ($scope.flag === 1) {
                $http({
                    method: "GET",
                    url: "/test/users/".concat(newValue * $scope.usersPerPage).concat("&")
                        .concat($scope.usersPerPage),
                    headers: {
                        "Accept": "application/json;charset=utf-8",
                        "Accept-Charset":"charset=utf-8"
                    }
                }).then(function succes(response) {
                    $scope.usersarray = response.data;
                }, function (response){
                    alert("server was murdered");
                });
                $http({
                    method: "GET",
                    url: "/test/userscount",
                    headers: {
                        "Accept": "application/json;charset=utf-8",
                        "Accept-Charset":"charset=utf-8"
                    }
                }).then(function succes(response) {
                    $scope.total = response.data;
                });
                }
                if ($scope.flag === 2) {
                    $http({
                        method: "GET",
                        url: "/test/findbyid/".concat($scope.curfindid),
                        headers: {
                            "Accept": "application/json;charset=utf-8",
                            "Accept-Charset":"charset=utf-8"
                        }
                    }).then(function succes(response) {
                        $scope.usersarray = response.data;
                    }, function (response){
                        alert("server was murdered");
                    });
                    $scope.total = 1;
                }
                if ($scope.flag === 3) {
                    $http({
                        method: "GET",
                        url: "/test/findbyname/"
                            .concat($scope.curfindname).concat("&")
                            .concat(newValue * $scope.usersPerPage).concat("&")
                            .concat($scope.usersPerPage).concat("&")
                            .concat($scope.curlike),
                        headers: {
                            "Accept": "application/json;charset=utf-8",
                            "Accept-Charset":"charset=utf-8"
                        }
                    }).then(function succes(response) {
                        $scope.usersarray = response.data;
                    }, function (response){
                        alert("server was murdered");
                    });
                    $http({
                        method: "GET",
                        url: "/test/countbyname/".concat($scope.curfindname).concat("&")
                            .concat($scope.curlike),
                        headers: {
                            "Accept": "application/json;charset=utf-8",
                            "Accept-Charset":"charset=utf-8"
                        }
                    }).then(function succes(response) {
                        $scope.total = response.data;
                    });
                }
                if ($scope.flag === 4) {
                    $http({
                        method: "GET",
                        url: "/test/findbyage/".concat($scope.curagestart).concat("&")
                            .concat($scope.curageend).concat("&")
                            .concat(newValue * $scope.usersPerPage).concat("&")
                            .concat($scope.usersPerPage),
                        headers: {
                            "Accept": "application/json;charset=utf-8",
                            "Accept-Charset":"charset=utf-8"
                        }
                    }).then(function succes(response) {
                        $scope.usersarray = response.data;
                    }, function (response){
                        alert("server was murdered");
                    });
                    $http({
                        method: "GET",
                        url: "/test/countbyage/".concat($scope.curagestart).concat("&")
                            .concat($scope.curageend),
                        headers: {
                            "Accept": "application/json;charset=utf-8",
                            "Accept-Charset":"charset=utf-8"
                        }
                    }).then(function succes(response) {
                        $scope.total = response.data;
                    });
                }
                if ($scope.flag === 5) {
                    $http({
                        method: "GET",
                        url: "/test/findbydate/"
                            .concat($scope.curdatestart).concat("&")
                            .concat($scope.curdateend).concat("&")
                            .concat(newValue * $scope.usersPerPage).concat("&")
                            .concat($scope.usersPerPage),
                        headers: {
                            "Accept": "application/json;charset=utf-8",
                            "Accept-Charset":"charset=utf-8"
                        }
                    }).then(function succes(response) {
                        $scope.usersarray = response.data;
                    }, function (response){
                        alert("server was murdered");
                    });
                    $http({
                        method: "GET",
                        url: "/test/countbydate/"
                            .concat(angular.toJson($scope.curdatestart)).concat("&")
                            .concat(angular.toJson($scope.curdateend)),
                        headers: {
                            "Accept": "application/json;charset=utf-8",
                            "Accept-Charset":"charset=utf-8"
                        }
                    }).then(function succes(response) {
                        $scope.total = response.data;
                    });
                }
                if ($scope.flag === 6) {
                    $http({
                        method: "GET",
                        url: "/test/findadmins/"
                            .concat($scope.curadm).concat("&")
                            .concat(newValue * $scope.usersPerPage).concat("&")
                            .concat($scope.usersPerPage),
                        headers: {
                            "Accept": "application/json;charset=utf-8",
                            "Accept-Charset":"charset=utf-8"
                        }
                    }).then(function succes(response) {
                        $scope.usersarray = response.data;
                    }, function (response){
                        alert("server was murdered");
                    });
                    $http({
                        method: "GET",
                        url: "/test/countadmins/".concat($scope.curadm),
                        headers: {
                            "Accept": "application/json;charset=utf-8",
                            "Accept-Charset":"charset=utf-8"
                        }
                    }).then(function succes(response) {
                        $scope.total = response.data;
                    });
                }
        }
        });
        /*==================================================================*/
	}]);
})();