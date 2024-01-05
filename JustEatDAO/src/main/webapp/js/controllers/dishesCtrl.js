angular.module('JustEatDAO')
.controller('dishesCtrl', ['dishesFactory','$routeParams', '$location', function(dishesFactory, $routeParams, $location){
	var dishesVM = this;
	dishesVM.dishes = [];
	dishesVM.idr = {};
	dishesVM.functions = {
		readDishes: function(id){
			dishesFactory.getDishesByRestaurant(id)
			.then(function(response){
					console.log("Reading all the dishes: ", response);
					dishesVM.dishes = response;
				}, function(response){
					console.log("Error reading dishes, Response:",response.status);
					$location.path('/')
				});
		}
	}
	
	if(!($routeParams.ID==undefined)){
		dishesVM.functions.readDishes($routeParams.ID);
		dishesVM.idr=$routeParams.ID;
	}
}])