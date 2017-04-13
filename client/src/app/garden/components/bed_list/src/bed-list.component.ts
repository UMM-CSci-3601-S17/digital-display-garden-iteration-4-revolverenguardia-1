/**
 * Provides all data and related operations for the BedListComponent. This component
 * is shared within the GardenComoponent that encapsulates both this component and the
 * PlantListComponent.
 *
 * @author Iteration 2 - Team Omar Anwar
 */
import {OnInit, Component} from "@angular/core";
import {Bed} from "./bed";
import {BedListService} from "./bed-list.service";
import {RouterModule} from "@angular/router";
import {PlantFilter} from "../../plant_list/src/plantfilter";
import {PlantListComponent} from "../../plant_list/src/plant-list.component";
import {GardenComponent} from "../../../src/garden-component";

@Component({
    selector: 'bed-list',
    templateUrl: 'bed-list.component.html'
})
export class BedListComponent implements OnInit {

    // Full list of all bed names for the BedList
    private bedNames: Bed[];

    // Current bed filter
    private bedFilter: string;

    /**
     * Title for the bed list view on the HTML Bed List Component.
     * This is used for filtering to show all plants and is linked to the PlantFilter class.
     */
    public readonly BED_LIST_HEADER: string = PlantFilter.FILTER_BY_ALL_PLANTS;

    constructor(private bedListService: BedListService) { }

    /**
     * Should filter by the provided bed name.
     * @param bedName - the bed name to filter by
     */
    private handleBedListClick(bedName): void{
        if(this.bedFilter == bedName)
            return;

        if(this.bedFilter == bedName)
            this.bedFilter = "ALL";
        else
            this.bedFilter = bedName;

        PlantListComponent.getInstance().filterByBedName(this.bedFilter);

        this.bedListService.reportBedVisit(bedName, false).subscribe();
    }

    /**
     * Returns the Beds collection
     * @returns {Bed[]} The bed collection
     */
    public getBedNames(): Bed[]{
        return this.bedNames;
    }

    ngOnInit(): void {
        this.bedListService.getBedNames().subscribe(
            bedNames => this.bedNames = bedNames,
            err => {
                console.log(err);
            }
        );

        this.bedFilter = GardenComponent.getInstance().getBedURLParameter();
    }
}