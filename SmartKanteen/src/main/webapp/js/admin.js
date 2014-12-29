angular.module('canteen', [ 'ngRoute', 'ngResource' ]).factory('Menus',
		[ '$resource', function($resource) {
			// var MenuRestful = $resource('rest/service');
			var MenuService = {
				restService : $resource('rest/kanteen/menu'),
				data : [],
				data1 : [ ],
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
		controller : 'HomeCtrl',
		templateUrl : 'view/homepage.html'
	}).when('/todaymenus', {
		controller : 'CatererListCtrl',
		templateUrl : 'view/catererlist.html'
	}).when('/caterer/:catererId', {
		controller : 'CatererMenuCtrl',
		templateUrl : 'view/caterermenulist.html'
	}).when('/menulist', {
		controller : 'ListCtrl',
		templateUrl : 'view/menulist.html'
	}).when('/dailymenu', {
		controller : 'DailyMenuCtrl',
		templateUrl : 'view/todaysmenulist.html'//UserMenu End
	}).when('/user', {
		controller : 'userCtrl',
		templateUrl : 'view/adduser.html'//UserMenu End
	}).when('/admin/menu', {//Admin Menu
		controller : 'AddMenuCtrl',
		templateUrl : 'view/catererMenuUpdate.html'
	}).when('/superadmin/caterer', {//SuperAdmin Menu
		controller : 'AddCatererCtrl',
		templateUrl : 'view/addCaterer.html'
	}).otherwise({
		redirectTo : '/'
	});
}).controller('MenuCtrl', function($scope, Menus) {
	
	
	var userMenu = [ {
		name : "Home",
		url : "#/"
	}, {
		name : "Today's Menu",
		url : "#/todaymenus"
	}, {
		name : "My Order",
		url : "#/new"
	}, ];

	$scope.loginMenu = [ {
		name : "Register Now!!",
		url : "#/user"
	}, {
		name : "Logout",
		url : "#/logout"
	}, ];
	
	var adminMenu = [ 
	                 {name : "Home", url : "#/"},
	                 {name : "Master Menu",url : "#/admin/menu"},
	                 {name : "Today's Menu",url : "#/todaymenus"},
	                 {name : "Order", url : "#/new"	},
	                 ];
	
	$scope.loginMenu = [ {
		name : "Register Now!!",
		url : "#/user"
	}, {
		name : "Logout",
		url : "#/logout"
	}, ];
	
	var superAdminMenu = [ 
	                 {name : "Home", url : "#/"},
	                 {name : "Caterers", url : "#/superadmin/caterer"},
	                 {name : "Master Menu",url : "#/admin/menu"}	                 ];
	
	$scope.loginMenu = [ {
		name : "Register Now!!",
		url : "#/user"
	}, {
		name : "Logout",
		url : "#/logout"
	}, ];
	
//$scope.mainMenu=superAdminMenu;
	$scope.mainMenu=adminMenu;
//	
}).controller('HomeCtrl', function($scope, Menus, $resource, $http) {
	$scope.menudata = $resource('rest/service/caterer/1/menu').get();
}).controller('CatererListCtrl', function($scope, Menus, $resource, $http) {
	$scope.catererData = $resource('rest/service/caterer/').get();
}).controller('CatererMenuCtrl', function($scope, Menus, $resource, $http, $routeParams) {
	var catererId = $routeParams.catererId;
	$scope.menudata = $resource('rest/service/caterer/'+catererId+'/menu').get();
}).controller('ListCtrl', function($scope, Menus, $resource, $http) {
	$scope.menudata = $resource('rest/service/caterer/1/menu').get();
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
			//$scope.menudata = $resource('rest/service/caterer/1/menu/date/2014-12-10').get();
			$scope.status="Started";

//			
//			$scope.add=function(){
//				var cMenu=[{ itemName: "Thali101", description: "Jain Thali",price: 100,prepTime: 15},{ itemName: "Thali102", description: "Jain Thali",price: 100,prepTime: 15}];
//				$resource('rest/service/caterer/1/menu/').save(cMenu);
//				$scope.status=cMenu;
//			};
//			
//			$scope.update=function(){
//				var cMenu={ itemId:15,itemName: "jamun", description: "second description",price: 100,prepTime: 15};
//				$resource('rest/service/caterer/1/menu/').save(cMenu);
//				$scope.status=cMenu;
//			};
			
//			$scope.add=function(){
//				var cMenu={catererName:"MDP"};
//				$resource('rest/service/caterer/').save(cMenu);
//				$scope.status=cMenu;
//			};
//			
//			$scope.update=function(){
//				//var cMenu={catererName:"Royal Foodie"};
//				$resource('rest/service/caterer/1').save();
//				$scope.status=cMenu;
//			};
			
			$scope.add=function(){
				var cMenu=[{itemId : 1}, {itemId : 2}, {itemId : 3}, {itemId : 4}];
				$resource('rest/service/caterer/10/menu/date/2014-12-18').save(cMenu);
				$scope.status=cMenu;
			};
			
//			$scope.update=function(){
//				//var cMenu={catererName:"Royal Foodie"};
//				$resource('rest/service/caterer/1').save();
//				$scope.status=cMenu;
//			};
			
			
			
//			
			$scope.remove=function(){
				var itemId=2;
				$resource('').delete(cMenu);
				$scope.status=cMenu;
			};
			
			//$scope.status="ended";
//			$scope.menudata = $resource(
//					'rest/kanteen/menu/caterer/1/date/2014-12-10').get();
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
//			$scope.remove = function(id) {
//				var result = Menus.remove(id);
//				if (result) {
//					$location.path('/');
//				}
//			};
			// alert($scope.menus);
		})

.controller('CreateCtrl', function($scope, $location, $resource, Menus) {
	$scope.save = function() {
		
		var result = $resource('rest/service/caterer/3/menu/').save($scope.menu);
		if (result) {
			$location.path('/');
		}
	};
		})
.controller('AddMenuCtrl', function($scope, $location, $resource, $routeParams) {
	var catererId = 34;
	$scope.get=function(){
		$scope.menudata = $resource('rest/service/caterer/'+catererId+'/menu').get();
		if($scope.menudata){
			$scope.displayMenuList=true;
		}
	};
	$scope.update=function(cMenu){
		$scope.menu=cMenu;
		$scope.displayMenuList=false;
	};
	$scope.save = function() {
		var result = $resource('rest/service/caterer/'+catererId+'/menu/').save($scope.menu);
		if (result) {
			$scope.get();
		}
	};
	$scope.get();
//	$scope.displayMenuList=true;
	
})
.controller('AddCatererCtrl', function($scope, $location, $resource, $routeParams) {
	//var catererId = 3;
	$scope.get=function(){
		$scope.catererdata = $resource('rest/service/caterer').get();
//		if($scope.catererdata){
//			$scope.displayMenuList=true;
//		}
	};
	$scope.update=function(cCaterer){
		$scope.caterer=cCaterer;
		//$scope.displayMenuList=false;
	};
	$scope.save = function() {
		var result = $resource('rest/service/caterer').save($scope.caterer);
		if (result) {
			$scope.get();
		}
	};
//	$scope.get();
//	$scope.displayMenuList=true;
	
})
.controller('EditCtrl', function($scope, $location, $routeParams, Menus) {
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
