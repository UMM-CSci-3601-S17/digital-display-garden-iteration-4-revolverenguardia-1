/**
 * Interface for the common name lists for the garden.
 *
 * @author Iteration 3 - Team revolver en guardia
 */
import {Component} from "@angular/core";
import {CommonNameDropdownService} from "./common-name-dropdown.service";
import {PlantListService} from "../../plant_list/src/plant-list.service";
import {PlantFilter} from "../../plant_list/src/plantfilter";

@Component({
    selector: 'common-name-dropdown',
    templateUrl: 'common-name-dropdown.component.html'
})
export class CommonNameDropdownComponent {

    /**
     * TODO: Comment
     * @type {string}
     */
    private selectedCommonName: string = PlantFilter.NO_FILTER;

    constructor(private commonNameListService: CommonNameDropdownService,
                private plantListService: PlantListService) {
    }

    /**
     * Requests that the PlantListComponent is filtered by CommonName upon
     * a click event for a list item.
     * @param commonName - the common name to filter by
     */
    private handleCommonNameSelect(commonName): void {
        this.plantListService.setCommonNameFilter(commonName);
    }

    /**
     * Ensures that the filter state persists when this component
     * is loaded and re-loaded.
     */
    ngOnInit(){
        this.selectedCommonName = this.plantListService.getCommonNameFilter();
    }
}