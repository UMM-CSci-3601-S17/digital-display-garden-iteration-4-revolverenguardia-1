import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {Http} from "@angular/http";


@Component({
    selector: 'admin-component',
    templateUrl: 'admin-component.html',
})

export class AdminComponent implements OnInit {
    private readonly URL: string = API_URL + "admin";
    authorized : Boolean = false;

    constructor(private http:Http) { }

    //Request authorization from the server in order to view admin buttons
    isAuthorized(): Observable<Boolean> {
        return this.http.get(this.URL).map(res => res.json());
    }

    ngOnInit(): void {
        this.isAuthorized().subscribe((auth => {this.authorized = auth;
        console.log("auth=" + this.authorized);}));
    }
}