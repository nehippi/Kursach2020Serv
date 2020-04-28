package noteRecognizer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
//import java.util.ConcurrentLinkedQueue;

public class WaveFileBuilder {
    ConcurrentLinkedQueue<int[]> sampleInt;


    public WaveFileBuilder(ConcurrentLinkedQueue<int[]> sampleInt) {
        this.sampleInt = sampleInt;
    }

    public void putAudioWav(File file) {
        try {
//            File temp=new File("tmp.wav");
//            if(temp.exists()){
//                temp.delete();
//            }
//
//
//            int[] samples1 = new int[1024];
//            for(int i=0; i < samples1.length; i++){
//                samples1[i] = (int)Math.round((Integer.MAX_VALUE/2)*
//                        (Math.sin(2*Math.PI*440*i/44100)));
//            }
//            WaveFile waveFile=new WaveFile(4,44100,1,samples1);
//            waveFile.saveFile(new File("testwaw1.wav"));

//
//
//
//
//            int numberSamles = waveFile.getData().length / waveFile.getSampleSize();
//
//            int[] samples = new int[numberSamles];
//
//            for (int i = 0; i < numberSamles; i++) {
//                samples[i] = waveFile.getSampleInt(i);
//            }
//            waveFile=new WaveFile(4,44100,1,samples);
//            waveFile.saveFile(new File("temp.wav"));
//            waveFile=new WaveFile(new File("temp.wav"));
//
//            numberSamles = waveFile.getData().length / waveFile.getSampleSize();
//            samples = new int[numberSamles];
//
//            for (int i = 0; i < numberSamles; i++) {
//                samples[i] = waveFile.getSampleInt(i);
//            }
//           try{
         WaveFile waveFile=new WaveFile(file);
            int numberSamles = waveFile.getData().length / waveFile.getSampleSize();

            int n = 1024;
             int countOfPortions = numberSamles / n;
             int[] s=getSamplesFromFile(waveFile);
            for (int i = 15; i < countOfPortions ; i++) {
                int[] bufSamples=Arrays.copyOfRange(s, i * n, (i + 1) * n);
               // new WaveFile(4,44100,1,bufSamples).saveFile(new File(i+".wav"));

               // sampleInt.add( getSamplesFromFile(new WaveFile(new File(i+".wav"))));

                sampleInt.add(bufSamples);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int[] getSamplesFromFile(WaveFile waveFile){
        try {
            //FileWriter fileWriter=new FileWriter("bytes.txt");
            int numberSamles = waveFile.getData().length / waveFile.getSampleSize();
            int[] samples = new int[numberSamles];

            for (int i =0; i < numberSamles; i++) {
                samples[i] = waveFile.getSampleInt(i);
             //   fileWriter.write(""+samples[i]+'\n');
            }
           // fileWriter.flush();
            return samples;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}


