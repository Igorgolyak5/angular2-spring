import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {LoginComponent} from "./login/login.component";
import {Router, RouterModule, Routes} from "@angular/router";
import {Http, HttpModule, RequestOptions, XHRBackend} from "@angular/http";
import {httpFactory} from "./_factory/http.factory";
import {AuthenticationService} from "./_services/auth.service";
import {FormsModule} from "@angular/forms";
import {ApplicationComponent} from "./application/application.component";
import {ApplicationService} from "./application/application.service";
import {ToastModule} from "ng2-toastr";
import {ModalComponent} from "./modal/modal.component";
import {OperatorsComponent} from "./operators/operator.component";
import {PublishersService} from "./publisher/publisher.service";
import {OperatorsService} from "./operators/operator.service";
import {PublishersComponent} from "./publisher/publisher.component";
import {NotificationService} from "./_services/notification.service";
import {SelectLoaderService} from "./select-loader/select.loader.service";
import {SelectLoaderComponent} from "./select-loader/select.loader.component";
import {LeftMenuComponent} from "./left-menu/left.menu.component";
import {IPermissionGuardModel, Ng2Permission} from "angular2-permission/dist";
import {AuthGuardService} from "./_services/auth.guard.service";

const routes: Routes = [
    {   path: '',
        redirectTo: '/login',
        pathMatch: 'full'
    },
    {   path: 'login',
        component: LoginComponent,
    },
    {   path: 'operators',
        component: OperatorsComponent,
        canActivate: [AuthGuardService],
        data: {
            Permission: {
                Only: ['ADMIN'],
            } as IPermissionGuardModel
        },
        children: []
    },
    {
        path: 'publishers',
        component: PublishersComponent,
        canActivate: [AuthGuardService],
        data: {
            Permission: {
                Only: ['ADMIN','ADOPS']
            } as IPermissionGuardModel
        },
        children: []
    },
    {   path: 'application',
        component: ApplicationComponent,
        canActivate: [AuthGuardService],
        data: {
            Permission: {
                Only: ['ADOPS','PUBLISHER'],
                RedirectTo: 'publishers'
            } as IPermissionGuardModel
        },
        children: []
    }
];

@NgModule({
    imports: [BrowserModule, FormsModule, HttpModule, RouterModule.forRoot(routes), Ng2Permission, ToastModule.forRoot()],
    declarations: [LeftMenuComponent, AppComponent,
        SelectLoaderComponent, LoginComponent, ApplicationComponent, PublishersComponent, OperatorsComponent, ModalComponent],
    providers: [AuthGuardService, PublishersService, OperatorsService, ApplicationService, AuthenticationService, NotificationService,
        SelectLoaderService, {provide:  Http, useFactory: httpFactory,  deps: [XHRBackend, RequestOptions, NotificationService, Router]}],
    bootstrap: [AppComponent]
})
export class AppModule {
}
