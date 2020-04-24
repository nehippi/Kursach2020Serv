package noteRecognizer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
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
            WaveFile waveFile = new WaveFile(file);
            int numberSamles = waveFile.getData().length / waveFile.getSampleSize();
            int n = 1024;
            int[] samples = new int[numberSamles];
            for (int i = 0; i < numberSamles; i++) {
                samples[i] = waveFile.getSampleInt(i);
            }
            int countOfPortions = numberSamles / n;
            for (int i = 0; i < countOfPortions - 1; i++) {
                sampleInt.add(Arrays.copyOfRange(samples, i * n, (i + 1) * n));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


