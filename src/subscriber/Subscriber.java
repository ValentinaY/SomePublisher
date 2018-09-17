/**
 * 
 */
package subscriber;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

import javax.swing.JFrame;

/**
 * @author valya
 *
 */
public class Subscriber {
	private static int[] brokers = {5432,5433,5434,5435,5436};
	private static int[] brokerr = {5437,5438,5439,5440,5441};
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Random aleatorio = new Random(System.currentTimeMillis());
		int id=aleatorio.nextInt();
//		C�digo de la ventana y lectura de datos
		JFrame window = new GUISubscriber();
		while(window.isActive());
		GUISubscriber suscriber = (GUISubscriber) window;
		System.out.println("Suscrito a las etiquetas de la regi�n "+suscriber.getcollecteddata()[1]+" del pa�s "+suscriber.getcollecteddata()[0]);
		String line ="";
		Socket socket = null;
		try {
			int cont=0;
			boolean sent=false;
			//C�digo para la suscripci�n
			do {
				try {
					socket = new Socket("127.0.0.1",brokers[cont]);
					if(socket.isConnected()) {
						DataInputStream in = new DataInputStream(socket.getInputStream());
						DataOutputStream out = new DataOutputStream(socket.getOutputStream());
						System.out.println("Subscriptor conectado en la direcci�n "+socket.getInetAddress()+" con puerto en "+socket.getPort());
						out.writeUTF("$SUSCRIPCI�N");
						out.writeUTF(suscriber.getcollecteddata()[0]);
						out.writeUTF(suscriber.getcollecteddata()[1]);	
						out.writeUTF(Integer.toString(id));
						in.readUTF();	
						sent =true;
						socket.close();
					}
					else {
						cont++;
					}
				} catch (Exception e) {
					cont++;
				}
			}while(!sent && cont<5);
				
			//C�digo para la recepci�n de informaci�n
			while(true) {
				socket = new Socket("127.0.0.1",brokers[cont]);
				if(socket.isConnected()) {
					DataInputStream in = new DataInputStream(socket.getInputStream());
					DataOutputStream out = new DataOutputStream(socket.getOutputStream());
					out.writeUTF("Me va a enviar informaci�n o qu�?");
					out.writeUTF("Acu�rdese de m�, yo soy "+id);
					in.readUTF();	
					sent =true;
					socket.close();
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			
	}
}


