package broker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ConnectionP extends Thread {

	//Flujo de datos hacia el publisher
	DataInputStream pin;
	DataOutputStream pout;
	
	//Sockets
	Socket publisherSocket;
	New noticia;

	
   public ConnectionP (Socket publisherSocket, New noticia){
      try {
		  this.publisherSocket = publisherSocket;
		  this.noticia=noticia;
//		  Se crean flujos de entrada y salida para el editor
		  pin = new DataInputStream(publisherSocket.getInputStream());
		  pout =new DataOutputStream(publisherSocket.getOutputStream());
		  this.start();
	} catch(IOException e){
	      System.out.println("Connection:"+e.getMessage());
	}
   }
   public void run() {
	      try {	                                     
	    	  while(true) {
//	    		  El servidor lee lo que envía el publicador
	    		  String data = pin.readUTF();
	    		  if(data.contains("#")) {
//	    			Queda la linea sin la almohadilla
	    			String tags = data.substring(1); 
//	    			Quedan todas las etiquetas
	    			String[] alltags =data.split(" ");
//	    			Se agregan todos los tags a la noticia
	    			for (String string : alltags) {
						noticia.addTag(string);
					}
	    		  }
	    		  else {
	    			  noticia.addContent(data);
	    		  }
	    		  
//	    		  Se hace un flujo de datos hacia el publisher, no funciona sin eso
	    		  pout =new DataOutputStream(publisherSocket.getOutputStream());
//	    		  El publicador está esperando una confirmación
	    		  
	    		  while(noticia.getValidated() != Broker.getsubscribers());
	    		  pout.writeUTF("pang");	    			  
	    	  }
	      } catch (EOFException e){
		     System.out.println("EOF:"+e.getMessage());
	      } catch(IOException e){
		     System.out.println("readline:"+e.getMessage());
	      } finally{
	      try {
	    	  pout.close();
	      }catch (IOException e){/*close failed*/}}
	   } // end 
}