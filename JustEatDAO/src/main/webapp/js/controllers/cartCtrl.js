angular.module('JustEatDAO')
.controller('cartCtrl', ['cartFactory', '$rootScope', function(cartFactory, $rootScope){
	var cartVM = this;
	cartVM.cart = [];
	
	cartVM.functions = {
		
		readCart: function(){
			cartFactory.getCart()
				.then(function(response){
					cartVM.cart = response
					console.log("Getting cart from user ",cartVM.cart.price,", Response: ", response);
				}, function(response){
					console.log("Error reading cart", response);
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
		},
		deleteFromCart: function (idd){
			cartFactory.deleteFromCart(idd)
			.then(function(){
				$rootScope.$emit('cartUpdated');
				console.log("Deleted dish ",idd," from the cart");
			}, function(){
				console.log("Error deleting from cart");
			})
		} 
	}
	cartVM.functions.readCart();
	
	$rootScope.$on('cartUpdated', function() {
	    cartVM.functions.readCart();
	  });
}])