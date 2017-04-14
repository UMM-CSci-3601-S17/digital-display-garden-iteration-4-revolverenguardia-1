import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from "rxjs";

@Injectable()
export class AdminService {
    private url: string = API_URL;
    constructor(private http:Http) { }

    getUploadIds(): Observable<string[]> {
        return this.http.request(this.url + "admin/uploadIds").map(res => res.json());
    }

    getLiveUploadId(): Observable<string> {
        return this.http.request(this.url + "admin/liveUploadId").map(res => res.json());
    }

    //Google Charts HTTP requests

    getViewsPerHour(): Observable<any[][]> {
        return this.http.request(this.url + "admin/charts/viewsPerHour").map(res => res.json())
    }

    getBedMetadataForMap(): Observable<any[]> {
        return this.http.request(this.url + "admin/charts/plantMetadataMap").map(res => res.json())
    }

    getBedMetadataForBubble(): Observable<any[]> {
        return this.http.request(this.url + "chart/plantMetadataBubbleMap").map(res => res.json())
    }
}
