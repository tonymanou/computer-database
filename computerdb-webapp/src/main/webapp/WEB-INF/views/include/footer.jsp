<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<script src="<c:url value="/js/jquery.min.js" />"></script>
	<script src="<c:url value="/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/js/lang/message_${pageContext.request.locale}.js" />"></script>
	<script src="<c:url value="/js/dashboard.js" />"></script>
	<c:if test="${param.needValidation == true}">
		<script src="<c:url value="/js/validation.js" />"></script>
	</c:if>
</body>
</html>