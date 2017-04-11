import { Component, OnInit } from '@angular/core';


@Component({
    selector: 'home-component',
    templateUrl: 'home-component.html',
})

export class HomeComponent implements OnInit {
    url : String = API_URL;
    constructor() {

    }

    ngOnInit(): void {

    }
}