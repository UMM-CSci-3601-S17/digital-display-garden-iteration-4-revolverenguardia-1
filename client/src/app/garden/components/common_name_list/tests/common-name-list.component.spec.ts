import { ComponentFixture, TestBed, async } from "@angular/core/testing";
import { Observable } from "rxjs";
import { CommonNameListComponent } from "../src/common-name-list.component";
import { CommonName } from "../src/common-name";
import { CommonNameListService } from "../src/common-name-list.service";


describe("Common name list", () => {

    let commonNameList: CommonNameListComponent;
    let fixture: ComponentFixture<CommonNameListComponent>;

    let commonNameListServiceStub: {
        getCommonNames: () => Observable<CommonName[]>
    };

    beforeEach(() => {
        // stub UserService for test purposes
        commonNameListServiceStub = {
            getCommonNames: () => Observable.of([
                {
                    id: "CommonName1",
                    commonName: "CommonName1"
                },
                {
                    id: "CommonName2",
                    commonName: "CommonName2"
                },
                {
                    id: "CommonName3",
                    commonName: "CommonName3"
                }
            ])
        };

        TestBed.configureTestingModule({
            declarations: [ CommonNameListComponent ],
            providers:    [ { provide: CommonNameListService, useValue: commonNameListServiceStub } ]
        })
    });

    beforeEach(async(() => {
        TestBed.compileComponents().then(() => {
            fixture = TestBed.createComponent(CommonNameListComponent);
            commonNameList = fixture.componentInstance;
            fixture.detectChanges();
        });
    }));

    // it("Get Common Name List Header", () => {
    //     let commonName: CommonNameListComponent = new CommonNameListComponent(null);
    //     expect(commonName.COMMON_NAME_LIST_HEADER).toBe("ALL");
    // });
    //
    // it("Get the Number of Common Names", () => {
    //     expect(commonNameList.getCommonNamesFromServer().length).toBe(3);
    // });

});


