import {Injectable} from "@angular/core";
import {ConnectionBackend, Headers, Http, Request, RequestOptions, RequestOptionsArgs, Response} from "@angular/http";
import {Observable} from "rxjs/Rx";
import {NotificationService} from "./notification.service";
import {Router} from "@angular/router";
import {TOKEN_AUTH_URL} from "./auth.constant";

/**
 * Interceptor http for adding custom logic to request.
 *
 * @author Igor Holiak
 */
@Injectable()
export class CustomHttp extends Http {

    constructor(backend: ConnectionBackend, defaultOptions: RequestOptions, private notificationService: NotificationService, private router: Router) {
        super(backend, defaultOptions);
    }

    request(url: Request, options?: RequestOptionsArgs): Observable<Response> {
        return super.request(url, options).catch((e) => {
            if (url.url.indexOf(TOKEN_AUTH_URL) < 0) {
                return this.processError(e)
            }
        });
    }

    /**
     * Method for processing error.
     *
     * @param error
     * @returns {Observable<any>}
     */
    processError(error: any): Observable<any> {
        if (error.status === 500) {
            this.notificationService.error("System error. Please turn to administrator of system.");
        }
        if (error.status === 400) {
            this.notificationService.warming(error.text());
        }
        if (error.status === 401) {
            this.router.navigate(['login']);
        }
        return Observable.throw(error);
    }

    /**
     * Method for executing get request.
     * @param {string} url
     * @param {RequestOptionsArgs} options
     * @returns {Observable<Response>}
     */
    get(url: string, options?: RequestOptionsArgs): Observable<Response> {
        url = this.updateUrl(url);
        return super.get(url, this.getRequestOptionArgs(options));
    }

    /**
     * Method for executing post request.
     *
     * @param {string} url of request
     * @param {string} body of request
     * @param {RequestOptionsArgs} options of request
     * @returns {Observable<Response>} response from back-end.
     */
    post(url: string, body: string, options?: RequestOptionsArgs): Observable<Response> {
        url = this.updateUrl(url);
        return super.post(url, body, this.getRequestOptionArgs(options));
    }

    /**
     * Method for executing method put request.
     *
     * @param {string} url of request
     * @param {string} body of request
     * @param {RequestOptionsArgs} options of request
     * @returns {Observable<Response>} response from back-end.
     */
    put(url: string, body: string, options?: RequestOptionsArgs): Observable<Response> {
        url = this.updateUrl(url);
        return super.put(url, body, this.getRequestOptionArgs(options));
    }

    /**
     * Method for executing method delete request.
     *
     * @param {string} url of request
     * @param {RequestOptionsArgs} options of request
     * @returns {Observable<Response>} response from back-end.
     */
    delete(url: string, options?: RequestOptionsArgs): Observable<Response> {
        url = this.updateUrl(url);
        return super.delete(url, this.getRequestOptionArgs(options));
    }

    /**
     * Method for added real back-end url to relative path.
     *
     * @param {string} req relative path
     * @returns {string} full url
     */
    private updateUrl(req: string) {
        return  environment.origin + req;
    }

    /**
     * Method needed for add custom header to request.
     *
     * @param {RequestOptionsArgs} options of request
     * @returns {RequestOptionsArgs} full option with custom logic
     */
    private getRequestOptionArgs(options?: RequestOptionsArgs) : RequestOptionsArgs {
        if (options == null) {
            options = new RequestOptions();
        }
        if (options.headers == null) {
            options.headers = new Headers();
        }
        let tokenObj = JSON.parse(localStorage.getItem("currentUser"));
        if (tokenObj) {
            options.headers.append('Authorization', 'Bearer ' + tokenObj.token);
        }

        return options;
    }
}

/**
 * Constant is real url to back-end.
 *
 * @type {{origin: string}}
 */
export const environment = {
    origin: 'http://localhost:9090'
};
