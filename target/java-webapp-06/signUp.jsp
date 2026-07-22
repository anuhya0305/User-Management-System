<%@ page import= "com.codegnan.app.javawebapp06.database.UserDatabase" %>
<%@ page import= "com.codegnan.app.javawebapp06.entity.Credentials" %>
<%@ page import= "com.codegnan.app.javawebapp06.entity.User" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <%
         String firstName = request.getParameter("fname");
         String lastName = request.getParameter("lname");
         String username = request.getParameter("uname");
         String loginPassword = request.getParameter("lpass");

         Credentials credentials = new Credentials();
         credentials.setUsername(username);
         credentials.setLoginPassword(loginPassword);
         User user = new User();
         user.setFirstName(firstName);
         user.setLastName(lastName);
         user.setCredentials(credentials);

         UserDatabase userDatabase = new UserDatabase();
         boolean isUserSaved = userDatabase.save(user);
         if (isUserSaved) {
    %>
            <h1>Congratulations!!! Sign Up Successful.</h1>
    <%
          } else {
    %>
            <h1>Apologies!!!Please try again later</h1>
    <%
        }
    %>
</body>
</html>