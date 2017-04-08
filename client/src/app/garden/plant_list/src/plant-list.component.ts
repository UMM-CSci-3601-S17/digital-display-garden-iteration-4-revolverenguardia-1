/**
 * Represents all functions and data that are contained within the Plant list view within the Garden
 * interface. The list contains all plants that have been filtered by the current Bed list selection.
 *
 * @author Iteration 2 - Team Omar Anwar
 */
import {Component, OnInit} from '@angular/core';
import { PlantListService } from "./plant-list.service";
import { Plant } from "./plant";
import { FilterBy } from "../../filter.pipe";

@Component({
    selector: 'plant-list',
    templateUrl: 'plant-list.component.html',
    providers: [ FilterBy ]
})

export class PlantListComponent {

    // The list of filtered plant to display within the HTML
    private filteredPlants: Plant[] = [];

    // the currently selected plant within the html
    private selectedPlant: Plant;

    // Static factory class instance variable
    private static plantListComponent: PlantListComponent;

    constructor(private plantListService: PlantListService) {
        // Keep track of 'this' for static factory method
        PlantListComponent.plantListComponent = this;
    }

    /**
     * Static factory method to return the currently instantiated PlantListComponent.
     * @returns {PlantListComponent} - the current PlantListComponent
     */
    public static getInstance(): PlantListComponent{
        return PlantListComponent.plantListComponent;
    }

    /**
     * Upon a click or touch event within the PlantListComponent the currently selected Plant's
     * PlantComponent view will be opened and presented to the user.
     * @param selectedPlant - the currently selected plant
     */
    private handlePlantListClick(selectedPlant: Plant){
        // Search the array of plants for the currently selected plant
        this.filteredPlants.forEach((plant, index) => {
            if(selectedPlant == plant){
                this.selectedPlant = selectedPlant;
            }
        });

        // Request data from the server for the selected plant
        this.plantListService.getPlantById(selectedPlant.id);
    }

    /**
     * Sets the array of filtered plants.
     * @param filteredPlants - the filtered plants to set
     */
    public setFilteredPlants(filteredPlants: Plant[]){
        this.filteredPlants = filteredPlants;
    }

    /**
     * Gets the filtered plants collection.
     * Currently implemented for testing.
     * @returns {Plant[]} - the filtered plants collection
     */
    public getFilteredPlants(): Plant[]{
        return this.filteredPlants;
    }

    /**
     * Filters the filteredplants array by the provided bed name.
     * @param bedName - the bed name to filter the PlantListComponent's data by
     */
    public filterByBedName(bedName: string): void{
        this.plantListService.filterByBedName(bedName);
    }

}
