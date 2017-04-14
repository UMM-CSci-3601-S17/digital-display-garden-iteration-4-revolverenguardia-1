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
import {CommonNameCollection} from "./commonnamecollection";

@Injectable()
export class CommonNameListService {

    private commonNameCollection: CommonNameCollection;

    private commonNames: CommonName[];

    constructor(private http:Http) {
        this.getCommonNamesFromServer().subscribe(
            commonNames => {
                console.log("NLS - getCommonNamesFromServer()");
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

    public getCommonNames(): CommonName[]{
        return this.commonNames;
    }
}
