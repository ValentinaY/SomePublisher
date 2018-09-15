package publisher;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
public class Publisher {
	private static final String FILENAME = "news.txt";
	private static int[] ports = {5982,5983,5984,5985,5986};
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Socket socket=null;
			int cont=0;
			do {
				try {
					socket = new Socket("127.0.0.1",ports[cont]);				
				} catch (Exception e) {
					cont++;
				}				
			}while(!socket.isConnected() && cont<5);
//			Se crean los canales de difusión
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out;
			FileReader fr = new FileReader(FILENAME);
			BufferedReader br = new BufferedReader(fr);

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
					out = new DataOutputStream(socket.getOutputStream());
					if(!sCurrentLine.isEmpty()) {
						System.out.println("Estoy enviando un "+sCurrentLine);
						out.writeUTF(sCurrentLine);
						String pang = in.readUTF();
						System.out.println(pang);						
					}
				}
			}
			br.close();
			socket.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}