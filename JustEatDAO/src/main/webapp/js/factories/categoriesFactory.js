angular.module('JustEatDAO')
.factory('categoriesFactory', ['$http',function($http){
	
	//TODO Add the base URL needed for executing the web services
	
	var url = 'https://localhost:8443/JustEatDAO/rest/categories/';
    var categoriesInterface = {
    	getCategories: function(){
    		
    		//TODO Complete this function
    		url = url;
    		return $http.get(url)
				.then(function(response){
					return response.data;
				});
    	
    	},
    	getCategoriesByRestaurant: function(idr){
			var urlid = url + 'restaurant/'+idr;
			return $http.get(urlid)
				.then(function(response){
					return response.data;
				});
		},
    	getCategory : function(id){

        	//TODO Complete this function
        	var urlid = url + id;
        	return $http.get(urlid)
        		.then(function(response){
					return response.data;
				});
    	
    	},
    	deleteCategory : function(id){

        	//TODO Complete this function
        	var urlid = url + id;
        	return $http.delete(urlid)
        		.then(function(response){
					return response.data;
				});
    	
    	},
    	putCategory : function(category){

        	//TODO Complete this function
        	var urlid = url + category.id;
        	return $http.put(urlid, category)
        		.then(function(response){
					return response.status;
				});

    	},
    	postCategory:  function(category){
        
    		//TODO Complete this function
    		url = url;
    		return $http.post(url, category)
				.then(function(response){
					return response.status;
				});

    	}				  
    };
    return categoriesInterface;
}])