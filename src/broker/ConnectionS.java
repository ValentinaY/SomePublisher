/**
 * 
 */
package broker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
	}
	
	public void run() {
		try {
			String line;
			DataInputStream in = new DataInputStream(subscriberSocket.getInputStream());
			DataOutputStream out = new DataOutputStream(subscriberSocket.getOutputStream());
			int cont=0;
			collecteddata="";
			while(cont<2) {
				line=in.readUTF();
				collecteddata.concat(line);
				if(cont==1) {
					collecteddata.concat("-");
				}
				cont++;
			}
			System.out.println("Alguien se suscribió en "+collecteddata);
			out.writeUTF("pang");
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

}
