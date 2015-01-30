<%@ tag body-content="empty"%>
<%@ attribute name="target"%>
<%@ attribute name="currentPage" required="true"%>
<%@ attribute name="elementsPerPage"%>
<%@ attribute name="page" required="true" type="com.tonymanou.computerdb.pagination.ComputerPage"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="elems" value="${elementsPerPage == null ? page.numElementsPerPage : elementsPerPage}" />
<c:url value="${target}">
	<c:param name="page">${currentPage}</c:param>
	<c:if test="${elems != 10}"><c:param name="elems">${elems}</c:param></c:if>
	<c:if test="${page.searchQuery != null && page.searchQuery != ''}"><c:param name="search">${page.searchQuery}</c:param></c:if>
	<c:if test="${page.order != 'ID'}"><c:param name="order">${page.order}</c:param></c:if>
	<c:if test="${page.orderType != 'ASC'}"><c:param name="ordertype">${page.orderType}</c:param></c:if>
</c:url>