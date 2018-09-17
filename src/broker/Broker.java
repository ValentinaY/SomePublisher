package broker;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author valya
 *
 */
public class Broker {
	
	public static void main (String args[]) {
		ServerSocket listenSocketp = null;
		ServerSocket listenSockets = null;
		Scanner scanner = new Scanner(System.in);
		   try{
//			   Los brokers no se conectan entre ellos, no se sabe en qué puerto escuchan los otros servidores
			   
			   System.out.println("Escriba el puerto en el que desea crear el socket publicador-servidor. (5982)");
//			   Puerto usado por el publicador
			   int serverPort = scanner.nextInt();
			   System.out.println("Escriba el puerto en el que desea crear el socket subscritor-servidor. (5432)");
//			   Puerto usado por un subscriptor
			   int subsPort = scanner.nextInt();
			   scanner.close();
//			   Se crea un socket para el publicador y otro hacia el cliente
			   listenSockets = new ServerSocket(subsPort);
			   listenSocketp = new ServerSocket(serverPort);	  
			   
			   System.out.println("Esperando por los suscriptores...");
			   Socket subscriberSocket = listenSockets.accept();
			   ConnectionS d = new ConnectionS(subscriberSocket);

			   
//			   Se establece un socket entre en broker y el publisher
			   System.out.println("Esperando por el publicador...");				   
			   Socket publisherSocket = listenSocketp.accept();
			   @SuppressWarnings("unused")
			   ConnectionP c = new ConnectionP(publisherSocket);
			   System.out.println("Broker activo en la ip "+subscriberSocket.getInetAddress()+" y el puerto "+subscriberSocket.getPort());
			   
			   //Aquí está el código PAÍS-REGIÓN para asociar el enum Tag
			   String codigo = d.getcode();
			   System.out.println(codigo);
		   } catch(IOException e) {
			   System.out.println("Listen socket:"+e.getMessage());
		   }
		   finally {
			try {
				listenSocketp.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
}
}
