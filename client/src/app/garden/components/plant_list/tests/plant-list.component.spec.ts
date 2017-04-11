import { ComponentFixture, TestBed, async } from "@angular/core/testing";
import { Observable } from "rxjs";
import {PlantListComponent} from "../src/plant-list.component";
import {PlantListService} from "../src/plant-list.service";
import {Plant} from "../src/plant";
import {FormsModule} from "@angular/forms";
import {RouterTestingModule} from "@angular/router/testing";



describe("Plant list", () => {

    let plantList: PlantListComponent;
    let fixture: ComponentFixture<PlantListComponent>;

    let plantListServiceStub: {
        getPlants: () => Observable<Plant[]>
    };

    beforeEach(() => {
        // stub PlantService for test purposes
        plantListServiceStub = {
            getPlants: () => Observable.of([
                {
                    id: "PlantID1",
                    commonName: "CommonName1",
                    cultivar: "Cultivar1",
                    source: "Source1",
                    gardenLocation: "BedName1",

                },
                {
                    id: "PlantID2",
                    commonName: "CommonName2,",
                    cultivar: "Cultivar2,",
                    source: "Source2,",
                    gardenLocation: "BedName2,",
                },
                {
                    id: "PlantID3",
                    commonName: "CommonName3",
                    cultivar: "Cultivar3",
                    source: "Source3",
                    gardenLocation: "BedName3",
                }
                ])
        };

        TestBed.configureTestingModule({
            declarations: [ PlantListComponent ],
            providers:    [ { provide: PlantListService, useValue: plantListServiceStub } ],
            imports:      [RouterTestingModule, FormsModule]
        })
    });

    beforeEach(async(() => {
        TestBed.compileComponents().then(() => {
            fixture = TestBed.createComponent(PlantListComponent);
            plantList = fixture.componentInstance;
            fixture.detectChanges();
        });
    }));

    it("Get Instance of PlantListComponent", () => {
        let plc: PlantListComponent  = new PlantListComponent(null);
        expect(PlantListComponent.getInstance() == null).toBe(false);
    });

    it("Set Filtered Plants", () => {

        let plants: Plant[] = [];
        plants.push(new Plant("PlantID1", "CommonName1", "Cultivar1", "Source1", "GardenLocation1"));
        plants.push(new Plant("PlantID2", "CommonName2", "Cultivar2", "Source2", "GardenLocation2"));
        plants.push(new Plant("PlantID3", "CommonName3", "Cultivar3", "Source3", "GardenLocation3"));

        expect(plants.length).toBe(3);

        plantList.setFilteredPlants(plants);

        expect(plantList.getFilteredPlants().length).toBe(3);
    });

});
