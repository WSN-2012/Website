<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="server.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/template_style.css" rel="stylesheet" type="text/css" />
<link href="css/coda-slider.css" rel="stylesheet" type="text/css" media="screen" charset="utf-8" />
<script src="js/jquery-1.2.6.js" type="text/javascript"></script>
<!-- <script src="js/jquery.scrollTo-1.3.3.js" type="text/javascript"></script>
<script src="js/jquery.localscroll-1.2.5.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.serialScroll-1.2.1.js" type="text/javascript" charset="utf-8"></script>-->
<script src="js/coda-slider.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.easing.1.3.js" type="text/javascript" charset="utf-8"></script>
</head>

<body>
<%
boolean usernameExist = false;
boolean loggedIn = false;
boolean loginfail = false;//to printout message if the user inserts wrong or not existing credentials in login page
User loggedInUser = (User) session.getAttribute(SessionKeys.USER_OBJECT);
if(loggedInUser != null){ //user is already logged in
	loggedIn = true;
	if(request.getParameter("logout") != null){
		session.removeAttribute(SessionKeys.USER_OBJECT);
		session.invalidate();
		loggedIn = false;
		loginfail = false;
	}
}else{
	if(request.getParameter("username") !=null &&
		request.getParameter("password") != null &&
		request.getParameter("login") !=null &&
		request.getParameter("login").equals("Login")){ //login has been requested
		loggedInUser = SQLQueries.login(request.getParameter("username"), request.getParameter("password"));
		if (loggedInUser != null) {//user successfully logged in.
			session.setAttribute(SessionKeys.USER_OBJECT, loggedInUser);
			loggedIn = true;
			loginfail = false;
		}
		else{
			loginfail=true;
		}
	}else if(request.getParameter("author")!=null &&
	request.getParameter("email")!=null &&
	request.getParameter("username")!=null &&
	request.getParameter("password")!=null &&
	request.getParameter("register")!=null &&
	request.getParameter("register").equals("Register")){
		if(SQLQueries.usernameExistance(request.getParameter("username"))){
			usernameExist = true;
			out.println("<h1>Natty</h1>");
		}else{
			loggedInUser = SQLQueries.register(request.getParameter("username"), request.getParameter("password"), request.getParameter("email"), request.getParameter("author"));
			if(loggedInUser != null){//user successfully registered and logged in.
				session.setAttribute(SessionKeys.USER_OBJECT, loggedInUser);
				loggedIn = true;
				loginfail = false;
				usernameExist = false;
			}
			else{
				loginfail=true;
				usernameExist = false;
			}
		}
	}
}
%>
<div id="slider">
	
    <div id="templatemo_sidebar">
    	
    	<a href="index.jsp">
    		<div id="wsn_header">
        	
        	</div>
        </a>
         <!-- end of header -->
        
        <ul class="navigation">
            <li><a href="index.jsp">Home<span class="ui_icon home"></span></a></li>
            <%if(loggedIn){%>
            	<li><a href="AccountSettings.jsp">Account Settings<span class="ui_icon aboutus"></span></a></li>
            <%}else{%>
            	<li><a href="Register.jsp" lang="selected">Sign Up<span class="ui_icon aboutus"></span></a></li>
            <%} %>
            <li><a href="ServerConfig.jsp">Server Configuration<span class="ui_icon services"></span></a></li>
            <li><a href="ContactUs.jsp">Contact Us<span class="ui_icon contactus"></span></a></li>
        </ul>
    </div> <!-- end of sidebar -->

	<div id="templatemo_main">
    	        
        <div id="content">
            	
            <div class="scroll">
                <div class="scrollContainer">
                
                    <div class="panel" id="account">
                        <%
						if(loggedIn){
						
							response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
							String newLocn = "AccountSettings.jsp";
							response.setHeader("Location",newLocn);

						}else{
						%>
	                        <div id="register_form">
	                            <form method="post" name="contact" action="index.jsp">
	                                
	                                <label for="author">Your Name:</label> <input type="text" id="author" name="author" class="required input_field" />
	                                <div class="cleaner_h10"></div>
	                                
	                                <label for="email">Your Email:</label> <input type="text" id="email" name="email" class="validate-email required input_field" />
	                                <div class="cleaner_h10"></div>
	                                
	                                <label for="username">Choose a username:</label> <input type="text" id="username" name="username" class="required input_field" onchange="checkUsernameExistence()"/>
	                                
	                                <div class="cleaner_h10"></div>
	                                
	                                <label for="password">Choose a password:</label> <input type="password" id="password" name="password" class="required input_field" />
	                                <div class="cleaner_h10"></div>
	                                
	                                <label for="password">Repeat your password:</label> <input type="password" id="password2" name="password2" class="required input_field" />
	                                <div class="cleaner_h10"></div>
	
	                                <input type="submit" class="submit_btn" name="register" id="register" value="Register" />
	                            	
	                            </form>
							</div>
						<%
							}
						%>
                    </div>
                
                </div>
			</div>
            
        <!-- end of scroll -->
        
        </div> <!-- end of content -->
        
        <div id="templatemo_footer">

            Copyright © 2012 <a href="#">WSN team Fall 2012</a>
        
        </div> <!-- end of templatemo_footer -->
    
    </div> <!-- end of main -->
</div>

</body>
</html>