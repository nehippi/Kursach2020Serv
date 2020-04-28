package server;

import noteRecognizer.Processer;

import java.io.*;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;


public class ClientProcesser implements Runnable {
    private ServerSocket server;
    private int number;
    private Socket client;
    private Socket clientFromMainServ;


    public ClientProcesser(int number, Socket client, ServerSocket server,Socket clientFromMainServ) {
        super();
        this.clientFromMainServ=clientFromMainServ;
        this.server = server;
        this.number = number;
        this.client = client;
    }

    private void process() {
        try {
            DataInputStream dis;
            InputStream in = client.getInputStream();
            dis = new DataInputStream(in);
            String outEtalon = number + "etalonWAV.wav";
            String outAudio = number + "audioWAV.wav";
            readFromSocketConvertToWav(dis,outAudio);
            readFromSocketConvertToWav(dis,outEtalon);

            ArrayList<Integer> notes = Processer.getRecognizedNotes(new File(outAudio),new File(outEtalon));
            System.out.println(notes);

            System.out.println("byte recived");
            client.shutdownInput();
            client.shutdownOutput();
            client.close();
            server.close();
            System.out.println("succes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readFromSocketConvertToWav(DataInputStream dis,String outFileName){
        try {
        File audio = new File("temp3Gp");
        if (audio.exists()) {
            audio.delete();
        }


        FileOutputStream outputStream = new FileOutputStream(audio);
        int l = readFromSocketWriteToFile(outputStream,dis);
        outputStream.flush();
        outputStream.close();

            File outAudioWav = new File(outFileName);
            if (outAudioWav.exists()) {
                outAudioWav.delete();
            }
            String exeQuery = "ffmpeg -i " + audio.getAbsolutePath() + " -acodec pcm_s32le " + outAudioWav.getAbsolutePath();
            Process process=Runtime.getRuntime().exec(exeQuery);
            while(process.isAlive()){

            }
            System.out.println("succes");
        } catch (IOException e) {
        e.printStackTrace();
    }



    }

    public static int readFromSocketWriteToFile(FileOutputStream fos,DataInputStream dis) throws IOException {


        System.out.println("start reading");
        int lengh= dis.readInt();

        System.out.println(lengh);
        byte[] buffer = new byte[lengh];
        dis.readFully(buffer, 0, lengh);

        /*while(wasRead!=lengh){
            System.out.println(wasRead);
            lengh-=wasRead;
            wasRead=dis.read(buffer, 0, lengh);

        }
        System.out.println(wasRead);*/
        fos.write(buffer);
        return lengh;
    }

    public static void writeToSocket(Socket socket) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(666);
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        System.out.println("client connected to private server " + number);
        process();
        try {
            clientFromMainServ.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}