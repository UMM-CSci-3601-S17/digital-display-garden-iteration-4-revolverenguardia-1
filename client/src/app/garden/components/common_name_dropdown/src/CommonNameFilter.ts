/**
 * TODO
 * Will filter any given collection of plants based on the provided methods.
 * Used for future reuse and extensibility of filtering support.
 *
 * @author Iteration 2 - Team Omar Anwar
 * @editor Iteration 3 - Revolver en Guardia
 */

import {CommonName} from "./common-name";
import {Plant} from "../../plant_list/src/plant";

export class CommonNameFilter {

    /**
     * Denotes that no filter should be applied
     */
    public static readonly NO_FILTER: string = "ALL";

    public static filterByBed(plants: Plant[], bed: string, commonNames: CommonName[]): CommonName[]{

        if(bed == CommonNameFilter.NO_FILTER)
            return commonNames;

        let filteredCommonNames: string[] = [];
        let constructedCommonNames: CommonName[] = [];

        console.log("Received " + plants.length + " Plants");

        plants.forEach((plant, index) => {
            if(plant.gardenLocation == bed)
                if(filteredCommonNames.indexOf(plant.commonName) < 0)
                    filteredCommonNames.push(plant.commonName);
        });

        filteredCommonNames.forEach((commonName, index) => {
            constructedCommonNames.push(new CommonName(commonName));
        });

        return constructedCommonNames;
    }
}
