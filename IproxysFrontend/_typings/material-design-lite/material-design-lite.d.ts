declare var componentHandler:IComponentHandlerStatic;
declare var dialogPolyfill:IDialogPolyFill;

declare var getmdlSelect;

declare interface  IInit{
    init(selector:string):void;
}
declare interface IComponentHandlerStatic extends upgradeDom {
    upgradeAllRegistered():void;
    upgradeElements(eles:NodeListOf<Element>):void;
    downgradeElements(ele:Element|any):void;
}
declare interface  IFormElement {
    reset():void;
}
declare interface upgradeDom {
    upgradeDom():void;
}
declare  interface DisableCheck {
    disabled:boolean;
    checked:boolean;
}
declare interface MaterialProgressProperty {
    MaterialProgress:MaterialProgress;
    innerHTML:string;
    MaterialMenu:any;

}

declare interface  MaterialProgress {
    setProgress(progress:number):void;
}

declare interface IMaterialSnackbar {
    showSnackbar(any:any):void;
}

declare interface IDialog {
    close():void;
    showModal():void;
}
declare interface IDialogPolyFill {
    registerDialog(dialog:Element):void;
}
declare interface  Element extends MaterialProgressProperty,DisableCheck,IDialog,IFormElement {
    MaterialSnackbar:IMaterialSnackbar;
}

declare interface IFormControllerExt extends angular.IFormController {
    email:any;
    username:any;
}

declare interface IHttpRequestConfigHeadersExt extends angular.IHttpRequestConfigHeaders {

    Authorization?:string;
}

declare class MaterialCheckbox{
    constructor(elements:any);
}