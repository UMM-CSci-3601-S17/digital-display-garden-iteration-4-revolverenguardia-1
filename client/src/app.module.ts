import { NgModule }       from '@angular/core';
import { BrowserModule }  from '@angular/platform-browser';
import { HttpModule, JsonpModule } from '@angular/http';
import { AppComponent }         from './app/app.component';
import { routing } from './app/app.routes';
import { FormsModule } from '@angular/forms';
import { PipeModule } from './pipe.module';

import { NavbarComponent } from './app/navbar/navbar.component';
import {GardenComponent} from "./app/garden/src/garden-component";
import {PlantListComponent} from "./app/garden/plant_list/src/plant-list.component";
import {BedListComponent} from "./app/garden/bed_list/src/bed-list.component";
import {PlantListService} from "./app/garden/plant_list/src/plant-list.service";
import {BedListService} from "./app/garden/bed_list/src/bed-list.service";
import {AdminComponent} from "./app/admin/admin.component";
import {PlantComponent} from "./app/garden/plant_list/src/plant.component";
import {ImportComponent} from "./app/admin/import.component";
import {AdminService} from "./app/admin/admin.service";
import {ExportComponent} from "./app/admin/export.component";
import {FileUploadComponent} from "./app/admin/file-upload.component";
import {PlantService} from "./app/garden/plant_list/src/plant.service";
import {FilterGardenSidebarComponent} from "./app/garden/filter_garden_sidebar/filter-garden-sidebar.component";



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
        GardenComponent,
        FilterGardenSidebarComponent,
        PlantListComponent,
        BedListComponent,
        PlantComponent,
        AdminComponent,
        ImportComponent,
        ExportComponent,
        FileUploadComponent
    ],
    providers: [ PlantListService, BedListService, AdminService, PlantService ],
    bootstrap: [ AppComponent ]
})

export class AppModule {}