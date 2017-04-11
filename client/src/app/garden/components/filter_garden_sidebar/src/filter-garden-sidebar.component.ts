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
        this.bedNavWidth = 200;
        console.log("Open Bed Nav " + this.bedNavWidth);
    }

    public closeBedNav(): void{
        this.bedNavWidth = 0;
        console.log("Close Bed Nav " + this.bedNavWidth);
    }

    public openNameNav(): void{
        this.nameNavWidth = 200;
        console.log("Open Name Nav " + this.nameNavWidth);
    }

    public closeNameNav(): void{
        this.nameNavWidth = 0;
        console.log("Close Name Nav " + this.nameNavWidth);
    }

}
