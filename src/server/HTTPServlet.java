package server;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


/**
 * Servlet implementation class HTTPServlet
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
				
				List<Gateway> listGateway = SQLQueries.getAllGateway();//Get Gateway info from DB
				jsonGateway = gson.toJson(listGateway);//convert list to json String
			}else if(request.getParameter("gateway")!=null){//if data for a specific gateway is requested
				
				String[] gatewayValue = request.getParameterValues("gateway");
				int gatewayID = Integer.parseInt(gatewayValue[0]);
				List<Data> listGatewayData= SQLQueries.getGatewayData(gatewayID);//Get Data info based on one gateway from DB
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
