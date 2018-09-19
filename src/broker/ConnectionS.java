/**
 * 
 */
package broker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
	New noticia;
		
	public ConnectionS(int port,New noticia) {
		this.port=port;
		this.noticia=noticia;
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
							if(cont==0) {
								collecteddata+="-";
							//AÑADIR DATOS AL HASHMAP
							}
							if(cont==1) {
								collecteddata+=";";
							}
							cont++;
						}
						//Queda de forma: COLOMBIA-CARIBE;1278
						out.writeUTF("Pang broker-sub");
						writeonfile(collecteddata, subscriberSocket.getInetAddress().toString(), subscriberSocket.getPort());
					}
					if(line.compareTo("#SEARCH") == 0) {
						//noticia.validatedpp();
						//System.out.println("VALIDATED: "+noticia.getValidated());
						//Se lee el id del cliente
						line=in.readUTF();
//						System.out.println(line);
						/**
						 * Se verifica el id del cliente en el archivo de suscriptores, obteniendo la región.
						 * Se buscan las etiquetas de la región.
						 * Se verifica si hay noticias para la etiqueta.
						 * Se envían las noticias al suscriptor.
						 */
						
//						Se verifica el id del cliente en el archivo de suscriptores, obteniendo la región.
						String region=getregionfromid(line);
						//System.out.println(region);
						
						ArrayList<String> tags = new ArrayList<String>();
						
						Tags tag = new Tags();
						
						for (String code : tag.getMap().get(region))tags.add(code);
						//se busca en el mapa las etiquetas asociadas a esa zona y se insertan en tags
						
						boolean belongs =false;
						for (String tags1 : tags) {
							for (String tags2 : noticia.getTags()) {
								if(tags1.compareTo(tags2) == 0) {
									belongs=true;
									break;
								}
							}
						}
						
//						Se verifica si las etiquetas de la noticia contienen al menos una etiqueta del cliente
//						Si es así, se envía
						if(belongs==true) {
							out.writeUTF("WAIT");
							for (String string : noticia.getContent()) {
								out.writeUTF(string);								
							}
							out.writeUTF("DONE");
							noticia=new New();
						}
						else {
							out.writeUTF("PANG");
						}
						
//						En cualquier caso, se entiende que el suscriptor ha sido validado
						
						
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
	
	public static String getregionfromid(String id) {
		String region="";
		try {
			FileReader f = new FileReader("subs.txt");
			@SuppressWarnings("resource")
			BufferedReader b = new BufferedReader(f);
			String[] lines;
			while((region = b.readLine())!=null) {
				lines = region.split(";");
				if(id.compareTo( lines[1] )==0) {
					return lines[0];
				}
			}
			b.close();			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return region;
	}
}
