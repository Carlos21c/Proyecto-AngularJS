angular.module('JustEatDAO')
.controller('myRestaurantsCtrl', ['restaurantsFactory', function(restaurantsFactory){
	var myRestaurantsVM = this;
	myRestaurantsVM.restaurants = [];
	myRestaurantsVM.functions = {
		readMyRestaurants: function(){
			restaurantsFactory.getRestaurants()
				.then(function(response){
					console.log("Reading all the user's restaurants, Response: ", response)
					myRestaurantsVM.restaurants = response;
				})
				}, function(response){
					console.log("Error reading the user's restaurant, Response:",response.status)
				}
		}
	myRestaurantsVM.functions.readMyRestaurants();
}])