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
//			   Puerto usado por el suscriptor
			   int subsPort = 5432;
//			   Se crea un socket para el publicador y otro hacia el cliente
			   ServerSocket listenSocketp = new ServerSocket(serverPort);	  
			   ServerSocket listenSockets = new ServerSocket(subsPort);
//			   El servidor permanece escuchando
			   System.out.println("Esperando por el publicador...");

//			   Se establece un socket entre en broker y el publisher
			   System.out.println("Esperando por la conexión del publicador...");
			   Socket publisherSocket = listenSocketp.accept();
			   ConnectionP c = new ConnectionP(publisherSocket);
			   System.out.println("Esperando por los suscriptores...");
			   Socket subscriberSocket = listenSockets.accept();

		   } catch(IOException e) {
			   System.out.println("Listen socket:"+e.getMessage());
		   }
}

}
