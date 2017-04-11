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
    value : number = 3;
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

    public bedLocations = [[45.593823, -95.875248], [45.593831, -95.875525], [45.593113, -95.877688], [45.593008, -95.876990], [45.593512, -95.876351]];
    public bedNames = ['5', '6', '7', '9', '10', '11', '13'];

    public mapOptions = {
        chartType: `Map`,
        dataTable: [['Lat', 'Long', 'Views'],
            [this.bedLocations[0][0], this.bedLocations[0][1], '<h2>Bed ' + this.bedNames[0] + '</h2>' +
            '<div> ' +
            '<strong>Likes:</strong> ' + this.value + '<br/>' +
            '<strong>Dislikes:</strong> '+ this.value + '<br/>' +
            '<strong>Comments:</strong> ' + this.value + ''+
            '</div>'],
            [this.bedLocations[1][0], this.bedLocations[1][1], '<div> Test</div>'],
        ],
        options: {'zoomLevel' : '18', showInfoWindow: true}
    }

    public bubbleChartOption = {
        chartType: `BubbleChart`,
        dataTable: [['ID', 'X', 'Y', 'Temperature'],
            ['',   80,  167,      120],
            ['',   79,  136,      130],
            ['',   78,  184,      50],
            ['',   72,  278,      230],
            ['',   81,  200,      210],
            ['',   72,  170,      100],
            ['',   68,  477,      80]
        ],
        options: {backgroundColor: 'none'}
    }

}
