/**
 * Filters Common Names from a specific Bed. Currently, this class
 * only supports the retrieval of all Common Names from a provided bed.
 * Used for future reuse and extensibility of filtering support.
 *
 * @author Iteration 4 - Team Revolver en Guardia++
 */

import {CommonName} from "./common-name";
import {Plant} from "../../plant_list/src/plant";
import {Bed} from "../../bed_dropdown/src/bed";

export class CommonNameFilter {

    /**
     * Denotes that no filter should be applied
     */
    public static readonly NO_FILTER: string = "ALL";

    /**
     * Filters all Common Names by Bed. Only Common Names contained within the
     * provided Bed are returned.
     * @param plants - the entire Plant collection for the garden
     * @param commonNames - the entire CommonName collection for the garden
     * @param bed - the Bed to get Common Names from
     * @returns {CommonName[]} - Common Names within the provided Bed
     */
    public static filterByBed(plants: Plant[], commonNames: CommonName[], bed: Bed): CommonName[]{

        // If NO_Filter then don't filter
        if(bed._id == CommonNameFilter.NO_FILTER)
            return commonNames;

        // Common names being filtered
        let filteredCommonNames: string[] = [];

        // Common Name Strings to Common Name Objects
        let commonNamesInBed: CommonName[] = [];

        // Get all common name strings in the current bed
        plants.forEach((plant, index) => {
            if(plant.gardenLocation == bed._id)
                if(filteredCommonNames.indexOf(plant.commonName) < 0)
                    filteredCommonNames.push(plant.commonName);
        });

        // Construct common name objects from strings
        filteredCommonNames.forEach((commonName, index) => {
            commonNamesInBed.push(new CommonName(commonName));
        });

        return commonNamesInBed;
    }
}
