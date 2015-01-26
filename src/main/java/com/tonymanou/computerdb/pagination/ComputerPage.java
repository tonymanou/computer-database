package com.tonymanou.computerdb.pagination;

public class ComputerPage {

  private int currentPage;
  private int numPages;
  private int numElementsPerPage;
  private int numElements;
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

  public int getNumElements() {
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

    public void setNumElements(int num) {
      page.numElements = num;
    }

    public void setSearchQuery(String search) {
      page.searchQuery = search;
    }

    public void setOrder(ComputerOrder order) {
      page.order = order;
    }

    public void setOrderType(OrderType orderType) {
      page.orderType = orderType;
    }

    public ComputerPage build() {
      page.numPages = page.numElements / page.numElementsPerPage;
      if (page.numElements % page.numElementsPerPage != 0) {
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
