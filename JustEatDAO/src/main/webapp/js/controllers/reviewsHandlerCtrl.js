angular.module('JustEatDAO')
.controller('reviewsHandlerCtrl', ['reviewsFactory', '$routeParams', '$location',
	function(reviewsFactory, $routeParams, $location){
		var reviewsHandlerVM = this;
		reviewsHandlerVM.review = {};
		reviewsHandlerVM.rated = false;
		
		reviewsHandlerVM.functions = {
			where: function(route){
				return $location.path() == route; 
			},
			readReview: function(idr){
				reviewsFactory.getReview(idr)
					.then(function(response){
						console.log("Reading review from restaurant: ", idr, " , Response: ", response);
						var rev = response;
						if (rev != null && rev != ''){
							reviewsHandlerVM.review = rev;
							reviewsHandlerVM.rated = true;
						}
					}, function(response){
						console.log("Error reading review, ", idr," Status: ", response.status)
					})
			},
			createReview: function(){
				reviewsFactory.postReviews(reviewsHandlerVM.review , $routeParams.IDR)
					.then(function(response){
						console.log("Creating review. Response: ", response);
					}, function(response){
						console.log("Error creating review, Status: ",response.status);
					})
			},
			updateReview: function(){
				reviewsFactory.putReview(reviewsHandlerVM.review, $routeParams.IDR)
				.then(function(response){
					console.log("Updating review. Response: ", response);
				}, function(response){
					console.log("Error updating review, Status: ", response.status); 
				})
			},
			
			reviewsHandlerSwitcher : function(){

				if(reviewsHandlerVM.rated){
					reviewsHandlerVM.functions.updateReview();
				}
				else{
					reviewsHandlerVM.functions.createReview();
				}
				$location.path('/restaurant/'+$routeParams.IDR);
			}
		};
		
		if(!($routeParams.IDR==undefined))
			reviewsHandlerVM.functions.readReview($routeParams.IDR);
	}
])