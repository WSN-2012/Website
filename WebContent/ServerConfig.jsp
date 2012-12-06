<%@page import="org.apache.catalina.ha.session.SessionIDMessage"%>
<%@page import="org.apache.catalina.ha.session.SessionMessageImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="server.*, database.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WSN Server - Server Configuration</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/template_style.css" rel="stylesheet" type="text/css" />
<link href="css/coda-slider.css" rel="stylesheet" type="text/css" media="screen" charset="utf-8" />
<script src="js/jquery-1.2.6.js" type="text/javascript"></script>
<script src="js/jquery.localscroll-1.2.5.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.serialScroll-1.2.1.js" type="text/javascript" charset="utf-8"></script>-->
<script src="js/coda-slider.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.easing.1.3.js" type="text/javascript" charset="utf-8"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
</head>

<body>
<%
boolean loggedIn = false;
User loggedInUser = (User) session.getAttribute(SessionKeys.USER_OBJECT);
if(loggedInUser != null){ //user is already logged in
	loggedIn = true;
	if(request.getParameter("logout") != null){
		session.removeAttribute(SessionKeys.USER_OBJECT);
		session.invalidate();
		loggedIn = false;
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
				<li><a href="<%= response.encodeURL("AccountSettings.jsp")%>">Account Settings<span
						class="ui_icon aboutus"></span></a></li>
				<%
					} else {
				%>
				<li><a href="<%= response.encodeURL("Register.jsp")%>">Sign Up<span
						class="ui_icon aboutus"></span></a></li>
				<%
					}
				%>
				<li><a href="<%= response.encodeURL("ServerConfig.jsp")%>" lang="selected">Server Configuration<span
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
                    <div class="panel" id="serverconfig">
                        <h1>Under construction!!!</h1>
                        
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