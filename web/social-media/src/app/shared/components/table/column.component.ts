import { Component, Input, OnInit } from '@angular/core';
import { TableComponent } from './table.component';

@Component({
  selector: 'column',
  template: ``
})
export class ColumnComponent implements OnInit {

  @Input({ required: true })
  field: string = 'Invalid Field';

  @Input({ required: true })
  caption: string = 'Invalid Caption'

  constructor(public readonly table: TableComponent) {
  }

  ngOnInit() {
    this.table.addColumn(this);
  }

}
