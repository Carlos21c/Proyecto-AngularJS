angular.module('JustEatDAO')
.controller('profileCtrl', ['usersFactory', '$location', '$window', function(usersFactory, $location, $window){
	var profileVM = this;
	profileVM.user = {};
	profileVM.functions = {
		where: function(route){
			return $location.path() == route;
		},
		readUser: function(){
			usersFactory.getUser()
				.then(function(response){
					profileVM.user = response
					console.log("Getting user with id: ", profileVM.user.id);
				}, function(response){
					console.log("Error reading user data");
				})
		},
		editUser: function(){
			usersFactory.editUser(profileVM.user)
				.then(function(response){
					console.log("Updating user with id: ", profileVM.user.id);
					$location.path('/profile');
				}, function(response){
					console.log("Error updating user");
				})
		},
		logout: function(){
			usersFactory.logout()
				.then(function(response){
					console.log("Logout user correctly.");
					$window.location.href = '../LoginServlet.do';
				}, function(response){
					console.log("Error logging out.")
				})
		},
		deleteUser: function(){
			usersFactory.deleteUser()
				.then(function(response){
					console.log("Deleting user with id: ", profileVM.user.id);
					$window.location.href = '../LoginServlet.do';
				}, function(response){
					console.log("Error delteing user");
				})
		},
		profileSwitcher: function(){
			if(profileVM.functions.where('/logout')){
				profileVM.functions.logout();
			}
			else if(profileVM.functions.where('/deleteProfile')){
				profileVM.functions.deleteUser();
			}
			$location.path('/');
		}
	}
	profileVM.functions.readUser();
}])