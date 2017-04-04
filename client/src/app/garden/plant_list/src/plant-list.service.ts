/**
 * Provides the ability to request Plant data to be sent from the server. This class also contains
 * the primary Plant Collection that will be used to store the full list of plant data. Any filtered
 * plant data should be have Plant references stored within their respective classes. In addition,
 * this PlantListService also provides the ability to filter the plants contained within the
 * PlantListComponent.
 *
 * @author Iteration 2 - Team Omar Anwar
 */
import {Injectable} from '@angular/core';
import { Http } from '@angular/http';
import { Plant } from './plant';
import { Observable } from "rxjs";
import {PlantCollection} from "./plantcollection";
import {PlantListComponent} from "./plant-list.component";
import {PlantFilter} from "./plantfilter";

@Injectable()
export class PlantListService {

    private readonly URL: string = API_URL + "plant";

    private plantCollection: PlantCollection;

    // The bed filter we have currently filtered by
    private currentBedFilter;

    constructor(private http:Http) {

        this.getPlantsFromServer().subscribe(
            plants => this.plantCollection = new PlantCollection(plants),
            err => {
                console.log(err);
            }
        );
    }

    /**
     * Requests that the plant collection be sent from the server.
     * @returns {Observable<R>} - the received Observable Plant collection
     */
    private getPlantsFromServer(): Observable<Plant[]> {
        return this.http.request(API_URL + "plants").map(res => res.json());
    }

    /**
     * If the data has not already been filtered by the current bed name this method
     * filters the plant data.
     * @param bedName - bed name to filter by
     */
    public filterByBedName(bedName: string): void{

        // Check that we haven't already filtered
        if(this.currentBedFilter != bedName) {

            this.currentBedFilter = bedName;

            if(this.currentBedFilter === PlantFilter.FILTER_BY_ALL_PLANTS)
                PlantListComponent.getInstance().setFilteredPlants(this.plantCollection.getPlants());

            // Filter by the bed name
            else {
                let filteredPlants: Plant[] = PlantFilter.filterByBedName(bedName, this.plantCollection.getPlants());
                PlantListComponent.getInstance().setFilteredPlants(filteredPlants);
            }
        }
    }

    /**
     * Requests that the Plant specified by the provided id be sent from the server.
     * @param id
     * @returns {Observable<R>}
     */
    getPlantById(id: string): Observable<Plant> {
        console.log("Requesting: " + API_URL + "plant/" + id);
        return this.http.request(this.URL + "/" + id).map(res => res.json());
    }
}