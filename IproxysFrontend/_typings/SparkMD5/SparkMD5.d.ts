declare var SparkMD5:IStaticSparkMD5


declare interface IStaticSparkMD5 {
    hash(message:string):string
}