<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="include/header.jsp" />
<section id="main">
	<div class="container">
		<c:choose>
			<c:when test="${errorCode != null}">
				<div class="alert alert-danger">
					<h4>
						<c:choose>
							<c:when test="${errorCode == 403}">Error 403: Access denied.</c:when>
							<c:when test="${errorCode == 404}">Error 404: Page not found.</c:when>
							<c:when test="${errorCode == 500}">Error 500: Server error.</c:when>
							<c:otherwise>Error ${errorCode}</c:otherwise>
						</c:choose>
					</h4>
					<c:if test="${errorMessage != null && errorMessage != \"\" }">
						<p>${errorMessage}</p>
					</c:if>
				</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-info">No error occurred.</div>
			</c:otherwise>
		</c:choose>
		<a href="<c:url value="/" />">Go back to home.</a>
	</div>
</section>
<jsp:include page="include/footer.jsp" />