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
        dataTable: [['ID', 'X', 'Y', 'Colour (Likes)', 'Views(Size)'],
            ['1', 80,  90,      120,   47],
            ['2', 79,  90,      130,   11],
            ['3', 78,  90,      50,   22],
            ['4', 72,  90,      230,   51],
            ['5', 81,  90,      210,   117],
            ['6', 72,  90,      100,   3],
            ['7', 19,  14,      80,   44]
        ],
        options: {
            backgroundColor: 'none',
            width: 1140,
            height: 400,
            chartArea: {
                left: 0,
                top: 0,
                width: '100%',
                height: '100%'
            },
            hAxis: {
                gridlines: {count: 0},
                minValue: 0,
                maxValue: 100,
                viewWindow: {min: 0, max: 100}},
            vAxis: {
                gridlines: {count: 0},
                minValue: 0,
                maxValue: 100,
                viewWindow: {min: 0, max: 100}},
            colorAxis: {colors: ['blue', 'red']},
        }
    }

}
