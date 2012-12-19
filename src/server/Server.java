/*
 * Copyright 2012 KTH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

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