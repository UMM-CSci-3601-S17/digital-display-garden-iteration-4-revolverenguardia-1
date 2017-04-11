/**
 * A Garden Component contains the Bed List Component and Plant List Component.
 * @author Skye Antinozzi
 * @author Shawn Saliyev
 */
import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {PlantListService} from "../plant_list/src/plant-list.service";
import {PlantListComponent} from "../plant_list/src/plant-list.component";

@Component({
    selector: 'garden-component',
    templateUrl: 'garden-component.html'
})
export class GardenComponent implements OnInit {

    constructor(private route: ActivatedRoute){

    }

    ngOnInit(){
        let bedName: string = this.route.snapshot.params['id'];
        PlantListComponent.getInstance().filterByBedName(bedName);
    }
}