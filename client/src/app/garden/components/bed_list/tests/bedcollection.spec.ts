/**
 * Tests the BedCollection class to ensure that it handles Bed data correctly.
 *
 * @author Iteration 2 - Team Omar Anwar
 * @editor Iteration 3 - Team Revolver en Guardia
 */
import {Bed} from "../src/bed";
import {BedCollection} from "../src/bedcollection";

describe("Test Bed Collection", () => {

    /**
     * Ensures that a BedCollection can be created properly.
     * @author Iteration 2 - Team Omar Anwar
     * @editor Iteration 3 - Team Revolver en Guardia
     */
    it("Check BedCollection Construction", () => {
        let beds: Bed[] = [];
        let bed1: Bed = new Bed("Bedname1"),
            bed2: Bed = new Bed("BedName2"),
            bed3: Bed = new Bed("BedName3"),
            bed4: Bed = new Bed("BedName4");
        beds.push(bed1);
        beds.push(bed2);
        beds.push(bed3);

        let bedCollection: BedCollection = new BedCollection(beds);

        expect(bedCollection.getBeds().length).toBe(3);

        expect(bedCollection.getBeds().indexOf(bed1)).toBeGreaterThan(-1);
        expect(bedCollection.getBeds().indexOf(bed2)).toBeGreaterThan(-1);
        expect(bedCollection.getBeds().indexOf(bed3)).toBeGreaterThan(-1);

        expect(bedCollection.getBeds().indexOf(bed4)).toBe(-1);
    });
});
