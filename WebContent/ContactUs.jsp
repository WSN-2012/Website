<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="server.*, java.util.*, javax.mail.*, javax.mail.internet.*, javax.activation.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/template_style.css" rel="stylesheet" type="text/css" />
<link href="css/coda-slider.css" rel="stylesheet" type="text/css" media="screen" charset="utf-8" />
<script src="js/jquery-1.2.6.js" type="text/javascript"></script>
<!--  <script src="js/jquery.scrollTo-1.3.3.js" type="text/javascript"></script>
<script src="js/jquery.localscroll-1.2.5.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.serialScroll-1.2.1.js" type="text/javascript" charset="utf-8"></script>-->
<script src="js/coda-slider.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.easing.1.3.js" type="text/javascript" charset="utf-8"></script>
<title>WSN Server - Contact us</title>
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
if(request.getParameter("sendEmail")!=null &&
request.getParameter("sendEmail").equals("Send")){
	String to = "nataliparats@gmail.com";//Recipient's email ID needs to be mentioned.
	String from = "wentfgjwnwo@gmail.com";// Sender's email ID needs to be mentioned
	String host = "localhost";// Assuming you are sending email from localhost
	Properties properties = System.getProperties();// Get system properties
	properties.setProperty("mail.smtp.host", host);//Setup mail server
	
	// Get the default Session object.
	Session s = Session.getDefaultInstance(properties);
	
	try{
	   // Create a default MimeMessage object.
	   MimeMessage message = new MimeMessage(s);
	
	   // Set From: header field of the header.
	   message.setFrom(new InternetAddress(from));
	
	   // Set To: header field of the header.
	   message.addRecipient(Message.RecipientType.TO,
	                            new InternetAddress(to));
	
	   // Set Subject: header field
	   message.setSubject("This is the Subject Line!");
	
	   // Now set the actual message
	   message.setText("This is actual message");
	
	   // Send message
	   Transport.send(message);
	   System.out.println("Sent message successfully....");
	}catch (MessagingException mex) {
	   mex.printStackTrace();
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
            <li><a href="#account"><%if(loggedIn)%>Account Settings<span class="ui_icon aboutus"></span><%else%>Sign Up<span class="ui_icon aboutus"></span></a></li>
            <li><a href="#config">Server Configuration<span class="ui_icon services"></span></a></li>
            <li><a href="ContactUs.jsp" lang="selected">Contact Us<span class="ui_icon contactus"></span></a></li>
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
                                
                                <input type="submit" class="submit_btn" name="sendEmail" id="sendEmail" value="Send" />
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