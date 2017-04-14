/**
 * Represents all functions and data that are contained within the PlantListComponent view
 * within the GardenComponent.
 *
 * @author Iteration 2 - Team Omar Anwar
 * @author Iteration 3 - Team Revolver en Guardia
 */
import {Component} from '@angular/core';
import {PlantListService} from "./plant-list.service";

@Component({
    selector: 'plant-list',
    templateUrl: 'plant-list.component.html'
})

export class PlantListComponent {

    constructor(private plantListService: PlantListService){ }

}
