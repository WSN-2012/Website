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
<script src="js/jquery.scrollTo-1.3.3.js" type="text/javascript"></script>
<script src="js/jquery.localscroll-1.2.5.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.serialScroll-1.2.1.js" type="text/javascript" charset="utf-8"></script>
<script src="js/coda-slider.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.easing.1.3.js" type="text/javascript" charset="utf-8"></script>
</head>

<body>
<%
boolean loggedIn = false;
User loggedInUser = (User) session.getAttribute(SessionKeys.USER_OBJECT);
if(loggedInUser != null){ //user in already logged in
	loggedIn = true;
	if(request.getParameter("logout") != null){
		session.removeAttribute(SessionKeys.USER_OBJECT);
		session.invalidate();
		loggedIn = false;
	}
}
else{
	if(request.getParameter("username") !=null &&
		request.getParameter("password") != null &&
		request.getParameter("login").equals("Login")){ //login has been requested
		loggedInUser = Authentication.login(request.getParameter("username"), request.getParameter("password"));
		if (loggedInUser != null) {
			//user successfully logged in.
			session.setAttribute(SessionKeys.USER_OBJECT, loggedInUser);
			loggedIn = true;
		}
	}
	//stuff to do when not logged in...
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
            <li><a href="#home">Home<span class="ui_icon home"></span></a></li>
            <li><a href="#account"><%if(loggedIn)%>Account Settings<span class="ui_icon aboutus"></span><%else%>Sign Up<span class="ui_icon aboutus"></span></a></li>
            <li><a href="#config">Server Configuration<span class="ui_icon services"></span></a></li>
            <li><a href="#contactus">Contact Us<span class="ui_icon contactus"></span></a></li>
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
	                            
	                            </form>
							</div>
							<br />
							<p>If you don't have an account yet, please <a href="#account">register here</a>. An administrator will receive your request.</p>
						<%
                     	}
						%>
						
                     	
                     	</div> <!-- end of home -->
                    
                    <div class="panel" id="account">
                        <%
						if(loggedIn){
						%>
							<p>Here you can modify your account information.</p>
						
						<%
						}else{
						%>
	                        <div id="register_form">
	                            <form method="post" name="contact" action="index.jsp">
	                                
	                                <label for="author">Your Name:</label> <input type="text" id="author" name="author" class="required input_field" />
	                                <div class="cleaner_h10"></div>
	                                
	                                <label for="email">Your Email:</label> <input type="text" id="email" name="email" class="validate-email required input_field" />
	                                <div class="cleaner_h10"></div>
	                                
	                                <label for="username">Choose a username:</label> <input type="text" id="username" name="username" class="required input_field" />
	                                <div class="cleaner_h10"></div>
	                                
	                                <label for="password">Choose a password:</label> <input type="password" id="password" name="password" class="required input_field" />
	                                <div class="cleaner_h10"></div>
	                                
	                                <label for="password">Repeat your password:</label> <input type="password" id="password2" name="password2" class="required input_field" />
	                                <div class="cleaner_h10"></div>
	
	                                <input type="submit" class="submit_btn" name="submit" id="submit" value="Register" />
	                            
	                            </form>
							</div>
						<%
						}
						%>
                    </div>
                    
                    <div class="panel" id="config">
                        <%
						if(loggedIn){
						%>
							<p>Here you can configure the server.</p>
						
						<%
						}else{
						%>
	                        <p>You do not have access to this area! Please <a href="#home">login</a> first.</p>
						<%
						}
						%>
                    </div>
                
                    <div class="panel" id="contactus">
                        <h1>Feel free to send us a message</h1>
                        <div id="contact_form">
                            <form method="post" name="contact" action="#contactus">
                                
                                <label for="author">Your Name:</label> <input type="text" id="author" name="author" class="required input_field" />
                                <div class="cleaner_h10"></div>
                                
                                <label for="email">Your Email:</label> <input type="text" id="email" name="email" class="validate-email required input_field" />
                                <div class="cleaner_h10"></div>
                                
                                <label for="text">Message:</label> <textarea id="text" name="text" rows="0" cols="0" class="required"></textarea>
                                <div class="cleaner_h10"></div>
                                
                                <input type="submit" class="submit_btn" name="submit" id="submit" value="Send" />
                                <input type="reset" class="submit_btn" name="reset" id="reset" value="Reset" />
                            
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