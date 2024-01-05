angular.module('JustEatDAO')
.controller('restaurantCtrl', ['restaurantsFactory', 'dishesFactory', 'categoriesFactory', 'reviewsFactory', 'usersFactory', 'cartFactory', '$routeParams', '$location','$rootScope', 
function(restaurantsFactory, dishesFactory, categoriesFactory, reviewsFactory, usersFactory, cartFactory, $routeParams, $location, $rootScope){
	var restaurantVM = this;
	restaurantVM.restaurant = {};
	restaurantVM.restaurant.categories = [];
	restaurantVM.restaurant.dishes = [];
	restaurantVM.restaurant.reviews = [];
	
	restaurantVM.functions = {
		
		readRestaurant: function (id){
			restaurantsFactory.getRestaurantPublic(id)
				.then(function(response){
                        console.log("Reading restaurant with id: ",id," Response: ",response);
                        restaurantVM.restaurant = response;
                        categoriesFactory.getCategoriesByRestaurant(restaurantVM.restaurant.id)
							.then(function(response){
								console.log("Reading all the categories of the restaurant, Response: ",response);
								restaurantVM.restaurant.categories = response;
							}, function(response){
									console.log("Error reading categories");
								});
					
				}, function(response){
                        console.log("Error reading restaurant ", id);
                        $location.path('/');
                });
                
			dishesFactory.getDishesByRestaurantPublic(id)
			.then(function(response){
					console.log("Reading all the dishes: ", response);
					restaurantVM.restaurant.dishes = response;
				}, function(response){
					console.log("Error reading dishes, Response:",response.status);
				});
            
        	reviewsFactory.getReviews(id)
        		.then(function(response){
					console.log("Reading all reviews from restaurant ", id, "Response: ", response);
					restaurantVM.restaurant.reviews = response;
					restaurantVM.restaurant.reviews.forEach(function(review){
						usersFactory.getUserById(review.idu)
							.then(function(response){
								console.log("Reading user from review ", review.id, ", Response: ", response);
								review.userName = response.name;
							}, function(response){
								console.log("Error reading user");
							});
					});
				});
			restaurantsFactory.isFavorite(id)
				.then(function(response){
					console.log("Checking if the restaurant is favorite, ",id, "Response: ",response);
					restaurantVM.restaurant.fav = response;
				}, function(response){
					console.log("Error checking if restaurant is favorite, ",response);
				})
		},
		addToFavorites: function(id){
			restaurantsFactory.addToFavorites(id)
				.then(function(){
					console.log("Added to favorites");
					restaurantVM.restaurant.fav = true;
				},function(){
					console.log("Error Adding To Favorites")
				})
		},
		deleteFromFavorites: function(id){
			restaurantsFactory.deleteFromFavorites(id)
				.then(function(){
					console.log("Deleted from favorites");
					restaurantVM.restaurant.fav = false;
				},function(){
					console.log("Error deleting fromo Favorites");
				})
		},
		addToCart: function (idd){
			cartFactory.addToCart(idd)
			.then(function(){
				$rootScope.$emit('cartUpdated');
				console.log("Added dish ",idd," to the cart");
			}, function(){
				console.log("Error adding to cart");
			})
			$location.path("/cart");
		}
	}
	if(!($routeParams.IDR == undefined))
		restaurantVM.functions.readRestaurant($routeParams.IDR);
}])