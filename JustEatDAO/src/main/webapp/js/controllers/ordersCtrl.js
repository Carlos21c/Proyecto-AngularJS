angular.module('JustEatDAO')
.controller('ordersCtrl', ['ordersFactory','dishesFactory', function(ordersFactory, dishesFactory){
	var ordersVM = this;
	ordersVM.orders = [];
	ordersVM.functions = {
		readOrders: function(){
			ordersFactory.getOrders()
				.then(function(response){
					console.log("Reading all the orders of the user");
					ordersVM.orders = response;
					ordersVM.orders.forEach(function(order){
						dishesFactory.getDishesByOrder(order.id)
							.then(function(response){
								console.log("Reading all the dishes of the order");
								order.dishes = response;
							}, function(response){
								console.log("Error reading dishes");
							})
					});
				}, function(response){
					console.log("Error reading orders");
				})
		}
	}
	ordersVM.functions.readOrders();
}])