<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="include/header.jsp" />
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><fmt:message key="title.edit-computer" /></h1>
                    <c:choose>
                    <c:when test="${computerDTO.id == null}">
						<div class="alert alert-danger">
							<h4>404: <fmt:message key="form.computer-not-found" /></h4>
						</div>
                    </c:when>
                    <c:otherwise>
                    <div class="label label-default pull-right">
                        id: ${computerDTO.id}
                    </div>
                    <br>
                    <form:form action="" method="POST" modelAttribute="computerDTO">
						<form:errors path="" cssClass="alert alert-danger" element="div" />
                        <fieldset>
                            <div class="form-group">
                                <label for="name"><fmt:message key="text.computer-name" /></label>
                                <form:errors path="name" cssClass="text-danger pull-right" />
                                <fmt:message key="text.name" var="text_name" />
                                <form:input path="name" class="form-control" placeholder="${text_name}" />
                            </div>
                            <fmt:message key="text.date-format" var="date_format" />
                            <div class="form-group">
                                <label for="introducedDate"><fmt:message key="text.introduced-date" /></label>
                                <form:errors path="introducedDate" cssClass="text-danger pull-right" />
                                <form:input path="introducedDate" type="date" class="form-control" placeholder="${date_format}" />
                            </div>
                            <div class="form-group">
                                <label for="discontinuedDate"><fmt:message key="text.discontinued-date" /></label>
                                <form:errors path="discontinuedDate"  cssClass="text-danger pull-right"/>
                                <form:input path="discontinuedDate" type="date" class="form-control" placeholder="${date_format}" />
                            </div>
                            <div class="form-group">
                                <label for="companyId"><fmt:message key="text.company" /></label>
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
                            <input type="submit" value="<fmt:message key="button.edit" />" class="btn btn-primary" />
                            <fmt:message key="text.or" />
                            <a href="<c:url value="/dashboard" />" class="btn btn-default"><fmt:message key="button.cancel" /></a>
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