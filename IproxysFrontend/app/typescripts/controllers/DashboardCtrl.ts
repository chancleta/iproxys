/// <reference path="../../../typings/tsd.d.ts" />

module App.Controllers {
    'use strict';
    export class DashboardCtrl {
        public static $inject = ["$interval"];

        private maxTicksPerGraph:number = 30;
        public data:any = [[]];
        public labels:Array<string>  = [""];
        public series:Array<string> = ['KB/s'];
        public options:any = {
            animation: false,
            tooltipTemplate: function(v) {return parseFloat(v.value).toFixed(2)+" KB/s";},//Formatting the tooltip 2 decimals and Kbit/s
        } ;
        constructor($interval:angular.IIntervalService) {

            // Update the dataset at 25FPS for a smoothly-animating chart
            $interval(() => {
                    if (this.data[0].length > this.maxTicksPerGraph) {
                        this.labels = this.labels.slice(1);
                        this.data[0] = this.data[0].slice(1);
                    }

                this.labels.push('');
                this.data[0].push(Math.random()*100);
            }, 1000);


            //LiveMontiorService.webSocket.$on("$message" , function(data){

            //});
        }


    }



}