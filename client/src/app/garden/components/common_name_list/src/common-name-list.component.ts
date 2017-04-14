/**
 * Provides all data and related operations for the CommonNameListComponent. This component
 * is shared within the GardenComoponent that encapsulates both this component and the
 * PlantListComponent.
 *
 * @author Iteration 3 - Team revolver en guardia
 */
import {Component} from "@angular/core";
import {CommonNameListService} from "./common-name-list.service";
import {PlantListService} from "../../plant_list/src/plant-list.service";
import {PlantFilter} from "../../plant_list/src/plantfilter";

@Component({
    selector: 'common-name-list',
    templateUrl: 'common-name-list.component.html'
})
export class CommonNameListComponent {

    constructor(private commonNameListService: CommonNameListService,
                private plantListService: PlantListService) { }

    /**
     * Should filter by the provided common name.
     * @param commonName - the common nameconsole.log("Finish handle common name list click"); to filter by
     */
    private handleCommonNameListClick(commonName): void{
        // If bed name is being deselected
        if(commonName == this.plantListService.getCommonNameFilter()) {
            console.log("CommonName List Deselect - " + commonName);
            // Then disable the filter
            this.plantListService.setCommonNameFilter(PlantFilter.NO_FILTER);
        }
        // Else, bed name is being selected
        else {
            console.log("CommonName List Select - " + commonName);
            this.plantListService.setCommonNameFilter(commonName);
        }
    }
}