package noteRecognizer;

import FFTV2.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
//import java.util.ConcurrentLinkedQueue;


public class Processer {

public static ArrayList<Integer> getRecognizedNotes(File audio,File etalon) {
 ConcurrentLinkedQueue<double[]> fftValues = new ConcurrentLinkedQueue<>();//значения после преобразования фурье
 ConcurrentLinkedQueue<int[]> sampleInt = new ConcurrentLinkedQueue<>();//значения семплов
 ConcurrentLinkedQueue<Double> freqs = new ConcurrentLinkedQueue<>();//частоты
 ConcurrentLinkedQueue<Double> equalizedFreqs = new ConcurrentLinkedQueue<>();//сглаженые частоты
 WaveFileBuilder waveFileBuilder = new WaveFileBuilder(sampleInt);
 FFTProcesser fftProcesser = new FFTProcesser(sampleInt, fftValues);
 Analizer analizer = new Analizer(fftValues, freqs);

 //test
 ConcurrentLinkedQueue<Complex[]> BFvalues = new ConcurrentLinkedQueue<>();
 ConcurrentLinkedQueue<List<Pair>> outValues = new ConcurrentLinkedQueue<>();
 NewFFTProcesser newFFTProcesser = new NewFFTProcesser(sampleInt, BFvalues);
 FilterProcesser filterProcesser = new FilterProcesser(BFvalues, outValues);

 waveFileBuilder.putAudioWav(etalon);
 newFFTProcesser.putFFTValues();
 filterProcesser.putOutValues();

 ArrayList<Double> ampletude = new ArrayList<>();
 ArrayList<Double> pitch = new ArrayList<>();
 ArrayList<Double> outFreqs=new ArrayList<>() ;
 while (!outValues.isEmpty()){
 List<Pair> out = outValues.poll();
 for (Pair var : out
 ) {
  ampletude.add(var.first);
  pitch.add(var.second);
 }
 outFreqs.add(AnalizerV2.getPitch(ampletude, pitch));
 ampletude=new ArrayList<>();
 pitch=new ArrayList<>();
}
 //test
 System.out.println("wdwewewe");
 System.out.println(outFreqs);

// waveFileBuilder.putAudioWav(etalon);
// fftProcesser.putFFTValues();
// analizer.putFreqs();
// while(!freqs.isEmpty()){
//  freqsList.add(freqs.poll());
// }
// System.out.println(freqsList);
// freqsList=Equalizer.getEqalizedFreqs(freqsList);
// System.out.println(freqsList);
//
 double etalonFreq=0;
 double sum=0;
 for(int i=0;i<outFreqs.size();i++){
  sum+=outFreqs.get(i);
 }
 etalonFreq=sum/outFreqs.size();

 //freqsList=new ArrayList<>();
 System.out.println(etalonFreq);

 outFreqs=new ArrayList<>();
 waveFileBuilder.putAudioWav(audio);
 newFFTProcesser.putFFTValues();
 filterProcesser.putOutValues();
  pitch = new ArrayList<>();

 ampletude = new ArrayList<>();
 outFreqs=new ArrayList<>() ;
 while (!outValues.isEmpty()){
  List<Pair> out = outValues.poll();
  for (Pair var : out
  ) {
   ampletude.add(var.first);
   pitch.add(var.second);
  }
  outFreqs.add(AnalizerV2.getPitch(ampletude, pitch));
  ampletude=new ArrayList<>();
  pitch=new ArrayList<>();
 }
 //test
 System.out.println("wdwewewe");
 System.out.println(outFreqs);
 outFreqs=Equalizer.getEqalizedFreqs(outFreqs);
 System.out.println(outFreqs);
 NoteRecognizer noteRecognizer=new NoteRecognizer(outFreqs,etalonFreq);
 return noteRecognizer.recognizeNotes();
}


}
