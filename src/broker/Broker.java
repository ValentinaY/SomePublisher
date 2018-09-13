package broker;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * 
 */

/**
 * @author valya
 *
 */
public class Broker {
	
	public static void main (String args[]) {
		   try{
//			   Puerto usado por el publicador
			   int serverPort = 5982;
			   ServerSocket listenSocketp = new ServerSocket(serverPort);	   
//			   El servidor permanece escuchando
			   System.out.println("Esperando por el publicador...");

//			   Se establece un socket entre en broker y el publisher
			   Socket publisherSocket = listenSocketp.accept();
			   System.out.println("Esperando por la conexión del publicador...");
		       ConnectionP c = new ConnectionP(publisherSocket);

		   } catch(IOException e) {
			   System.out.println("Listen socket:"+e.getMessage());
		   }
}

}
