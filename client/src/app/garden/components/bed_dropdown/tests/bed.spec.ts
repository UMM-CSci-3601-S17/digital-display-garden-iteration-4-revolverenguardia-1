/**
 * Tests the Bed class to ensure that it stores bed data correctly.
 *
 * @author Iteration 3 - Team Revolver en Guardia
 */
import {Bed} from "../src/bed";

describe("Test Bed Class", () => {

    /**
     * Tests prpoer construction of Bed objects.
     * @author Iteration 3 - Team Revolver en Guardia
     */
    it("Check Proper Construction", () => {
        let bed1: Bed = new Bed("BedID1"),
            bed2: Bed = new Bed("BedID2"),
            bed3: Bed = new Bed("BedID3");

        expect(bed1._id).toBe("BedID1");
        expect(bed2._id).toBe("BedID2");
        expect(bed3._id).toBe("BedID3");

    });
});
