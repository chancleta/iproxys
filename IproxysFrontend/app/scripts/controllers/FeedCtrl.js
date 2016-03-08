'use strict';

socialNetworkApp.controller('FeedCtrl',["$scope","$location","AuthenticationService","FeedService", function FeedCtrl($scope, $location, AuthenticationService,FeedService){

  if(!AuthenticationService.isUserLoggedIn()){
    $location.path("/login");
  }
  //$scope.feed  = FeedService.getFeeList();
   FeedService.getFeeList().$promise.then(function(feedData){
     $scope.feed = feedData;
   });

    $scope.$on("ngRepeatDone",function(){
      FeedService.lazyLoadPosts( $scope.feed );

      var postContainer = document.getElementById("feedcontainer");

      var loader = document.getElementById("touchloader");
      var isTouched = false;
      var isMoved = false;
      var prevY = 0;
      var movedY =  false;
      var draggedPixels = 0;

      //Add the start of the touching
      var onTouchStart = function(e){
        isTouched = true;
        e.preventDefault();
      };

      var onTouchEnd = function(e){

        if(isMoved){
          //loader.style.display = "none";
          // loader.style.height = "0px";
          draggedPixels = 0;

          //Here we need to do a request and reload all the posts
          loader.style.transition = "max-height 0.5s ease-in";
          loader.style.maxHeight = "0px";
        }
        isTouched = false;
        isMoved = false;

        e.preventDefault();
      };

      var OnTouchMove = function(e){
        if(isTouched){


          if(e.type == "mousemove"){
            console.log(e);
            movedY = e.clientY > prevY;
            prevY =  e.clientY;
          }else{
            movedY = e.changedTouches[0].clientY > prevY;
            prevY =  e.changedTouches[0].clientY;
          }
            if(movedY){
            loader.style.display = "block";
            draggedPixels += 2;
            loader.style.height = draggedPixels+'px';
            loader.style.transition = "max-height 0.01s ease-out";
            loader.style.maxHeight = "80px";
            isMoved = true;

          }
        }
        e.preventDefault();
      };

      postContainer.addEventListener("touchstart",onTouchStart,false);
      postContainer.addEventListener("mousedown",onTouchStart,false);

      postContainer.addEventListener("touchend",onTouchEnd,false);
      postContainer.addEventListener("mouseup",onTouchEnd,false);

      postContainer.addEventListener("touchmove",OnTouchMove,false);
      postContainer.addEventListener("mousemove",OnTouchMove,false);


    });
}]);
