/**
 * A Garden Component contains the Bed List Component and Plant List Component.
 * @author Iteration 2 - Team Omar Anwar
 * @editor Iteration 3 - Team Revolver en Guardia
 */
import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {PlantListService} from "../components/plant_list/src/plant-list.service";

@Component({
    selector: 'garden-component',
    templateUrl: 'garden-component.html'
})
export class GardenComponent implements OnInit {

    // The parameter provided for the bed name at the end of the URL (bed/1n, bed/5, etc.)
    private bedNameURLParameter: string;

    // Static factory class instance variable
    private static gardenComponent: GardenComponent;


    constructor(private route: ActivatedRoute, private pls: PlantListService){
        GardenComponent.gardenComponent = this;
    }

    /**
     * Static factory method to return the currently instantiated GardenComponent.
     * @returns {GardenComponent} - the current GardenComponent
     */
    public static getInstance(): GardenComponent{
        return GardenComponent.gardenComponent;
    }

    /**
     * Returns the current bed parameter. For instance, 1n in bed/1n or 5 in bed/5.
     * @returns {string} - the bed name parameter
     */
    public getBedURLParameter(): string{
        return this.bedNameURLParameter;
    }

    ngOnInit(){
        this.route.params
            .map(params => params['id'])
            .subscribe(bedName => {
                // Store the bed name parameter to be used for filtering
                this.bedNameURLParameter = bedName.toUpperCase();
            });
    }
}