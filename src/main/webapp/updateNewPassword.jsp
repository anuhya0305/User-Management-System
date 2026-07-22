<%@ page import= "com.codegnan.app.javawebapp06.database.UserDatabase" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
   <%
            String username = request.getParameter("uname");
            String newLoginPassword = request.getParameter("newpass");

            UserDatabase userDatabase = new UserDatabase();
            boolean isNewPasswordUpdated = userDatabase.updateLoginPasswordByUsername(username, newLoginPassword);
            if (isNewPasswordUpdated) {
       %>
               <h1>Password successfully updated!!!</h1>
               <a href="userSignInForm.html">Sign In Now</a>
       <%
             } else {
       %>
               <h1>Invalid Username. Please try again later</h1>
               <a href="forgotPasswordForm.html">Try Again</a>
       <%
           }
       %>
</body>
</html>