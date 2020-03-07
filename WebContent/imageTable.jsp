<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<table border=1px; width="100%">
		<tr>
			<td>SNO</td>
			<td>Image Name</td>
			<td>Image Size</td>
			<td>Image</td>
			<td>Actions</td>
		</tr>

		<c:forEach items="${pictureList}" var="picture" varStatus="status">
			<tr>

				<td>${status.index + 1}</td>
				<td>${nameList[status.index]}</td>
				<td>${sizeList[status.index]}MB</td>
				<td><img height="150" width="100"
					src="data:image/jpg;base64,${picture}" /></td>
				<td>
					<form action="Image" method="get">
						Edit <input type="submit" name="" class="edit"> <input
							type="hidden" name="action" value="put" /> <input type="hidden"
							name="content" value="${numberList[status.index]}" />

					</form>
					<form action="Image" method="post">
						Delete <input type="image" name="" class="delete"> <input
							type="hidden" name="action" value="delete"
							value="${numberList[status.index]}" /> <input type="hidden"
							name="content" value="${numberList[status.index]}" />

					</form>
				</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>