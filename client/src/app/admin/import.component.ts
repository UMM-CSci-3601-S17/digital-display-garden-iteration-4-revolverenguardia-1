import {Component, OnInit, ViewChild} from '@angular/core';
import { AdminService } from './admin.service';


@Component({
    selector: 'import-component',
    templateUrl: 'import.component.html',
})

export class ImportComponent implements OnInit {

    @ViewChild('fu') fu;

    filename:string;
    uploadAttempted:boolean = false;

    handleUploadForImport(){
        this.fu.uploadForImport().subscribe(
            response => {
                this.filename = response.json();
                this.uploadAttempted = true;
            },
            err => {
                this.uploadAttempted = true;
                console.log(err);
            }

        );
    }

    handleUploadForPatch(){
        this.fu.uploadForPatch().subscribe(
            response => {
                this.filename = response.json();
                this.uploadAttempted = true;
            },
            err => {
                this.uploadAttempted = true;
            }

        );
    }

    ngOnInit(): void {

    }
}
