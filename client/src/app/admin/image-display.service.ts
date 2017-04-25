/**
 * Created by holma198 on 4/24/17.
 */
import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {Observable} from "rxjs";

@Injectable()
export class ImageDisplayService {

    private plantName: string;

    constructor(private http:Http) {

    }

    public getImageFromServer(): Observable<File> {
        return this.http.request(API_URL + "getImage" + "/:" + this.plantName).map(res => res.json());
    }

}