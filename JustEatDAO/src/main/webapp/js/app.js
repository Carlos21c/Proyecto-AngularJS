angular.module('JustEatDAO',['ngRoute'])
.config(function($routeProvider){
	$routeProvider
		.when("/", {
			controller: "mainPageCtrl",
			controllerAs: "mainPageVM",
			templateUrl: "mainPageTemplate.html",
			resolve: {
				delay: function($q, $timeout) {
    			var delay = $q.defer();
    			$timeout(delay.resolve, 500);
    			return delay.promise;
    			}
			}
		})
		.when("/logout", {
			controller: "profileCtrl",
			controllerAs: "profileVM",
			templateUrl: "logoutDeleteTemplate.html"
		})
		.when("/profile", {
			controller: "profileCtrl",
			controllerAs: "profileVM",
			templateUrl: "profileTemplate.html"
		})
		.when("/editProfile", {
			controller: "profileCtrl",
			controllerAs: "profileVM",
			templateUrl: "profileFormTemplate.html"
		})
		.when("/deleteProfile", {
			controller: "profileCtrl",
			controllerAs: "profileVM",
			templateUrl: "logoutDeleteTemplate.html"
		})
		.when("/categories", {
			controller: "categoriesCtrl",
			controllerAs: "categoriesVM",
			templateUrl: "categoriesTemplate.html",
			resolve: {
				delay: function($q, $timeout) {
    			var delay = $q.defer();
    			$timeout(delay.resolve, 500);
    			return delay.promise;
    			}
			}
		})
		.when("/addCategory",{
			controller: "categoriesHandlerCtrl",
			controllerAs: "categoriesHandlerVM",
			templateUrl: "categoryHandlerTemplate.html"
		})
		.when("/editCategory/:ID",{
			controller: "categoriesHandlerCtrl",
			controllerAs: "categoriesHandlerVM",
			templateUrl: "categoryHandlerTemplate.html"
		})
		.when("/deleteCategory/:ID",{
			controller: "categoriesHandlerCtrl",
			controllerAs: "categoriesHandlerVM",
			templateUrl: "categoryHandlerTemplate.html"
		})
		.when("/orders", {
			controller: "ordersCtrl",
			controllerAs: "ordersVM",
			templateUrl: "ordersTemplate.html"
		})
		.when("/favorites",{
			controller: "favoritesCtrl",
			controllerAs: "favoritesVM",
			templateUrl: "favoritesTemplate.html"
		})
		.when("/myRestaurants",{
			controller: "myRestaurantsCtrl",
			controllerAs: "myRestaurantsVM",
			templateUrl: "myRestaurantsTemplate.html",
			resolve: {
				delay: function($q, $timeout) {
    			var delay = $q.defer();
    			$timeout(delay.resolve, 500);
    			return delay.promise;
    			}
			}
		})
		.when("/addRestaurant",{
			controller: "restaurantsHandlerCtrl",
			controllerAs: "restaurantsHandlerVM",
			templateUrl: "restaurantsHandlerTemplate.html"
		})
		.when("/editRestaurant/:ID",{
			controller: "restaurantsHandlerCtrl",
			controllerAs: "restaurantsHandlerVM",
			templateUrl: "restaurantsHandlerTemplate.html"
		})
		.when("/deleteRestaurant/:ID",{
			controller: "restaurantsHandlerCtrl",
			controllerAs: "restaurantsHandlerVM",
			templateUrl: "restaurantsHandlerTemplate.html"
		})
		.when("/editMenu/:ID",{
			controller: "dishesCtrl",
			controllerAs: "dishesVM",
			templateUrl: "dishesTemplate.html",
			resolve: {
				delay: function($q, $timeout) {
    			var delay = $q.defer();
    			$timeout(delay.resolve, 500);
    			return delay.promise;
    			}
			}
		})
		.when("/editMenu/:IDR/addDish",{
			controller: "dishesHandlerCtrl",
			controllerAs: "dishesHandlerVM",
			templateUrl: "dishesHandlerTemplate.html"
		})
		.when("/editMenu/:IDR/editDish/:IDD",{
			controller: "dishesHandlerCtrl",
			controllerAs: "dishesHandlerVM",
			templateUrl: "dishesHandlerTemplate.html"
		})
		.when("/editMenu/:IDR/deleteDish/:IDD",{
			controller: "dishesHandlerCtrl",
			controllerAs: "dishesHandlerVM",
			templateUrl: "dishesHandlerTemplate.html"
		})
		.when("/search",{
			controller: "restaurantsSearchCtrl",
			controllerAs: "restaurantsSearchVM",
			templateUrl: "restaurantsSearchTemplate.html"
		})
		.when("/restaurant/:IDR", {
			controller: "restaurantCtrl",
			controllerAs: "restaurantVM",
			templateUrl: "restaurantTemplate.html"
		})
		.when("/restaurant/:IDR/review", {
			controller: "reviewsHandlerCtrl",
			controllerAs: "reviewsHandlerVM",
			templateUrl: "reviewsHandlerTemplate.html"
		})
		.when("/cart", {
			controller: "cartCtrl",
			controllerAs: "cartVM",
			templateUrl: "cartTemplate.html"
		})
		.when("/confirmOrder", {
			controller: "confirmOrderCtrl",
			controllerAs: "confirmOrderVM",
			templateUrl: "confirmOrderTemplate.html"
		})
})