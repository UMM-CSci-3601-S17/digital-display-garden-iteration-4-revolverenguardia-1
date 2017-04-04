// Imports
import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {PlantListComponent} from "./garden/plant-list.component";
import {PlantListComponent} from "./garden/plant_list/src/plant-list.component";

import {PlantComponent} from "./garden/plant.component";
import {PlantComponent} from "./garden/plant_list/src/plant.component";

import {AdminComponent} from "./admin/admin.component";
import {ExportComponent} from "./admin/export.component";
import {ImportComponent} from "./admin/import.component";
import {BedComponent} from "./garden/bed.component";

import {GardenComponent} from "./garden/src/garden-component";

// Route Configuration
export const routes: Routes = [
    { path: '', component: GardenComponent },
    { path: 'plant/:plantID', component: PlantComponent },
    { path: 'admin', component: AdminComponent},
    { path: 'admin/exportData', component: ExportComponent},
    { path: 'admin/importData', component: ImportComponent},
    { path: 'bed/:gardenLocation', component: BedComponent }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes);