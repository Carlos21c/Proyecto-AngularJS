angular.module('JustEatDAO')
.controller('categoriesHandlerCtrl', ['categoriesFactory','$routeParams', '$location',
	function(categoriesFactory, $routeParams, $location){
		var categoriesHandlerVM = this;
		categoriesHandlerVM.category = {};
		categoriesHandlerVM.functions = {
			where: function(route){
				return $location.path() == route;
			},
			readCategory: function(id){
				categoriesFactory.getCategory(id)
					.then(function(response){
						console.log("Reading category with id: ",id," Response: ",response);
						categoriesHandlerVM.category = response;
					}, function(response){
						console.log("Error reading category ", id);
						$location.path('/');
					})
			},
			updateCategory : function() {

				//TODO Complete this function
				categoriesFactory.putCategory(categoriesHandlerVM.category)
					.then(function(response){
						console.log("Updating category with id: ", categoriesHandlerVM.category.id, " Response: ", response);
					}, function(response){
						console.log("Error updating category");
					})
				
			},
			createCategory : function() {
				//TODO Complete this function
				categoriesFactory.postCategory(categoriesHandlerVM.category)
					.then(function(response){
						console.log("Creating category. Response: ", response);
					}, function(response){
						console.log("Error creating category");
					})
				
			},
			deleteCategory : function(id) {
    
				//TODO Complete this function
				categoriesFactory.deleteCategory(id)
					.then(function(response){
						console.log("Deleting category. Response: ", response);
					}, function(response){
						console.log("Error deleting category, ", categoriesHandlerVM.category.id);
					})
			},
			categoriesHandlerSwitcher : function(){

				//TODO Complete this function
				if(categoriesHandlerVM.functions.where('/addCategory')){
					console.log($location.path());
					categoriesHandlerVM.functions.createCategory();
				}
				else if(categoriesHandlerVM.functions.where('/editCategory/'+categoriesHandlerVM.category.id)){
					console.log($location.path());
					categoriesHandlerVM.functions.updateCategory();
				}
				else if(categoriesHandlerVM.functions.where('/deleteCategory/'+categoriesHandlerVM.category.id)){
					console.log($location.path());
					categoriesHandlerVM.functions.deleteCategory(categoriesHandlerVM.category.id);
				}
				$location.path('/categories');
			}
		};
		if(!($routeParams.ID==undefined))
			categoriesHandlerVM.functions.readCategory($routeParams.ID);
}]);