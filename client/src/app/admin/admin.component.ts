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


    ngOnInit(): void {
        window.location.href = API_URL + "admin";
    }
}