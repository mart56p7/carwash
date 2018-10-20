//The basecode for this java program is taken from https://www.pegaxchange.com/2017/12/07/simple-tcp-ip-server-client-java/ and https://medium.com/@ssaurel/create-a-simple-http-web-server-in-java-3fc12b29d5fd and then modified to meet our needs.

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Date;
import java.io.IOException;
import java.io.PrintWriter;

public class MyServerSocket implements Runnable
{
   private Socket client;
   
   /**
   Constructor, the socket we get here, is the "connection" from the client
   */
   public MyServerSocket(Socket c) throws Exception
   {
      client = c;
   }

   
   /**
   After we get a connection from the client, we have initialized a new object of MyServerSocket and we talk to the client through this method.
   */
   public void run()
   {
      BufferedReader in = null;
      PrintWriter out = null;
      try
      {
         String data = null;
         String clientAddress = client.getInetAddress().getHostAddress();
         System.out.println("\r\nNew connection from " + clientAddress);
        
         in = new BufferedReader(
            new InputStreamReader(client.getInputStream())
            );
         // get first line of the request from the client
         String input = in.readLine();
			// we parse the request with a string tokenizer
			StringTokenizer parse = new StringTokenizer(input);
			String method = parse.nextToken().toUpperCase(); // we get the HTTP method of the client
			// we get file requested
			String fileRequested = parse.nextToken().toLowerCase();
         if (method.equals("GET"))
         {
            System.out.println("Supported connection, trying to get " + fileRequested);
            //WE dont care about the rest of the data
            //while ((data = in.readLine()) != null)
            //{
            //   System.out.println("\r\nMessage from " + clientAddress + ": " + data);
            //}
            
            //Lets see what kinda fileRequest we got - We could add more here. If its data we want to return, we would use JSON.
            out = new PrintWriter(client.getOutputStream());
            if(fileRequested.equals("/stat1"))
            {
               String page = "<html><head></head><body><h1>stat1</h1>OMFG! BBQ CHICKEN ON FIRE<br/><img src='https://www.pcgamesn.com/wp-content/uploads/legacy/the_sims_maxis_mimes_bill_irwin_david_shiner-580x326.jpg'><a href='index'>Back to Index</a></body></html>";
               outData(out, page);
            }
            else if(fileRequested.equals("/stat2"))
            {
               String page = "<html><head></head><body><h1>stat2</h1>OMFG! BBQ CHICKEN ON FIRE<br/><img src='https://www.pcgamesn.com/wp-content/uploads/legacy/the_sims_maxis_mimes_bill_irwin_david_shiner-580x326.jpg'><a href='index'>Back to Index</a></body></html>";
               outData(out, page);
            }
            else if(fileRequested.startsWith("/users/verify/"))
            {
               try
               {
                  String userid = fileRequested.substring(14);
                  System.out.println("Looking up userid = " + userid);
                  
                  //Ok we found our user..
                  String page = "{ \"verified\": TRUE }";
                  outData(out, page);
               }
               catch(Exception e)
               {
                  System.out.println("Failuere to get userid");
               }
            }
            else if(fileRequested.startsWith("/users/getbalance/"))
            {
               try
               {
                  String userid = fileRequested.substring(18);
                  System.out.println("Looking up userid = " + userid);
                  
                  //Ok we found our user..
                  String page = "{ \"Balance\": 500 }";
                  outData(out, page);
               }
               catch(Exception e)
               {
                  System.out.println("Failuere to get userid");
               }
            }                    
            else
            {
               String page = "<html><head></head><body><h1>Default start page</h1>To get a user balance go to http://localhost/users/getBalance/<USERID><br/>To verify a user go to http://localhost/users/verify/<USERID><br/><a href='stat1'>View statistics 1</a><br/><a href='stat2'>View statistics 2</a></body></html>";
               outData(out, page);
            }
         }               
      }
      catch(Exception e)
      {
         System.err.println("Server error : " + e);
      }
      finally
      {
         try
         {
            in.close();
				out.close();
            client.close();       
         }
         catch(Exception e)
         {
            System.err.println("Error closing stream : " + e.getMessage());
         }
      }
    }

   /**
   This is there to print header information to client
   */
   private void outHeader(PrintWriter out, int length)
   {
      out.println("HTTP/1.1 200 OK");
      out.println("Server: Java HTTP Server from SSaurel : 1.0");
      out.println("Date: " + new Date());
      out.println("Content-type: text/html; charset=utf-8");
      out.println("Content-length: " + length);
      out.println(); // blank line between headers and content, very important !
      out.flush(); // flush character output stream buffer
   }
   
   /**
   Here we write the data to our client. The s is a String with html in it. So the entire page is wrapped inside s.
   */
   private void outData(PrintWriter out, String s)
   {
      outHeader(out, s.length());
      out.println(s);
      out.flush(); // flush character output stream buffer
   }


    
    public static void main(String[] args) throws Exception
    {
		try {
         //We listen on port 80 (normal webserver port)
			ServerSocket serverConnect = new ServerSocket(80);
			// we listen until user halts server execution
			while (true) {
            //When we get a new connection we create a new Socket to talk to the client. and create an object of the type MyServerSocket
				MyServerSocket myServer = new MyServerSocket(serverConnect.accept());
					System.out.println("Connecton opened. (" + new Date() + ")");			
				// create a dedicated thread to manage the client connection
				Thread thread = new Thread(myServer);
				thread.start();
			}
			
		} catch (IOException e) {
			System.err.println("Server Connection error : " + e.getMessage());
		}
    }
    
    
}