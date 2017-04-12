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
import {ActivatedRoute} from "@angular/router";

@Injectable()
export class BedListService {

    constructor(private http:Http, private route: ActivatedRoute) { }

    /**
     * Requests the list of bed names (garden locations) from the server.
     * @returns {Observable<R>} - the bed name collection from the server
     */
    getBedNames(): Observable<Bed[]> {
        return this.http.request(API_URL + "/gardenLocations").map(res => res.json());
    }

    reportBedVisit(gardenLocation : string): Observable<boolean> {
        console.log("GOT HERE " + gardenLocation);
        console.log(API_URL + "qrVisit");
        console.log(this.route.snapshot.params["qr"])

        if(this.route.snapshot.params["qr"]) {
            console.log("OJOJOJOJ");
            return this.http.post(API_URL + "qrVisit", gardenLocation).map(res => res.json());
        } else {
            console.log("OKOKOKOK");
            return this.http.post(API_URL + "bedVisit", gardenLocation).map(res => res.json());
        }
    }


}