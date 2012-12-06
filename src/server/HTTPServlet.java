package server;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.*;


/**
 * Servlet implementation class HTTPServlet
 * to listen to HTTP requests and send response  using JSON objects
 */
@WebServlet("/HTTPServlet")
public class HTTPServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		String format = request.getParameter("format");
		if(format == null || format.equalsIgnoreCase("json")){//if the format parameter is null or json, send json objects to http requests
			response.setContentType("application/json");
			String jsonGateway = "";
			
			if(request.getParameter("getGateways")!=null){//if gateways is requested
				
				List<Gateway> listGateway = SQLQueries.getAllGateway();//Get a list of Gateway instances from DB
				jsonGateway = gson.toJson(listGateway);//convert list to json String
				
			}else if(request.getParameter("gateway")!=null && request.getParameter("getSensors")!=null){//if gateways is requested
				
				int gatewayValue = Integer.parseInt(request.getParameter("gateway"));
				List<Sensor> listSensor = SQLQueries.getAllSensor(gatewayValue);//Get a list of Sensor instances from DB
				jsonGateway = gson.toJson(listSensor);//convert list to json String
				
			}else if(request.getParameter("sensor")!=null){//if data for a specific gateway is requested
				
				List<Data> listGatewayData= SQLQueries.getSensorData(request.getParameter("sensor"));//Get Data for a specific gateway from DB
				jsonGateway = gson.toJson(listGatewayData);//convert list to json String
				
			}
			response.setContentLength(jsonGateway.length());
	        response.getWriter().print(jsonGateway);
	        response.flushBuffer();
	        response.getWriter().close();
	        
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
