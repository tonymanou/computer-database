package com.tonymanou.computerdb.pagination;

public class ComputerPage {

  private int currentPage;
  private int numPages;
  private int numElementsPerPage;
  private int numElements;
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
    if (page < 1) {
      currentPage = 1;
    } else if (page > numPages) {
      currentPage = numPages;
    } else {
      currentPage = page;
    }
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

  public void setNumElementsPerPage(int elementsPerPage) {
    numElementsPerPage = elementsPerPage;
    numPages = elementsPerPage;
    currentPage = 1;
  }

  public int getNumElements() {
    return numElements;
  }

  public void setNumElements(int numElements) {
    this.numElements = numElements;
    numPages = (int) Math.floor(0.5 +  (double) numElements / numElementsPerPage);
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

  public void setOrderType(OrderType orderType) {
    this.orderType = orderType;
  }

  public enum OrderType {
    ASC, DESC
  }

  public static enum ComputerOrder {
    ID, NAME, COMPANY;
  }
}
