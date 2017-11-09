import {Http, Headers, Response} from "@angular/http";
import {Observable} from "rxjs/Rx";
import {Injectable} from "@angular/core";
import {TOKEN_AUTH_PASSWORD, TOKEN_AUTH_URL, TOKEN_AUTH_USERNAME} from "./auth.constant";
import {PermissionService} from "angular2-permission/dist";

/**
 * Service for work with authorization of user.
 */
@Injectable()
export class AuthenticationService {

    constructor(private http: Http, private permissionService: PermissionService) {

    }

    /**
     * Method needed for login user.
     *
     * @param {string} username
     * @param {string} password
     * @returns {Observable<boolean>}
     */
    login(username: string, password: string): Observable<boolean> {
        const body = `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}&grant_type=password`;

        const headers = new Headers();
        headers.append("Content-Type", "application/x-www-form-urlencoded");
        headers.append("Authorization", "Basic " + btoa(TOKEN_AUTH_USERNAME + ":" + TOKEN_AUTH_PASSWORD));

        return this.http.post(TOKEN_AUTH_URL, body, {headers})
            .map((response: Response) => {
                // login successful if there's a jwt token in the response
                let token = response.json() && response.json().access_token;
                if (token) {
                    // store username and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify({ username: username, token: token, role: response.json().role}));

                    // return true to indicate successful login
                    return true;
                } else {
                    // return false to indicate failed login
                    return false;
                }
            }).catch((error:any) => {
                return Observable.throw(error.json().error || 'Server error')
            });
    }

    /**
     * Get current user.
     *
     * @returns {String} token
     */
    getUser(): any {
        return JSON.parse(localStorage.getItem('currentUser'));
    }

    /**
     * Logout current user.
     */
    logout(): void {
        // clear token remove user from local storage to log user out
        localStorage.removeItem('currentUser');
    }
}
