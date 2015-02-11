package com.tonymanou.computerdb.pagination;

public class ComputerPage {

  private int currentPage;
  private int numPages;
  private int numElementsPerPage;
  private long numElements;
  private String searchQuery;
  private ComputerOrder order;
  private OrderType orderType;

  private ComputerPage() {
    currentPage = 1;
    numPages = 1;
    numElementsPerPage = 10;
    order = ComputerOrder.ID;
    orderType = OrderType.ASC;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public int getNumPages() {
    return numPages;
  }

  public int getElementsOffset() {
    return (currentPage - 1) * numElementsPerPage;
  }

  public int getNumElementsPerPage() {
    return numElementsPerPage;
  }

  public long getNumElements() {
    return numElements;
  }

  public String getSearchQuery() {
    return searchQuery;
  }

  public ComputerOrder getOrder() {
    return order;
  }

  public OrderType getOrderType() {
    return orderType;
  }

  public static Builder getBuilder() {
    return new Builder();
  }

  public static class Builder {
    private ComputerPage page;

    private Builder() {
      page = new ComputerPage();
    }

    public void setCurrentPage(int pageIndex) {
      page.currentPage = pageIndex;
    }

    public void setNumElementsPerPage(int elementsPerPage) {
      page.numElementsPerPage = elementsPerPage;
    }

    public void setNumElements(long number) {
      page.numElements = number;
    }

    public void setSearchQuery(String search) {
      page.searchQuery = search;
    }

    public String getSearchQuery() {
      return page.searchQuery;
    }

    public void setOrder(ComputerOrder order) {
      page.order = order;
    }

    public void setOrderType(OrderType orderType) {
      page.orderType = orderType;
    }

    public ComputerPage build() {
      page.numPages = (int) (page.numElements / page.numElementsPerPage);
      if (page.numPages <= 0) {
        page.numPages = 1;
      } else if (page.numElements % page.numElementsPerPage != 0) {
        page.numPages++;
      }

      if (page.currentPage < 1) {
        page.currentPage = 1;
      } else if (page.currentPage > page.numPages) {
        page.currentPage = page.numPages;
      }

      return page;
    }
  }

  public enum OrderType {
    ASC, DESC
  }

  public static enum ComputerOrder {
    ID, NAME, COMPANY;
  }
}
