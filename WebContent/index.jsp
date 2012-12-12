<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="server.*, database.*, java.util.*"%>
<%@ page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>WSN-Server - Homepage</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/template_style.css" rel="stylesheet" type="text/css" />
<link href="css/coda-slider.css" rel="stylesheet" type="text/css"
	media="screen" charset="utf-8" />
<script src="js/jquery-1.2.6.js" type="text/javascript"></script>
<script src="js/jquery.localscroll-1.2.5.js" type="text/javascript"
	charset="utf-8"></script>
<script src="js/jquery.serialScroll-1.2.1.js" type="text/javascript"
	charset="utf-8"></script>
<script src="js/coda-slider.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.easing.1.3.js" type="text/javascript"
	charset="utf-8"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
</head>
<body>
	<%
		boolean loggedIn = false;//user is logged in
		boolean loginfail = false;//to printout error messages in the login page
		User loggedInUser = null;
		loggedInUser = (User) session.getAttribute(SessionKeys.USER_OBJECT);//get current User if user has successfully logged in
		List<Data> data = null;
		if (loggedInUser != null) { //user is already logged in
			loggedIn = true;
			loginfail = false;
			if (request.getParameter("logout") != null) {
				session.removeAttribute(SessionKeys.USER_OBJECT);
				session.invalidate();
				loggedIn = false;
				loginfail = false;
			}
		} else {//user not logged in
				//if loggin button is pressed
			if (request.getParameter("username") != null
					&& request.getParameter("password") != null
					&& request.getParameter("login") != null
					&& request.getParameter("login").equals("Login")) { //login has been requested
				loggedInUser = SQLQueries.login(
						request.getParameter("username"),
						request.getParameter("password"));
				if (loggedInUser != null) {//user successfully logged in.
					session.setAttribute(SessionKeys.USER_OBJECT,
							loggedInUser);
					loggedIn = true;
					loginfail = false;
				} else {//user not successfully logged in display error
					loginfail = true;
					request.setAttribute("err",
							"Login Failed! Please check your credentials and try again");
				}
			}
		}
	%>
	<div id="slider">

		<div id="templatemo_sidebar">

			<a href="index.jsp">
				<div id="wsn_header"></div>
			</a>
			<!-- end of header -->
			<ul class="navigation">
				<li><a href="<%=response.encodeURL("index.jsp")%>"
					lang="selected">Home<span class="ui_icon home"></span></a></li>
				<%
					if (loggedIn) {
				%>
				<li><a href="<%=response.encodeURL("AccountSettings.jsp")%>">Account
						Settings<span class="ui_icon aboutus"></span>
				</a></li>
				<%
					} else {
				%>
				<li><a href="<%=response.encodeURL("Register.jsp")%>">Sign
						Up<span class="ui_icon aboutus"></span>
				</a></li>
				<%
					}
				%>
				<li><a href="<%=response.encodeURL("ServerConfig.jsp")%>">Server
						Configuration<span class="ui_icon services"></span>
				</a></li>
				<li><a href="<%=response.encodeURL("ContactUs.jsp")%>">Contact
						Us<span class="ui_icon contactus"></span>
				</a></li>
			</ul>
		</div>
		<!-- end of sidebar -->

		<div id="templatemo_main">
			<div id="content">
				<div class="scroll">
					<div class="scrollContainer">
						<!-- if user is logged in display logout button -->
						<%
							if (loggedIn) {
						%>
						<div id="logout">
							<form method="post" name="contact"
								action="<%=response.encodeURL("index.jsp")%>">
								<input type="submit" name="logout" id="logout" value="Logout" />
							</form>
						</div>
						<%
							}
						%>
						<div class="panel" id="home">
							<h1>Welcome to the WSN Fall 2012 website!</h1>
							<p>
								This is the interface to the web server where all data collected
								from remote sensor networks is stored. For more information
								about WSN team 2012 and their project, visit <a
									href="http://ttaportal.org/menu/projects/wsn/fall-2012/"
									target="_blank">ttaportal.org</a>
							</p>

							<!-- If user is logged in display page with data -->
							<%
								if (loggedIn) {
							%>
							<p>
								<%
									out.print(loggedInUser.getName());
								%>, you are currently logged in.
							</p>
							<form method="post" name="form_gateway" action="<%=response.encodeURL("index.jsp")%>">
								<!-- display gateway list -->
								<label for="gateways">Gateways</label> 
								<select name="gateways" onchange="document.form_gateway.submit()">
									<option value="Select Gateway">Select Gateway</option>
									<!-- Retain gateway option selected after submitting the form -->
									<%
										if (request.getParameter("gateways") != null) {
									%>
									<option value="<%=request.getParameter("gateways")%>"
										selected="selected"><%=SQLQueries.getGateway(
											Integer.parseInt(request.getParameter("gateways")))
											.getName()%>
									</option>
									<%
										}
										List<Gateway> listOfGateways = SQLQueries.getAllGateway();
										for (int i = 0; i < listOfGateways.size(); i++) {
											if (request.getParameter("gateways") != null){
												if(!(SQLQueries.getGateway(Integer.parseInt(request.getParameter("gateways"))).getName()
														.equals(listOfGateways.get(i).getName()))){
									%>
									<option value="<%=listOfGateways.get(i).getId()%>">
										<%
											out.print(listOfGateways.get(i).getName());
										%>
									</option>
									<%
												}
											} else {
									%>
									<option value="<%=listOfGateways.get(i).getId()%>">
										<%
											out.print(listOfGateways.get(i).getName());
										%>
									</option>
									<%
										}
									%>
									<%
										}
									%>
								</select>
								
								<!-- display sensor list based on the gateway that is selected before-->
								<label for="sensors">Sensors</label> 
								<select id="sensors" name="sensors" onchange="document.form_gateway.submit()">
									<option value="Select Sensor">Select Sensor</option>
									<%
										if (request.getParameter("sensors") != null
													&& !request.getParameter("sensors").equals(
															"Select Sensor")) {
									%>
									<option value="<%=request.getParameter("sensors")%>"selected="selected">
									<%=SQLQueries.getSensor(request.getParameter("sensors")).getName()%></option>
									<%
										}
											if (request.getParameter("gateways") != null
													&& !request.getParameter("gateways").equals(
															"Select Gateway")) {
												List<Sensor> listOfSensors = SQLQueries.getAllSensor(Integer.parseInt(request.getParameter("gateways")));
												for (int j = 0; j < listOfSensors.size(); j++) {
													if (request.getParameter("sensors") != null && !request.getParameter("sensors").equals("Select Sensor")){
														if(!SQLQueries.getSensor(request.getParameter("sensors")).getName().equals(listOfSensors.get(j).getName())) {

									%>
									<option value="<%=listOfSensors.get(j).getId()%>">
										<%
											out.print(listOfSensors.get(j).getName());
										%>
									</option>
									<%
														}
													} else {
									%>
									<option value="<%=listOfSensors.get(j).getId()%>">
										<%
											out.print(listOfSensors.get(j).getName());
										%>
									</option>
									<%
										}
									%>

									<%
										}
											}
									%>
								</select>

								<!-- display data table for a specific sensor registered in a specific gateway -->
								<%
									if (request.getParameter("sensors") != null
												&& !request.getParameter("sensors").equals(
														"Select Sensor")) {
											data = SQLQueries.getSensorData(request.getParameter(
													"sensors").toString());
								%>
								<table id="data_table" summary="Data from WSN">
									<thead>
										<tr>
											<th scope="col">Timestamp<br/><span style="font-size: 8px;font-weight: lighter;">Creation date</span></th>
											<th scope="col">UT<br/><span style="font-size: 8px;font-weight: lighter;">Unix time</span></th>
											<th scope="col">T<br/><span style="font-size: 8px;font-weight: lighter;">Temperature</span></th>
											<th scope="col">T_MCU<br/></th>
											<th scope="col">PS<br/><span style="font-size: 8px;font-weight: lighter;">Power Saver Indicator</span></th>
											<th scope="col">V_MCU<br/><span style="font-size: 8px;font-weight: lighter;">Microcontroller Voltage</span></th>
											<th scope="col">UP<br/><span style="font-size: 8px;font-weight: lighter;">Uptime (HEX format)</span></th>
											<th scope="col">RH<br/><span style="font-size: 8px;font-weight: lighter;">Relative Humidity in %</span></th>
											<th scope="col">V_IN<br/><span style="font-size: 8px;font-weight: lighter;">Voltage Input</span></th>
										</tr>
									</thead>
									<tbody>
										<%
											for (int i = 0; i < data.size(); i++) {
										%>
										<tr>
											<td>
												<%
													out.print(data.get(i).getUtimestamp());
												%>
											</td>
											<td>
												<%
													out.print(data.get(i).getWSData().get("ut"));
												%>
											</td>
											<td>
												<%
													out.print(data.get(i).getWSData().get("t"));
												%>
											</td>
											<td>
												<%
													out.print(data.get(i).getWSData().get("t_mcu"));
												%>
											</td>
											<td>
												<%
													out.print(data.get(i).getWSData().get("ps"));
												%>
											</td>
											<td>
												<%
													out.print(data.get(i).getWSData().get("v_mcu"));
												%>
											</td>
											<td>
												<%
													out.print(data.get(i).getWSData().get("up"));
												%>
											</td>
											<td>
												<%
													out.print(data.get(i).getWSData().get("rh"));
												%>
											</td>
											<td>
												<%
													out.print(data.get(i).getWSData().get("v_in"));
												%>
											</td>
										</tr>
										<%
											}
												}
										%>
									</tbody>
								</table>
							</form>
							<!-- if user is not logged in display login form -->
							<%
								} else {
							%>
							<p>Access to stored data is restricted! Please login.</p>

							<div id="contact_form">
								<form method="post" name="contact"
									action="<%=response.encodeURL("index.jsp")%>">

									<label for="username">Username:</label> <input type="text"
										id="username" name="username" class="required input_field" />
									<div class="cleaner_h10"></div>

									<label for="password">Password:</label> <input type="password"
										id="password" name="password" class="required input_field" />
									<div class="cleaner_h10"></div>

									<input type="submit" class="submit_btn" name="login" id="login"
										value="Login" />
									<!-- if login fails display error message -->
									<%
										if ((request.getAttribute("loginFail") != null && (Boolean) request
													.getAttribute("loginFail")) || loginfail) {
									%>
									<p id="errmsg" style="color: red;">
										<%
											out.print(request.getAttribute("err"));
										%>
									</p>
									<%
										}
									%>
								</form>
							</div>
							<br />
							<p>
								If you don't have an account yet, please <a href="<%=response.encodeURL("Register.jsp")%>">register
									here</a>. An administrator will receive your request.
							</p>
							<%
								}
							%>


						</div>
						<!-- end of home -->

					</div>
				</div>
			</div>

			<!-- end of scroll -->

		</div>
		<!-- end of templato_main -->

		<div id="templatemo_footer">

			Copyright © 2012 <a href="#">WSN team Fall 2012</a>

		</div>
	</div>
	<!-- end of main -->
	</div>

</body>
</html>