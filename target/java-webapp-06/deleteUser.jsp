<%@ page import="com.codegnan.app.javawebapp06.database.UserDatabase" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Delete User</title>
</head>
<body>

<%
    String username = request.getParameter("username");

    UserDatabase userDatabase = new UserDatabase();
    boolean isUserDeleted = userDatabase.deleteByUsername(username);

    if (isUserDeleted) {
%>

    <h1>User successfully removed!</h1>

<%
    } else {
%>

    <h1>Invalid Username. Please try again.</h1>
    <a href="deleteUserForm.html">Try Again</a>

<%
    }
%>

</body>
</html>