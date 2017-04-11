/**
 * Provides the ability to request Plant data to be sent from the server. This class also contains
 * the primary Plant Collection that will be used to store the full list of plant data. Any filtered
 * plant data should be have Plant references stored within their respective classes. In addition,
 * this PlantListService also provides the ability to filter the plants contained within the
 * PlantListComponent.
 *
 * @author Iteration 3 - Team revolver en guardia
 */
import {Injectable} from '@angular/core';
import { Http } from '@angular/http';
import { Plant } from './plant';
import { Observable } from "rxjs";
import {PlantCollection} from "./plantcollection";
import {PlantListComponent} from "./plant-list.component";
import {PlantFilter} from "./plantfilter";
import {GardenComponent} from "../../../src/garden-component";



@Injectable()
export class PlantListService {

    private readonly URL: string = API_URL + "plant";

    private plantCollection: PlantCollection;

    // The bed filter we have currently filtered by
    private currentBedFilter;

    // The common name filter we have currently filtered by
    private currentCommonNameFilter;

    constructor(private http:Http) { }

    /**
     * Requests that the plant collection be sent from the server.
     * @returns {Observable<R>} - the received Observable Plant collection
     */
    public getPlantsFromServer(): Observable<Plant[]> {
        return this.http.request(API_URL + "plants").map(res => res.json());
    }

    /**
     * If the data has not already been filtered by the current common name this method
     * filters the plant data.
     * @param commonName - common name to filter by
     */
    public filterByCommonName(commonName: string): void{

        // Check that we haven't already filtered
        if(this.currentCommonNameFilter != commonName) {

            this.currentCommonNameFilter = commonName;

            if(this.currentCommonNameFilter === PlantFilter.FILTER_BY_ALL_PLANTS)
                PlantListComponent.getInstance().setFilteredPlants(this.plantCollection.getPlants());

            // Filter by the common name
            else {
                let filteredPlants: Plant[] = PlantFilter.filterByCommonName(commonName, this.plantCollection.getPlants());
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