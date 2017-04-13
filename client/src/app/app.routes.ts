import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {GardenComponent} from "./garden/src/garden-component";
import {AdminComponent} from "./admin/admin.component";
import {PlantComponent} from "./garden/components/plant_list/src/plant.component";
import {ImportComponent} from "./admin/import.component";
import {ExportComponent} from "./admin/export.component";
import {PlantListComponent} from "./garden/components/plant_list/src/plant-list.component";
import {GraphComponent} from "./admin/google-charts.component";


export const routes: Routes = [

    { path: '', redirectTo: "/bed/all", pathMatch: 'full' },
    { path: 'bed', redirectTo: "/bed/all", pathMatch: 'full' },
    { path: 'bed/:id', component: GardenComponent },
    { path: 'plant/:id', component: PlantComponent },
    { path: 'admin', component: AdminComponent },
    { path: 'admin/importData', component: ImportComponent},
    { path: 'admin/exportData', component: ExportComponent},
    { path: 'graph', component: GraphComponent},

];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes);