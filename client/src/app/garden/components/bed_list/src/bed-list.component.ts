/**
 * Provides all data and related operations for the BedListComponent. This component
 * is shared within the GardenComoponent that encapsulates both this component and the
 * PlantListComponent.
 *
 * @author Iteration 2 - Team Omar Anwar
 */
import {Component} from "@angular/core";
import {BedListService} from "./bed-list.service";
import {PlantListService} from "../../plant_list/src/plant-list.service";
import {PlantFilter} from "../../plant_list/src/plantfilter";

@Component({
    selector: 'bed-list',
    templateUrl: 'bed-list.component.html'
})
export class BedListComponent {

    constructor(private bedListService: BedListService,
                private plantListService: PlantListService) { }

    /**
     * Filters by the provided bed name.
     * @param bedName - the bed name to filter by
     */
    private handleBedListClick(bedName): void{

        // If bed name is being deselected
        if(bedName == this.plantListService.getBedFilter())
            // Then disable the filter
            this.plantListService.setBedFilter(PlantFilter.NO_FILTER);

        // Else, bed name is being selected
        else
            // So disable the filter
            this.plantListService.setBedFilter(bedName);

    }

}