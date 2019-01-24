package ServerPack;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

import trabalho.Airport;
import trabalho.ComparatePlanes;
import trabalho.Plane;
import trabalho.Plane_Commercial;
import trabalho.Plane_Medic;
import trabalho.Plane_Transport;
import trabalho.Runway;

public class Server {

	private GUIServer gui;
	private static Airport airport;
	private Runway r1;
	private String path = "/Users/nicolerita/Documents/workspace/ADHGest_NicoleRita/config.txt";
	private String s_name = "Aeroporto Humberto Deglado";
	
	private static LinkedList<Plane> planes = new LinkedList<>();
	private static final int PORT_NUMBER = 5001;

	private boolean open_connection;
	
	private int i_fuel_capacity,
	i_fuel_consumption,
	i_fuel_moment,
	i_plane_capacity,
	i_time_landing,
	i_timer,
	i_plane_number = 0;


	public Server() throws IOException { //Server(String s)? --> para .jar
		airport = new Airport(s_name,this);
	}

	public void readFile(String path) throws IOException{

		File file = new File(path);
		FileReader reader = new FileReader(file);
		BufferedReader buffer = new BufferedReader(reader);
		int num = 0;
		String linha;
		while((linha =buffer.readLine()) != null){
			
			i_plane_number++;
			String[] strings = linha.split(" ");
			s_name = strings[0];
			i_fuel_capacity = Integer.parseInt(strings[1]);
			i_fuel_moment = Integer.parseInt(strings[2]);
			i_fuel_consumption = Integer.parseInt(strings[3]);


			if(linha.startsWith("U")){
				Plane_Medic pm = new Plane_Medic(i_plane_number, s_name, i_fuel_capacity, 
						i_fuel_moment, i_fuel_consumption, this.airport);
				planes.add(pm);
			}
			if(linha.startsWith("C")){
				i_plane_capacity = Integer.parseInt(strings[4]);
				Plane_Commercial pc= new Plane_Commercial(i_plane_number, s_name, i_fuel_capacity,
						i_fuel_moment,i_fuel_consumption, i_plane_capacity, this.airport);
				planes.add(pc);
			}
			if(linha.startsWith("P")){
				i_plane_capacity = Integer.parseInt(strings[4]);
				Plane_Transport pt= new Plane_Transport(i_plane_number, s_name, i_fuel_capacity, 
						i_fuel_moment,i_fuel_consumption, i_plane_capacity, this.airport);
				planes.add(pt);
			}
		}
		reader.close();
	}

	public void run() throws IOException{
		ServerSocket ss = new ServerSocket(PORT_NUMBER);
		Socket socket = null;
		InputStream in;
		OutputStream out;
		
		while(true){
			try{
				socket = ss.accept();
			} catch (IOException ex)  {
				System.out.println("Can't accept client connection");
			}
			open_connection = true;
			in = socket.getInputStream();
			
			out = new FileOutputStream("/Users/nicolerita/Documents/workspace/ADHGest_NicoleRita/config.txt");
			byte[] bytes = new byte[16*1024];
			int count;
			while((count = in.read(bytes))>0)
				out.write(bytes,0,count);
			
			readFile("/Users/nicolerita/Documents/workspace/ADHGest_NicoleRita/config.txt");
			
			airport.addPlanesToAirport(planes);
			planes.clear();
			airport.manage_planes();
			
			while(open_connection) {
				
			}
			System.out.println("Shutting off client");
			out.close();
			in.close();
			socket.close();
			ss.close();
		}

	}
	
	public void closeClientConnection() {
		this.open_connection = false;
	}
	

	public static void main(String[] args) throws IOException{
		//Server s = new Server(args[0]); --> para correr no terminal em .jar (java -jar proj3.jar args[0] )
		Server s = new Server();
		s.run();
	}

}
