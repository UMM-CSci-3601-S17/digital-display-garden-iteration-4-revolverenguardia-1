/**
 * TODO: Comment Fix
 * Represents a collection of plants.
 * Used for future reuse and extensibility.
 *
 * @author Iteration 2 - Team Omar Anwar
 */
import {CommonName} from "./common-name";

export class CommonNameCollection {

    constructor(private commonNames: CommonName[]){ }

    /**
     * TODO: Comment Fix
     * Returns the stored plant collection
     * @returns {Plant[]} - the plant collection
     */
    public getCommonNames(): CommonName[]{
        return this.commonNames;
    }
}