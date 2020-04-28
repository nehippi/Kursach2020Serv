package noteRecognizer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Equalizer {


    public static ArrayList<Double> getEqalizedFreqs(ArrayList<Double> list) {
        int window = 3;
        for (int i = 1; i < list.size()-1; i++) {

                list.set(i, getMediana((list.subList(i - 1, i+1))));

        }
        return list;
    }


    private Double getMax(List<Double> list) {
        Double max = Double.MIN_VALUE;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > max) {
                max = list.get(i);
            }
        }
        return max;
    }


    private Double getMin(ArrayList<Double> list) {
        Double min = Double.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) < min) {
                min = list.get(i);
            }
        }
        return min;
    }

    private static Double getMediana(List<Double> list) {
        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }
        return sum / list.size();
    }
}