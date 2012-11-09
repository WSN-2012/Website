<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="server.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>WSN-Server - Homepage</title>
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
session.setAttribute("loginFail", false);//to printout error messages in the login page
User loggedInUser = (User) session.getAttribute(SessionKeys.USER_OBJECT);
if(loggedInUser != null){ //user is already logged in
	loggedIn = true;
	if(request.getParameter("logout") != null){
		session.removeAttribute(SessionKeys.USER_OBJECT);
		session.invalidate();
		loggedIn = false;
		session.setAttribute("loginFail", false);
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
			session.setAttribute("loginFail", false);
		}
		else{
			session.setAttribute("loginFail", true);
			session.setAttribute("err", "Login Failed! Please check your credentials and try again");
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
            <li><a href="index.jsp" lang="selected">Home<span class="ui_icon home"></span></a></li>
            <%if(loggedIn){%>
            	<li><a href="AccountSettings.jsp">Account Settings<span class="ui_icon aboutus"></span></a></li>
            <%}else{%>
            	<li><a href="Register.jsp">Sign Up<span class="ui_icon aboutus"></span></a></li>
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
                
                    <div class="panel" id="home">
                        <h1>Welcome to the WSN Fall 2012 website!</h1>    
                        <p>This is the interface to the web server where all data collected from remote sensor networks is stored.
                        For more information about WSN team 2012 and their project, visit <a href="http://ttaportal.org/menu/projects/wsn/fall-2012/" target="_blank">ttaportal.org</a></p>
                     	
                     	<%
                     	if(loggedIn){
	                    %>
	                     	<p>You are currently logged in.</p>
	                     	
	                     	<table id="data_table" summary="Data from WSN">
								<thead>
									<tr>
										<th scope="col">Timestamp</th>
										<th scope="col">UT</th>
										<th scope="col">ID</th>
										<th scope="col">T</th>
										<th scope="col">PS</th>
										<th scope="col">V_MCU</th>
										<th scope="col">UP</th>
										<th scope="col">RH</th>
										<th scope="col">V_IN</th>
										<th scope="col">V_A1</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>2012-05-22 13:57:46</td>
										<td>1337687866</td>
										<td>283c0cdd030000d7</td>
										<td>30.44</td>
										<td>0</td>
										<td>2.92</td>
										<td>2BF04</td>
										<td>42.4</td>
										<td>4.66</td>
										<td>3.57</td>
									</tr>
									<tr>
										<td>2012-05-22 13:57:46</td>
										<td>1337687866</td>
										<td>283c0cdd030000d7</td>
										<td>30.44</td>
										<td>0</td>
										<td>2.92</td>
										<td>2BF04</td>
										<td>42.4</td>
										<td>4.66</td>
										<td>3.57</td>
									</tr>
								</tbody>
							</table>
	                     	
                     	<%
                     	}else{
						%>
	                     	<p>Access to stored data is restricted! Please login.</p>
	                     	
	                     	<div id="contact_form">
	                            <form method="post" name="contact" action="index.jsp">
	                                
	                                <label for="username">Username:</label> <input type="text" id="username" name="username" class="required input_field" />
	                                <div class="cleaner_h10"></div>
	                                
	                                <label for="password">Password:</label> <input type="password" id="password" name="password" class="required input_field" />
	                                <div class="cleaner_h10"></div>
	
	                                <input type="submit" class="submit_btn" name="login" id="login" value="Login" />
	                                <%
										if(usernameExist){
									%>
	                            		<p id="errmsg" style="color: red;">Username already exists! Choose another username and try again.</p>
	                                <%
	                                }else if((Boolean)session.getAttribute("loginFail")) {
	                                %>
	                            		<p id="errmsg" style="color: red;">Login Failed! Check your credentials and try again.</p>
	                            	<% } %>
	                            </form>
							</div>
							<br />
							<p>If you don't have an account yet, please <a href="#account">register here</a>. An administrator will receive your request.</p>
						<%
                     	}
						%>
						
                     	
                     	</div> <!-- end of home -->
                     	
                    </div>
                </div>
			</div>
            
        <!-- end of scroll -->
        
        </div> <!-- end of templato_main -->
        
        <div id="templatemo_footer">

            Copyright © 2012 <a href="#">WSN team Fall 2012</a>
        
        </div> <!-- end of templatemo_footer -->
    
    </div> <!-- end of main -->
</div>

</body>
</html>