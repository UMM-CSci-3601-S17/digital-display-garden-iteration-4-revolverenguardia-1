/**
 * TODO: Comment Fix
 * Represents a collection of plants.
 * Used for future reuse and extensibility.
 *
 * @author Iteration 2 - Team Omar Anwar
 */
import {Bed} from "./bed";

export class BedCollection {

    constructor(private beds: Bed[]){ }

    /**
     * TODO: Comment Fix
     * Returns the stored plant collection
     * @returns {Plant[]} - the plant collection
     */
    public getBeds(): Bed[]{
        return this.beds;
    }
}