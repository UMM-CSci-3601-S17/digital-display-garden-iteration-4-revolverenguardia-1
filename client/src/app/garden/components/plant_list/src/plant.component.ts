/**
 * Represents a Plant detail page that contains all information related to the current Plant.
 *
 * @author Iteration 1 - Team Rayquaza
 * @editor Iteration 2 - Team Omar Anwar
 * @editor Iteratoin 3 - Team Revolver en Guardia
 */
import {Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {Plant} from './plant';
import {PlantService} from './plant.service';
import 'rxjs/add/operator/switchMap';
import {ImageDisplayService} from "./image-display.service";

@Component({
    selector: 'plant-component',
    templateUrl: 'plant.component.html'
})


export class PlantComponent implements OnInit {


    @ViewChild('imageDisplay') imageDisplay: ElementRef;

    private imageService: ImageDisplayService;

    private pic: string = "";

    public No_Image_URL: string = API_URL + "admin/getImage/" + "No Image";


    // Has the current PlantComponent been liked or disliked?
    public rated: Boolean = false;

    // Has the current PlantComponent been commented on?
    private commented: Boolean = false;

    // Placeholder plant for loading Plant data for the PlantComponent
    private plant: Plant = {id: "", commonName: "", cultivar: "", source: "", gardenLocation: ""};

    public Image_URL: string;

    public goto_URL: string;
        /**
     * Creates a new PlantComponent that uses a PlantService for requesting Plant data. Also,
     * has a route to provide routing to specific PlantComponent pages that have unique URLs based on
     * the plant id.
     * @param plantService - the PlantService that provides data
     * @param route - the routing service that routes to specific PlantComponent pages
     */
    constructor(private plantService: PlantService, private route: ActivatedRoute ) {
        //@ViewChild('imgdisplay') imageDisplay: ElementRef;
        }


    /**
     * On initialization generates the URL with the proper plant ID and also helps to
     * populate the PlantComponent page with the Plant data referneced by the id.
     *
     * For example, a URL for the Alternanthera would be http://localhost:9000/plant/16001
     */
    ngOnInit(): void {


        this.route.params
            .switchMap((params: Params) => this.plantService.getPlantById(params['id']))
            .subscribe(plant => {
                this.plant = plant;
                this.Image_URL = API_URL + "admin/getImage/" + this.plant.cultivar;
                this.goto_URL = this.Image_URL;
            });



    }

    /**
     * Rates the currently loaded Plant that is displayed on the PlantComponent with either a like or dislike.
     * @param rating - the rating to be provided
     */
    public rate(rating: boolean): void {
        if (!this.rated) {this.rated = false;
            this.plantService.ratePlant(this.plant.id, rating)
                .subscribe(succeeded => this.rated = succeeded);
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
                    .subscribe(succeeded => this.commented = succeeded);
            }
        }
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

    setDefaultPic() {
        this.pic = "assets/images/my-image.png";
    }


}

