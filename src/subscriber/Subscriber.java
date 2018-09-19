/**
 * 
 */
package subscriber;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Random;

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
		Random aleatorio = new Random(System.currentTimeMillis());
		int id=aleatorio.nextInt();
//		Código de la ventana y lectura de datos
		JFrame window = new GUISubscriber();
		while(window.isActive());
		GUISubscriber suscriber = (GUISubscriber) window;
		System.out.println("Suscrito a las etiquetas de la región "+suscriber.getcollecteddata()[1]+" del país "+suscriber.getcollecteddata()[0]);//esto para interfaz
		Socket socket = null;
		try {
			int cont=2;
			boolean sent=false;
			//Código para la suscripción
			do {
			//	System.out.println("puerto:"+brokers[cont]);
				try {
					socket = new Socket("127.0.0.1",brokers[cont]);
					socket.setSoTimeout(5000);
					if(socket.isConnected()) {
						
						DataInputStream in = new DataInputStream(socket.getInputStream());
						DataOutputStream out = new DataOutputStream(socket.getOutputStream());
						System.out.println("Subscriptor conectado en la dirección "+socket.getInetAddress()+" con puerto en "+socket.getPort());
						out.writeUTF("$SUSCRIPCIÓN");
						out.writeUTF(suscriber.getcollecteddata()[0]);
						out.writeUTF(suscriber.getcollecteddata()[1]);	
						out.writeUTF(Integer.toString(id));
						String data = in.readUTF();	
						System.out.println("In:"+data);
						sent =true;
						socket.close();
					}
					else {
						cont++;
						if(cont==5)cont=0;
					}
				} catch (Exception e) {
					cont++;
					if(cont==5)cont=0;
				}
				
			}while(!sent );
				
			String received="";
			//Código para la recepción de información
			while(true) {
				try {
					socket = new Socket("127.0.0.1",brokers[cont]);
					if(socket.isConnected()) {
						DataInputStream in = new DataInputStream(socket.getInputStream());
						DataOutputStream out = new DataOutputStream(socket.getOutputStream());
						out.writeUTF("#SEARCH");
						//System.out.println(Integer.toString(id));
						out.writeUTF(Integer.toString(id));
						received=in.readUTF();	
						//System.out.println(received);
						/**
						 * En una interfaz se muestran los datos de 'received'
						 */
						if(received.equals("WAIT")) {
							while(!received.equals("DONE")) {
								received=in.readUTF();	
								System.out.println(received);
							}
						}
						if(received.equals("PANG")) {
							do {
								try {
									socket = new Socket("127.0.0.1",brokers[cont]);
									
								} catch (Exception e) {
									cont++;
									if(cont==5)cont=0;
								}				
							}while(!socket.isConnected());
						}
						//sent =true;
						socket.close();
					}
			 }catch (Exception e2) {
				 //   System.out.println("Buscando Broker en puerto:"+ brokers[cont]);
				    do {
						try {
							socket = new Socket("127.0.0.1",brokers[cont]);
							
						} catch (Exception e) {
							cont++;
							if(cont==5)cont=0;
						}				
					}while(!socket.isConnected());
				}
			}
		} catch (Exception  e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			
	}
}


