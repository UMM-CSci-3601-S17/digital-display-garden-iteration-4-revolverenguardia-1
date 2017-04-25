/**
 * A Plant Info Component contains the Plant Component.
 * @editor Iteration 4 - Team Revolver en Guardia++
 */
import {Component} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {PlantService} from "../components/plant_list/src/plant.service";

@Component({
    selector: 'plant-info-component',
    templateUrl: 'plant-info-component.html'
})
export class PlantInfoComponent {

    constructor(private route: ActivatedRoute, private pls: PlantService){ }

}
