import {Component} from '@angular/core';
import {AuthenticationService} from "../_services/auth.service";
import {Router} from "@angular/router";

@Component({
    selector: 'left-menu',
    templateUrl: './left.menu.component.html',
    styleUrls: ['left.menu.component.css']
})
export class LeftMenuComponent {

    constructor(private authenticationService: AuthenticationService,
                private router: Router){
    }

    logout() {
        this.authenticationService.logout();
        this.router.navigate(['login']);
    }
}
