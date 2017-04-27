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


    ngOnInit(): void {
        this.updateLineChart();
        this.updateBarChart();
        this.updateMap();
        this.updateBubble();
        this.updatetop20likes();
        this.updatetop20dislikes();
        this.updatetop20comments();
    }

    public updateLineChart(): void{
        this.adminService.getViewsPerHour()
            .subscribe(result => {
                this.line_ChartData["dataTable"] = result;
                this.line_ChartData = Object.create(this.line_ChartData);
            }, err => console.log(err));
    }

    public updateBarChart(): void{

        this.adminService.getViewsPerHour()
            .subscribe(result => {
                this.columnChartOptions["dataTable"] = result;
                this.columnChartOptions = Object.create(this.columnChartOptions);
            }, err => console.log(err));
    }

    public updateMap(): void{
        this.adminService.getBedMetadataForMap()
            .subscribe(result => {
                this.mapOptions.dataTable = this.createDataTableMap(this.processMapData(result));
                this.mapOptions = Object.create(this.mapOptions);
            }, err => console.log(err));
    }

    public updateBubble(): void{
        this.adminService.getBedMetadataForBubble()
            .subscribe(result => {
                this.bubbleOption.dataTable = this.createDataTableBubble(result);
                this.bubbleOption = Object.create(this.bubbleOption);
            }, err => console.log(err));
    }

    public updatetop20likes(): void{
        this.adminService.get20MostLikes()
            .subscribe(result => {
                this.top20ChartDataLikes["dataTable"] = this.createDataTableTop20(result);
                this.top20ChartDataLikes = Object.create(this.top20ChartDataLikes);
            }, err => console.log(err));
    }

    public updatetop20dislikes(): void{

        this.adminService.get20MostDisLikes()
            .subscribe(result => {
                this.top20ChartDataDisLikes["dataTable"] = this.createDataTableTop20(result);
                this.top20ChartDataDisLikes = Object.create(this.top20ChartDataDisLikes);
            }, err => console.log(err));
    }

    public updatetop20comments(): void{

        this.adminService.get20MostComments()
            .subscribe(result => {
                this.top20ChartDataComments["dataTable"] = this.createDataTableTop20(result);
                this.top20ChartDataComments = Object.create(this.top20ChartDataComments);
            }, err => console.log(err));
    }

    public top20ChartDataLikes = {
        chartType: `ColumnChart`,
        dataTable: [['Hour', 'Views'],
            ['0',  0], ['1',  0], ['2',  0], ['3',  0], ['4',  0], ['5',  0], ['6',  0], ['7',  0], ['8',  0], ['9',  0], ['10',  0], ['11',  0], ['12',  0],
            ['13',  0], ['14',  0], ['15',  0], ['16',  0], ['17',  0], ['18',  0], ['19',  0], ['20',  0], ['21',  0], ['22',  0], ['23',  0],
        ],
        options: {'title': 'Top 20 Plants with Most Likes', hAxis : {'title' :'Cultivar Name'}, vAxis : {'title' :'Amount of Likes'}, legend:{position: 'none'}},
    }

    public top20ChartDataDisLikes = {
        chartType: `ColumnChart`,
        dataTable: [['Hour', 'Views'],
            ['0',  0], ['1',  0], ['2',  0], ['3',  0], ['4',  0], ['5',  0], ['6',  0], ['7',  0], ['8',  0], ['9',  0], ['10',  0], ['11',  0], ['12',  0],
            ['13',  0], ['14',  0], ['15',  0], ['16',  0], ['17',  0], ['18',  0], ['19',  0], ['20',  0], ['21',  0], ['22',  0], ['23',  0],
        ],
        options: {'title': 'Top 20 Plants with Most DisLikes', hAxis : {'title' :'Cultivar Name'}, vAxis : {'title' :'Amount of Dislikes'}, legend:{position: 'none'}},
    }

    public top20ChartDataComments = {
        chartType: `ColumnChart`,
        dataTable: [['Hour', 'Views'],
            ['0',  0], ['1',  0], ['2',  0], ['3',  0], ['4',  0], ['5',  0], ['6',  0], ['7',  0], ['8',  0], ['9',  0], ['10',  0], ['11',  0], ['12',  0],
            ['13',  0], ['14',  0], ['15',  0], ['16',  0], ['17',  0], ['18',  0], ['19',  0], ['20',  0], ['21',  0], ['22',  0], ['23',  0],
        ],
        options: {'title': 'Top 20 Plants with Most Comments', hAxis : {'title' :'Cultivar Name'}, vAxis : {'title' :'Amount of Comments'}, legend:{position: 'none'}},
    }

    public line_ChartData = {
        chartType: `LineChart`,
        dataTable: [['Hour', 'Views'],
            ['0',  0], ['1',  0], ['2',  0], ['3',  0], ['4',  0], ['5',  0], ['6',  0],
            ['7',  0], ['8',  0], ['9',  0], ['10',  0], ['11',  0], ['12',  0], ['13',  0],
            ['14',  0], ['15',  0], ['16',  0], ['17',  0], ['18',  0], ['19',  0], ['20',  0], ['21',  0], ['22',  0], ['23',  0],
        ],
        options: {'title': 'Flower View Counts over Time', hAxis : {'title' :'Time (hours)'}, vAxis : {'title' :'Flower View Counts'}},
    };


    public columnChartOptions = {
        chartType: `ColumnChart`,
        dataTable: [['Hour', 'Views'],
            ['0',  0], ['1',  0], ['2',  0], ['3',  0], ['4',  0], ['5',  0], ['6',  0], ['7',  0], ['8',  0], ['9',  0], ['10',  0], ['11',  0], ['12',  0],
            ['13',  0], ['14',  0], ['15',  0], ['16',  0], ['17',  0], ['18',  0], ['19',  0], ['20',  0], ['21',  0], ['22',  0], ['23',  0],
        ],
        options: {'title': 'Flower View Counts over Time', hAxis : {'title' :'Time (hours)'}, vAxis : {'title' :'Flower View Counts'}},
    }

    //Latitude and Longitude of POSSIBLE gardenLocations (TODO:confirm locations with customer)
    public bedLocations = [[45.593823, -95.875248], [45.593831, -95.875525], [45.593113, -95.877688], [45.593008, -95.876990],
        [45.593512, -95.876351], [45.593284, -95.877349], [45.593689, -95.874984], [45.593712, -95.875958], [45.593826, -95.875539],
        [45.593560, -95.875597], [45.593592, -95.875406], [45.593357, -95.875850], [45.593461, -95.875177]];

    //TODO: Same as previous todo, these are kind of random bubbles
    public bedLocationsForBubble = [[36,33], [37,25], [49,16], [54,56], [61,24], [61,40],
        [19,14], [30,4], [34,10], [42,25.5], [46.5,29], [46.1,45.3]]

    public mapOptions = {
        chartType: `Map`,
        dataTable: [['Lat', 'Long', 'Views'],
            [this.bedLocations[0][0], this.bedLocations[0][1], 'Update Graph'],
        ],
        options: {'zoomLevel' : '18', title : false, showInfoWindow: true}
    }

    public bubbleOption = {
        chartType: `BubbleChart`,
        dataTable: [['Bed Number', 'X', 'Y', 'Likes (Colour)', 'Views (Size)'],
            ['10',    36,      33,                1,             1],
            ['11',    37,      25,                1,             1],
            ['13',    49,      16,                1,             1],
            ['1N',    54,      56,                1,             1],
            ['1S',    61,      24,                1,             1],
            ['2N',    61,      40,                1,             1],
            ['2S',    19,      14,                1,             1],
            ['5' ,    30,       4,                1,             1],
            ['6' ,    34,      10,                1,             1],
            ['7' ,    42,    25.5,                1,             1],
            ['9' ,    46.5,    29,                1,             1],
            ['LG',    46.1,  45.3,                1,             1]
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
                title: null,
                gridlines: {count: 0},
                minValue: 0,
                maxValue: 100,
                viewWindow: {min: 0, max: 100}},
            vAxis: {
                title: null,
                gridlines: {count: 0},
                minValue: 0,
                maxValue: 100,
                viewWindow: {min: 0, max: 100}},
            colorAxis: {colors: ['blue', 'purple']},
            bubble: {
                textStyle: {
                    fontSize: 12,
                    color: `white`,
                },
            },
        }
    }





    /**
     * Creates the tooltip HTML for each of the beds.
     * mapData is data from the Server that is an array of objects that look like
     * {likes : number, disklikes : number, comments : number}
     *
     * This function creates a pretty HTML tooltip that is displayed when you click on a bed in the Google zMap
     * @param mapData
     * @returns {Array<string>}
     */
    private processMapData(mapData : any[]) : string[]
    {
        var buffer : Array<string> = new Array<string>(mapData.length);

        for(var i : number = 0; i < mapData.length; i++)
        {
            buffer[i] =  '<h2>Bed ' + mapData[i]["gardenLocation"] + '</h2>';
            buffer[i] += '<div><strong>Likes:</strong> ' + mapData[i]["likes"]       + '<br/>';
            buffer[i] += '<div><strong>Dislikes:</strong> ' + mapData[i]["dislikes"] + '<br/>';
            buffer[i] += '<div><strong>Comments:</strong> ' + mapData[i]["comments"] + '<br/>';
            buffer[i] += '</div>';
        }

        return buffer;
    }

    private createDataTableBubble(entry : any[]) : any[][]
    {

        var dataTable : any[][] = new Array<Array<any>>(entry.length+1);

        for(var i : number = 0; i < entry.length+1; i++)
        {
            dataTable[i] = new Array<any>(5);
        }

        dataTable[0][0] = 'Bed';
        dataTable[0][1] = 'X';
        dataTable[0][2] = 'Y';
        dataTable[0][3] = 'Likes (Colour)';
        dataTable[0][4] = 'Views (Size)';
        for(var i : number = 0; i < entry.length; i++)
        {
            dataTable[i+1][0] = entry[i]['gardenLocation'];
            dataTable[i+1][1] = this.bedLocationsForBubble[i][0];
            dataTable[i+1][2] = this.bedLocationsForBubble[i][1];
            dataTable[i+1][3] = entry[i]['likes'];
            dataTable[i+1][4] = entry[i]['pageViews'];
        }
        return dataTable;

    }

    private createDataTableMap(toolWindow: string[]) : any[][]
    {
        var dataTable : any[][] = new Array<Array<any>>(toolWindow.length+1);

        for(var i : number = 0; i < toolWindow.length+1; i++)
        {
            dataTable[i] = new Array<any>(3);
        }

        dataTable[0][0] = 'Lat';
        dataTable[0][1] = 'Long';
        dataTable[0][2] = 'ToolWindow';
        for(var i : number = 0; i < toolWindow.length; i++)
        {


            dataTable[i+1][0] = this.bedLocations[i][0];
            dataTable[i+1][1] = this.bedLocations[i][1];
            dataTable[i+1][2] = toolWindow[i];
            console.log(toolWindow[i]);
        }

        return dataTable;
    }

    private createDataTableTop20(toolWindow: string[]): any[][]{
        var dataTable: any [][] = new Array<Array<any>>(toolWindow.length+1);
        for(var i : number = 0; i < toolWindow.length+1; i++){
            dataTable[i] = new Array<any>(2);
        }
        dataTable[0][0] = "Plant ID";
        dataTable[0][1] = "";
        for(var i : number = 0; i < toolWindow.length; i++)
        {


            dataTable[i+1][0] = toolWindow[i]['cultivarName'];
            dataTable[i+1][1] = toolWindow[i]['likes'];
        }

        return dataTable;
    }

}
