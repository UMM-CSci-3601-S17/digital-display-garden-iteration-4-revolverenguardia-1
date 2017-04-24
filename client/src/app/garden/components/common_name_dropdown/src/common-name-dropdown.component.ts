/**
 * Interface for the common name lists for the garden.
 *
 * @author Iteration 3 - Team revolver en guardia
 */
import {Component} from "@angular/core";
import {CommonNameListService} from "./common-name-dropdown.service";
import {PlantListService} from "../../plant_list/src/plant-list.service";
import {PlantFilter} from "../../plant_list/src/plantfilter";

@Component({
    selector: 'common-name-dropdown',
    templateUrl: 'common-name-dropdown.component.html'
})
export class CommonNameListComponent {

    /**
     * TODO: Comment
     * @type {string}
     */
    private selectedCommonName: string = PlantFilter.NO_FILTER;

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

    ngOnInit(){
        this.selectedCommonName = this.plantListService.getCommonNameFilter();
        console.log("Set common name filter " + this.selectedCommonName);
    }
}