angular.module('JustEatDAO')
.controller('confirmOrderCtrl', ['cartFactory', '$location', '$rootScope', function(cartFactory, $location, $rootScope){
	var confirmOrderVM = this;
	confirmOrderVM.cart = {};
	confirmOrderVM.functions = {
		readCart: function(){
			cartFactory.getCart()
				.then(function(response){
					confirmOrderVM.cart = response;
					console.log("Getting cart from user ",confirmOrderVM.cart.price,", Response: ", response);
				}, function(response){
					console.log("Error reading cart", response);
				})
		},
		sendOrder: function (){
			cartFactory.sendOrder()
			.then(function(response){
				$rootScope.$emit('cartUpdated');
				console.log("Sending order", response);
			}, function(){
				console.log("Error sending order");
			})
			$location.path("/");
		}
	};
	
	confirmOrderVM.functions.readCart();
}])