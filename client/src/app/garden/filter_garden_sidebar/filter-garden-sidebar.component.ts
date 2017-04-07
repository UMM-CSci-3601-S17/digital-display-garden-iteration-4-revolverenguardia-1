/**
 * TODO: Class Comment Header
 * @author Iteration 3 - Team Revolver en Guardia
 */
import { Component } from '@angular/core';
import {FilterBy} from "../filter.pipe";


@Component({
    selector: 'filter-garden-sidebar',
    templateUrl: 'filter-garden-sidebar.component.html',
    providers: [ FilterBy ]
})

export class FilterGardenSidebarComponent {

}
