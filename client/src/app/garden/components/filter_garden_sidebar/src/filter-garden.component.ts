/**
 * The FilterGardenSidebarComponent is anchored on the left side of the screen
 * in a vertical orientation that provides an interface to filter the PlantListComponent.
 * @author Iteration 3 - Team Revolver en Guardia
 * @editor Iteration 4 - Team Revolver en Guardia++
 */
import { Component } from '@angular/core';
import {BedListService} from "../../bed_list/src/bed-list.service";
import {CommonNameListService} from "../../common_name_list/src/common-name-list.service";
import {Bed} from "../../bed_list/src/bed";

@Component({
    selector: 'filter-garden-component',
    templateUrl: 'filter-garden.component.html',
})

export class FilterGardenComponent {

    constructor(private bedListService: BedListService,
                private commonNameListService: CommonNameListService){ }
}

