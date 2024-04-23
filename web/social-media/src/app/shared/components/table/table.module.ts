import { NgModule } from '@angular/core';
import { TableComponent } from './table.component';
import { ColumnComponent } from './column.component';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { TableButtonComponent } from './table-button.component';

@NgModule({
  imports: [CommonModule, TableModule, InputTextModule],
  exports: [TableComponent, ColumnComponent, TableButtonComponent],
  declarations: [TableComponent, ColumnComponent, TableButtonComponent],
  providers: [],
})
export class TableCustomModule { }
