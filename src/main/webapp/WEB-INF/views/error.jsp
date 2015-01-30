<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="include/header.jsp" />
<section id="main">
	<div class="container">
		<c:choose>
			<c:when test="${errorCode != null}">
				<div class="alert alert-danger">
					<h4>
						<c:choose>
							<c:when test="${errorCode == 403}"><fmt:message key="error.error403" /></c:when>
							<c:when test="${errorCode == 404}"><fmt:message key="error.error404" /></c:when>
							<c:when test="${errorCode == 500}"><fmt:message key="error.error500" /></c:when>
							<c:otherwise><fmt:message key="error.other" /> ${errorCode}</c:otherwise>
						</c:choose>
					</h4>
					<c:if test="${errorMessage != null && errorMessage != \"\" }">
						<p>${errorMessage}</p>
					</c:if>
					<img class="centered" alt="Sad panda" src="<c:url value="/images/sad_panda.png" />">
				</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-info"><fmt:message key="error.no-error" /></div>
			</c:otherwise>
		</c:choose>
		<a href="<c:url value="/" />"><fmt:message key="link.go-back-home" /></a>
	</div>
</section>
<jsp:include page="include/footer.jsp" />