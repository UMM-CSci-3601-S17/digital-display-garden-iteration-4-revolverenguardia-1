/**
 * A Garden Component contains the Bed List Component and Plant List Component.
 * @author Iteration 2 - Team Omar Anwar
 * @editor Iteration 3 - Team Revolver en Guardia
 */
import {Component} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {PlantListService} from "../components/plant_list/src/plant-list.service";

@Component({
    selector: 'garden-component',
    templateUrl: 'garden-component.html'
})
export class GardenComponent {

    constructor(private route: ActivatedRoute, private pls: PlantListService){ }

}