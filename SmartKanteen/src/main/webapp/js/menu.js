angular.module('todoApp', []).controller('MenuController',
		[ '$scope', function($scope) {
			$scope.MenuId=5;
			$scope.dMenu={ItemID: 00, ItemName: "", Description: "",Price: 0,PrepTime: 0};
			$scope.cMenu={ItemID: 00, ItemName: "", Description: "",Price: 0,PrepTime: 0};
			$scope.menus = [ 
			                {ItemID: 01, ItemName: "Thali", Description: "Jain Thali",Price: 100,PrepTime: 15},
			                {ItemID: 02, ItemName: "Biryani", Description: "Kashmiri Biryani",Price: 50,PrepTime: 30},
			                {ItemID: 03, ItemName: "Curd Rice", Description: "Special Curd Rice",Price: 35,PrepTime: 20},
			                {ItemID: 04, ItemName: "Desertz", Description: "Gulam Jamoon",Price: 20,PrepTime: 10},
			                {ItemID: 05, ItemName: "Roti", Description: "Thanduri Roti",Price: 10,PrepTime: 20},
//			                 {
//				text : 'learn angular',
//				done : true
//			}, {
//				text : 'build an angular app',
//				done : false
//			} 
			
			];

			$scope.addMenu = function() {
				$scope.MenuId=$scope.MenuId+1;
				$scope.cMenu.ItemID = $scope.MenuId;
				$scope.menus.push($scope.cMenu);
				$scope.cMenu = $scope.dMenu;
			};

//			$scope.remaining = function() {
//				var count = 0;
//				angular.forEach($scope.todos, function(todo) {
//					count += todo.done ? 0 : 1;
//				});
//				return count;
//			};

			$scope.remove = function(delMenuIndex) {
				if (delMenuIndex > -1) {
					$scope.menus.splice(delMenuIndex, 1);
				}
			};
		} ])
		.controller('MenuUpdateController',
		[ '$scope', function($scope) {
			
			
		}]);
