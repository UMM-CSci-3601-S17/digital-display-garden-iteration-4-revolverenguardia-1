import { NgModule }       from '@angular/core';
import { BrowserModule }  from '@angular/platform-browser';
import { HttpModule, JsonpModule } from '@angular/http';

import { AppComponent }         from './app/app.component';
import { NavbarComponent } from './app/navbar/navbar.component';

// import { PlantListService } from './app/garden/plant-list.service';
import { PlantListService } from './app/garden/plant_list/src/plant-list.service';

import { routing } from './app/app.routes';
import { FormsModule } from '@angular/forms';

import { PipeModule } from './pipe.module';
import {AdminComponent} from "./app/admin/admin.component";
import {ExportComponent} from "./app/admin/export.component";
import {AdminService} from "./app/admin/admin.service";
import {ImportComponent} from "./app/admin/import.component";
import {FileUploadComponent} from "./app/admin/file-upload.component";

import { PlantListComponent } from './app/garden/plant_list/src/plant-list.component';
import {PlantComponent} from "./app/garden/plant_list/src/plant.component";
import {PlantService} from "./app/garden/plant_list/src/plant.service";
import {BedListComponent} from "./app/garden/bed_list/src/bed-list.component";
import {BedListService} from "./app/garden/bed_list/src/bed-list.service";
import {GardenComponent} from "./app/garden/src/garden-component";



@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        JsonpModule,
        routing,
        FormsModule,
        PipeModule
    ],
    declarations: [
        AppComponent,
        NavbarComponent,
        AdminComponent,
        ExportComponent,
        ImportComponent,
        FileUploadComponent,
        PlantListComponent,
        GardenComponent,
        BedListComponent,
        PlantComponent

    ],
    providers: [ PlantListService, BedListService, PlantService, AdminService ],
    bootstrap: [ AppComponent ]
})

export class AppModule {}