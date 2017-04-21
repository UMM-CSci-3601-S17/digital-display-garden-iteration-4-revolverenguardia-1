/**
 * Created by hamme503 on 4/21/17.
 */

import {Component, Input, EventEmitter, Output} from "@angular/core";
import { BedListService } from "../garden/components/bed_list/src/bed-list.service";
import { PlantListService } from "../garden/components/plant_list/src/plant-list.service";
import { Bed } from '../garden/components/bed_list/src/bed';
import {DropdownValue} from "./DropdownValue";

@Component({
    selector: 'bed-dropdown',
    //templateUrl: 'bed-dropdown.component.html'
    template: `
    <ul>
      <li *ngFor="#value of values" (click)="select(value.value)">{{value.label}}</li>
    </ul>
  `
})

export class BedDropdownComponent {

    constructor(private bedListService: BedListService) {

        this.select = new EventEmitter();
    }

    public beds = this.bedListService.getBedNames();

    @Input()
    values: DropdownValue[];

    @Output()
    select: EventEmitter<any>;


    selectItem(value) {
        this.select.emit(value);
    }


}