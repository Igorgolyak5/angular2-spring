import {Injectable, ViewContainerRef} from "@angular/core";
import {ToastsManager} from "ng2-toastr";

/**
 * Notification service for notification user about changes.
 *
 * @author Igor Holiak
 */
@Injectable()
export class NotificationService {

    constructor(private toastr: ToastsManager) {
    }

    /**
     * Method needed for notification about success result of action.
     *
     * @param {String} message
     */
    success(message: string) {
        this.toastr.success(message);
    }

    /**
     * Method needed for notification about warming result of action.
     *
     * @param {String} message
     */
    warming(message: string) {
        this.toastr.warning(message);
    }

    /**
     * Method needed for notification about error result of action.
     *
     * @param {String} message
     */
    error(message: string) {
        this.toastr.error(message);
    }
}
