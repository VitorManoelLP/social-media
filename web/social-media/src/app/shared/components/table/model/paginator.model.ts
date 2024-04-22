import { range } from "lodash";
import { PageParameter, Pageable } from "../../../http/http.param";

export class Paginator {

  private readonly DEFAULT_PAGE_SIZE = 5;

  readonly page: PageParameter = PageParameter.empty(this.DEFAULT_PAGE_SIZE);

  readonly pageStatus: PageStatusModel = {
    elementsToShow: [],
    itemsPeerPageRange: [this.DEFAULT_PAGE_SIZE, 10, 15]
  }

  public onNext() {

    const activeElement = this.pageStatus.elementsToShow.findIndex(element => element.active);

    function hasNextValue(pageStatus: PageStatusModel) {
      return activeElement !== null && pageStatus.elementsToShow.length - 1 != activeElement;
    }

    if (hasNextValue(this.pageStatus)) {
      this.page.withPage(this.page.page + 1);
    }

  }

  public onPrevious() {

    const activeElement = this.pageStatus.elementsToShow.findIndex(element => element.active);

    function hasPreviousValue() {
      return activeElement !== null && activeElement != 0;
    }

    if (hasPreviousValue()) {
      this.page.withPage(this.page.page - 1);
    }

  }

  onChangeStatus(page: Pageable<any>) {

    const totalPages = range(1, page.totalPages + 1);

    const currentPage = totalPages[page.number];

    const pagesToShow = [{ number: currentPage, active: true }];

    if (this.shouldAddNextPage(currentPage, page)) {
      this.addPage(pagesToShow, currentPage + 1)
    }

    if (this.shouldAddOneMoreNext(currentPage, page)) {
      this.addPage(pagesToShow, currentPage + 2)
    }

    if (this.shouldAddPreviousPage(page)) {
      this.addFirstElement(pagesToShow, currentPage - 1);
    }

    if (this.shouldAddOneMorePrevious(currentPage)) {
      this.addFirstElement(pagesToShow, currentPage - 2);
    }

    this.pageStatus.elementsToShow = pagesToShow;
  }

  private shouldAddNextPage(currentPage: number, page: Pageable<any>) {
    return currentPage < page.totalPages;
  }

  private shouldAddOneMoreNext(currentPage: number, page: Pageable<any>) {
    return currentPage + 2 <= page.totalPages;
  }

  private shouldAddOneMorePrevious(currentPage: number) {
    return currentPage - 2 > 0;
  }

  private shouldAddPreviousPage(page: Pageable<any>) {
    return page.number != 0;
  }

  private addPage(pagesToShow: PageToShow[], number: number) {
    pagesToShow.push({ number, active: false });
  }

  private addFirstElement(pagesToShow: PageToShow[], number: number) {
    pagesToShow.unshift({ number, active: false });
  }

}

interface PageStatusModel {
  elementsToShow: PageToShow[];
  itemsPeerPageRange: number[]
}

interface PageToShow {
  number: number;
  active: boolean;
}
