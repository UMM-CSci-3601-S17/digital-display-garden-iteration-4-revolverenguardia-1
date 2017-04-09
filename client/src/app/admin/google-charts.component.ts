import {Component, OnInit} from '@angular/core';
//import { Ng2GoogleChartsModule } from 'ng2-google-charts';
import {AdminService} from "./admin.service";
import {Observable} from "rxjs";

@Component({
    templateUrl: 'google-charts.component.html'
})

// Component class
export class GraphComponent implements OnInit {


    constructor(private adminService: AdminService) {
    }

    dataTable : any[][];

    ngOnInit(): void {
        this.adminService.getViewsPerHour()
            .subscribe(result => { this.line_ChartData["dataTable"] = result;
                 console.log(result)}, err => console.log(err));
    }

    public line_ChartData = {
        chartType: `LineChart`,
        dataTable: [['Year', 'Sales', 'Expenses'],
            ['2004',  1000,      400],
            ['2005',  1170,      460],
            ['2006',  660,       1120],
            ['2007',  1030,      540]],
        options: {'title': 'dataAndStuff'},
    };

}
