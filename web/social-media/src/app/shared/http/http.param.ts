export class HttpParam {

  private constructor(readonly resource: string, readonly body?: any, readonly search?: string, readonly page?: PageParameter) {
    this.resource = resource;
    this.body = body;
    this.search = search;
  }

  static of(value: { resource: string, readonly body?: any, readonly search?: string, readonly page?: PageParameter }) {
    return new HttpParam(value.resource, value.body, value.search, value.page);
  }

  public buildRequest() {
    return `${this.resource}${this.buildRequestParam()}`
  }

  private buildRequestParam() {

    let requestParam = '';

    if (this.page) {
      requestParam = this.setPage();
    }

    if (this.search) {
      requestParam = requestParam += requestParam.includes('?') ? '&' + this.setSearch() : '?' + this.setSearch();
    }

    return requestParam;
  }

  private setSearch() {
    return this.search ? `search=${this.search}` : '';
  }

  private setPage() {
    return this.page ? `${this.getPageRequestParam()}` : '';
  }

  private getPageRequestParam(): string {
    return `?page=${this.page?.page}&size=${this.page?.size}`;
  }


}

export class PageParameter {

  _page: number;
  _size: number;

  constructor(page: number, size: number) {
    this._page = page;
    this._size = size;
  }

  get page() {
    return this._page;
  }

  get size() {
    return this._size;
  }

  withSize(size: number) {
    this._size = size;
    return this;
  }

  withPage(page: number) {
    this._page = page;
    return this;
  }

  static empty(sizeDefault: number): PageParameter {
    return new PageParameter(0, sizeDefault);
  }

}

export class Pageable<T> {
  content: T[];
  number: number;
  pageSize: number;
  totalElements: number;
  totalPages: number;

  constructor(content: T[], number: number, pageSize: number, totalElements: number, totalPages: number) {
    this.content = content;
    this.number = number;
    this.pageSize = pageSize;
    this.totalElements = totalElements;
    this.totalPages = totalPages;
  }

  static ofEmpty() {
    return new Pageable([], 0, 10, 0, 0);
  }

}
