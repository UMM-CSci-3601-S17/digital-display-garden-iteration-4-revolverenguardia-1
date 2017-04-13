/**
 * TODO: Class Comment Header
 * @author Iteration 3 - Team Revolver en Guardia
 */
import { Component } from '@angular/core';

@Component({
    selector: 'filter-garden-sidebar',
    templateUrl: 'filter-garden-sidebar.component.html',
})

export class FilterGardenSidebarComponent {

    public bedNavWidth: number = 0;
    public nameNavWidth: number = 0;

    public openBedNav(): void{
        this.bedNavWidth = 260;
    }

    public closeBedNav(): void{
        this.bedNavWidth = 0;
    }

    public openNameNav(): void{
        this.nameNavWidth = 260;
    }

    public closeNameNav(): void{
        this.nameNavWidth = 0;
    }

}
