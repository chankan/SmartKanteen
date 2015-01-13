var loginRequired = function( $location, $q,$rootScope ) {  
    var deferred = $q.defer();
    if($rootScope.userSession && $rootScope.userSession.login) {
    		deferred.resolve();
    } else {
    	deferred.reject()
    	$location.path('/login');
    }
    return deferred.promise;
}

var superAdminRoleRequired = function( $location, $q,$rootScope ) {  
    var deferred = $q.defer();
    if($rootScope.userSession && $rootScope.userSession.login) {
    	if($rootScope.userSession.role==1){
    		deferred.resolve();
    	}
    	else{
    		$location.path('/unauthorized');
        	deferred.reject()
    	}
    } else {
    	$location.path('/login');
    	deferred.reject()
    }
    return deferred.promise;
}

var adminRoleRequired = function( $location, $q,$rootScope ) {  
	var deferred = $q.defer();
	if($rootScope.userSession && $rootScope.userSession.login) {
		if($rootScope.userSession.role==2){
			deferred.resolve();
		}
		else{
			deferred.reject()
			$location.path('/unauthorized');
		}
	} else {
		deferred.reject()
		$location.path('/login');
	}
	return deferred.promise;
}


function getMenuList(role){
	var mainMenu;
	var userMenu = [ 
	                 {name : "Home", url : "#/"	}, 
	                 {name : "Today's Menu",url : "#/todaymenus"},
	                 {name : "My Order",url : "#/new"},
	                 ];

	var adminMenu = [ 
	                 {name : "Home", url : "#/"},
	                 {name : "Master Menu",url : "#/admin/menu"},
	                 {name : "Today's Menu",url : "#/admin/dailymenu"},
	                 {name : "Order", url : "#/new"	},
	                 ];
		
	var superAdminMenu = [ 
	                 {name : "Home", url : "#/"},
	                 {name : "Caterers", url : "#/superadmin/caterer"},
	                 {name : "Master Menu",url : "#/admin/menu"}
	                 ];
	
	if(role==3){mainMenu=userMenu}
	else if(role==2){mainMenu=adminMenu}
	else if(role==1){mainMenu=superAdminMenu}
	return mainMenu;
}

