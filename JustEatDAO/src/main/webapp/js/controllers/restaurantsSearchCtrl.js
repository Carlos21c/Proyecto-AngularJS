angular.module('JustEatDAO')
.controller('restaurantsSearchCtrl', ['restaurantsFactory', 'categoriesFactory', '$routeParams',
function(restaurantsFactory, categoriesFactory, $routeParams){
	var restaurantsSearchVM = this;
	restaurantsSearchVM.restaurants=[];
	restaurantsSearchVM.categories=[];
	restaurantsSearchVM.functions = {
		readRestaurants: function(){
			restaurantsFactory.getAllRestaurants()
			.then(function(response){
				console.log("Reading all restaurants", response);
				restaurantsSearchVM.restaurants = response;
				restaurantsSearchVM.restaurants.forEach(function(restaurant){
					categoriesFactory.getCategoriesByRestaurant(restaurant.id)
							.then(function(response){
								console.log("Reading all the categories of the restaurant, Response: ",response);
								restaurant.categories = response;
							}, function(response){
								console.log("Error reading categories");
							})
				})
			}, function(response){
				console.log("Error reading all restaurants");
			})
		}, readCategories : function() {
			categoriesFactory.getCategories()
				.then(function(response){
					console.log("Reading all the categories: ", response);
					restaurantsSearchVM.categories = response;
				}, function(response){
					console.log("Error reading categories");
				})
		}
	}
	restaurantsSearchVM.searchBar = $routeParams.search;
	restaurantsSearchVM.selectedCategory = "";
	if($routeParams.category != null){
		restaurantsSearchVM.selectedCategory = $routeParams.category.toString();
	}
	restaurantsSearchVM.selectedOrder = "name";
	restaurantsSearchVM.selectedAvailable = "";
	restaurantsSearchVM.functions.readRestaurants();
	restaurantsSearchVM.functions.readCategories(); 
}])