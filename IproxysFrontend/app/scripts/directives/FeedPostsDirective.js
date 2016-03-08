'use strict';
socialNetworkApp.directive('feedPosts', function(){
  return{
    restrict: "E",
    templateUrl: "/views/directives/feedPosts.html",
    replace: true,
    scope: {
      feedposts:"="
    }
  };

});

