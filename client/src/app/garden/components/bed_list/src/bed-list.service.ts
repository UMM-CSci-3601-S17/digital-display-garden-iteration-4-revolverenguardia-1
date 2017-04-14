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
import {BedCollection} from "./bedcollection";

@Injectable()
export class BedListService {

    private bedCollection: BedCollection;

    private beds: Bed[];

    constructor(private http:Http) {
        this.getBedNamesFromServer().subscribe(
            bedNames => {
                console.log("BLS - getBedNamesFromServer()")
                this.bedCollection = new BedCollection(bedNames);
                this.beds = this.bedCollection.getBeds();
            },
            err => {
                console.log(err);
            }
        );
    }

    /**
     * Requests the list of bed names (garden locations) from the server.
     * @returns {Observable<R>} - the bed name collection from the server
     */
    private getBedNamesFromServer(): Observable<Bed[]> {
        return this.http.request(API_URL + "/gardenLocations").map(res => res.json());
    }

    /**
     * TODO: Update Comment
     * @returns {Bed[]}
     */
    public getBedNames(): Bed[]{
        return this.beds;
    }
}