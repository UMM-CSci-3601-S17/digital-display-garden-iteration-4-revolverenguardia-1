/**
 * The FilterGardenSidebarComponent is anchored on the left side of the screen
 * in a vertical orientation and provides an interface to filter the PlantListComponent.
 * @author Iteration 3 - Team Revolver en Guardia
 */
import { Component } from '@angular/core';

@Component({
    selector: 'filter-garden-sidebar',
    templateUrl: 'filter-garden-sidebar.component.html',
})

export class FilterGardenSidebarComponent {

    /**
     * Width of the navigation bars
     */
    public static readonly NAV_BAR_WIDTH: number = 260;

    /**
     * Width of the bed navigation bar.
     */
    public bedNavWidth: number = 0;

    /**
     * Width of the common name navigation bar.
     */
    public commonNameNavWidth: number = 0;

    /**
     * Opens the bed navigation bar by decreasing its width to 0.
     */
    public openBedNav(): void{
        this.bedNavWidth = FilterGardenSidebarComponent.NAV_BAR_WIDTH;
    }

    /**
     * Opens the bed navigation bar by increasing its width NAV_BAR_WIDTH.
     */
    public closeBedNav(): void{
        this.bedNavWidth = 0;
    }

    /**
     * Opens the bed navigation bar by increasing its width to NAV_BAR_WIDTH.
     */
    public openCommonNameNav(): void{
        this.commonNameNavWidth = FilterGardenSidebarComponent.NAV_BAR_WIDTH;
    }

    /**
     * Closes the common name navigation bar by decreasing its width to 0.
     */
    public closeCommonNameNav(): void{
        this.commonNameNavWidth = 0;
    }
}
