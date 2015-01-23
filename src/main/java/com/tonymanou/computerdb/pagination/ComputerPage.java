package com.tonymanou.computerdb.pagination;

public class ComputerPage {

  private int currentPage;
  private int numPages;
  private long numElementsPerPage;
  private long numElements;
  private String searchQuery;
  private ComputerOrder order;
  private OrderType orderType;

  public ComputerPage() {
    currentPage = 1;
    numPages = 1;
    numElementsPerPage = 10;
    order = ComputerOrder.ID;
    orderType = OrderType.ASC;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int page) {
    currentPage = numPages;
  }

  public int getNumPages() {
    return numPages;
  }

  public long getElementsOffset() {
    return (currentPage - 1) * numElementsPerPage;
  }

  public long getNumElementsPerPage() {
    return numElementsPerPage;
  }

  public void setNumElementsPerPage(long elements) {
    numElementsPerPage = elements;
    currentPage = 1;
    numPages = (int) Math.floor(0.5 + (double) elements / numElementsPerPage);
  }

  public long getNumElements() {
    return numElements;
  }

  public void setNumElements(long elements) {
    numElements = elements;
    numPages = (int) Math.floor(0.5 + (double) elements / numElementsPerPage);
  }

  public String getSearchQuery() {
    return searchQuery;
  }

  public void setSearchQuery(String search) {
    searchQuery = search;
  }

  public ComputerOrder getOrder() {
    return order;
  }

  public void setOrder(ComputerOrder order) {
    this.order = order;
  }

  public OrderType getOrderType() {
    return orderType;
  }

  public void setOrderType(OrderType type) {
    orderType = type;
  }

//  public static Builder getBuilder() {
//    return new Builder();
//  }
//
//  public static class Builder {
//
//    private ComputerPage cPage;
//
//    public Builder() {
//      cPage = new ComputerPage();
//    }
//
//    public void setCurrentPage(int page) {
//      cPage.currentPage = page;
//    }
//
//    public void setNumElementsPerPage(int elementsPerPage) {
//      numElementsPerPage = elementsPerPage;
//      numPages = elementsPerPage;
//      currentPage = 1;
//    }
//
//    public void setNumElements(int numElements) {
//      this.numElements = numElements;
//      numPages = (int) Math.floor(0.5 + (double) numElements / numElementsPerPage);
//    }
//
//    public void setSearchQuery(String searchQuery) {
//      cPage.searchQuery = searchQuery;
//    }
//
//    public void setOrder(ComputerOrder order) {
//      cPage.order = order;
//    }
//
//    public void setOrderType(OrderType orderType) {
//      cPage.orderType = orderType;
//    }
//
//    public ComputerPage build() {
//      if (cPage.numElementsPerPage < -1) {
//        cPage.numElementsPerPage = cPage.numElements;
//      }
//      return cPage;
//    }
//  }

  public enum OrderType {
    ASC, DESC
  }

  public static enum ComputerOrder {
    ID, NAME, COMPANY;
  }
}
