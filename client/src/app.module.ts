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
import {GraphComponent} from "./app/admin/google-charts.component";
import { Ng2GoogleChartsModule } from 'ng2-google-charts';



@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        JsonpModule,
        routing,
        FormsModule,
        PipeModule,
        Ng2GoogleChartsModule,
    ],
    declarations: [
        AppComponent,
        NavbarComponent,
        GardenComponent,
        PlantListComponent,
        BedListComponent,
        PlantComponent,
        AdminComponent,
        ImportComponent,
        ExportComponent,
        FileUploadComponent,
        GraphComponent,

    ],
    providers: [ PlantListService, BedListService, AdminService, PlantService ],
    bootstrap: [ AppComponent ]
})

export class AppModule {}