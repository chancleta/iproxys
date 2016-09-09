/// <reference path="../../../typings/tsd.d.ts" />

module App.Controllers {
    'use strict';


    export class FrontPageCtrl {
        public static $inject = ["$location","$anchorScroll"];

        constructor(public $location:angular.ILocationService, public $anchorScroll:angular.IAnchorScrollService) {
            document.querySelector("title").innerHTML = "Activaciones RD";
        }

        scrollTo(scrollToId:string) {
            this.$anchorScroll(scrollToId);
        }
    }
}