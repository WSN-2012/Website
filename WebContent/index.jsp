<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="server.*, java.util.List" %>
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
boolean loginfail = false;//to printout error messages in the login page
User loggedInUser = null;
loggedInUser = (User) session.getAttribute(SessionKeys.USER_OBJECT);
List<Data> data = null;
if(loggedInUser != null){ //user is already logged in
	loggedIn = true;
	loginfail = false;
	data = SQLQueries.getAllData();
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
			data = SQLQueries.getAllData();
			loggedIn = true;
			loginfail = false;
		}
		else{
			loginfail = true;
			request.setAttribute("err", "Login Failed! Please check your credentials and try again");
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
	                     	<p><%out.print(loggedInUser.getName()); %>, you are currently logged in.</p>
	                     	
	                     	<table id="data_table" summary="Data from WSN">
								<thead>
									<tr>
										<th scope="col">Timestamp</th>
										<th scope="col">UT</th>
										<th scope="col">T</th>
										<th scope="col">T_MCU</th>
										<th scope="col">PS</th>
										<th scope="col">V_MCU</th>
										<th scope="col">UP</th>
										<th scope="col">RH</th>
										<th scope="col">V_IN</th>
										<th scope="col">Gateway Name</th>
									</tr>
								</thead>
								<tbody>
								<%for (int i=0; i<data.size();i++){ %>
									<tr>
										<td><% out.print(data.get(i).getUtimestamp()); %></td>
										<td><% out.print(data.get(i).getUt()); %></td>
										<td><% out.print(data.get(i).getT()); %></td>
										<td><% out.print(data.get(i).getT_mcu()); %></td>
										<td><% out.print(data.get(i).getPs()); %></td>
										<td><% out.print(data.get(i).getV_mcu()); %></td>
										<td><% out.print(data.get(i).getUp()); %></td>
										<td><% out.print(data.get(i).getRh()); %></td>
										<td><% out.print(data.get(i).getV_in()); %></td>
										<td><% out.print(data.get(i).getSensorName());%></td>
									</tr>
									<%} %>
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
	                                	if((request.getAttribute("loginFail")!=null && (Boolean)request.getAttribute("loginFail")) || loginfail){		
	                                %>
	                            		<p id="errmsg" style="color: red;"><%out.print(request.getAttribute("err"));%></p>
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