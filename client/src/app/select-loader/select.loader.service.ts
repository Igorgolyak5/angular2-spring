import {Http} from "@angular/http";
import {Injectable} from "@angular/core";

@Injectable()
export class SelectLoaderService {

    constructor(private http: Http) {

    }

    /**
     * Method needed for getting all app.
     */
    getAll(url: string) {
        return this.http.get(url);
    }
}
