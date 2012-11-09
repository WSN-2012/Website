package server;

//import server.Textfile;
import java.net.*;
import java.io.IOException;

import java.net.Socket;

/**
*
* @author Nikolaos Roumpoutsos
*         Natalia Paratsikidou
*/
public class Server {

  static final String USAGE = "java Server [hostname] [port] ";
  static String filename = "words.txt";//consider passing it as parameter

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws IOException {

      int port = 8081;
      String host = "localhost";
      boolean listening = true;
      if (args.length > 1) {//NEED control for hostname !!!!!!!!!!!!!
          try {
              port = Integer.parseInt(args[1]);
          } catch (NumberFormatException e) {
              System.err.println(USAGE);
              System.exit(0);
          }
      }
      //   if (port.equalsIgnoreCase("-h")
      //       || port.equalsIgnoreCase("-help")) {
      //           System.out.println(USAGE);
      //           System.exit(1);
      //   }



      try {
          InetAddress addr = InetAddress.getByName(host);
          
          ServerSocket serversocket = new ServerSocket(port, 1000, addr);

          while (listening) {    // the main server's loop
              Socket clientsocket = serversocket.accept();
              DeviceHandler handler = new DeviceHandler(clientsocket);//open a thread to each requested device
              handler.setPriority(handler.getPriority() + 1);
              handler.start();
          }
          serversocket.close(); 
      } catch (IOException e) {
          System.out.println(e);
          System.exit(0);
      }

  }
}