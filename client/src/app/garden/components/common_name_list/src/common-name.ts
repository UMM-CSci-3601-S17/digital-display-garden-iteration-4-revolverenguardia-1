/**
 * Empty class for easy-to-read naming conventions for Common Names.
 * If this is to be removed we should update how the common names are reported from the server.
 * (Instead of _id:"1S" it should be _id:'unique_id', common_name:"Angelonia")
 *
 * @author Iteration 3 - Team revolver en guardia
 */
export class CommonName {

    constructor(public commonName: string){ }
}
