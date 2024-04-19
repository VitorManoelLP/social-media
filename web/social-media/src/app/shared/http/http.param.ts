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
      requestParam = requestParam.includes('?') ? '&' + this.setSearch() : this.setSearch();
    }

    return requestParam;
  }

  private setSearch() {
    return this.search ? `?search=${this.search}` : '';
  }

  private setPage() {
    return this.page ? `${this.getPageRequestParam()}` : '';
  }

  private getPageRequestParam(): string {
    return `?page=${this.page?.page}&size=${this.page?.size}`;
  }


}

export interface PageParameter {
  page: number;
  size: number;
}

export class Pageable<T> {
  content: T[];
  pageNumber: number;
  pageSize: number;
  totalElements: number;

  constructor(content: T[], pageNumber: number, pageSize: number, totalElements: number) {
    this.content = content;
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.totalElements = totalElements;
  }

  static ofEmpty() {
    return new Pageable([], 0, 10, 0);
  }

}
