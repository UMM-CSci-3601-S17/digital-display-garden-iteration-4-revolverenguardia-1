/**
 * The CommonNameListService's primary function is to request all common names from the server.
 * These common names are then loaded into the CommonNameListComponent for viewing and interaction.
 *
 * @author Iteration 3 - Team revolver en guardia
 */
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {CommonName} from "./common-name";
import {Http} from "@angular/http";

@Injectable()
export class CommonNameListService {

    constructor(private http:Http) { }

    /**
     * Requests the list of common names from the server.
     * @returns {Observable<R>} - the common name collection from the server
     */
    getCommonNames(): Observable<CommonName[]> {
        return this.http.request(API_URL + "/commonNames").map(res => res.json());
    }
}
