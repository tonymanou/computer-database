<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="include/header.jsp" />
    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                ${page.numElements} computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="" method="GET" class="form-inline">
                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" value="${page.searchQuery}" />
                        <input type="submit" id="searchsubmit" value="Filter by name" class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="<c:url value="/computer/add" />">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->
                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>Computer name</th>
                        <th>Introduced date</th>
                        <th>Discontinued date</th>
                        <th>Company</th>
                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                    <c:forEach items="${computers}" var="computer">
                        <tony:computerRow computer="${computer}" />
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<tony:pagination page="${page}"/>
			<div class="btn-group btn-group-sm pull-right" role="group">
				<a href="<tony:link currentPage="1" elementsPerPage="10" page="${page}"/>" class="btn btn-default <c:if test="${page.numElementsPerPage == 10}">active</c:if>">10</a>
				<a href="<tony:link currentPage="1" elementsPerPage="50" page="${page}"/>" class="btn btn-default <c:if test="${page.numElementsPerPage == 50}">active</c:if>">50</a>
				<a href="<tony:link currentPage="1" elementsPerPage="100" page="${page}"/>" class="btn btn-default <c:if test="${page.numElementsPerPage == 100}">active</c:if>">100</a>
			</div>
		</div>
	</footer>
<jsp:include page="include/footer.jsp" />