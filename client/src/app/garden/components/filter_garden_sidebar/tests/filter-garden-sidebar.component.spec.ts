// /**
//  * Tests the Garden Side Navigation Bar to ensure that display properties are set correctly.
//  *
//  * @author Iteration 3 - Team Revolver en Guardia
//  */
// import {FilterGardenComponent} from "../src/filter-garden.component";
// import {ComponentFixture, TestBed, async} from "@angular/core/testing";
// import {FormsModule} from "@angular/forms";
// import {BedListComponent} from "../../bed_dropdown/src/bed-dropdown.component";
// import {CommonNameListComponent} from "../../common_name_dropdown/src/common-name-dropdown.component";
// import {RouterTestingModule} from "@angular/router/testing";
// import {BedListService} from "../../bed_dropdown/src/bed-dropdown.service";
// import {CommonNameListService} from "../../common_name_dropdown/src/common-name-dropdown.service";
// import {HttpModule} from "@angular/http";
// import {PlantListService} from "../../plant_list/src/plant-list.service";
// import {Observable} from "rxjs";
// import {Bed} from "../../bed_dropdown/src/bed";
// import {CommonName} from "../../common_name_dropdown/src/common-name";
// import {Plant} from "../../plant_list/src/plant";
//
// describe("Test Display Properties of Filter Sidebar", () => {
//
//     let filterGardenSidebarComponent: FilterGardenComponent;
//     let fixture: ComponentFixture<FilterGardenComponent>;
//
//     let bedListServiceStub: {
//         getBedNames: () => Observable<Bed[]>
//     };
//
//     let commonNameListServiceStub: {
//         getCommonNames: () => Observable<CommonName[]>
//     };
//
//     let plantListServiceStub: {
//         getPlants: () => Observable<Plant[]>
//     };
//
//     beforeEach(() => {
//         bedListServiceStub = {
//             getBedNames: () => Observable.of([
//                 {
//                     bedName: "Bed1"
//                 },
//                 {
//                     bedName: "Bed2"
//                 },
//                 {
//                     bedName: "Bed3"
//                 }
//             ])
//         };
//
//         commonNameListServiceStub = {
//             getCommonNames: () => Observable.of([
//                 {
//                     commonName: "CommonName1"
//                 },
//                 {
//                     commonName: "CommonName2"
//                 },
//                 {
//                     commonName: "CommonName3"
//                 }
//             ])
//         };
//
//         plantListServiceStub = {
//             getPlants: () => Observable.of([
//                 {
//                     id: "PlantID1",
//                     commonName: "CommonName1",
//                     cultivar: "Cultivar1",
//                     source: "Source1",
//                     gardenLocation: "BedName1",
//
//                 },
//                 {
//                     id: "PlantID2",
//                     commonName: "CommonName2,",
//                     cultivar: "Cultivar2,",
//                     source: "Source2,",
//                     gardenLocation: "BedName2,",
//                 },
//                 {
//                     id: "PlantID3",
//                     commonName: "CommonName3",
//                     cultivar: "Cultivar3",
//                     source: "Source3",
//                     gardenLocation: "BedName3",
//                 }
//             ])
//         };
//
//
//         TestBed.configureTestingModule({
//         providers: [ {provide: BedListService, useValue: bedListServiceStub},
//                      {provide: CommonNameListService, useValue: commonNameListServiceStub},
//                      {provide: PlantListService, useValue: plantListServiceStub}],
//         imports: [FormsModule, RouterTestingModule, HttpModule],
//         declarations: [ FilterGardenComponent, BedListComponent, CommonNameListComponent ]
//         })
//
//     });
//
//
//     beforeEach(() => {
//
//     });
//
//     beforeEach(async(() => {
//         TestBed.compileComponents().then(() => {
//             fixture = TestBed.createComponent(FilterGardenComponent);
//             filterGardenSidebarComponent = fixture.componentInstance;
//         });
//     }));
// });
