//import java.util.ConcurrentLinkedQueue;
package noteRecognizer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Analizer {
    ConcurrentLinkedQueue<double[]> fftValues;
    ConcurrentLinkedQueue<Double> freqs;

    public Analizer(ConcurrentLinkedQueue<double[]> fftValues, ConcurrentLinkedQueue<Double> freqs) {
        this.fftValues = fftValues;
        this.freqs = freqs;
    }


    public void putFreqs() {

        while (!fftValues.isEmpty()) {

            //analize start
            double max = 0;
            double[] fftValue = fftValues.poll();
            for (int i = 0; i < fftValue.length / 2; i++) {
                if (fftValue[i] > max) max = fftValue[i];
            }
            /**
             * ищем максимальное значение массива после ффт
             * и определяем в какой частоте оно расположено
             */

            try {
               // FileWriter fileWriter=new FileWriter("bytes.txt",true);
                double freq = 0;
                for (int i = 0; i < fftValue.length / 2; i++) {
                    if (max == fftValue[i]) {
                        freq = i * 44100 / 1024;//возможно надо i+1 но это не точно
                        if (freq > 260 && freq < 2000) {
                           // fileWriter.write(""+freq+'\n');
                            freqs.add(freq);//если что тут был конструктор Double
                        }
                    }
                }
               // fileWriter.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //analize end


        }


    }


    int[] getRangeOfBigestColumn(double[] arr, int window) {
        int[] range = {0, 0};
        double max = 0;

        for (int i = 0; i < arr.length; i++) {
            double[] subArr = Arrays.copyOfRange(arr, i, i + window);
            double mean = getMeanFromArray(subArr);
            if (mean > max) {
                max = mean;
                range = new int[]{i, i + window};
            }
        }
        return range;
    }

    public static double getMeanFromArray(double[] array) {
        double sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += Math.abs(array[i]);
        }
        return sum / array.length;
    }


}
