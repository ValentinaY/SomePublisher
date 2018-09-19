package broker;
import java.io.File;
import java.io.FileNotFoundException;
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
		New noticia = new New();
		ServerSocket listenSocketp = null;
		Scanner scanner = new Scanner(System.in);
	
		   try{
//			   Los brokers no se conectan entre ellos, no se sabe en qu� puerto escuchan los otros servidores
			   
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
			   @SuppressWarnings("unused")
			   ConnectionS d = new ConnectionS(subsPort,noticia);

			   
//			   Se establece un socket entre en broker y el publisher
			   System.out.println("Esperando por el publicador...");				   
			   Socket publisherSocket = listenSocketp.accept();
			   @SuppressWarnings("unused")
			   ConnectionP c = new ConnectionP(publisherSocket,noticia);
			   
			   
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
	public static int getsubscribers() {
		File input = new File("subs.txt"); Scanner iterate;
		int numLines=0; 
		try {
			iterate = new Scanner(input);
			while(iterate.hasNextLine()) { iterate.nextLine(); numLines++; }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numLines;
	}
}
