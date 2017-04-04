import { ComponentFixture, TestBed, async } from "@angular/core/testing";
import { Observable } from "rxjs";
import {BedListComponent} from "../src/bed-list.component";
import {Bed} from "../src/bed";
import {BedListService} from "../src/bed-list.service";


describe("Bed list", () => {

    let bedList: BedListComponent;
    let fixture: ComponentFixture<BedListComponent>;

    let bedListServiceStub: {
        getBedNames: () => Observable<Bed[]>
    };

    beforeEach(() => {
        // stub UserService for test purposes
        bedListServiceStub = {
            getBedNames: () => Observable.of([
                {
                    _id: "Bed1",
                },
                {
                    _id: "Bed2",
                },
                {
                    _id: "Bed3",
                }
            ])
        };

        TestBed.configureTestingModule({
            declarations: [ BedListComponent ],
            providers:    [ { provide: BedListService, useValue: bedListServiceStub } ]
        })
    });

    beforeEach(async(() => {
        TestBed.compileComponents().then(() => {
            fixture = TestBed.createComponent(BedListComponent);
            bedList = fixture.componentInstance;
            fixture.detectChanges();
        });
    }));

    it("Get Bed List Header", () => {
        let bed: BedListComponent = new BedListComponent(null);
        expect(bed.BED_LIST_HEADER).toBe("Bed");
    });

    it("Get Bed Names", () => {
        expect(bedList.getBedNames().length).toBe(3);
    });

});

