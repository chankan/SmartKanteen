angular.module('canteen', [ 'ngRoute', 'ngResource' ]).factory('Menus',
		[ '$resource', function($resource) {
			// var MenuRestful = $resource('rest/service');
			var MenuService = {
				restService : $resource('rest/kanteen/menu'),
				data : [],
				data1 : [
				{
					ItemID : 1,
					ItemName : "Thali",
					Description : "Jain Thali",
					Price : 100,
					PrepTime : 15
				}, {
					ItemID : 2,
					ItemName : "Biryani",
					Description : "Kashmiri Biryani",
					Price : 50,
					PrepTime : 30
				}, {
					ItemID : 3,
					ItemName : "Curd Rice",
					Description : "Special Curd Rice",
					Price : 35,
					PrepTime : 20
				}, {
					ItemID : 4,
					ItemName : "Desertz",
					Description : "Gulam Jamoon",
					Price : 20,
					PrepTime : 10
				}, {
					ItemID : 5,
					ItemName : "Roti",
					Description : "Thanduri Roti",
					Price : 10,
					PrepTime : 20
				}, {
					ItemID : 6,
					ItemName : "Roti 1",
					Description : "Thanduri Roti",
					Price : 10,
					PrepTime : 20
				} ],
				menuIndex : 6,

				getAll : function() {
					// $http.get('/rest/service/')
					// .success(function(data, status, headers, config) {
					// this.data=[{ItemID: 1, ItemName: "Thali", Description:
					// "Jain Thali",Price: 100,PrepTime: 15},];
					// }).
					// error(function(data, status, headers, config) {
					// this.data=this.data1;
					// });
					// return $http.get('rest/service/');
					return this.restService.get();
				},
				get : function(id) {
					var menu = null;
					for ( var i = 0; i < this.data.length; i++) {
						if ((this.data[i]).ItemID == id) {
							menu = this.data[i];
						}
					}
					return menu;
				},
				remove : function(id) {
					if (id >= 0) {
						for ( var i = 0; i < this.data.length; i++) {
							if ((this.data[i]).ItemID == id) {
								this.data.splice(i, 1);
							}
						}

					}
				},
				save : function(id, menu) {
					if (id >= 0) {
						this.data[id] = menu;
					} else {
						menu.ItemID = this.menuIndex++;
						this.data.push(menu);
					}
					return true;
				}
			};
			return MenuService;
		} ]).config(function($routeProvider) {
	$routeProvider.when('/', {
		controller : 'ListCtrl',
		templateUrl : 'view/menulist.html'
	}).when('/dailymenu', {
		controller : 'DailyMenuCtrl',
		templateUrl : 'view/todaysmenulist.html'
	}).when('/new', {
		controller : 'CreateCtrl',
		templateUrl : 'detail.html'
	}).otherwise({
		redirectTo : '/'
	});
}).controller('MenuCtrl', function($scope, Menus) {
	$scope.mainMenu = [ {
		name : "Home",
		url : "#/"
	}, {
		name : "Today's Menu",
		url : "#/dailymenu"
	}, {
		name : "My Order",
		url : "#/new"
	}, ];

	$scope.loginMenu = [ {
		name : "UserName",
		url : "#/"
	}, {
		name : "Logout",
		url : "#/logout"
	}, ];
	// $scope.remove = function(id) {
	// var result=Menus.remove(id);
	// if(result){
	// $location.path('/');
	// }
	// };
	// alert($scope.menus);
}).controller('ListCtrl', function($scope, Menus, $resource, $http) {
	$scope.menudata = $resource('rest/kanteen/menu').get();
	// $scope.menus =[{itemID: 1, itemName: "Thali", description: "Jain
	// Thali",price: 100,prepTime: 15},];

	// Menus.getAll().success(function(data, status, headers, config) {
	// $scope.menus =data.menu;
	// }).
	// error(function(data, status, headers, config) {
	// $scope.menus =[{ItemID: 1, ItemName: "Thali", Description: "Jain
	// Thali",Price: 100,PrepTime: 15},];
	// });
	// var Menu = $resource('/rest/service/');
	// $scope.menus =Menu.get();
	// $http.get('/rest/service/')
	// .success(function(data, status, headers, config) {
	// $scope.menus=[{ItemID: 1, ItemName: "Thali", Description: "Jain
	// Thali",Price: 100,PrepTime: 15},];
	// }).
	// error(function(data, status, headers, config) {
	// $scope.menus=[{ItemID: 2, ItemName: "Thali", Description: "Jain
	// Thali",Price: 100,PrepTime: 15},];
	// });
	$scope.remove = function(id) {
		var result = Menus.remove(id);
		if (result) {
			$location.path('/');
		}
	};
	// alert($scope.menus);
})

.controller(
		'DailyMenuCtrl',
		function($scope, Menus, $resource, $http) {
			$scope.menudata = $resource(
					'rest/kanteen/menu/caterer/1/date/2014-12-10').get();
			// $scope.menus =[{itemID: 1, itemName: "Thali", description: "Jain
			// Thali",price: 100,prepTime: 15},];

			// Menus.getAll().success(function(data, status, headers, config) {
			// $scope.menus =data.menu;
			// }).
			// error(function(data, status, headers, config) {
			// $scope.menus =[{ItemID: 1, ItemName: "Thali", Description: "Jain
			// Thali",Price: 100,PrepTime: 15},];
			// });
			// var Menu = $resource('/rest/service/');
			// $scope.menus =Menu.get();
			// $http.get('/rest/service/')
			// .success(function(data, status, headers, config) {
			// $scope.menus=[{ItemID: 1, ItemName: "Thali", Description: "Jain
			// Thali",Price: 100,PrepTime: 15},];
			// }).
			// error(function(data, status, headers, config) {
			// $scope.menus=[{ItemID: 2, ItemName: "Thali", Description: "Jain
			// Thali",Price: 100,PrepTime: 15},];
			// });
			$scope.remove = function(id) {
				var result = Menus.remove(id);
				if (result) {
					$location.path('/');
				}
			};
			// alert($scope.menus);
		})

.controller('CreateCtrl', function($scope, $location, Menus) {
	$scope.menu = {
		ItemID : -1,
		ItemName : "",
		Description : "",
		Price : 0,
		PrepTime : 0
	};
	$scope.save = function() {
		var result = Menus.save(-1, $scope.menu);
		if (result) {
			$location.path('/');
		}
	};
}).controller('EditCtrl', function($scope, $location, $routeParams, Menus) {
	var cmenu = Menus.get($routeParams.menuId);
	$scope.errormessage = "";
	if (cmenu != null) {
		$scope.menu = cmenu;
	} else {
		$scope.error = true;
		$scope.errormessage = "Menu detail not present.";
	}
	// /*menuId, menuIndex;

	// menus = Menus;
	// $indexFor(menuId);
	// menus[menuIndex];

	// destroy = function() {
	// then(function(data) {
	// path('/');
	// });
	// };

	$scope.save = function() {
		var result = Menus.save($routeParams.menuId, $scope.menu);
		if (result) {
			$location.path('/');
		}
	};
	$scope.remove = function() {
		var result = Menus.remove($scope.menu.ItemID);
		if (result) {
			$location.path('/');
		}
	};
});
