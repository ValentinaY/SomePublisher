package broker;
import java.io.IOException;
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
		Scanner scanner = new Scanner(System.in);
		   try{
//			   Los brokers no se conectan entre ellos, no se sabe en qué puerto escuchan los otros servidores
			   
			   System.out.println("Escriba el puerto en el que desea crear el socket publicador-servidor. (5982)");
//			   Puerto usado por el publicador
//			   int serverPort = scanner.nextInt();
			   int serverPort =5982;
			   System.out.println("Escriba el puerto en el que desea crear el socket subscritor-servidor. (5432)");
//			   Puerto usado por un subscriptor
//			   int subsPort = scanner.nextInt();
			   int subsPort = 5432;
			   scanner.close();
//			   Se crea un socket para el publicador y otro hacia el cliente
			   listenSocketp = new ServerSocket(serverPort);	  
			   
			   System.out.println("Esperando por los suscriptores...");
			   ConnectionS d = new ConnectionS(subsPort);

			   
//			   Se establece un socket entre en broker y el publisher
			   System.out.println("Esperando por el publicador...");				   
			   Socket publisherSocket = listenSocketp.accept();
			   @SuppressWarnings("unused")
			   ConnectionP c = new ConnectionP(publisherSocket);
			   
			   
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
