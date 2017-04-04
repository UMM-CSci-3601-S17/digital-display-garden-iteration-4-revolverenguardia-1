/**
 * The BedListService's primary function is to request all bed names from the server.
 * These bed names are then loaded into the BedListComponent for viewing and interaction.
 *
 * @author Iteration 2 - Team Omar Anwar
 */
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Bed} from "./bed";
import {Http} from "@angular/http";

@Injectable()
export class BedListService {

    constructor(private http:Http) { }

    /**
     * Requests the list of bed names (garden locations) from the server.
     * @returns {Observable<R>} - the bed name collection from the server
     */
    getBedNames(): Observable<Bed[]> {
        return this.http.request(API_URL + "/gardenLocations").map(res => res.json());
    }
}