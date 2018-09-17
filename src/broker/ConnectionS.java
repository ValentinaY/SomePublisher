/**
 * 
 */
package broker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author valya
 *
 */
public class ConnectionS extends Thread{
	/**
	 * 
	 */
	Socket subscriberSocket;
	private String collecteddata;
	public ConnectionS(Socket subscriberSocket) {
		this.subscriberSocket = subscriberSocket;
		// TODO Auto-generated constructor stub
		this.start();
		System.out.println("Suscriptor en archivo");
	}
	
	public void run() {		
		String line;
		try {
			DataInputStream in = new DataInputStream(subscriberSocket.getInputStream());
			DataOutputStream out = new DataOutputStream(subscriberSocket.getOutputStream());
			while(true) {
				line=in.readUTF();
				if(line.compareTo("$SUSCRIPCIÓN") == 0) {
					int cont=0;
					collecteddata="";
					while(cont<2) {
						line=in.readUTF();
						System.out.println("Leímos "+line);
						collecteddata+=line;
						if(cont==0) {
							collecteddata+="-";
						}
						cont++;
					}
				System.out.println("Alguien se suscribió en "+collecteddata);
				out.writeUTF("pang");
				writeonfile(collecteddata, subscriberSocket.getInetAddress().toString(), subscriberSocket.getPort());
				}
			}
		} catch (IOException e) {
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
		PrintWriter writer;
		try {
			writer = new PrintWriter("subs.txt");
			System.out.println("data:"+data);
			writer.print(data);
			writer.print(";");
			writer.print(port);
			System.out.println(port);
			writer.print(";");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
