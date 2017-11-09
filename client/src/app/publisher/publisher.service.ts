import {Http} from "@angular/http";
import {Injectable} from "@angular/core";

@Injectable()
export class PublishersService {

    constructor(private http: Http) {

    }

    /**
     * Method needed for getting all app.
     */
    getAll() {
        return this.http.get("/publishers")
    }

    /**
     * Method for update operator.
     *
     * @param operator
     * @returns {Observable<Response>}
     */
    update(operator: any) {
        return this.http.put('/publishers/', operator);
    }

    /**
     * Method deleting operator by identifier.
     *
     * @param {String} id - identifier operator.
     * @returns {Observable<Response>}
     */
    delete(id: String) {
        return this.http.delete(`/publishers/${id}`);
    }

    /**
     * Method adding operato.
     *
     * @param {String} operator
     * @returns {Observable<Response>}
     */
    create(operator: any) {
        return this.http.post('/publishers/', operator);
    }
}
