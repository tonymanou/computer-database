<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="include/header.jsp" />
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>Edit Computer</h1>
                    <c:choose>
                    <c:when test="${computer == null}">
						<div class="alert alert-danger">
							<h4>404: computer not found</h4>
						</div>
                    </c:when>
                    <c:otherwise>
                    <div class="label label-default pull-right">
                        id: ${computer.id}
                    </div>
                    <br>
                    <c:if test="${errorhs != null}">
						<div class="alert alert-danger">
							<c:forEach items="${errorhs}" var="text">
								<p>${text}</p>
							</c:forEach>
						</div>
                    </c:if>
                    <form:form action="" method="POST" modelAttribute="computer">
                        <fieldset>
                            <div class="form-group">
                                <label for="name">Computer name</label>
                                <form:errors path="name" />
                                <form:input path="name" class="form-control" placeholder="Computer name" />
                            </div>
                            <div class="form-group">
                                <label for="introducedDate">Introduced date</label>
                                <form:errors path="introducedDate" />
                                <form:input path="introducedDate" type="date" class="form-control" placeholder="Introduced date" />
                            </div>
                            <div class="form-group">
                                <label for="discontinuedDate">Discontinued date</label>
                                <form:errors path="discontinuedDate" />
                                <form:input path="discontinuedDate" type="date" class="form-control" placeholder="Discontinued date" />
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <form:errors path="companyId" />
                                <select class="form-control" id="companyId" name="companyId" >
                                    <option value="0">--</option>
                                    <c:forEach items="${companies}" var="company">
                                        <option value="${company.id}"<c:if test="${company.id == computer.companyId}"> selected="selected"</c:if>><c:out value="${company.name}" /></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary" />
                            or
                            <a href="<c:url value="/dashboard" />" class="btn btn-default">Cancel</a>
                        </div>
                    </form:form>
                    </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </section>
<jsp:include page="include/footer.jsp">
    <jsp:param name="needValidation" value="true" />
</jsp:include>