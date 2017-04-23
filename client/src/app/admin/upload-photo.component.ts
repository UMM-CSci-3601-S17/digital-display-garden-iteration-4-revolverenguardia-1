import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AdminService } from './admin.service';
import {ImageUploadComponent} from "./image-upload.component";
import {PlantListComponent} from "../../app/garden/components/plant_list/src/plant-list.component";
import {Plant} from "../../app/garden/components/plant_list/src/plant";
import {PlantListService} from "../../app/garden/components/plant_list/src/plant-list.service";
import { Observable } from "rxjs";

@Component({
    selector: 'upload-photo-component',
    templateUrl: 'upload-photo.component.html',
})


export class UploadPhotoComponent implements OnInit {

    constructor(private plantListService: PlantListService){ }

    @ViewChild('iu') iu;
    @ViewChild('nameInput') nameInput: ElementRef;

    ListOfAllPlants: Observable<Plant[]>;

    newFileName: string;
    flowerName: string;
    filename:string;
    uploadAttempted:boolean = false;
    public currentFlower: string;
    dropDownName:string = "Flower Name";


    handleUpload() {
        console.log("inputName = " + this.newFileName);
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
        this.dropDownName = cultiVar;
        this.flowerName = cultiVar;
    }

    retrievePlantNames() {
        this.ListOfAllPlants = this.plantListService.getPlantsFromServer()
    }


    ngOnInit(): void {

    }
}
