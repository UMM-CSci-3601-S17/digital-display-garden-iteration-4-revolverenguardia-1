import { NgModule }       from '@angular/core';
import { BrowserModule }  from '@angular/platform-browser';
import { HttpModule, JsonpModule } from '@angular/http';
import { AppComponent }         from './app/app.component';
import { routing } from './app/app.routes';
import { FormsModule } from '@angular/forms';
import { PipeModule } from './pipe.module';

import { NavbarComponent } from './app/navbar/navbar.component';
import {GardenComponent} from "./app/garden/src/garden-component";
import {PlantListComponent} from "./app/garden/components/plant_list/src/plant-list.component";
import {BedListComponent} from "./app/garden/components/bed_list/src/bed-list.component";
import { CommonNameListComponent } from "./app/garden/components/common_name_list/src/common-name-list.component";

import {PlantListService} from "./app/garden/components/plant_list/src/plant-list.service";
import {BedListService} from "./app/garden/components/bed_list/src/bed-list.service";
import { CommonNameListService } from "./app/garden/components/common_name_list/src/common-name-list.service";
import {AdminComponent} from "./app/admin/admin.component";
import {PlantComponent} from "./app/garden/components/plant_list/src/plant.component";
import {ImportComponent} from "./app/admin/import.component";
import {AdminService} from "./app/admin/admin.service";
import {ExportComponent} from "./app/admin/export.component";
import {FileUploadComponent} from "./app/admin/file-upload.component";
import {PlantService} from "./app/garden/components/plant_list/src/plant.service";
import {GraphComponent} from "./app/admin/google-charts.component";
import { Ng2GoogleChartsModule } from 'ng2-google-charts';
import {UploadPhotoComponent } from './app/admin/upload-photo.component';
import { ImageUploadComponent } from './app/admin/image-upload.component';

import {FilterGardenSidebarComponent} from "./app/garden/components/filter_garden_sidebar/src/filter-garden-sidebar.component";
import {FooterComponent} from "./app/garden/components/footer/src/footer.component";
import {RouterModule} from "@angular/router";

@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        JsonpModule,
        routing,
        FormsModule,
        PipeModule,
        Ng2GoogleChartsModule,
        PipeModule,
        RouterModule
    ],
    declarations: [
        AppComponent,
        NavbarComponent,
        GardenComponent,
        FilterGardenSidebarComponent,
        PlantListComponent,
        BedListComponent,
        CommonNameListComponent,
        PlantComponent,
        AdminComponent,
        ImportComponent,
        ExportComponent,
        FileUploadComponent,
        FooterComponent,
        GraphComponent,
        ImageUploadComponent,
        UploadPhotoComponent,

    ],
    providers: [
        PlantListService,
        BedListService,
        CommonNameListService,
        AdminService,
        PlantService
    ],
    bootstrap: [ AppComponent ]
})

export class AppModule {}