angular.module('canteen', [  'ngSanitize', 'ngRoute', 'ngResource','mgcrea.ngStrap'])
		.factory('CatererRes',[ '$resource', function($resource) {
			var service = $resource('rest/service/caterer');
			return service;
		} ])
		.factory('Menus',[ '$resource', function($resource) {
			var MenuService = $resource('rest/service/caterer/:catererId/menu/:menuDate',{catererId: '@catererId', menuDate:'@menuDate'});
			return MenuService;
		} ])
		.factory('TagService',[ '$resource', function($resource) {
			var TagService = $resource('rest/service/tag');
			return TagService;
		} ])
		.factory('UserMgr', ['$http', '$rootScope', '$location', function($http, $rootScope, $location) {
		      return {
		        login: function(user,successCallback,failuerCallBack) {
		        	user.loginId=user.name;
		          return $http.post('rest/user/login', user)
		            .success(function(data) {
		              $rootScope.userSession = data;
		              $rootScope.userSession.login=true;
		              if( $rootScope.userSession.userCatererMapping && $rootScope.userSession.userCatererMapping.caterer){
		            	  $rootScope.userSession.catererId=$rootScope.userSession.userCatererMapping.caterer.catererId;
		              }
		              $rootScope.userSession.mainMenu=getMenuList($rootScope.userSession.role);
		              $http.defaults.headers.common['userSession']= $rootScope.userSession.sessionId;
		              successCallback();
		            }).error(function(response) {
		            	$rootScope.userSession =null
		            	$http.defaults.headers.common['userSession']=null;
		            	failuerCallBack();
		            });
		        },
		        signup: function(user) {
		          return $http.post('rest/user/signup', user)
		            .success(function() {
		              $location.path('/login');
		            }).error(function(response) {});
		        },
		        logout: function() {
		        	 $rootScope.userSession =null
		        	 $http.defaults.headers.common['userSession']= null;
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
		templateUrl : 'view/caterermenulist.html',
		resolve: {  loginRequired: loginRequired }	
	}).when('/caterer/:catererId/menu/:dailyMenuDate', {
		controller : 'CatererMenuCtrl',
		templateUrl : 'view/caterermenulist.html',
		resolve: {  loginRequired: loginRequired }	
	}).when('/caterer/:catererId/menu', {
		controller : 'CatererMenuCtrl',
		templateUrl : 'view/caterermenulist.html',
		resolve: {  loginRequired: loginRequired }	
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
	}).when('/admin/dailymenu', {//Admin Menu
		controller : 'AddDailyMenuCtrl',
		templateUrl : 'view/catererMenuUpdate.html',
		resolve: { loginRequired: loginRequired }
	}).when('/superadmin/caterer', {//SuperAdmin Menu
		controller : 'AddCatererCtrl',
		templateUrl : 'view/addCaterer.html',
		resolve: { loginRequired: superAdminRoleRequired }	
	}).when('/login', {//login Menu
		controller : 'LoginCtrl',
		templateUrl : 'view/login.html'
	}).when('/unauthorized', {//login Menu
		controller : 'StaticCtrl',
		templateUrl : 'view/unauthorized.html'
	}).when('/register', {//login Menu
		controller : 'RegistrationCtrl',
		templateUrl : 'view/adduser.html'
	}).otherwise({
		redirectTo : '/'
	});
}).controller('MenuCtrl', function($scope, Menus, UserMgr) {
	$scope.loginMenu = [ {
		name : "Register Now!!",
		url : "#/register"
	}, {
		name : "Log In",
		url : "#/login"
	}, ];
	
	$scope.logout=function(){
		UserMgr.logout();
	};
}).controller('LoginCtrl', function($scope,$rootScope, $alert, $location,UserMgr) {
	$scope.user={name:"",password:""};
	var myAlert = {title: 'Login Failed:', content: '', placement: 'top', type: 'danger', show: false,container:'#alerts-container'};

	$scope.login=function(){
		UserMgr.login($scope.user,function(){ $location.path('/');},function(){myAlert.content='Username or Password is not proper.';myAlert.show=true;$alert(myAlert)});
		$scope.user={name:"",password:""};
	};
}).controller('StaticCtrl', function($scope) {

}).controller('RegistrationCtrl', function($scope,$rootScope,UserMgr) {
	$scope.user={userId:-1, loginId:"aa", password:"aa", emailId:"aa@aa.com"};
	$scope.errorMessage="";
	$scope.signup=function(){UserMgr.signup($scope.user)};

}).controller('HomeCtrl', function($scope, Menus, $resource, $http) {

}).controller('CatererListCtrl', function($scope, Menus, $resource, $http, $filter) {
	var cDate = new Date();
	$scope.todaysDate= $filter('date')(cDate, "yyyyMMdd");
	$scope.catererData = $resource('rest/service/caterer/').get();
}).controller('CatererMenuCtrl', function($scope, Menus, $resource, $http, $routeParams, TagService, $select, $filter) {
	var catererId = $routeParams.catererId;
	if($routeParams.dailyMenuDate){
		$scope.dailyMenuDate = $routeParams.dailyMenuDate;
	}
	else{
		$scope.dailyMenuDate =$filter('date')(new Date(), "yyyyMMdd");
	}
	$scope.get=function(){
		Menus.get({catererId:catererId,menuDate:$scope.dailyMenuDate}, function(response){if(response){ angular.forEach(response.menu, function(value,key){if(value.menuTagsMapping.tags){value.tag=value.menuTagsMapping.tags.split(',');} }); $scope.menudata=response.menu;}}, function(){$scope.menudata=[];});
		$scope.tags=TagService.get();
	};
	$scope.get();
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
.controller('AddMenuCtrl', function($scope, $location, $resource, $routeParams, $rootScope, Menus) {
//	var catererId = 2;
	if($rootScope.userSession  && $rootScope.userSession.catererId){
		$scope.catererId=$rootScope.userSession.catererId
		$scope.displayMenuList=true;
		$scope.get=function(){
			Menus.get({catererId:$scope.catererId}, function(response){if(response){$scope.menudata=response.menu;$scope.displayMenuList=true;}}, function(){$scope.menudata=[];$scope.displayMenuList=false;});
		};
		$scope.update=function(cMenu){
			$scope.menu=cMenu;
			$scope.displayMenuList=false;
		};
		$scope.save = function() {
			if($scope.menu.itemId >0){
				$scope.menu.menuTagsMapping=null;
			}
			Menus.save({catererId:$scope.catererId},$scope.menu,function(){$scope.get();},function(response){$scope.errormessage=response.data});
		};
		$scope.cancel=function(){
			$scope.menu=null;
			$scope.displayMenuList=true;
		}
		$scope.get();
	}
	else{
		$scope.catererId=-1;
	}
	
})
.controller('AddDailyMenuCtrl', function($scope, $location, $resource, $routeParams, $rootScope, Menus, $alert) {
//	var catererId = 2;
	if($rootScope.userSession  && $rootScope.userSession.catererId){
		$scope.catererId=$rootScope.userSession.catererId
		$scope.displayMenuList=true;
		$scope.dailyMenuMap=true;
		var message = {title: 'Daily Menu Mapping:', content: '', placement: 'top', type: 'danger', show: true, container:'#alerts-container'};
		$scope.get=function(){
			Menus.get({catererId:$scope.catererId}, function(response){if(response){$scope.menudata=response.menu;}}, function(){$scope.menudata=[];$scope.displayMenuList=false;});
		};
		$scope.add=function(){
			var selectedMenu=[];
			angular.forEach($scope.menudata, function(value,key){if(value.dailyMenu){this.push({"itemId":value.itemId});} },selectedMenu);
			Menus.save({catererId:$scope.catererId,menuDate:$scope.dailyMenuDate}, {"menu":selectedMenu}, function(response){if(response){ message.content=(" Daily menu added for date :"+$scope.dailyMenuDate); message.type='success';$alert(message); /*$scope.menudata=response.menuList;*/}}, function(){ message.content=(" Failed to add Daily menu for date :"+$scope.dailyMenuDate); message.type='danger';$alert(message);/*$scope.menudata=[];*/});
	
		};	
		$scope.get();
	}
	else{
		$scope.catererId=-1;
	}
})
.controller('AddCatererCtrl', function($scope, $location, CatererRes) {
	$scope.displayList=true;
	$scope.get=function(){
		CatererRes.get({},function(response){if(response){$scope.catererdata=response.caterer;$scope.displayList=true;}}, function(){$scope.catererdata=[];$scope.displayList=true;});
	};
	$scope.update=function(cCaterer){
		$scope.caterer=cCaterer;
		$scope.displayList=false;
	};
	$scope.save = function() {
		CatererRes.save($scope.caterer,function(){$scope.get();},function(response){$scope.errormessage=response.data});
	};
	$scope.get();
	
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
