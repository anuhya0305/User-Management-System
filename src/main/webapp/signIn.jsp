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
            String username = request.getParameter("uname");
            String loginPassword = request.getParameter("lpass");

            UserDatabase userDatabase = new UserDatabase();
            User user = userDatabase.findByUsernameAndLoginPassword(username, loginPassword);
            if (user != null) {
       %>
               <h1>Welcome <%= user.getFirstName() %> <%= user.getLastName() %>!!!</h1>
       <%
             } else {
       %>
               <h1>Invalid Access</h1>
               <a href="userSignInForm.html">Try Again</a>
       <%
           }
       %>
</body>
</html>