import {Component, ViewContainerRef} from '@angular/core';
import {ToastsManager} from "ng2-toastr";

@Component({
    selector: 'my-app',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {

    constructor(private toastr: ToastsManager, viewRef: ViewContainerRef){
        this.toastr.setRootViewContainerRef(viewRef);
    }
}
