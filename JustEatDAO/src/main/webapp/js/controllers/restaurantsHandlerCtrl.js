angular.module('JustEatDAO')
.controller('restaurantsHandlerCtrl', ['restaurantsFactory','categoriesFactory', '$routeParams', '$location',
	function(restaurantsFactory, categoriesFactory, $routeParams, $location){
		var restaurantsHandlerVM = this;
		
		restaurantsHandlerVM.categories = [];
		restaurantsHandlerVM.restaurant = {};
		restaurantsHandlerVM.restaurant.list = [];
		restaurantsHandlerVM.emptyCategories = false;
		restaurantsHandlerVM.functions = {
			where: function(route){
				return $location.path() == route;
			},
			readRestaurant: function(id){
                restaurantsFactory.getRestaurant(id)
                    .then(function(response){
                        console.log("Reading restaurant with id: ",id," Response: ",response);
                        restaurantsHandlerVM.restaurant = response;
                        categoriesFactory.getCategoriesByRestaurant(restaurantsHandlerVM.restaurant.id)
								.then(function(response){
									console.log("Reading all the categories of the restaurant, Response: ",response);
									restaurantsHandlerVM.restaurant.categories = response;
									restaurantsHandlerVM.categories.forEach(function(category){
										category.checked=false;
										restaurantsHandlerVM.restaurant.categories.forEach(function(restCategory){
											if(category.id==restCategory.id){
												category.checked=true;
											}
										})
									})
								}, function(response){
									console.log("Error reading categories");
								});
                    }, function(response){
                        console.log("Error reading restaurant ", id);
                        $location.path('/');
                    });
            },
			updateRestaurant : function() {

				//TODO Complete this function
				
				restaurantsHandlerVM.restaurant.list = restaurantsHandlerVM.functions.getSelectedCategories();
				if(restaurantsHandlerVM.restaurant.list.length > 0){
					restaurantsHandlerVM.emptyCategories = false;
					restaurantsFactory.putRestaurant(restaurantsHandlerVM.restaurant)
						.then(function(response){
							console.log("Updating restaurant with id: ", restaurantsHandlerVM.restaurant.id, " Response: ", response, restaurantsHandlerVM.restaurant);
						}, function(response){
							console.log("Error updating restaurant");
						})
					$location.path('/myRestaurants');
				}
				else{
					restaurantsHandlerVM.emptyCategories = true;
					$location.path('/editRestaurant/'+restaurantsHandlerVM.restaurant.id);
					console.log("User must select at least one category");
				}
				
			},
			createRestaurant : function() {
				//TODO Complete this function
				restaurantsHandlerVM.restaurant.list = restaurantsHandlerVM.functions.getSelectedCategories();
				if(restaurantsHandlerVM.restaurant.list.length > 0){
					restaurantsHandlerVM.emptyCategories = false;
					restaurantsFactory.postRestaurant(restaurantsHandlerVM.restaurant)
						.then(function(response){
							console.log("Creating restaurant. Response: ", response, restaurantsHandlerVM.restaurant);
						}, function(response){
							console.log("Error creating restaurant");
						})
						$location.path('/myRestaurants');
				}
				else{
					restaurantsHandlerVM.emptyCategories = true;
					$location.path('/addRestaurant');
					console.log("User must select at least one category");
				}
			},
			deleteRestaurant : function(id) {
    
				//TODO Complete this function
				restaurantsFactory.deleteRestaurant(id)
					.then(function(response){
						console.log("Deleting restaurant. Response: ", response);
					}, function(response){
						console.log("Error deleting restaurant, ", restaurantsHandlerVM.restaurant.id);
					})
			},
			getSelectedCategories: function() {
				var selectedCategories = [];
				restaurantsHandlerVM.categories.forEach(function(category){
				    if(category.checked)
				        selectedCategories.push(category.id);
				});
				return selectedCategories;
			},
			restaurantsHandlerSwitcher : function(){
				
				

				if(restaurantsHandlerVM.functions.where('/addRestaurant')){
					console.log($location.path());
					restaurantsHandlerVM.functions.createRestaurant();
				}
				else if(restaurantsHandlerVM.functions.where('/editRestaurant/'+restaurantsHandlerVM.restaurant.id)){
					console.log($location.path());
					restaurantsHandlerVM.functions.updateRestaurant();
				}
				else if(restaurantsHandlerVM.functions.where('/deleteRestaurant/'+restaurantsHandlerVM.restaurant.id)){
					console.log($location.path());
					restaurantsHandlerVM.functions.deleteRestaurant(restaurantsHandlerVM.restaurant.id);
					$location.path('/myRestaurants');
				}
				
			}
		};
		categoriesFactory.getCategories()
				.then(function(response){
					console.log("Reading all the categories: ", response);
					restaurantsHandlerVM.categories = response;
				}, function(response){
					console.log("Error reading categories");
				});
		if(!($routeParams.ID==undefined))
			restaurantsHandlerVM.functions.readRestaurant($routeParams.ID);
}]);