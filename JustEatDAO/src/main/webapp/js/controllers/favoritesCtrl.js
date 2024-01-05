angular.module('JustEatDAO')
.controller('favoritesCtrl', ['restaurantsFactory','categoriesFactory', function(restaurantsFactory, categoriesFactory){
	var favoritesVM = this;
	favoritesVM.restaurants = [];
	favoritesVM.functions = {
		readFavorites: function(){
			restaurantsFactory.getFavorites()
				.then(function(response){
					console.log("Reading all the favorites: ", response);
					favoritesVM.restaurants = response;
					favoritesVM.restaurants.forEach(function(restaurant){
						categoriesFactory.getCategoriesByRestaurant(restaurant.id)
							.then(function(response){
								console.log("Reading all the categories of the restaurant, Response: ",response);
								restaurant.categories = response;
							}, function(response){
								console.log("Error reading categories");
							})
					})
				}, function(response){
					console.log("Error reading favorites");
				})
		}
	}
	favoritesVM.functions.readFavorites();
}])