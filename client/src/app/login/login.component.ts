import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../_services/auth.service";
import {Router} from "@angular/router";
import {ToastsManager} from "ng2-toastr";
import {NotificationService} from "../_services/notification.service";

@Component({
    selector: 'login-component',
    templateUrl: 'login.component.html',
    styleUrls: ['login.component.css']
})
export class LoginComponent implements OnInit {
    model: any = {};

    constructor(private authenticationService: AuthenticationService,
                private notificationService: NotificationService,
                private router: Router) { }

    ngOnInit() {
        // reset login status
        this.authenticationService.logout();
    }

    login () { // custom class, may be empty now
        this.authenticationService.login(this.model.username, this.model.password)
            .subscribe(result => {
                if (result === true) {
                    // login successful
                    this.router.navigate(['application']);
                } else {
                   console.log('faild')
                }
            }, error => {
                this.notificationService.warming("Username or password incorrect");
            });

    }
}
