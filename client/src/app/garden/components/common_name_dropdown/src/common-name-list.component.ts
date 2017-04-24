/**
 * Interface for the common name lists for the garden.
 *
 * @author Iteration 3 - Team revolver en guardia
 */
import {Component} from "@angular/core";
import {CommonNameListService} from "./common-name-list.service";
import {PlantListService} from "../../plant_list/src/plant-list.service";
import {PlantFilter} from "../../plant_list/src/plantfilter";

@Component({
    selector: 'common-name-dropdown',
    templateUrl: 'common-name-list.component.html'
})
export class CommonNameListComponent {

    constructor(private commonNameListService: CommonNameListService,
                private plantListService: PlantListService) {
    }

    /**
     * Requests that the PlantListComponent is filtered by CommonName upon
     * a click event for a list item.
     * @param commonName - the common name to filter by
     */
    private handleCommonNameSelect(commonName): void {

        console.log(commonName);

        // If bed name is being deselected
        if (commonName == this.plantListService.getCommonNameFilter())
            // Then disable the filter
            this.plantListService.setCommonNameFilter(PlantFilter.NO_FILTER);

        // Else, bed name is being selected
        else
            // So enable the filter
            this.plantListService.setCommonNameFilter(commonName);

    }
}