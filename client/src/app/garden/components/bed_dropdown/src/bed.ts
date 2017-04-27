/**
 * Empty class for easy-to-read naming conventions for Bed Names.
 * If this is to be removed we should update how the bed names are reported from the server.
 * (Instead of _id:"1S" it should be _id:'unique_id', bed_name:"1s")
 *
 * @author Iteration 2 - Team Omar Anwar
 */
export class Bed {

    constructor(public _id: string){ }
}

