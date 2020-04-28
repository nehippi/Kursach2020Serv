package FFTV2;

import noteRecognizer.Complex;

import java.util.concurrent.ConcurrentLinkedQueue;

public class NewFFTProcesser {
    ConcurrentLinkedQueue<int[]> samples;
    ConcurrentLinkedQueue<Complex[]> fftvalues;

    public NewFFTProcesser(ConcurrentLinkedQueue<int[]> samples, ConcurrentLinkedQueue<Complex[]> fftvalues) {
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
                //Complex[] out = FFT.fft(samplesComplex);
                Complex[] out= NewFFT.DecimationInFrequency(samplesComplex,true);

                double[] realPart = new double[out.length];
                double[] imaginaryPart = new double[out.length];
                //   FileWriter fileWriter=new FileWriter("bytes.txt",true);


                for (int i = 0; i < out.length; i++) {
                    realPart[i] = out[i].re();
                    imaginaryPart[i] = out[i].im();
                    //   fileWriter.write(""+realPart[i]+'\n');
                }
                // fileWriter.flush();
                fftvalues.add(out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
