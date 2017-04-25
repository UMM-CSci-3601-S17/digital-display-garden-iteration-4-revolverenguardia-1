import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {GardenComponent} from "./garden/src/garden-component";
import {AdminComponent} from "./admin/admin.component";
import {ImportComponent} from "./admin/import.component";
import {ExportComponent} from "./admin/export.component";
import {GraphComponent} from "./admin/google-charts.component";
import {PlantInfoComponent} from "./garden/src/plant-info-component";


export const routes: Routes = [

    { path: '', redirectTo: "/bed/all", pathMatch: 'full' },
    { path: 'bed', redirectTo: "/bed/all", pathMatch: 'full' },
    { path: 'bed/:id', component: GardenComponent },
    { path: 'plant/:id', component: PlantInfoComponent },
    { path: 'admin', component: AdminComponent },
    { path: 'admin/importData', component: ImportComponent},
    { path: 'admin/exportData', component: ExportComponent},
    { path: 'graph', component: GraphComponent},

];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes);