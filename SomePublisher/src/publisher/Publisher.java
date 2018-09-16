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
import java.util.Scanner;
public class Publisher {
	private static final String FILENAME = "news.txt";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			int port=5432;
			Socket socket;
			socket = new Socket("127.0.0.1",port);
//			Se crean los canales de difusión
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out;
/**************************************************************/
//lectura de archivos
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
					out.writeUTF(sCurrentLine);
					String pang = in.readUTF();
				}
			}//elihw
			br.close();
//Fin lectura de archivos
/**************************************************************/
/*****************************************************************/
//Comunicacion
//			El sistema inicia cuando el publicador escribe
			String publication;
			Scanner scanner = new Scanner(System.in);
			publication=scanner.nextLine();
//			Se leen por consola publicaciones. 
			while(publication!="exit") {
//Se envía la cadena UTF por el flujo de salida hacia el broker
				out = new DataOutputStream(socket.getOutputStream());
				out.writeUTF(publication);
				
/**********************************************************
 * NOTA DE OMAR: EN ESTA PARTE SE INICIA UN CONTEO DE TIEMPO
 *  Y CUANDO PASEN 5 SEGUNDOS CAMBIA EL PUERTO DEL SOCKET
 *  AL SIGUIENTE Y AL LLEGAR AL ULTIMO VUELVE A PROBAR CON EL
 *  PRIMERO, EL PROBLEMA ESTA QUE  EL IN.READUTF ES BLOQUEANTE
 **********************************************************/
				Date startDate = new Date();//Empiezo a contar el tiempo
				int numSeconds=0;//variable para contar los segundos
				while(numSeconds<=5) {
				Date endDate = new Date();//Termino de contar
				 numSeconds = (int)((endDate.getTime() - startDate.getTime()) / 1000);
				}
				if(numSeconds>=5) {
						if(port>5437)port=5432;
						else port++;
						socket = new Socket("127.0.0.1",port);
					}
				
/*************************************************************/				
				in = new DataInputStream(socket.getInputStream());
				String data = in.readUTF();
				System.out.println("Received from server: "+data);
				publication=scanner.nextLine();
			}
//FinComunicacion
/*****************************************************************/
			

			socket.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}