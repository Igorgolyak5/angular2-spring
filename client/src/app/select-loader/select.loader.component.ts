import {Component, ElementRef, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SelectLoaderService} from "./select.loader.service";

@Component({
    selector: 'select-loader',
    templateUrl: 'select.loader.component.html'
})
export class SelectLoaderComponent implements OnInit {

    url: string;
    items: any = [];
    @Input() bindModelData: any;
    @Input() isMultiple: boolean = false;

    // note that this must be named as the input name + "Change"
    @Output() bindModelDataChange: any = new EventEmitter();

    constructor(elm: ElementRef, private selectLoaderService: SelectLoaderService) {
        this.url = elm.nativeElement.getAttribute('url');
    }

    ngOnInit(): void {
        this.refreshAll();
    }

    refreshAll() {
        this.selectLoaderService.getAll(this.url).subscribe(result => {
            this.items = result.json();
            this.onChange(this.items);
        });
    }

    getAll() {
        return this.items;
    }

    onChange(event) {
        if (this.isMultiple) {
            this.bindModelData = event;
            this.bindModelDataChange.emit(event);
        } else {
            this.bindModelData = event[0];
            this.bindModelDataChange.emit(event[0]);
        }
    }
}
