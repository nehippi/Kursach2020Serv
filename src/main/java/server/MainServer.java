package server;

import noteRecognizer.Processer;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class MainServer {
	 
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		int clientCounter=0;
		ServerSocket server = new ServerSocket(8080, 10, InetAddress.getByName("192.168.1.240"));
        System.out.println("server started");

//        String outEtalon = "testwaw1.wav";
//		String outAudio = 1 + "audioWAV.wav";
//		Processer processer=new Processer();
//		ArrayList<Integer> notes = processer.getRecognizedNotes(new File(outAudio),new File(outEtalon));

//		System.out.println(notes);
		while(true) {
			Socket client=server.accept();
			System.out.println("client connected");
			ServerSocket privateServer=new ServerSocket(0, 0, InetAddress.getByName("192.168.1.240"));
			writeToSocket(client,privateServer.getLocalPort());
			System.out.println("private server started "+clientCounter);
			clientCounter++;
			Thread clientThread=new Thread(new ClientProcesser(clientCounter, privateServer.accept(),privateServer,client));

			clientThread.setName("client"+clientCounter);
			clientThread.start();

		}

	}


	public static void writeToSocket(Socket socket,int msg) {
		try {
			OutputStream outputStream = socket.getOutputStream();
			DataOutputStream dos=new DataOutputStream(outputStream);
			dos.writeInt(msg);
			dos.flush();
			dos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
