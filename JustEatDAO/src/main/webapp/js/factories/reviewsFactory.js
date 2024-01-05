angular.module('JustEatDAO')
.factory('reviewsFactory', ['$http', function($http){
		var url = 'https://localhost:8443/JustEatDAO/rest/reviews/';
		var reviewsInterface = {
			getReview: function(idr){
				var urlid = url +'getReview/'+idr;
				return $http.get(urlid)
					.then(function(response){
					return response.data;
				});
			},
			getReviews: function(idr){
				var urlid = url + idr;
				return $http.get(urlid)
					.then(function(response){
					return response.data;
				});
			},
			postReviews: function(review, idr){
				url = url+idr;
				return $http.post(url, review)
					.then(function(response){
					return response.status;
				})
			}, 
			putReview: function(review, idr){
				url = url + idr;
				return $http.put(url, review)
					.then(function(response){
					return response.status;
				})
			}
		}
		return reviewsInterface;
	}])