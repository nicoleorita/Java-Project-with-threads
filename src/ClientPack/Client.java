package ClientPack;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client {

	final static String host = "127.0.0.1";
	final static int PORT_NUMBER = 5001;
	public static final String path= "/Users/nicolerita/Documents/workspace/ADHGest_NicoleRita/config.txt";
	
	

	private static void run() throws IOException {

		Socket socket = null;
		socket = new Socket(host,PORT_NUMBER);
		
		File file = new File("/Users/nicolerita/Documents/workspace/ADHGest_NicoleRita/config.txt");
		long length = file.length();
		
		byte[] bytes = new byte[16*1024];
		
		InputStream in = new FileInputStream(file);
		OutputStream out = socket.getOutputStream();
		
		int count;
		while((count = in.read(bytes))>0)
			out.write(bytes, 0, count);
		out.flush(); 
		out.close();
		in.close();
		socket.close();
	}

	public static void main(String[] args) throws IOException {
		run();
	}
}
