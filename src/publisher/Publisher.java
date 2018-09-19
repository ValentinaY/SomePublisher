package publisher;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
public class Publisher {
	private static final String FILENAME = "news.txt";
	private static int[] ports = {5982,5983,5984,5985,5986};
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			int cont=0;
			Socket socket= new Socket();
			System.out.println(cont);
			//int cont=0;
			do {
				try {
					System.out.println(cont);
					socket = new Socket("127.0.0.1",ports[cont]);
					
				} catch (Exception e) {
					cont++;
					if(cont==5)cont=0;
				}				
			}while(!socket.isConnected() && cont<5);
//			Se crean los canales de difusión
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out;
			FileReader fr = new FileReader(FILENAME);
			BufferedReader br = new BufferedReader(fr);
/***********************************************************************************/
/*LECTURA DE ARCHIVOS DE LAS NOTICIAS*/
			String sCurrentLine="";
			System.out.println("Reading file...");
//			Mientras queden lineas por leer
			while ((sCurrentLine = br.readLine()) != null) {
				
//				Si la linea actual indica la hora
				if(sCurrentLine.startsWith("$")){
//					Se inicia la comparación con la hora dada
					Date date = new Date();
					
//					Se crea un calendario y se le asigna la hora actual
					Calendar calendar = GregorianCalendar.getInstance();
					calendar.setTime(date);
					
//					La linea leída se divide
					sCurrentLine = sCurrentLine.substring(1);
					System.out.println(sCurrentLine);
					String[] parts =sCurrentLine.split(":");
					while(calendar.get(Calendar.HOUR_OF_DAY) >= Integer.parseInt(parts[0]) && calendar.get(Calendar.MINUTE) >= Integer.parseInt(parts[1])){
						while(calendar.get(Calendar.SECOND) < Integer.parseInt(parts[2])) {
							date = new Date();
							calendar.setTime(date);
//							System.out.println("Esperando a "+Integer.parseInt(parts[2])+" desde "+calendar.get(Calendar.SECOND));
						}
						break;
					}
				}
				else {
					if(sCurrentLine.startsWith("/")) {
						System.out.println("Alcanzado fin de la noticia, esperando pang");	
						try {
							socket.setSoTimeout(10000);
							out = new DataOutputStream(socket.getOutputStream());
							System.out.println("Estoy enviando un :"+sCurrentLine);
							out.writeUTF(sCurrentLine);
							String pang = in.readUTF();
							System.out.println(pang);	
						}catch (SocketTimeoutException ste) {
						    
						}
						
					}
					else {
					try {
						socket.setSoTimeout(10000);
						out = new DataOutputStream(socket.getOutputStream());
						if(!sCurrentLine.isEmpty()) {
							System.out.println("Estoy enviando un :"+sCurrentLine);
							out.writeUTF(sCurrentLine);
						//	String pang = in.readUTF();
						//	System.out.println(pang);						
						}
					}catch (SocketTimeoutException ste) {
					    System.out.println("Socket timed out!");
					    do {
							try {
								System.out.println(cont);
								socket = new Socket("127.0.0.1",ports[cont]);
								
							} catch (Exception e) {
								cont++;
								if(cont==5)cont=0;
							}				
						}while(!socket.isConnected());
					}
				}
				}
				
			}
			br.close();
/**********************************************************************************/
			socket.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}