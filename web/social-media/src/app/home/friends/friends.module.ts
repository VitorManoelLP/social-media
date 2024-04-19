import { NgModule } from '@angular/core';
import { FriendsComponent } from './friends.component';
import { CommonModule } from '@angular/common';
import { TableComponent } from '../../shared/components/table/table.component';

@NgModule({
  declarations: [FriendsComponent],
  exports: [FriendsComponent],
  imports: [
    CommonModule,
    TableComponent
  ],
})
export class FriendsModule { }
