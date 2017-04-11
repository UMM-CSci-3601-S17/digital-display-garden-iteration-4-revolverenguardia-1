/**
 * Provides all data and related operations for the CommonNameListComponent. This component
 * is shared within the GardenComoponent that encapsulates both this component and the
 * PlantListComponent.
 *
 * @author Iteration 3 - Team revolver en guardia
 */
import {OnInit, Component} from "@angular/core";
import {CommonName} from "./common-name";
import {CommonNameListService} from "./common-name-list.service";
import {PlantFilter} from "../../plant_list/src/plantfilter";
import {PlantListComponent} from "../../plant_list/src/plant-list.component";

@Component({
    selector: 'common-name-list',
    templateUrl: 'common-name-list.component.html'
})
export class CommonNameListComponent implements OnInit {

    // Full list of all common names for the CommonNameList
    private commonNames: CommonName[];

    /**
     * Title for the common name list view on the HTML Common Name List Component.
     * This is used for filtering to show all plants and is linked to the PlantFilter class.
     */
    public readonly COMMON_NAME_LIST_HEADER: string = PlantFilter.FILTER_BY_ALL_PLANTS;

    constructor(private commonNameListService: CommonNameListService) { }

    /**
     * Should filter by the provided common name.
     * @param commonName - the common name to filter by
     */
    private handleCommonNameListClick(commonName): void{
        PlantListComponent.getInstance().filterByCommonName(commonName);
    }

    /**
     * Returns the CommonNames collection
     * @returns {CommonName[]} The common name collection
     */
    public getCommonNames(): CommonName[]{
        return this.commonNames;
    }

    ngOnInit(): void {
        this.commonNameListService.getCommonNames().subscribe(
            commonNames => this.commonNames = commonNames,
            err => {
                console.log(err);
            }
        );
    }
}