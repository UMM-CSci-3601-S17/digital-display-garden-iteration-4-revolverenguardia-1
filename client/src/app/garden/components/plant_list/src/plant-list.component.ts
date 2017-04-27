/**
 * Represents all functions and data that are contained within the PlantListComponent view
 * within the GardenComponent.
 *
 * @author Iteration 2 - Team Omar Anwar
 * @author Iteration 3 - Team Revolver en Guardia
 */
import {Component, OnInit} from '@angular/core';
import {PlantListService} from "./plant-list.service";
import {ActivatedRoute} from "@angular/router";
import {BedDropdownComponent} from "../../bed_dropdown/src/bed-dropdown.component";
import {BedDropdownService} from "../../bed_dropdown/src/bed-dropdown.service";
import {GardenComponent} from "../../../src/garden-component";
import {Location} from '@angular/common';

@Component({
    selector: 'plant-list',
    templateUrl: 'plant-list.component.html'
})

export class PlantListComponent implements OnInit {

    constructor(private plantListService: PlantListService, private bedListService : BedDropdownService, private route: ActivatedRoute, private location: Location){ }

    ngOnInit(){
        //Send reportBedVisit Post request
        this.route.queryParams
            .map(queryParams => queryParams['qr'])
            .subscribe(isQr => {
                this.route.params.subscribe(params => {
                    var bedName = params['id'];

                    //Send post request to server reporting a Bed Visit
                    this.bedListService.reportBedVisit(bedName, isQr).subscribe();

                    //This hides the qr=true query param from the user (and removes it from browser history)
                    this.location.replaceState("/bed/" + bedName);

                });
                err => {
                    console.log(err);
                }
            });
            err => {
                console.log(err);
            }
    }
}
