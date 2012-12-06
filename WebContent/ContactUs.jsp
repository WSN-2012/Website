<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="server.*, database.*" %>
<%@ page import="java.io.*,java.util.*,javax.mail.*"%>
<%@ page import="javax.mail.internet.*,javax.activation.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*;" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/template_style.css" rel="stylesheet" type="text/css" />
<link href="css/coda-slider.css" rel="stylesheet" type="text/css" media="screen" charset="utf-8" />
<script src="js/jquery-1.2.6.js" type="text/javascript"></script>
<script src="js/jquery.localscroll-1.2.5.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.serialScroll-1.2.1.js" type="text/javascript" charset="utf-8"></script>
<script src="js/coda-slider.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.easing.1.3.js" type="text/javascript" charset="utf-8"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
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
	String host="", user="", pass="";
	host ="smtp.gmail.com"; //"smtp.gmail.com";
	user ="kozzetest"; // gmail id to send the emails
	pass ="kostas01"; //Your gmail password
	String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	String to ="natpar@kth.se"; // out going email id
	String from =request.getParameter("email"); //Email id of the recipient
	String subject = request.getParameter("name") + " WSN web contact page";//Subject
	String messageText =request.getParameter("text");//Message to be sent
	boolean sessionDebug = true;

	Properties props = System.getProperties();
	props.put("mail.host", host);
	props.put("mail.transport.protocol.", "smtp");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.", "true");
	props.put("mail.smtp.port", "465");
	props.put("mail.smtp.socketFactory.fallback", "false");
	props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
	Session mailSession = Session.getDefaultInstance(props, null);
	mailSession.setDebug(sessionDebug);
	Message msg = new MimeMessage(mailSession);
	msg.setFrom(new InternetAddress(from));
	InternetAddress[] address = {new InternetAddress(to)};
	msg.setRecipients(Message.RecipientType.TO, address);
	msg.setSubject(subject);
	msg.setContent(messageText, "text/html"); // use setText if you want to send text
	Transport transport = mailSession.getTransport("smtp");
	transport.connect(host, user, pass);
	try {
	transport.sendMessage(msg, msg.getAllRecipients());

	out.println("message successfully sended"); // assume it was sent
	}
	catch (Exception err) {

	out.println("message not successfully sended"); // assume it’s a fail
	}
	transport.close();
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
				<li><a href="<%= response.encodeURL("ServerConfig.jsp")%>">Server Configuration<span
						class="ui_icon services"></span></a></li>
				<li><a href="<%= response.encodeURL("ContactUs.jsp")%>" lang="selected">Contact Us<span
						class="ui_icon contactus"></span></a></li>
			</ul>
    </div> <!-- end of sidebar -->

	<div id="templatemo_main">
    	        
        <div id="content">
            <div class="scroll">
                <div class="scrollContainer">
	                <%if(loggedIn){%>
	        					<div id="logout">
	            				<form method="post" name="contact" action="<%= response.encodeURL("index.jsp")%>">
	            				<input type="submit" name="logout" id="logout" value="Logout" />
	            				</form>
	            				</div>
	            	<%}%>
                	
                    <div class="panel" id="contactus">
                        <h1>Feel free to send us a message</h1>
                        <div id="contact_form">
                            <form method="post" name="contact" action="#contactus">
                                
                                <label for="name">Your Name:</label> <input type="text" id="name" name="name" class="required input_field" />
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