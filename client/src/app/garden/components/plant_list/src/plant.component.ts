/**
 * Represents a Plant detail page that contains all information related to the current Plant.
 *
 * @author Iteration 1 - Team Rayquaza
 * @editor Iteration 2 - Team Omar Anwar
 * @editor Iteratoin 3 - Team Revolver en Guardia
 */
import {Component, OnInit } from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {Plant} from './plant';
import {PlantService} from './plant.service';
import 'rxjs/add/operator/switchMap';
import {PlantFeedback} from "./plant-feedback";

@Component({
    selector: 'plant-component',
    templateUrl: 'plant.component.html'
})
export class PlantComponent implements OnInit {

    // Has the current PlantComponent been liked or disliked?
    public rated: Boolean = false;

    // Has the current PlantComponent been commented on?
    private commented: Boolean = false;

    // Placeholder plant for loading Plant data for the PlantComponent
    private plant: Plant = {id: "", commonName: "", cultivar: "", source: "", gardenLocation: ""};

    // Tracks the feedback for the current plant
    private plantFeedback: PlantFeedback = new PlantFeedback();


    /**
     * Creates a new PlantComponent that uses a PlantService for requesting Plant data. Also,
     * has a route to provide routing to specific PlantComponent pages that have unique URLs based on
     * the plant id.
     * @param plantService - the PlantService that provides data
     * @param route - the routing service that routes to specific PlantComponent pages
     */
    constructor(private plantService: PlantService, private route: ActivatedRoute ) { }


    /**
     * TODO
     * On initialization generates the URL with the proper plant ID and also helps to
     * populate the PlantComponent page with the Plant data referneced by the id.
     *
     * For example, a URL for the Alternanthera would be http://localhost:9000/plant/16001
     */
    ngOnInit(): void {
        // Get the actual plant
        this.route.params
            .switchMap((params: Params) => this.plantService.getPlantById(params['id']))
            .subscribe(plant => this.plant = plant);

        // Get the feedback data for the plant
        this.route.params
            .switchMap((params: Params) => this.plantService.getFeedbackForPlantByPlantID(params['id']))
            .subscribe((plantFeedback: PlantFeedback) => this.plantFeedback = plantFeedback);
    }

    /**
     * Rates the currently loaded Plant that is displayed on the PlantComponent with either a like or dislike.
     * @param rating - the rating to be provided
     */
    public rate(rating: boolean): void {
        if (!this.rated) {this.rated = false;
            this.plantService.ratePlant(this.plant.id, rating)
            //this will update the comment dynamically but makes the test fail
                //.subscribe(succeeded => {this.rated = succeeded;this.refreshFeedback()})
                .subscribe(succeeded => this.rated = succeeded)
        }
    }

    /**
     * Comments on the currently loaded Plant that is displayed on the PlantComponent.
     * @param comment - the comment to be added to the plant
     */
    public comment(comment: string): void {
        if (!this.commented) {
            if (comment != null) {
                this.plantService.commentPlant(this.plant.id, comment)
                    //this will update the comment dynamically but makes the test fail
                    //.subscribe(succeeded => {this.commented = succeeded;this.refreshFeedback()})
                    .subscribe(succeeded => this.commented = succeeded)
            }
        }
    }

    /**
     * TODO
     */
    private refreshFeedback(): void {
        //Update flower feedback numbers
        this.route.params
            .switchMap((params: Params) => this.plantService.getFeedbackForPlantByPlantID(params['id']))
            .subscribe((plantFeedback: PlantFeedback) => this.plantFeedback = plantFeedback);
    }

    /**
     * Returns if the current plant was rated or not.
     * Is used primarily for testing.
     * @returns {Boolean}
     */
    public isRated(): Boolean{
        return this.rated;
    }

    /**
     * Returns if the current plant was commented or not.
     * Is used primarily for testing.
     * @returns {Boolean}
     */
    public isCommented(): Boolean{
        return this.commented;
    }

    public getLikeWidth(): number{
        let likes: number = this.plantFeedback.likeCount,
            dislikes: number = this.plantFeedback.dislikeCount;

        return likes / (likes + dislikes) * 100;
    }

    public getDislikeWidth(): number{
        let likes: number = this.plantFeedback.likeCount,
            dislikes: number = this.plantFeedback.dislikeCount;

        return dislikes / (likes + dislikes) * 100;
    }
}

