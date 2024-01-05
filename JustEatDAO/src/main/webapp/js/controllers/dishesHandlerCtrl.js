angular.module('JustEatDAO')
.controller('dishesHandlerCtrl',['dishesFactory','$routeParams', '$location',
	function(dishesFactory, $routeParams, $location){
		var dishesHandlerVM = this;
		dishesHandlerVM.dish = {};
		dishesHandlerVM.idr = {};
		
		dishesHandlerVM.functions = {
			where: function(route){
				return $location.path() == route;
			},
			readDish: function(idd){
				dishesFactory.getDish(idd)
					.then(function(response){
						console.log("Reading dish with id: ",idd, " , Response: ", response);
						dishesHandlerVM.dish = response;
						
					}, function(response){
						console.log("Error reading dish, ",idd," Status: ", response.status);
						$location.path("/");
						
					})
			},
			createDish: function(idr){
				dishesFactory.postDish(idr, dishesHandlerVM.dish)
					.then(function(response){
						console.log("Creating dish. Response: ", response);
						$location.path('/editMenu/'+$routeParams.IDR);
					}, function(response){
						console.log("Error creating restaurant, Status: ",response.status);
					})
			},
			updateDish: function(){
				dishesFactory.putDish(dishesHandlerVM.dish)
					.then(function(response){
						console.log("Updating dish, Response: ",response);
					}, function(response){
						console.log("Error updating dish, Status: ", response.status);
					})
			},
			deleteDish: function(idd){
				dishesFactory.deleteDish(idd)
					.then(function(response){
						console.log("Deleting dish, "+idd+", Response", response);
					}, function(response){
						console.log("Error deleting dish, Status: ",response.status);
					})
			},
			dishesHandlerSwitcher : function(){

				//TODO Complete this function
				if(dishesHandlerVM.functions.where('/editMenu/'+$routeParams.IDR+'/addDish')){
					console.log($location.path());
					dishesHandlerVM.functions.createDish($routeParams.IDR);
				}
				else if(dishesHandlerVM.functions.where('/editMenu/'+$routeParams.IDR+'/editDish/'+dishesHandlerVM.dish.id)){
					console.log($location.path());
					dishesHandlerVM.functions.updateDish($routeParams.IDR);
				}
				else if(dishesHandlerVM.functions.where('/editMenu/'+$routeParams.IDR+'/deleteDish/'+dishesHandlerVM.dish.id)){
					console.log($location.path());
					dishesHandlerVM.functions.deleteDish(dishesHandlerVM.dish.id);
				}
				$location.path('/editMenu/'+$routeParams.IDR)
			}
		};
		if(!($routeParams.IDD==undefined))
			dishesHandlerVM.functions.readDish($routeParams.IDD);
		dishesHandlerVM.idr = $routeParams.IDR;
	}])