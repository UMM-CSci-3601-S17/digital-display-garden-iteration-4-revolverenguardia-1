/**
 * A "dummy" component that allows for the GardenComponent to be reloaded,
 * or refreshed. To achieve this, this component can be routed to which
 * will, in turn, route back to the GardenComponent causing the component
 * to refresh.
 *
 * @author Clean Up Iteration - Team Revolver en Guardia++
 */
import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {PlantListService} from "../garden/components/plant_list/src/plant-list.service";

@Component({
    selector: 'reload-garden',
    template: ''
})
export class ReloadGardenComponent {

    constructor(private router: Router,
                private plantListService: PlantListService) {

        // Clear any filters
        plantListService.clearFilters();

        // Route to a fresh GardenComponent
        router.navigate(['/']);
    }
}
