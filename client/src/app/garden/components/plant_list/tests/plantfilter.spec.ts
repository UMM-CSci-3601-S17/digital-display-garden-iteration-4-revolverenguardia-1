import {PlantFilter} from "../src/plantfilter";
import {Plant} from "../src/plant";
describe("Plant list", () => {

    it("Check Bed List Header", () => {
        expect(PlantFilter.NO_FILTER).toBe("ALL");
    });

    it("Filter by Bed Name", () => {
        let plants: Plant[] = [];
        plants.push(new Plant("PlantID1", "CommonName1", "Cultivar1", "Source1", "BedName1"));
        plants.push(new Plant("PlantID2", "CommonName2", "Cultivar2", "Source2", "BedName1"));
        plants.push(new Plant("PlantID3", "CommonName3", "Cultivar3", "Source3", "BedName2"));

        let filteredPlants: Plant[] = PlantFilter.filterByBedName("BedName1", plants);
        expect(filteredPlants.length).toBe(2);

        filteredPlants = PlantFilter.filterByBedName("BedName2", plants);
        expect(filteredPlants.length).toBe(1);
    });

});
