angular.module('JustEatDAO')
.controller('categoriesCtrl', ['categoriesFactory',function(categoriesFactory){
    var categoriesVM = this;
    categoriesVM.categories=[];
    categoriesVM.functions = {
    	
		//TODO Complete this function
		readCategories : function() {
			categoriesFactory.getCategories()
				.then(function(response){
					console.log("Reading all the categories: ", response);
					categoriesVM.categories = response;
				}, function(response){
					console.log("Error reading categories");
				})
		}
    }
    categoriesVM.functions.readCategories();
}])