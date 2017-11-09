import {Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthenticationService} from './auth.service';
import {IPermissionGuardModel, PermissionService} from "angular2-permission/dist";

@Injectable()
export class AuthGuardService implements CanActivate {

    constructor(private authenticationService: AuthenticationService,
                private permissionService: PermissionService,
                private router: Router) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        let data = route.data["Permission"] as IPermissionGuardModel;

        if (Array.isArray(data.Only) && Array.isArray(data.Except))
            throw "can't use both 'Only' and 'Except' in route data.";

        if (Array.isArray(data.Only))
        {
            let user = this.authenticationService.getUser();
            this.permissionService.add(user.role);
            let roles = data.Only.filter(only=>only === user.role);
            if (roles.length > 0) {
                return true;
            }
            if (data.RedirectTo && data.RedirectTo !== undefined)
                this.router.navigate(['publishers']);

            return false;
        }
    }
}