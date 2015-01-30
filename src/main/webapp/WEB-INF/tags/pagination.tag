<%@ tag body-content="empty"%>
<%@ attribute name="page" required="true" type="com.tonymanou.computerdb.pagination.ComputerPage" %>
<%@ attribute name="pageSpan"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tony" tagdir="/WEB-INF/tags"%>
<c:set var="span" value="${pageSpan != null ? pageSpan : 4}" />
<c:set var="begin" value="${page.currentPage > span ? page.currentPage - span : 1}" />
<c:set var="end" value="${page.currentPage + span < page.numPages ? page.currentPage + span : page.numPages}" />
<ul class="pagination">
	<c:if test="${page.currentPage != 1}">
		<li><a href="<tony:link currentPage="1" page="${page}"/>" aria-label="Previous"> <span aria-hidden="true">&laquo;</span></a></li>
	</c:if>
	<c:forEach begin="${begin}" end="${end}" var="i" varStatus="loop">
		<li <c:if test="${i == page.currentPage}">class="active"</c:if>>
			<a <c:if test="${i != page.currentPage}">href="<tony:link currentPage="${i}" page="${page}"/>"</c:if>>${i}</a>
		</li>
	</c:forEach>
	<c:if test="${page.currentPage != page.numPages}">
		<li><a href="<tony:link currentPage="${page.numPages}" page="${page}"/>" aria-label="Next"> <span aria-hidden="true">&raquo;</span></a></li>
	</c:if>
</ul>