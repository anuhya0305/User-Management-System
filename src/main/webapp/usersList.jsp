<%@ page import= "com.codegnan.app.javawebapp06.database.UserDatabase" %>
<%@ page import= "com.codegnan.app.javawebapp06.entity.Credentials" %>
<%@ page import= "com.codegnan.app.javawebapp06.entity.User" %>
<%@ page import= "java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <%!
        UserDatabase userDatabase;
        List<User> usersList;
    %>

    <%
        userDatabase = new UserDatabase();
        usersList = userDatabase.getAllUsers();
    %>

    <h1>Users List</h1>
    <table border="1" width="100%">
    <tr>
        <th>User ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Username</th>
    </tr>

    <%
        for (User user : usersList) {
            Credentials credentials = user.getCredentials();
    %>
            <tr>
                <td><%= user.getUserId() %></td>
                <td><%= user.getFirstName() %></td>
                <td><%= user.getLastName() %></td>
                <td><%= credentials.getUsername() %></td>
            </tr>
    <%
        }
    %>

    </table>
</body>
</html>