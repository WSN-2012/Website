<%@page import="com.sun.xml.internal.txw2.Document"%>
<%@page import="org.apache.catalina.ha.session.SessionIDMessage"%>
<%@page import="org.apache.catalina.ha.session.SessionMessageImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="server.*, database.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WSN Server - Account Settings</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/template_style.css" rel="stylesheet" type="text/css" />
<link href="css/coda-slider.css" rel="stylesheet" type="text/css" media="screen" charset="utf-8" />
<script src="js/jquery-1.2.6.js" type="text/javascript"></script>
<script src="js/jquery.localscroll-1.2.5.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.serialScroll-1.2.1.js" type="text/javascript" charset="utf-8"></script>
<script src="js/coda-slider.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.easing.1.3.js" type="text/javascript" charset="utf-8"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="http://jzaefferer.github.com/jquery-validation/jquery.validate.js"></script>
<style type="text/css">
	label.error { float: none; color: red; padding-left: .5em; vertical-align: top; }
</style>
<script>
  $(document).ready(function(){
    $("#accountsetsForm").validate({
    	rules: {
	        password2: {
	          equalTo: "#password"
	        }
      }});
  });
</script>
</head>

<body>
<%
boolean loggedIn = false;
User loggedInUser = (User) session.getAttribute(SessionKeys.USER_OBJECT);
String name = loggedInUser.getUserName();
if(loggedInUser != null){ //user is already logged in
	loggedIn = true;
	if(request.getParameter("logout") != null){
		session.removeAttribute(SessionKeys.USER_OBJECT);
		session.invalidate();
		loggedIn = false;
	}else if(request.getParameter("save_settings")!=null &&
			request.getParameter("save_settings").equals("Save")){
		if(request.getParameter("oldpassword")!=null && request.getParameter("oldpassword")!="" && request.getParameter("password")!=null && request.getParameter("password")!="" && request.getParameter("password").equals(request.getParameter("password2"))){
			if(SQLQueries.login(loggedInUser.getUserName(), request.getParameter("oldpassword"))==null){
				request.setAttribute("err", "Current password is not correct");
			}else if (loggedInUser.getUserName()!=request.getParameter("username")){
				if(SQLQueries.usernameExistance(request.getParameter("username"))){
					request.setAttribute("err", "The username already exists! Choose another one.");
				}
			}else{
				loggedInUser = SQLQueries.changeAccountSettings(request.getParameter("author"), loggedInUser.getUserName(), request.getParameter("username"), request.getParameter("email"), request.getParameter("password"));
				if(loggedInUser!=null){
					session.removeAttribute(SessionKeys.USER_OBJECT);
					session.setAttribute(SessionKeys.USER_OBJECT, loggedInUser);
					response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
					String newLocn = "index.jsp";
					response.encodeURL("index.jsp");
					response.setHeader("Location",newLocn);	
				}else{
					request.setAttribute("err", "User is not successful updated. Try again!");
				}
			}
		}else{
			if (loggedInUser.getUserName()!=request.getParameter("username")){
				if(SQLQueries.usernameExistance(request.getParameter("username"))){
					request.setAttribute("err", "The username already exists! Choose another one.");
				}
			}else{
				loggedInUser = SQLQueries.changeAccountSettings(request.getParameter("author"), loggedInUser.getUserName(), request.getParameter("username"), request.getParameter("email"));
				if(loggedInUser!=null){
					session.removeAttribute(SessionKeys.USER_OBJECT);
					session.setAttribute(SessionKeys.USER_OBJECT, loggedInUser);
					response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
					String newLocn = "index.jsp";
					response.encodeURL("index.jsp");
					response.setHeader("Location",newLocn);
				}else{
					request.setAttribute("err", "User is not successful updated. Try again!");
				}
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
				<li><a href="<%= response.encodeURL("index.jsp")%>">Home<span
						class="ui_icon home"></span></a></li>
				<%
					if (loggedIn) {
				%>
				<li><a href="<%= response.encodeURL("AccountSettings.jsp")%>" lang="selected">Account Settings<span
						class="ui_icon aboutus"></span></a></li>
				<%
					} else {
				%>
				<li><a href="<%= response.encodeURL("Register.jsp")%>">Sign Up<span
						class="ui_icon aboutus"></span></a></li>
				<%
					}
				%>
				<li><a href="<%= response.encodeURL("ServerConfig.jsp")%>">Server Configuration<span
						class="ui_icon services"></span></a></li>
				<li><a href="<%= response.encodeURL("ContactUs.jsp")%>">Contact Us<span
						class="ui_icon contactus"></span></a></li>
			</ul>
    </div> <!-- end of sidebar -->

	<div id="templatemo_main">
    	        
        <div id="content">
            	
            <div class="scroll">
                <div class="scrollContainer">
                	<!-- if user is logged in display logout button -->
					<%if (loggedIn) {%>
						<div id="logout">
							<form method="post" name="contact" action="<%= response.encodeURL("index.jsp")%>">
								<input type="submit" name="logout" id="logout" value="Logout" />
							</form>
						</div>
					<%}%>
                    <div class="panel" id="account">
                        <%
						if(loggedIn){%>
						
							<h2>Here you can modify your Account Settings</h2>
							 <div id="accountSettings_form">
	                            <form method="post" name="accountsets" id="accountsetsForm">
	                                
	                                <label for="author">Edit Name:</label> <input type="text" id="author" name="author" class="input_field" value="<% out.print(((User)session.getAttribute(SessionKeys.USER_OBJECT)).getName()); %>" class="input_field" />
	                                <div class="cleaner_h10"></div>
	                                
	                                <label for="email">Edit Email:</label> <input type="text" id="email" name="email" class="email input_field" value="<% out.print(((User)session.getAttribute(SessionKeys.USER_OBJECT)).getEmail()); %>" class="validate-email input_field" />
	                                <div class="cleaner_h10"></div>
	                                
	                                <label for="username">Edit Username:</label> <input type="text" id="username" name="username" class="input_field" value="<% out.print(((User)session.getAttribute(SessionKeys.USER_OBJECT)).getUserName()); %>" class="input_field"/>
	                                <div class="cleaner_h10"></div>
	                                
	                                <label for="oldpassword">Current password:</label> <input type="password" id="oldpassword" name="oldpassword" class="input_field" />
	                                <div class="cleaner_h10"></div>
	                                
	                                <label for="password">Edit a new password:</label> <input type="password" id="password" name="password" class="input_field" />
	                                <div class="cleaner_h10"></div>
	                                
	                                <label for="password2">Confirm new password:</label> <input type="password" id="password2" name="password2" class="input_field" />
	                                <div class="cleaner_h10"></div>
	
	                                <input type="submit" class="submit_btn" name="save_settings" id="save_settings" value="Save" />
	                                
	                                <% 
										if(request.getAttribute("err")!=null){
									%>
	                            	<p id="errmsg" style="color: red;"><%out.print(request.getAttribute("err"));%></p>
	                            	<%}
	                                }else{
										request.setAttribute("loginFail", true);
										request.setAttribute("err", "You have to login first in order to change you account settings.");
										RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
										rd.forward(request, response);
									}
									%>
	                            </form>
							</div>

						
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