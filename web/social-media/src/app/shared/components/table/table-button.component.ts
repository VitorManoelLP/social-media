import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TableComponent } from './table.component';

@Component({
  selector: 'table-button',
  template: ``
})

export class TableButtonComponent implements OnInit {

  @Input()
  description: string = 'Invalid Description';

  @Input()
  icon: string = '';

  @Output()
  onClick: EventEmitter<any> = new EventEmitter();

  @Input()
  show: Function = () => { return true };

  constructor(public readonly table: TableComponent) {
  }

  ngOnInit() {
    this.table.addButtons(this);
  }

  click(value: any) {
    this.onClick.emit(value);
  }

}
