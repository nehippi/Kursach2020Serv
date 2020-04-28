package FFTV2;

import noteRecognizer.Complex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FilterProcesser {

    int shiftPerFrame = 32;
    ConcurrentLinkedQueue<Complex[]> FFT;
    ConcurrentLinkedQueue<List<Pair>> newfftvalues;

    public FilterProcesser(ConcurrentLinkedQueue<Complex[]> FFT, ConcurrentLinkedQueue<List<Pair>> newfftvalues) {
        this.FFT = FFT;
        this.newfftvalues = newfftvalues;
    }

    public void putOutValues() {
        while (!FFT.isEmpty()) {
            List<Pair> a = getFilteredArr();
            if (a != null) newfftvalues.add(a);
        }
    }

    public List<Pair> getFilteredArr() {
        try {
            Complex[] spectrum0 = FFT.poll();
            Complex[] spectrum1 = FFT.peek();
            if (spectrum1 == null) return null;

            for (int i = 0; i < 1024; i++) {
                spectrum0[i] = spectrum0[i].divides(new Complex(1024, 0));
                spectrum1[i] = spectrum1[i].divides(new Complex(1024, 0));
            }

            ArrayList<Complex> arr0 = new ArrayList<>();
            arr0.addAll(Arrays.asList(spectrum0));


            ArrayList<Complex> arr1 = new ArrayList<>();
            arr1.addAll(Arrays.asList(spectrum1));
            List spectrum = Filters.GetJoinedSpectrum(arr0, arr1, shiftPerFrame, 44100);
            return spectrum;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

}
