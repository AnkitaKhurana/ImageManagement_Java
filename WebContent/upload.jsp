<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page isELIgnored="false"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload Area</title>

<style type="text/css">
input.delete {
	background-image: url(delete.png);
	width: 80px;
	height: 80px;
}

input.edit {
	background-image: url(edit.png);
	width: 80px;
	height: 80px;
	margin-right: 16px;
}
</style>
</head>
<body>
	<h3>Image Upload:</h3>
	Select an Image to upload:
	<br />
	<form action="Image" method="post" enctype="multipart/form-data">
		<input type="file" name="pic" accept="image/*"> <br /> <input
			type="submit" value="submit">
	</form>

	<c:if test="${not empty errorMessage}">
		<c:out value="${errorMessage}" />
	</c:if>


</body>
</html>