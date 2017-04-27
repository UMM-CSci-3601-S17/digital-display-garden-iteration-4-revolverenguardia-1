/**
 * Tests the BedListComponent class.
 * Currently no tests are implemented as the previous refactoring left the class
 * with no testable code.
 *
 * @author Iteration 2 - Team Omar Anwar
 * @editor Iteration 3 - Team Revolver en Guardia
 */
import { ComponentFixture, TestBed, async } from "@angular/core/testing";
import { Observable } from "rxjs";
import {BedDropdownComponent} from "../src/bed-dropdown.component";
import {Bed} from "../src/bed";
import {BedDropdownService} from "../src/bed-dropdown.service";
import {RouterTestingModule} from "@angular/router/testing";


describe("Bed List Component", () => {

    let bedList: BedDropdownComponent;
    let fixture: ComponentFixture<BedDropdownComponent>;

    let bedListServiceStub: {
        getBedNames: () => Observable<Bed[]>
    };

    beforeEach(() => {
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
            declarations: [ BedDropdownComponent ],
            providers:    [ { provide: BedDropdownService, useValue: bedListServiceStub } ],
            imports: [ RouterTestingModule ]
        })
    });

    beforeEach(async(() => {
        TestBed.compileComponents().then(() => {
            fixture = TestBed.createComponent(BedDropdownComponent);
            bedList = fixture.componentInstance;
            fixture.detectChanges();
        });
    }));

    // No tests required because the recent refactoring left the class with nothing testable.
    // This testing class is left as a placeholder for future possible tests.

});

