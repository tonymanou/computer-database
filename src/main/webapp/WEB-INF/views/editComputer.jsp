<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
                    <c:if test="${errors != null}">
						<div class="alert alert-danger">
							<c:forEach items="${errors}" var="text">
								<p>${text}</p>
							</c:forEach>
						</div>
                    </c:if>
                    <form action="<c:url value="/computer/edit" />" method="POST">
                        <input type="hidden" name="computerId" value="${computer.id}"/>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name" value="${computer.name}">
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date" value="${computer.introducedDate}">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date" value="${computer.discontinuedDate}">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId" >
                                    <option value="0">--</option>
                                    <c:forEach items="${companies}" var="company">
                                        <option value="${company.id}"<c:if test="${company.id == computer.companyId}"> selected="selected"</c:if>>${company.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="<c:url value="/dashboard" />" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                    </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </section>
<jsp:include page="include/footer.jsp">
    <jsp:param name="needValidation" value="true" />
</jsp:include>