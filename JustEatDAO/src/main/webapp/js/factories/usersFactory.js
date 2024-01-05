angular.module('JustEatDAO')
.factory('usersFactory',['$http', function($http){
	
	//TODO Add the base URL needed for executing the web services
	var url = 'https://localhost:8443/JustEatDAO/rest/users/';
	var usersInterface = {
    	getUser : function(){
	
			//TODO Complete this function
			url = url ;
			return $http.get(url)
				.then(function(response){
					return response.data;
				});
 
    	},
    	getUserById: function(id){
			var urlid = url + id;
			return $http.get(urlid)
				.then(function(response){
					return response.data;
				});
		},
    	editUser: function(user){
			url = url;
			return $http.put(url, user)
				.then(function(response){
					return response.status;
				});
		},
		logout: function(){
			url = url+'logout';
        	return $http.post(url)
        		.then(function(response){
					return response.status;
				});
		},
		deleteUser : function(){

        	//TODO Complete this function
        	url = url;
        	return $http.delete(url)
        		.then(function(response){
					return response.status;
				});
        }		
    }
    return usersInterface;
}])