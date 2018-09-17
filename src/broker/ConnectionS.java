/**
 * 
 */
package broker;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

/**
 * @author valya
 *
 */
public class ConnectionS extends Thread{
	/**
	 * 
	 */
	ServerSocket listenSockets = null;
	Socket subscriberSocket;
	static PrintWriter writer;
	int port;
	private String collecteddata;
	Queue <String> noticias;
	HashMap<Integer, List<String>> etiquetas = new HashMap<Integer, List<String>>();
	HashMap<Integer, Queue<String>> correo = new HashMap<Integer, Queue<String>>();
	
	public ConnectionS(int port,Queue<String> noticias) {
		this.port=port;
		this.noticias=noticias;

		try {
			listenSockets = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.start();
	}
	
	public void run() {	
		String line;
		try {
			while(true) {
				this.subscriberSocket = listenSockets.accept();
				DataInputStream in = new DataInputStream(subscriberSocket.getInputStream());
				DataOutputStream out = new DataOutputStream(subscriberSocket.getOutputStream());
				line="";
				try {
					line=in.readUTF();
					System.out.println("El broker leyó del suscriptor: "+line);
					if(line.compareTo("$SUSCRIPCIÓN") == 0) {
						int cont=0;
						collecteddata="";
						while(cont<3) {
							line=in.readUTF();
							collecteddata+=line;
							System.out.println("El sub me manda: "+collecteddata);
							if(cont==0 || cont==1) {
								collecteddata+="-";
								
							//AÑADIR DATOS AL HASHMAP
							}
							cont++;
						}
						
						out.writeUTF("Pang broker-sub");
						writeonfile(collecteddata, subscriberSocket.getInetAddress().toString(), subscriberSocket.getPort());
						
					}
					if(line.compareTo("#SEARCH") == 0) {
						line=in.readUTF();
						//Queue <String> aux=correo.get(Integer.parseInt(line));
						if(correo.get(Integer.parseInt(line)).peek().equals(null))out.writeUTF("Pang broker-sub");
						//si la cola esta vacia envia un pang
						else {
							while(!correo.get(Integer.parseInt(line)).peek().equals(null)) {
								out.writeUTF(correo.get(Integer.parseInt(line)).poll());
								//Si la cola de correo no esta vacia, envia hasta que se vacia
								
							}
						}
					}
				}catch (IOException e) {
					// TODO: handle exception
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
		}	
}
	
	public String getcode() {
		return collecteddata;
	}

	public static void writeonfile(String data,String inetAdress, int port) {
		try{
			  FileWriter fstream = new FileWriter("subs.txt",true);
			  BufferedWriter out = new BufferedWriter(fstream);
			  out.write(data+";"+port+";");
			  out.newLine();
			  out.close();
		  }catch (Exception e){
			 System.err.println("Error while writing to file: " +
		          e.getMessage());
		  }
	}
}
