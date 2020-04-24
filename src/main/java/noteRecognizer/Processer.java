package noteRecognizer;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
//import java.util.ConcurrentLinkedQueue;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.swing.*;

public class Processer {

public ArrayList<Integer> getRecognizedNotes(File audio,File etalon) {
 ConcurrentLinkedQueue<double[]> fftValues = new ConcurrentLinkedQueue<>();//значения после преобразования фурье
 ConcurrentLinkedQueue<int[]> sampleInt = new ConcurrentLinkedQueue<>();//значения семплов
 ConcurrentLinkedQueue<Double> freqs = new ConcurrentLinkedQueue<>();//частоты
 ConcurrentLinkedQueue<Double> equalizedFreqs = new ConcurrentLinkedQueue<>();//сглаженые частоты
 ArrayList<Double> freqsList=new ArrayList<>();
 WaveFileBuilder waveFileBuilder = new WaveFileBuilder(sampleInt);
 FFTProcesser fftProcesser = new FFTProcesser(sampleInt, fftValues);
 Analizer analizer = new Analizer(fftValues, freqs);

 waveFileBuilder.putAudioWav(etalon);
 fftProcesser.putFFTValues();
 analizer.putFreqs();
 while(!freqs.isEmpty()){
  freqsList.add(freqs.poll());
 }
 double etalonFreq=0;
 double sum=0;
 for(int i=0;i<freqsList.size();i++){
  sum+=freqsList.get(i);
 }
 etalonFreq=sum/freqsList.size();
 freqsList=new ArrayList<>();

 waveFileBuilder.putAudioWav(audio);
 fftProcesser.putFFTValues();
 analizer.putFreqs();
 while(!freqs.isEmpty()){
  freqsList.add(freqs.poll());
 }

 NoteRecognizer noteRecognizer=new NoteRecognizer(freqsList,etalonFreq);
 return noteRecognizer.recognizeNotes();
}


}
