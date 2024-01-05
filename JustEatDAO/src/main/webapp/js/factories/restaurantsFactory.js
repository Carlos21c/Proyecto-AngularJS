angular.module('JustEatDAO')
.factory('restaurantsFactory', ['$http',function($http){
	
	//TODO Add the base URL needed for executing the web services
	
	var url = 'https://localhost:8443/JustEatDAO/rest/restaurants/';
    var restaurantsInterface = {
    	getRestaurants: function(){
    		
    		//TODO Complete this function
    		url = url;
    		return $http.get(url)
				.then(function(response){
					return response.data;
				});
		},
		getAllRestaurants: function(){
			
			var urlid = url+'all';
			return $http.get(urlid).then(function(response){
				return response.data;
			})	
		},
    	getFavorites: function(){
			var urlid = url+'favorites';
			return $http.get(urlid)
				.then(function(response){
					return response.data;
				})
		},
		isFavorite: function(id){
			var urlid = url + "isFavorite/"+id;
			return $http.get(urlid)
			.then(function(response){
				return response.data;
			})
		},
		
		addToFavorites: function(id){
			var urlid = url + "addToFavorites/"+id;
			return $http.post(urlid)
				.then(function(response){
					return response;
				})
		},
		deleteFromFavorites: function(id){
			var urlid = url + "deleteFromFavorites/"+id;
			return $http.delete(urlid)
				.then(function(response){
					return response;
				})
		},
		getRestaurantPublic: function(id){
			var urlid = url +'public/'+ id;
        	return $http.get(urlid)
        		.then(function(response){
					return response.data;
				});
		},
    	getRestaurant : function(id){

        	//TODO Complete this function
        	var urlid = url + id;
        	return $http.get(urlid)
        		.then(function(response){
					return response.data;
				});
    	
    	},
    	putRestaurant : function(restaurant){

        	//TODO Complete this function
        	var urlid = url + restaurant.id;
        	return $http.put(urlid, restaurant)
        		.then(function(response){
					return response.status;
				});

    	},
    	deleteRestaurant : function(id){

        	//TODO Complete this function
        	var urlid = url + id;
        	return $http.delete(urlid)
        		.then(function(response){
					return response.status;
				});
        },
    	postRestaurant:  function(restaurant){
        
    		//TODO Complete this function
    		url = url;
    		return $http.post(url, restaurant)
				.then(function(response){
					return response.status;
				});

    	}				  
    };
    return restaurantsInterface;
}])