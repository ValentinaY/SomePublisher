/**
 * 
 */
package subscriber;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;

/**
 * @author valya
 *
 */
public class Subscriber {
	private static int[] brokers = {5432,5433,5434,5435,5436};
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		C�digo de la ventana y lectura de datos
		JFrame window = new GUISubscriber();
		while(window.isActive());
		GUISubscriber suscriber = (GUISubscriber) window;
		System.out.println("Suscrito a las etiquetas de la regi�n "+suscriber.getcollecteddata()[1]+" del pa�s "+suscriber.getcollecteddata()[0]);
		String line ="";
		Socket socket = null;
		try {
			int cont=0;
			
			//C�digo para la suscripci�n
			do {
				try {
					socket = new Socket("127.0.0.1",brokers[cont]);
					DataInputStream in = new DataInputStream(socket.getInputStream());
					DataOutputStream out = new DataOutputStream(socket.getOutputStream());
					System.out.println("Subscriptor conectado en la direcci�n "+socket.getInetAddress()+" con puerto en "+socket.getPort());
					out.writeUTF(suscriber.getcollecteddata()[0]);
					out.writeUTF(suscriber.getcollecteddata()[1]);					
					in.readUTF();
				} catch (Exception e) {
					cont++;
				}
			}while(!socket.isConnected() && cont<5);
			socket.close();
				
			//C�digo para la recepci�n de informaci�n
			do {
				try {
					socket = new Socket("127.0.0.1",brokers[cont]);
					System.out.println("Subscriptor conectado en la direcci�n "+socket.getInetAddress()+" con puerto en "+socket.getPort());
					DataInputStream in = new DataInputStream(socket.getInputStream());
					DataOutputStream out = new DataOutputStream(socket.getOutputStream());
					line=in.readUTF();
					out.writeUTF("pang");
				}
				catch (Exception e) {
					cont++;
				}
				System.out.println("Se ha recibido la l�nea "+line);
			}while(!socket.isConnected() && cont<5);
			socket.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			
	}
}


