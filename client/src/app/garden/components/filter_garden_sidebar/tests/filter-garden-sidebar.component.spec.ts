/**
 * Tests the Garden Side Navigation Bar to ensure that display properties are set correctly.
 *
 * @author Iteration 3 - Team Revolver en Guardia
 */
import {FilterGardenSidebarComponent} from "../src/filter-garden-sidebar.component";
import {ComponentFixture, TestBed, async} from "@angular/core/testing";
import {FormsModule} from "@angular/forms";
import {BedListComponent} from "../../bed_list/src/bed-list.component";
import {CommonNameListComponent} from "../../common_name_list/src/common-name-list.component";
import {RouterTestingModule} from "@angular/router/testing";
import {BedListService} from "../../bed_list/src/bed-list.service";
import {CommonNameListService} from "../../common_name_list/src/common-name-list.service";
import {HttpModule} from "@angular/http";
import {PlantListService} from "../../plant_list/src/plant-list.service";

describe("Test Display Properties of Filter Sidebar", () => {

    let filterGardenSidebarComponent: FilterGardenSidebarComponent;
    let fixture: ComponentFixture<FilterGardenSidebarComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [BedListService, CommonNameListService, PlantListService],
            imports: [FormsModule, RouterTestingModule, HttpModule],
            declarations: [ FilterGardenSidebarComponent, BedListComponent, CommonNameListComponent ]
        })
    });

    beforeEach(async(() => {
        TestBed.compileComponents().then(() => {
            fixture = TestBed.createComponent(FilterGardenSidebarComponent);
            filterGardenSidebarComponent = fixture.componentInstance;
        });
    }));

    /**
     * Tests that display width constant is set correctly.
     * @author Iteration 3 - Team Revolver en Guardia
     */
    it("Check Display Width Constant", () => {
        expect(filterGardenSidebarComponent.NAV_BAR_WIDTH).toBe(260);
    });

    /**
     * Tests display width for the Bed Nav Bar.
     * @author Iteration 3 - Team Revolver en Guardia
     */
    it("Check Bed Nav Display Properties", () => {
        expect(filterGardenSidebarComponent.bedNavWidth).toBe(0);
        filterGardenSidebarComponent.openBedNav();
        expect(filterGardenSidebarComponent.bedNavWidth).toBe(260);
        filterGardenSidebarComponent.closeBedNav();
        expect(filterGardenSidebarComponent.bedNavWidth).toBe(0);
    });

    /**
     * Tests display width for the Common Name Nav Bar.
     * @author Iteration 3 - Team Revolver en Guardia
     */
    it("Check Common Name Nav Display Properties", () => {
        expect(filterGardenSidebarComponent.commonNameNavWidth).toBe(0);
        filterGardenSidebarComponent.openCommonNameNav();
        expect(filterGardenSidebarComponent.commonNameNavWidth).toBe(260);
        filterGardenSidebarComponent.closeCommonNameNav();
        expect(filterGardenSidebarComponent.commonNameNavWidth).toBe(0);
    });

});
