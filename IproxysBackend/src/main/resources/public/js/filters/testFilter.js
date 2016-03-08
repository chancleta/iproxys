'use strict';
socialNetworkApp.filter('testFilter', function(){
    return function (input /*,filter parametters*/){
        return input + " filtered";
    };
});