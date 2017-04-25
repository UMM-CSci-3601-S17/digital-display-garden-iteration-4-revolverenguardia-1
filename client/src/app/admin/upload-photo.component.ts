import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AdminService } from './admin.service';
import {ImageUploadComponent} from "./image-upload.component";
import {PlantListComponent} from "../../app/garden/components/plant_list/src/plant-list.component";
import {Plant} from "../../app/garden/components/plant_list/src/plant";
import {PlantListService} from "../../app/garden/components/plant_list/src/plant-list.service";
import { Observable } from "rxjs";
import {BedListService} from "../../app/garden/components/bed_list/src/bed-list.service";

@Component({
    selector: 'upload-photo-component',
    templateUrl: 'upload-photo.component.html',
})


export class UploadPhotoComponent implements OnInit {

    constructor(private plantListService: PlantListService, private bedListService: BedListService){ }

    @ViewChild('iu') iu;
    @ViewChild('nameInput') nameInput: ElementRef;
    @ViewChild('flowerNameInput') flowerNameInput: ElementRef;

    ListOfAllPlants: Observable<Plant[]>;
    FilteredList: Observable<Plant[]>;

    newFileName: string = "";
    flowerName: string;
    filename: string;
    uploadAttempted:boolean = false;
    public currentFlower: string;
    flowerDropDownName: string = "Cultivar Name";
    bedDropDownName: string = "Bed Name";


    handleUpload() {
        console.log("inputName = " + this.newFileName);
        console.log("flowerName = " + this.flowerName);
        this.iu.upload(this.newFileName, this.flowerName).subscribe(
            response => {
                this.filename = response.json();
                this.uploadAttempted = true;
            },
            err => {
                this.uploadAttempted = true;
            }

        );
    }

    setFlower(cultiVar) {
        this.flowerDropDownName = cultiVar;
        this.flowerName = cultiVar;
    }

    setBed(Namebed) {
        this.bedDropDownName = Namebed;
        //this.plantListService.setBedFilter(Namebed);
    }

    // retrievePlantNames() {
    //     this.ListOfAllPlants = this.plantListService.getPlantsFromServer()
    // }


    ngOnInit(): void {

    }
}
