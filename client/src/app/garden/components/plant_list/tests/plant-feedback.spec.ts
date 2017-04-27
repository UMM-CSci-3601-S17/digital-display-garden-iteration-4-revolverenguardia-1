/**
 * Tests the PlantFeedback class to ensure that it stores feedback data correctly.
 *
 * @author Iteration 4 - Team Revolver en Guardia++
 */
import {PlantFeedback} from "../src/plant-feedback";

describe("Test PlantFeedback Class", () => {

    /**
     * Tests proper construction of PlantFeedback objects.
     * @author Iteration 4 - Team Revolver en Guardia++
     */
    it("Check Proper Construction", () => {
        let feedback: PlantFeedback = new PlantFeedback();

        expect(feedback.likeCount).toBe(0);
        expect(feedback.dislikeCount).toBe(0);
        expect(feedback.commentCount).toBe(0);
    });
});
