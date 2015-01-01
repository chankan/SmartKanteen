var loginRequired = function($location, $q,$rootScope) {  
    var deferred = $q.defer();
    if($rootScope.userSession && $rootScope.userSession.login) {
    	deferred.resolve();
    } else {
    	deferred.reject()
    	$location.path('/login');
    }
    return deferred.promise;
}

angular.module('canteen', [ 'ngRoute', 'ngResource' ]).factory('Menus',
		[ '$resource', function($resource) {
			var MenuService = $resource('rest/service/caterer/:catererId/menu',{catererId: '@catererId'});
			return MenuService;
		} ]).factory('UserMgr', ['$http', '$location', '$rootScope', function($http, $location, $rootScope) {
		      return {
		        login: function(user) {
		        	user.loginId=user.name;
		          return $http.post('rest/user/login', user)
		            .success(function(data) {
		              $rootScope.userSession = data;
		              $rootScope.userSession.login=true;
		              $location.path('/');
		            }).error(function(response) {$rootScope.userSession =null});
		        },
		        signup: function(user) {
		          return $http.post('rest/user/signup', user)
		            .success(function() {
		              $location.path('/login');
		            }).error(function(response) {});
		        },
		        logout: function() {
		        	 $rootScope.userSession =null
		          //return $http.get('user/logout').success(function() {
		        //	  $rootScope.userSession =null
		          //});
		        }
		      };
		    }]).config(function($routeProvider) {
	$routeProvider.when('/', {
		controller : 'HomeCtrl',
		templateUrl : 'view/homepage.html'
	}).when('/todaymenus', {
		controller : 'CatererListCtrl',
		templateUrl : 'view/catererlist.html',
		resolve: { loginRequired: loginRequired }	
	}).when('/caterer/:catererId', {
		controller : 'CatererMenuCtrl',
		templateUrl : 'view/caterermenulist.html'
	}).when('/menulist', {
		controller : 'ListCtrl',
		templateUrl : 'view/menulist.html'
	}).when('/dailymenu', {
		controller : 'DailyMenuCtrl',
		templateUrl : 'view/todaysmenulist.html',//UserMenu End
		resolve: { loginRequired: loginRequired }
	}).when('/user', {
		controller : 'userCtrl',
		templateUrl : 'view/adduser.html'//UserMenu End
	}).when('/admin/menu', {//Admin Menu
		controller : 'AddMenuCtrl',
		templateUrl : 'view/catererMenuUpdate.html',
		resolve: { loginRequired: loginRequired }
	}).when('/superadmin/caterer', {//SuperAdmin Menu
		controller : 'AddCatererCtrl',
		templateUrl : 'view/addCaterer.html'
	}).when('/login', {//login Menu
		controller : 'LoginCtrl',
		templateUrl : 'view/login.html'
	}).when('/register', {//login Menu
		controller : 'RegistrationCtrl',
		templateUrl : 'view/adduser.html'
	}).otherwise({
		redirectTo : '/'
	});
}).controller('MenuCtrl', function($scope, Menus, UserMgr) {
	
	
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

	var adminMenu = [ 
	                 {name : "Home", url : "#/"},
	                 {name : "Master Menu",url : "#/admin/menu"},
	                 {name : "Today's Menu",url : "#/todaymenus"},
	                 {name : "Order", url : "#/new"	},
	                 ];
		
	var superAdminMenu = [ 
	                 {name : "Home", url : "#/"},
	                 {name : "Caterers", url : "#/superadmin/caterer"},
	                 {name : "Master Menu",url : "#/admin/menu"}	                 ];
	
	$scope.loginMenu = [ {
		name : "Register Now!!",
		url : "#/register"
	}, {
		name : "Log In",
		url : "#/login"
	}, ];
	
//$scope.mainMenu=superAdminMenu;
	$scope.mainMenu=adminMenu;
	
	$scope.logout=function(){
		UserMgr.logout();
	};
}).controller('LoginCtrl', function($scope,$rootScope,UserMgr) {
	$scope.user={name:"",password:""};
	$scope.login=function(){
		UserMgr.login($scope.user);
	};
}).controller('RegistrationCtrl', function($scope,$rootScope,UserMgr) {
	$scope.user={userId:-1, loginId:"aa", password:"aa", emailId:"aa@aa.com"};
	$scope.errorMessage="";
	$scope.signup=function(){UserMgr.signup($scope.user)};

}).controller('HomeCtrl', function($scope, Menus, $resource, $http) {

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
.controller('AddMenuCtrl', function($scope, $location, $resource, $routeParams, Menus) {
	var catererId = 2;
	$scope.displayMenuList=true;
	$scope.get=function(){
		Menus.get({catererId:catererId}, function(response){if(response){$scope.menudata=response.menu;$scope.displayMenuList=true;}}, function(){$scope.menudata=[];$scope.displayMenuList=true;});
	};
	$scope.update=function(cMenu){
		$scope.menu=cMenu;
		$scope.displayMenuList=false;
	};
	$scope.save = function() {
		Menus.save({catererId:catererId},$scope.menu,function(){$scope.get();},function(response){$scope.errormessage=response.data});
//		var result = $resource('rest/service/caterer/'+catererId+'/menu/').save($scope.menu);
//		if (result) {
//			$scope.get();
//		}
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
