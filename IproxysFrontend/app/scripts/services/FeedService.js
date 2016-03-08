'use strict';
socialNetworkApp.factory('FeedService',["$resource","$http","ConfigData", function($resource,$http,ConfigData){
  var feedResource = $resource(ConfigData.url + ":" + ConfigData.port + "/feed",{},{ fetchFeedList: {method:'GET',isArray:true}});

  var lazyPosts = [];
  var lazyLoadPostsInitialized = false;
  var initLazyLoadPosts = function(){

    if(lazyPosts.length > 0){
      loadingProgressWidget(lazyPosts[0]);
    }

    angular.element(window).on("scroll",onScroll);
  };

  var onScroll = function(){
    var currentScroll = angular.element(window).scrollTop();
    var windowHeight =   angular.element(window).height();
    if(currentScroll < windowHeight/3) {
      return;
    }
    lazyPosts.forEach(function(post,index){
      var postDomObj = angular.element("#"+post.id);
      if( (currentScroll+windowHeight) >= postDomObj.offset().top){
        loadingProgressWidget(post);
      }

    });
  };


  var lazyLoadComments = function(postID){

  };

  var loadingProgressWidget = function(post){


    var postDomObj = angular.element("#"+post.id);

    //Removing this current post from the lazyPosts because we are going to load it
    lazyPosts.splice(lazyPosts.indexOf(post), 1);

    postDomObj.find(".thumbnail .radial-progress").show();

    var client = new XMLHttpRequest();

    client.addEventListener("progress", function(oEvent) {

      if(oEvent.lengthComputable){
        postDomObj.find(".thumbnail .radial-progress").attr("data-progress",parseInt( 100* oEvent.total / oEvent.loaded))
      }
    });

    client.onloadend = function(){

      var onProgressFadeOut = function(){
        var arr = new Uint8Array(client.response);
        // Convert the int array to a binary string
        // We have to use apply() as we are converting an *array*
        // and String.fromCharCode() takes one or more single values, not
        // an array.
        var raw = String.fromCharCode.apply(null,arr);
        // This works!!!
        var b64=btoa(raw);
        var dataURL="data:image/jpeg;base64,"+b64;
        postDomObj.find(".thumbnail").html($("<img>").attr("src",dataURL));

      };

      postDomObj.find(".thumbnail .radial-progress").fadeOut("fast",onProgressFadeOut);

    };
    client.responseType = 'arraybuffer';
    client.open("get", post.imgURL, true);
    client.send();




  };

return{
  getFeeList: function(){
    return feedResource.fetchFeedList();
  },

  lazyLoadPosts: function(posts){
    lazyPosts = lazyPosts.concat(posts);

    if(!lazyLoadPostsInitialized){
      initLazyLoadPosts();
      lazyLoadPostsInitialized = true;
    }
  }
};

}]);
