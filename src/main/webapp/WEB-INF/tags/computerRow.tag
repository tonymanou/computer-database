<%@ tag body-content="empty"%>
<%@ attribute name="computer" required="true"
	type="com.tonymanou.computerdb.dto.ComputerDTO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<tr>
	<td class="editMode"><input type="checkbox" name="cb" class="cb"
		value="${computer.id}"></td>
	<td><a href="<c:url value="/computer/edit?id=${computer.id}" />"
		onclick=""><c:out value="${computer.name}" /></a></td>
	<td>${computer.introducedDate}</td>
	<td>${computer.discontinuedDate}</td>
	<td><c:out value="${computer.companyName}" /></td>
</tr>