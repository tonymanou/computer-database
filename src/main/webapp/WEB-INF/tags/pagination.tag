<%@ tag body-content="empty"%>
<%@ attribute name="currentPage" required="true"%>
<%@ attribute name="pageCount" required="true"%>
<%@ attribute name="pageSpan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="span" value="${pageSpan != null ? pageSpan : 4}" />
<c:set var="begin" value="${currentPage > span ? currentPage - span : 1}" />
<c:set var="end" value="${currentPage + span < pageCount ? currentPage + span : pageCount}" />
<ul class="pagination">
	<c:if test="${currentPage != 1}">
		<li><a href="?page=1" aria-label="Previous"> <span aria-hidden="true">&laquo;</span></a></li>
	</c:if>
	<c:forEach begin="${begin}" end="${end}" var="i" varStatus="loop">
		<li <c:if test="${i == currentPage}">class="active"</c:if>>
			<a <c:if test="${i != currentPage}">href="?page=${i}"</c:if>>${i}</a>
		</li>
	</c:forEach>
	<c:if test="${currentPage != pageCount}">
		<li><a href="?page=${pageCount}" aria-label="Next"> <span aria-hidden="true">&raquo;</span></a></li>
	</c:if>
</ul>