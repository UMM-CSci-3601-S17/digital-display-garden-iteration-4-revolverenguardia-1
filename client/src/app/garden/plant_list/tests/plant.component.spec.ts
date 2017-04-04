import { ComponentFixture, TestBed, async } from "@angular/core/testing";
import { Plant } from "../src/plant";
import { PlantComponent } from "../src/plant.component";
import { Observable } from "rxjs";
import {PlantService} from "../src/plant.service";
import {FormsModule} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {isUndefined} from "util";

describe("Plant component", () => {

    let plantComponent: PlantComponent;
    let fixture: ComponentFixture<PlantComponent>;

    let plantServiceStub: {
        ratePlant: (id: string, rating: string) => Observable<Boolean>;
        commentPlant: (id: string, comment: string) => Observable<Boolean>;

    };

    beforeEach(() => {
        plantServiceStub = {
            ratePlant: (id: string, rating: string) => {

                let plant: Observable<Plant> = Observable.of([
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
                ].find(plant => plant.id === id));

                if(plant !== null)
                    return Observable.of(true);
                else
                    return Observable.of(false);

            },

            commentPlant: (id: string, comment: string) => {

                let plant: Observable<Plant> = Observable.of([
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
                ].find(plant => plant.id === id));

                if(plant !== null)
                    return Observable.of(true);
                else
                    return Observable.of(false);

            }
        };

        TestBed.configureTestingModule({
            imports: [FormsModule],
            declarations: [ PlantComponent ],
            providers:    [ { provide: PlantService, useValue: plantServiceStub },
                            { provide: ActivatedRoute, useValue: { params: Observable.of( {id: "PlantID1"} ) } } ]
        })
    });

    beforeEach(async(() => {
        TestBed.compileComponents().then(() => {
            fixture = TestBed.createComponent(PlantComponent);
            plantComponent = fixture.componentInstance;
        });
    }));

    it("Can Like A Plant", () => {

        let wasRated: Boolean = plantComponent.isRated();
        expect(wasRated).toBe(false);

        plantComponent.rate(plantComponent.LIKE);
        wasRated = plantComponent.isRated();
        expect(wasRated).toBe(true);

    });

    it("Can Dislike A Plant", () => {

        let wasRated: Boolean = plantComponent.isRated();
        expect(wasRated).toBe(false);

        plantComponent.rate(plantComponent.DISLIKE);
        wasRated = plantComponent.isRated();
        expect(wasRated).toBe(true);

    });

    it("Can Comment On A Plant", () => {

        let wasCommented: Boolean = plantComponent.isCommented();
        expect(wasCommented).toBe(false);

        plantComponent.comment("");
        wasCommented = plantComponent.isCommented();
        expect(wasCommented).toBe(true);

    });

});
