import { NgModule } from '@angular/core';
import { TableComponent } from './table.component';
import { ColumnComponent } from './column.component';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';

@NgModule({
  imports: [CommonModule, TableModule, InputTextModule],
  exports: [TableComponent, ColumnComponent],
  declarations: [TableComponent, ColumnComponent],
  providers: [],
})
export class TableCustomModule { }
