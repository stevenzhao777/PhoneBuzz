import java.net.InetSocketAddress;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpContext;

import java.io.OutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

public class PhoneBuzzPhase1 {
	public static void main(String[] args) throws Exception{
		HttpServer server=HttpServer.create(new InetSocketAddress(8080),0);
		server.createContext("/twilio", new twilioHandler());
		server.start();
        
	}
	
	static class twilioHandler implements HttpHandler{
		 public void handle(HttpExchange httpExchange){
            /**
             * Remember to implement input validation and X-Twilio-Signature validation (which seems to require https). 			 
             */
			 
			 Headers header=httpExchange.getResponseHeaders();
		     header.add("Content-Type", "application/xml");
			 
			 
				 if(httpExchange.getRequestMethod().equals("GET")){
					 
					 String urlQuery=httpExchange.getRequestURI().getQuery();
					 String[] params=urlQuery.split("&");
					 LinkedHashMap<String,String> paramTable=new LinkedHashMap<String,String>();
					 for(String param:params){
						 String[] pair=param.split("=");
						 if(pair.length>1){
							 paramTable.put(pair[0], pair[1]);
						 }
						 else{
							 paramTable.put(pair[0],"");
						 }
					 }
					 
					 StringBuilder result=new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response>");
				     
					 if(paramTable.containsKey("Digits")){
						 String buzz=buzzGen(Integer.parseInt(paramTable.get("Digits")));
						 result.append("<Say>");
						 result.append(buzz);
						 result.append("</Say>");
						 result.append("</Response>");
						 
					 }
					 else{
						 String response="Please enter the number you want the buzz game to end at. Finish with the pound key.";
						 result.append("<Gather timeout=\"10\" method=\"GET\">");
						 result.append("<Say>");
						 result.append(response);
						 result.append("</Say>");
						 result.append("</Gather>");
						 result.append("</Response>");
					 }
					 
					 try{
						 httpExchange.sendResponseHeaders(200, result.toString().length());
					 }
					 catch(IOException i){
						 System.err.println("Reponse header could not be set to 200");
						 i.printStackTrace();
					 }
					 
					 OutputStream output=httpExchange.getResponseBody();
					 try{
						 output.write(result.toString().getBytes());
					 }
					 catch(IOException i){
						 System.err.println("Could not write to response");
						 i.printStackTrace();
					 }
					 finally{
						 try{
							 output.close();
						 }
						 catch(IOException i){
							 System.err.println("Could not close output stream!");
							 i.printStackTrace();
						 }
					 }
					 
				 }
				 else{
					 try{
						 httpExchange.sendResponseHeaders(405, -1);
					 }
					 catch(IOException i){
						 System.err.println("Reponse header could not be set to 405");
						 i.printStackTrace();
					 }
				 }
			 
		 }
	}
	
	private static String buzzGen(int num){
		if(num<1){
			return "Please enter a number bigger than zero.";
		}
		StringBuilder result=new StringBuilder();
		for(int i=1;i<=num;i++){
			if(i%15==0){
				result.append("Fizz Buzz, ");
			}
			else if(i%3==0){
				result.append("Fizz, ");
			}
			else if(i%5==0){
				result.append("Buzz, ");
			}
			else{
				result.append(i+", ");
			}
		}
		
		return result.substring(0,result.length()-2);
	}
}


