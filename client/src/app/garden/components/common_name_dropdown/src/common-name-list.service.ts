/**
 * The CommonNameListService requests all common names from the server.
 * These common names are then loaded into the CommonNameListComponent for viewing and interaction.
 *
 * @author Iteration 3 - Team revolver en guardia
 */
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {CommonName} from "./common-name";
import {Http} from "@angular/http";
import {CommonNameCollection} from "./commonnamecollection";

@Injectable()
export class CommonNameListService {

    /**
     * Master collection of common names
     */
    private commonNameCollection: CommonNameCollection;

    /**
     * Collection of common names shown in CommonNameListComponent
     */
    public commonNames: CommonName[];

    constructor(private http:Http) {
        this.getCommonNamesFromServer().subscribe(
            commonNames => {
                this.commonNameCollection = new CommonNameCollection(commonNames);
                this.commonNames = this.commonNameCollection.getCommonNames();
            },
            err => {
                console.log(err);
            }
        );
    }

    /**
     * Requests the list of common names from the server.
     * @returns {Observable<R>} - the common name collection from the server
     */
    getCommonNamesFromServer(): Observable<CommonName[]> {
        return this.http.request(API_URL + "/commonNames").map(res => res.json());
    }

    /**
     * Gets the list of CommonNames shown within CommonNameListComponent
     * @returns {CommonName[]}
     */
    public getCommonNames(): CommonName[]{
        return this.commonNames;
    }
}
