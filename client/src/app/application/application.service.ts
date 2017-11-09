import {Http} from "@angular/http";
import {Injectable} from "@angular/core";

@Injectable()
export class ApplicationService {

    constructor(private http: Http) {
    }

    /**
     * Method needed for getting all app.
     */
    getAll() {
        return this.http.get("/applications");
    }

    /**
     * Method for update application.
     *
     * @param app
     * @returns {Observable<Response>}
     */
    update(app: any) {
        return this.http.put('/applications/', app);
    }

    /**
     * Method deleting application by identifier.
     *
     * @param {String} id - identifier operator.
     * @returns {Observable<Response>}
     */
    delete(id: String) {
        return this.http.delete(`/applications/${id}`);
    }

    /**
     * Method create application
     *
     * @param {String} app - app.
     * @returns {Observable<Response>}
     */
    create(app: any) {
        return this.http.post('/applications/', app);
    }
}
