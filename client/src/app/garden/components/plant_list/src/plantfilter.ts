/**
 * Will filter any given collection of plants based on the provided methods.
 * Used for future reuse and extensibility.
 *
 * @author Iteration 2 - Team Omar Anwar
 */
import {Plant} from "./plant";

export class PlantFilter {

    /**
     * Title for the bed list view on the HTML Bed List Component.
     * This is used for filtering to show all plants.
     */
    public static readonly FILTER_BY_ALL_PLANTS = "ALL";

    /**
     * Filters the provided plant collection by the provided bed name.
     * @param bedName - the bed name to filter by
     * @param plants - the plants array to filter
     * @returns {Plant[]} - the filtered plant array
     */
    public static filterByBedName(bedName: string, plants: Plant[]): Plant[]{

        let filteredPlants: Plant[] = [];

        plants.forEach((plant, index) => {
            if (plant.gardenLocation == bedName) {
                filteredPlants.push(plant);
            }
        });

        return filteredPlants;
    }

    /**
     * Filters the provided plant collection by the provided common name.
     * @param commonName - the common name to filter by
     * @param plants - the plants array to filter
     * @returns {Plant[]} - the filtered plant array
     */
    public static filterByCommonName(commonName: string, plants: Plant[]): Plant[]{

        // Don't filter
        if(commonName === "NO_FILTER")
            return plants;

        // Filter
        let filteredPlants: Plant[] = [];

        plants.forEach((plant, index) => {
            if (plant.commonName == commonName)
                filteredPlants.push(plant);
        });

        return filteredPlants;
    }
}