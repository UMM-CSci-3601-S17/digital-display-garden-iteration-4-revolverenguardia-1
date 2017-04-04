import {Plant} from "../src/plant";
import {PlantCollection} from "../src/plantcollection";
describe("Plant list", () => {

    it("Check Proper Construction", () => {
        let plants: Plant[] = [];
        plants.push(new Plant("PlantID1", "CommonName1", "Cultivar1", "Source1", "GardenLocation1"));
        plants.push(new Plant("PlantID2", "CommonName2", "Cultivar2", "Source2", "GardenLocation2"));
        plants.push(new Plant("PlantID3", "CommonName3", "Cultivar3", "Source3", "GardenLocation3"));

        let plantCollection: PlantCollection = new PlantCollection(plants);

        expect(plantCollection.getPlants().length).toBe(3);
    });
});
