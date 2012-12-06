<%@page import="org.apache.catalina.ha.session.SessionIDMessage"%>
<%@page import="org.apache.catalina.ha.session.SessionMessageImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="server.*, database.*" %>
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
	}else if(request.getParameter("save_settings")!=null &&
			request.getParameter("save_settings").equals("Save")){
		if(request.getParameter("password")!=null && request.getParameter("password")!="" && request.getParameter("password").equals(request.getParameter("password2"))){
			loggedInUser = SQLQueries.changeAccountSettings(request.getParameter("author"), loggedInUser.getUserName(), request.getParameter("username"), request.getParameter("email"), request.getParameter("password"));
			if(loggedInUser!=null){
				session.removeAttribute(SessionKeys.USER_OBJECT);
				session.setAttribute(SessionKeys.USER_OBJECT, loggedInUser);
			}
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			String newLocn = "index.jsp";
			response.setHeader("Location",newLocn);
		}else if(request.getParameter("password")!=null && !(request.getParameter("password").equals(request.getParameter("password2")))){
			request.setAttribute("err", "Password not equal");//Display error
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			String newLocn = "Register.jsp";
			response.setHeader("Location",newLocn);
		}else{
			loggedInUser = SQLQueries.changeAccountSettings(request.getParameter("author"), loggedInUser.getUserName(), request.getParameter("username"), request.getParameter("email"));
			if(loggedInUser!=null){
				session.removeAttribute(SessionKeys.USER_OBJECT);
				session.setAttribute(SessionKeys.USER_OBJECT, loggedInUser);
			}
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			String newLocn = "index.jsp";
			response.setHeader("Location",newLocn);
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
        <%if(loggedIn){%>
        					<div id="logout">
            				<form method="post" name="contact" action="index.jsp">
            				<input type="submit" name="logout" id="logout" value="Logout" />
            				</form>
            				</div>
            <%}%>
            	
            <div class="scroll">
                <div class="scrollContainer">
                
                    <div class="panel" id="account">
                        <%
						if(loggedIn){%>
						
							<h2>Here you can modify your Account Settings</h2>
							 <div id="accountSettings_form">
	                            <form method="post" name="contact" >
	                                
	                                <label for="author">Edit Name:</label> <input type="text" id="author" name="author" value="<% out.print(((User)session.getAttribute(SessionKeys.USER_OBJECT)).getName()); %>" class="input_field" />
	                                <div class="cleaner_h10"></div>
	                                
	                                <label for="email">Edit Email:</label> <input type="text" id="email" name="email" value="<% out.print(((User)session.getAttribute(SessionKeys.USER_OBJECT)).getEmail()); %>" class="validate-email input_field" />
	                                <div class="cleaner_h10"></div>
	                                
	                                <label for="username">Edit Username:</label> <input type="text" id="username" name="username" value="<% out.print(((User)session.getAttribute(SessionKeys.USER_OBJECT)).getUserName()); %>" class="input_field" onchange="checkUsernameExistence()"/>
	                                
	                                <div class="cleaner_h10"></div>
	                                
	                                <label for="password">Edit a new password:</label> <input type="password" id="password" name="password" class="input_field" />
	                                <div class="cleaner_h10"></div>
	                                
	                                <label for="password">Repeat new password:</label> <input type="password" id="password2" name="password2" class="<%if(request.getParameter("password")!=null)out.print("required"); %> input_field" />
	                                <div class="cleaner_h10"></div>
	
	                                <input type="submit" class="submit_btn" name="save_settings" id="save_settings" value="Save" />
	                                
	                                <% 
										if(request.getAttribute("err")!=null){
									%>
	                            		<p id="errmsg" style="color: red;"><%out.print(request.getAttribute("err"));%></p>
	                            </form>
							</div>

						<%}}else{
							request.setAttribute("loginFail", true);
							request.setAttribute("err", "You have to login first in order to change you account settings.");
							RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
							rd.forward(request, response);
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