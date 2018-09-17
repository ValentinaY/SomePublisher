package broker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;

public class ConnectionP extends Thread {

	//Flujo de datos hacia el publisher
	DataInputStream pin;
	DataOutputStream pout;
	
	//Sockets
	Socket publisherSocket;
	Queue <String> noticias;

	
   public ConnectionP (Socket publisherSocket, Queue<String> noticias){
      try {
		  this.publisherSocket = publisherSocket;
		  this.noticias=noticias;
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
	    		  System.out.println("Received from publisher: "+data);
	    		  noticias.add(data);
//	    		  Se hace un flujo de datos hacia el publisher, no funciona sin eso
	    		  pout =new DataOutputStream(publisherSocket.getOutputStream());
//	    		  El publicador está esperando una confirmación
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