import { Component, Input, OnInit } from "@angular/core";
import { Pageable } from "../../http/http.param";
import { ColumnComponent } from "./column.component";
import { interval, of, tap } from "rxjs";
import { Paginator } from "./model/paginator.model";
import { TableButtonComponent } from "./table-button.component";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html'
})
export class TableComponent implements OnInit {

  @Input({ alias: 'call', required: true })
  public callSearch!: Function;

  @Input({ required: true })
  public buildSearch!: Function;

  @Input()
  public handlingFunction: Function = () => { };

  public loading: boolean = false;

  public columns: ColumnComponent[] = [];
  public buttons: TableButtonComponent[] = [];

  public page: Pageable<any> = Pageable.ofEmpty();

  public paginator: Paginator = new Paginator();

  private _search: string = '';

  get pageStatus() {
    return this.paginator.pageStatus;
  }

  ngOnInit(): void {
    this.search();
  }

  onSearch(event: Event) {
    const search = (event.target as HTMLInputElement).value;
    this._search = `(${this.buildSearch(search)})`;
    if (!search.length) {
      this._search = '';
    }
    this.search();
  }

  public onPage(page: any) {
    this.paginator.page.withPage(page.number - 1);
    this.search()
  }

  public onNext() {
    this.paginator.onNext();
    this.search();
  }

  public onPrevious() {
    this.paginator.onPrevious();
    this.search();
  }

  public addColumn(column: ColumnComponent) {
    this.columns.push(column);
  }

  public addButtons(button: TableButtonComponent) {
    this.buttons.push(button);
  }

  public changeNumberPeerPage(event: Event) {
    const value = (event.target as HTMLSelectElement).value;
    this.paginator.page
      .withPage(0)
      .withSize(Number(value));
    this.search();
  }

  search() {
    this.loading = true; this.paginator.page
    this.callSearch(this._search, this.paginator.page)
      .pipe(tap((page: Pageable<any>) => {
        this.loading = false;
        if (page.content.length) {
          page.content.forEach(content => this.handlingFunction(content));
        }
      }))
      .subscribe((contentPage: Pageable<any>) => this.onChangePage(contentPage));
  }


  private onChangePage(contentPage: Pageable<any>) {
    this.page = contentPage;
    this.paginator.onChangeStatus(contentPage);
  }

}
