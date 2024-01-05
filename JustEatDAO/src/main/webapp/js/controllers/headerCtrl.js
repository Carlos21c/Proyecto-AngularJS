angular.module('JustEatDAO')
.controller('headerCtrl', ['cartFactory', '$rootScope', function(cartFactory, $rootScope){
	var headerVM = this;
	headerVM.cart={}
	headerVM.functions = {
		readCart: function(){
			cartFactory.getCart()
				.then(function(response){
					headerVM.cart = response
					console.log("Getting cart from user ",headerVM.cart.price,", Response: ", response);
				}, function(response){
					console.log("Error reading cart");
				})
		}
	}
	headerVM.functions.readCart();
	$rootScope.$on('cartUpdated', function() {
	    headerVM.functions.readCart();
	});
}])