angular.module('JustEatDAO')
.controller('mainPageCtrl', ['categoriesFactory', '$location',function(categoriesFactory, $location){
    var mainPageVM = this;
    mainPageVM.categories=[];
    mainPageVM.search = "";
    mainPageVM.category = {};
    mainPageVM.functions = {
    	
		//TODO Complete this function
		readCategories : function() {
			categoriesFactory.getCategories()
				.then(function(response){
					console.log("Reading all the categories: ", response);
					mainPageVM.categories = response;
				}, function(response){
					console.log("Error reading categories");
				})
		},
		search: function(){
			$location.path('/search').search({search: mainPageVM.search});
		},
		
		categoryClicked: function(id){
			$location.path('/search').search({category: id});
		}

    }
    
    mainPageVM.functions.readCategories();
}])