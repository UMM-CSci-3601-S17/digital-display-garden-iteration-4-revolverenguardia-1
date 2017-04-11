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
            .subscribe(result => { this.columnChartOptions["dataTable"] = result;
                 console.log(result)}, err => console.log(err));
        this.adminService.getViewsPerHour()
            .subscribe(result => { this.line_ChartData["dataTable"] = result;
                console.log(result)}, err => console.log(err));

    }

    /*updateGraph(): void{
        this.adminService.getViewsPerHour()
            .subscribe(result => { this.columnChartOptions["dataTable"] = result;
                console.log(result)}, err => console.log(err));
    }*/

    public line_ChartData = {
        chartType: `LineChart`,
        dataTable: [['Hour', 'Views'],
            ['0',  0],
            ['1',  0],
            ['2',  0],
            ['3',  0],
            ['4',  0],
            ['5',  0],
            ['6',  0],
            ['7',  0],
            ['8',  0],
            ['9',  0],
            ['10',  0],
            ['11',  0],
            ['12',  0],
            ['13',  0],
            ['14',  0],
            ['15',  0],
            ['16',  0],
            ['17',  0],
            ['18',  0],
            ['19',  0],
            ['20',  0],
            ['21',  0],
            ['22',  0],
            ['23',  0],
        ],
        options: {'title': 'Time vs. View Counts', hAxis : {'title' :'Time (in Hours)'}, vAxis : {'title' :'View Counts'}},
    };

    public columnChartOptions = {
        chartType: `ColumnChart`,
        dataTable: [['Hour', 'Views'],
            ['0',  0], ['1',  0], ['2',  0], ['3',  0], ['4',  0], ['5',  0], ['6',  0], ['7',  0], ['8',  0], ['9',  0], ['10',  0], ['11',  0], ['12',  0],
            ['13',  0], ['14',  0], ['15',  0], ['16',  0], ['17',  0], ['18',  0], ['19',  0], ['20',  0], ['21',  0], ['22',  0], ['23',  0],
            ],
        options: {'title': 'Time vs. View Counts', hAxis : {'title' :'Time (in Hours)'}, vAxis : {'title' :'View Counts'}},
    }

    public mapOptions = {

        chartType: `Map`,
        dataTable: [['Lat', 'Long', 'View Counts', 'Count'],
/*            [45.593823, -95.875248,   'View Counts:', 5000000],
            [45.593831, -95.875525,   'View Counts:', 800000000],*/
/*            ['Washington DC, United States',    'Washington', 'pink' ],
            ['Philadelphia PA, United States',  'Philly',     'green'],
            ['Pittsburgh PA, United States',    'Pittsburgh', 'green'],
            ['Buffalo NY, United States',       'Buffalo',    'blue' ],
            ['Baltimore MD, United States',     'Baltimore',  'pink' ],
            ['Albany NY, United States',        'Albany',     'blue' ],
            ['Allentown PA, United States',     'Allentown',  'green']*/
        ],
        options: {'zoomLevel' : '19', showTooltip: true, showInfoWindow: true}
    }

}
