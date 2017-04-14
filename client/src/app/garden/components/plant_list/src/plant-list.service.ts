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

    // URL for server plant collection
    private readonly URL: string = API_URL + "plant";

    // Master collection of all plants
    private plantCollection: PlantCollection;

    // Plants to display within the PlantListComponent
    private filteredPlants: Plant[] = [];

    // Current common name filter for plants within PlantListComponent
    private commonNameFilter: string = PlantFilter.NO_FILTER;

    // Current bed filter for plants within PlantListComponent
    private bedFilter: string = PlantFilter.NO_FILTER;

    constructor(private http:Http) {
        this.getPlantsFromServer().subscribe(
            plants => {
                console.log("PLS - getPlantsFromServer()");
                this.plantCollection = new PlantCollection(plants);
                this.filteredPlants = this.plantCollection.getPlants();
                // this.filteredPlants.push(new Plant("PlantID1", "AmazingPlant1", "Cultivar1", "Source1", "GardenLocation1"));
                err => {
                    console.log(err);
                }
            }
        );
    }

    /**
     * Requests that the plant collection be sent from the server.
     * @returns {Observable<R>} - the received Observable Plant collection
     */
    public getPlantsFromServer(): Observable<Plant[]> {
        return this.http.request(API_URL + "plants").map(res => res.json());
    }

    /**
     * Requests that the Plant specified by the provided id be sent from the server.
     * @param id
     * @returns {Observable<R>}
     */
    public getPlantById(id: string): Observable<Plant> {
        console.log("Requesting: " + API_URL + "plant/" + id);
        return this.http.request(this.URL + "/" + id).map(res => res.json());
    }

    private filterPlants(): void{
        let plantsBeingFiltered: Plant[] = this.plantCollection.getPlants();

        plantsBeingFiltered = PlantFilter.filterByBedName(this.bedFilter, plantsBeingFiltered);
        plantsBeingFiltered = PlantFilter.filterByCommonName(this.commonNameFilter, plantsBeingFiltered);

        this.filteredPlants = plantsBeingFiltered;

        console.log(this.filteredPlants.length + " Plants in PlantList")
    }

    public getFilteredPlants(): Plant[]{
        return this.filteredPlants;
    }

    public setBedFilter(filter: string): void{
        this.bedFilter = filter;
        this.filterPlants();
    }

    public setCommonNameFilter(filter: string): void{
        this.commonNameFilter = filter;
        this.filterPlants();
    }

    public getBedFilter(): string{
        return this.bedFilter;
    }

    public getCommonNameFilter(): string{
        return this.commonNameFilter;
    }

}