angular.module('JustEatDAO')
.factory('ordersFactory', ['$http', function($http){
	
	var url = 'https://localhost:8443/JustEatDAO/rest/orders/';
	var ordersInterface = {
		getOrders: function(){
			url = url;
			return $http.get(url)
				.then(function(response){
					return response.data;
				});
		},
		postOrder: function(order){
			url = url;
			return $http.post(url,order)
				.then(function(response){
					return response.status;
				});
		}
	};
	return ordersInterface;
}])