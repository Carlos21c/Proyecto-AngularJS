angular.module('JustEatDAO')
.factory('cartFactory',['$http',function($http){
	
	
	var url = 'https://localhost:8443/JustEatDAO/rest/cart/';
	var categoriesInterface = {
		getCart: function(){
			url = url;
    		return $http.get(url)
				.then(function(response){
					return response.data;
				});
		},
		addToCart: function(idd){
			var urlid = url+'add/'+idd;
			return $http.post(urlid)
        		.then(function(response){
					return response.data;
				});
		},
		deleteFromCart: function(idd){
			var urlid = url+'delete/'+idd;
			return $http.delete(urlid)
        		.then(function(response){
					return response.data;
				});
		},
		sendOrder: function(){
			return $http.post(url)
				.then(function(response){
					return response.data;
				})
		}
	}
	return categoriesInterface;
	}])