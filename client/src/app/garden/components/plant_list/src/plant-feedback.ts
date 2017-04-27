/**
 * Represents number of comments, likes and dislikes that visitors have
 * left on the Plant that stores this PlantFeedback object.
 */

export class PlantFeedback{


    /**
     * Number of comments left on the plant.
     */
    public commentCount:number;

    /**
     * Number of likes left on the plant.
     */
    public likeCount:number;

    /**
     * Number of dislikes left on the plant.
     */
    public dislikeCount:number;

    /**
     * Default constructor provides all values initialized to zero.
     * When loaded from a server, a PlantFeedback object has its
     * values initialized to values stored on the server for a specific
     * plant.
     */
    constructor(){
        this.commentCount = 0;
        this.likeCount = 0;
        this.dislikeCount = 0;
    }
}