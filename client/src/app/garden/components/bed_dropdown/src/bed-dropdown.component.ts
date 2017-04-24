/**
 * Provides all data and related operations for the BedListComponent. This component
 * is shared within the GardenComoponent that encapsulates both this component and the
 * PlantListComponent.
 *
 * @author Iteration 2 - Team Omar Anwar
 */
import {Component, OnInit} from "@angular/core";
import {BedListService} from "./bed-dropdown.service";
import {PlantListService} from "../../plant_list/src/plant-list.service";
import {PlantFilter} from "../../plant_list/src/plantfilter";
import {CommonNameListService} from "../../common_name_dropdown/src/common-name-dropdown.service";

@Component({
    selector: 'bed-dropdown',
    templateUrl: 'bed-dropdown.html'
})
export class BedListComponent implements OnInit{

    private selectedBed: string = PlantFilter.NO_FILTER;

    constructor(private bedListService: BedListService,
                private plantListService: PlantListService,
                private commonNameListService: CommonNameListService) { }

    /**
     * Filters by the provided bed name.
     * @param bedName - the bed name to filter by
     */
    private handleBedSelect(bedName): void{

        console.log("Handle bed select " + bedName);

        // If bed name is being deselected
        if(bedName == this.plantListService.getBedFilter())
            // Then disable the filter
            this.plantListService.setBedFilter(PlantFilter.NO_FILTER);

        // Else, bed name is being selected
        else
            // So disable the filter
            this.plantListService.setBedFilter(bedName);

        // TODO
        this.commonNameListService.updateCommonNamesDropdown(this.plantListService.getPlants(), bedName);
    }

    /**
     * TODO: Comment
     */
    ngOnInit(){
        this.selectedBed = this.plantListService.getBedFilter();
        console.log("Set bed filter " + this.selectedBed);
    }

}