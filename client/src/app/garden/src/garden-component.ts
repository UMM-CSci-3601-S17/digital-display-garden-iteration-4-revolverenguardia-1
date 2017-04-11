/**
 * A Garden Component contains the Bed List Component and Plant List Component.
 * @author Skye Antinozzi
 * @author Shawn Saliyev
 */
import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {PlantListService} from "../components/plant_list/src/plant-list.service";
import {PlantListComponent} from "../components/plant_list/src/plant-list.component";

@Component({
    selector: 'garden-component',
    templateUrl: 'garden-component.html'
})
export class GardenComponent implements OnInit {

    private bedURLParameter: string;

    private static gardenComponent: GardenComponent;

    constructor(private route: ActivatedRoute){
        GardenComponent.gardenComponent = this;
    }

    public static getInstance(): GardenComponent{
        return GardenComponent.gardenComponent;
    }

    public getBedURL(): string{
        return this.bedURLParameter;
    }

    ngOnInit(){
        this.route.params
            .map(params => params['id'])
            .subscribe(bedName => {
                this.bedURLParameter = bedName.toUpperCase();
            });
    }
}