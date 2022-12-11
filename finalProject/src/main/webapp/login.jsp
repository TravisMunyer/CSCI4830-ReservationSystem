<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>

<div align =center>
<h1>Login</h1>
<form action =LoginServlet method=post>
<table>
<tr><td>UserName: </td><td><input type=text name=txtName></td></tr>
<tr><td>Password: </td><td><input type=password name=password></td></tr>
<tr><td></td><td><input type=submit value=login></td></tr>
</table>
</form>
</div>
</body>
</html>