package server;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.catalina.util.Base64;

import sun.misc.BASE64Encoder;

/**
 *
 * @author Natalia Paratsikidou
 * 
 */
public class DeviceHandler extends Thread implements Serializable {

    private Socket socket;
    protected String Filename;
    
    // Create the input and output streams for the network socket.
    DataInputStream in = null;
    DataOutputStream out = null;
    
    public static void main(String args[]) throws Exception
    {
    	ServerSocket ss = new ServerSocket(8080);
    	while(true)
    	{
    		Socket s = ss.accept();
    		new DeviceHandler(s).start();
    	}
    }
    
    
    DeviceHandler(Socket s) {
        
            //thread constructor
            this.socket = s;
                
            try {
            	out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            try {
            	in = new DataInputStream(socket.getInputStream());
            } catch (IOException e) {
                System.out.println(e.toString());
                return;
            }
       
    }

    @Override
    public void run() {
        try {

            boolean running = true;
            String line;
           
            while (running) {
                
                try {
                	String prevLine=" ";
                	boolean tryToGetAnotherLine = true;
                	ArrayList<String> request = new ArrayList<String>();
                	while(tryToGetAnotherLine) {
                		line = readlineFromInputStream(in);
                		System.out.println("Received :[" + line+"]");
                         if(line.equals(""))
                         {
                    		 request.remove(request.size()-1);
                    		 tryToGetAnotherLine = false;
                       	 }
                         else
                    	 {
                        	 request.add(line);
                    	 }
                     }

            		String request_code = request.get(0).split(" ")[0];
            		String requested_object = request.get(0).split(" ")[1].substring(1);
                	String request_host;
                	for( String http_header : request)
                	{
                		System.out.println(http_header);
                		if(http_header.startsWith("Host:"))
                			request_host = http_header.split(":")[1].trim();
                	}
                	
                	String myresponse = "IT WORKS!";
                	BASE64Encoder encoder = new BASE64Encoder();
                	ByteArrayOutputStream baos = new ByteArrayOutputStream();
                	ObjectOutputStream ois = new ObjectOutputStream(baos);
                	ois.writeObject(new Car("Polo", 1998, "VW"));
//                	myresponse=encoder.encode(baos.toByteArray());
                	myresponse=new String(baos.toByteArray());
                	System.out.println(myresponse);
                	
                	out.write("HTTP/1.1 200 OK\r\n".getBytes());
                	out.write("Content-Type: binary/octet\r\n".getBytes());
                	out.write( ("Content-Length: "+myresponse.length()+"\r\n").getBytes());
                	out.write("\r\n".getBytes());
//                	out.write("\r\n".getBytes());
                	out.write(myresponse.getBytes());
                	out.flush();
                	
                	
                } catch (OptionalDataException ode) {
                    System.out.println(ode.toString());
                    return;
                } catch (IOException ioe) {
                    System.out.println(ioe.toString());
                    return;
                }
                
            
            }
            try {
                out.close();
                in.close();

            } catch (IOException ioe) {
                System.out.println(ioe.toString());
            }

            System.out.println("Connection Closed");
            socket.close();
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private static String readlineFromInputStream(DataInputStream is) throws IOException
    {
    	String line="";
    	int prevChar=0;
    	int newChar=0;
    	while(true)
    	{
    		newChar = is.read();
    		if(newChar < 0)
    			break;
    		if(prevChar == '\r')
    			if(newChar == '\n')
    				break;
    		line += (char) ( newChar);
    		prevChar = newChar;
    	}
    	
    	return newChar<0?null:line.substring(0, line.length()-1);
    }
    
}

class Car implements Serializable
{
	String model;
	int year;
	String name;
	public Car(String model, int year, String name)
	{
		this.model = model;
		this.year = year;
		this.name = name;
	}
}