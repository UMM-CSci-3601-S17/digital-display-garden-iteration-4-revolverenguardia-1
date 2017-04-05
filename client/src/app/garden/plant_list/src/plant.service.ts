/**
 * Provides the ability to request Plant data from a server and to also post metadata such as likes, dislikes
 * and comments for specific plants to said server.
 *
 * @author Iteration 1 - Team Rayquaza
 * @editor Iteration 2 - Team Omar Anwar
 */

//Imports
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Plant } from './plant';
import { Observable } from "rxjs";

@Injectable()
export class PlantService {

    private readonly URL: string = API_URL + "plant/";

    constructor(private http:Http) { }

    /**
     * Request that the database send over the plant specified by the provided id.
     * @param id - the id of the requested plant
     * @returns {Observable<Plant>} - upon successful respons from the server, an observable of the returned plant
     */
    getPlantById(id: string): Observable<Plant> {
        return this.http.request(this.URL + id).map(res => res.json());
    }

    /**
     * Rates the plant specified by the provided id with the provided rating.
     * @param id - the id of the plant to be rated
     * @param rating - the rating to rate the plant with
     * @returns {Observable<Boolean>} - true if the plant was successfully rated
     *                                - false if the plant rating failed
     */
    ratePlant(id: string, rating: boolean): Observable<Boolean> {
        let ratingObject = {
            id: id,
            like: rating
        };
        return this.http.post(this.URL + "rate", JSON.stringify(ratingObject)).map(res => res.json());
    }

    /**
     * Comments on the plant specified by the provided id with the provided comment.
     * @param id
     * @param comment
     * @returns {Observable<Boolean>} - true if the plant was successfully commented on
     *                                - false if the plant commenting failed
     */
    commentPlant(id: string, comment: string): Observable<Boolean> {
        let returnObject = {
            plantId: id,
            comment: comment
        };
        return this.http.post(this.URL + "leaveComment", JSON.stringify(returnObject)).map(res => res.json());
    }
}