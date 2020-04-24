package noteRecognizer;
//import java.util.ConcurrentLinkedQueue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class FFTProcesser {
    ConcurrentLinkedQueue<int[]> samples;
    ConcurrentLinkedQueue<double[]> fftvalues;

    public FFTProcesser(ConcurrentLinkedQueue<int[]> samples, ConcurrentLinkedQueue<double[]> fftvalues) {
        this.samples = samples;
        this.fftvalues = fftvalues;
    }


    public void putFFTValues() {
        /**
         * из значений семплов херню от фурье
         */


        while (!samples.isEmpty()) {

            try {
                int[] samplesArr = samples.poll();
                Complex[] samplesComplex = new Complex[1024];
                for (int i = 0; i < 1024; i++) {
                    samplesComplex[i] = new Complex((double) samplesArr[i], 0.0);
                }
                // System.out.println("fft");
                Complex[] out = FFT.fft(samplesComplex);
                double[] realPart = new double[out.length];
                double[] imaginaryPart = new double[out.length];
                for (int i = 0; i < out.length; i++) {
                    realPart[i] = out[i].re();
                    imaginaryPart[i] = out[i].im();
                }
                fftvalues.add(realPart);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}

