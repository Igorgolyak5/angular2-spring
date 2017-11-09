import {CustomHttp} from "../_services/custom.http";
import {Http, RequestOptions, XHRBackend} from "@angular/http";
import {NotificationService} from "../_services/notification.service";
import {Router} from "@angular/router";

/**
 * Factory creating http interceptor for interceptor handling http request.
 *
 * @param {XHRBackend} xhrBackend
 * @param {RequestOptions} requestOptions
 * @param {NotificationService} notificationService
 * @param {Router} router
 * @returns {Http}
 */
export function httpFactory(xhrBackend: XHRBackend, requestOptions: RequestOptions, notificationService: NotificationService, router: Router): Http {
    return new CustomHttp(xhrBackend, requestOptions, notificationService, router);
}
