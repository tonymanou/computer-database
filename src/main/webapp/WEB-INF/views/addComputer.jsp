<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="include/header.jsp" />
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>Add Computer</h1>
                    <c:if test="${errors != null}">
						<div class="alert alert-danger">
							<c:forEach items="${errors}" var="text">
								<p>${text}</p>
							</c:forEach>
						</div>
                    </c:if>
                    <form:form action="" method="POST" modelAttribute="computerDTO">
						<form:errors path="" cssClass="alert alert-danger" element="div" />
                        <fieldset>
                            <div class="form-group">
                                <label for="name">Computer name</label>
                                <form:errors path="name" cssClass="text-danger pull-right" />
                                <form:input path="name" class="form-control" placeholder="Computer name" />
                            </div>
                            <div class="form-group">
                                <label for="introducedDate">Introduced date</label>
                                <form:errors path="introducedDate" cssClass="text-danger pull-right" />
                                <form:input path="introducedDate" type="date" class="form-control" placeholder="Introduced date" />
                            </div>
                            <div class="form-group">
                                <label for="discontinuedDate">Discontinued date</label>
                                <form:errors path="discontinuedDate"  cssClass="text-danger pull-right"/>
                                <form:input path="discontinuedDate" type="date" class="form-control" placeholder="Discontinued date" />
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <form:errors path="companyId" cssClass="text-danger pull-right" />
                                <select class="form-control" id="companyId" name="companyId" >
                                    <option value="">--</option>
                                    <c:forEach items="${companies}" var="company">
                                        <option value="${company.id}"<c:if test="${company.id == computerDTO.companyId}"> selected="selected"</c:if>><c:out value="${company.name}" /></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary" />
                            or
                            <a href="<c:url value="/dashboard" />" class="btn btn-default">Cancel</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
<jsp:include page="include/footer.jsp">
    <jsp:param name="needValidation" value="true" />
</jsp:include>