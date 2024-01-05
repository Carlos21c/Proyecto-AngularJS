angular.module('JustEatDAO')
.factory("dishesFactory", ['$http', function($http){
	
	var url = 'https://localhost:8443/JustEatDAO/rest/dishes';
	var dishesInterface = {
		getDishesByOrder: function(id){
			var urlid = url + '?ido=' + id;
			return $http.get(urlid)
				.then(function(response){
					return response.data;
				});
		},
		getDishesByRestaurantPublic: function(id){
			var urlid = url + '?idr=' + id;
			return $http.get(urlid)
				.then(function(response){
					return response.data;
				});
		},
		getDishesByRestaurant: function(id){
			var urlid = url + '/byRestaurant/' + id;
			return $http.get(urlid)
				.then(function(response){
					return response.data;
				})
		},
		getDish: function(id){
			var urlid = url+'/' + id;
			return $http.get(urlid)
				.then(function(response){
					return response.data;
				})
		},
		putDish : function(dish){

        	//TODO Complete this function
        	var urlid = url+'/'+dish.id;
        	return $http.put(urlid, dish)
        		.then(function(response){
					return response.status;
				});

    	},
    	deleteDish : function(idd){

        	//TODO Complete this function
        	var urlid = url +'/'+idd;
        	return $http.delete(urlid)
        		.then(function(response){
					return response.status;
				});
        },
    	postDish:  function(idr, dish){
        
    		//TODO Complete this function
    		var urlid = url+'/'+idr;
    		return $http.post(urlid, dish)
				.then(function(response){
					return response.status;
				});

    	}
	};
	return dishesInterface;
}])