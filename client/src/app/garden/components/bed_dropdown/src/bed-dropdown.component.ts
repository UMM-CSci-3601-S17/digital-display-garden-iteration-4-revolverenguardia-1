/**
 * Provides all data and related operations for the BedListComponent. This component
 * is shared within the GardenComoponent that encapsulates both this component and the
 * PlantListComponent.
 *
 * @author Iteration 2 - Team Omar Anwar
 */
import {Component, OnInit} from "@angular/core";
import {BedDropdownService} from "./bed-dropdown.service";
import {PlantListService} from "../../plant_list/src/plant-list.service";
import {PlantFilter} from "../../plant_list/src/plantfilter";
import {CommonNameDropdownService} from "../../common_name_dropdown/src/common-name-dropdown.service";

@Component({
    selector: 'bed-dropdown',
    templateUrl: 'bed-dropdown.html'
})
export class BedDropdownComponent implements OnInit{

    private selectedBed: string = PlantFilter.NO_FILTER;

    constructor(private bedListService: BedDropdownService,
                private plantListService: PlantListService,
                private commonNameListService: CommonNameDropdownService) { }

    /**
     * Filters by the provided bed name.
     * @param bedName - the bed name to filter by
     */
    private handleBedSelect(bedName): void{

        // Filter plant list
        this.plantListService.setBedFilter(bedName);

        // Common name drop only have common names within current bed
        this.commonNameListService.updateCommonNamesDropdown(this.plantListService.getPlants(), bedName);

        this.bedListService.reportBedVisit(bedName, false).subscribe();
    }

    /**
     * Ensures that the filter state persists when this component
     * is loaded and re-loaded.
     */
    ngOnInit(){
        this.selectedBed = this.plantListService.getBedFilter();
    }

}