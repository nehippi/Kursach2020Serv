package noteRecognizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoteRecognizer {
    ArrayList<Double> freqs;
    Thread thread;
    int l = 3; //size of "window"
    int err = 4; //error from noise
    ArrayList<Double> arr;// array with freqs

    double etalon;

    public NoteRecognizer(ArrayList<Double> freqs, double etalon) {
        this.freqs = freqs;
        this.etalon = etalon;
        arr = freqs;
    }

    public ArrayList<Integer> recognizeNotes() {
        ArrayList<Double> freqs = new ArrayList<>();
        ArrayList<Double> buff = new ArrayList<>();
        ArrayList<Double> notes = new ArrayList<>();
        buff.add(arr.get(0));
        for (int i = 1; i < arr.size(); i++) {
            if ((arr.get(i) > buff.get(buff.size() - 1) + err || arr.get(i) < buff.get(buff.size() - 1) - err) && buff.size() > 40) {//если есть скачок и массив больше сорока
                notes.add(getAverage(buff));
                buff = new ArrayList<>();
                buff.add(arr.get(i + 1));
            } else {
                if ((arr.get(i) > buff.get(buff.size() - 1) + err || arr.get(i) < buff.get(buff.size() - 1) - err) && buff.size() < 40) {//скачок есть но массив не полон
                    buff = new ArrayList<>();
                    buff.add(arr.get(i + 1));
                }
            }

        }
        System.out.println(notes);
        ArrayList<Integer> out = new ArrayList<>();
        for (Double fr :
                notes) {
            out.add(recognizeNote(fr));
        }
        return out;


    }


    private double getAverage(ArrayList<Double> arr) {
        double sum = 0;
        for (int i = 0; i < arr.size(); i++) {
            sum += arr.get(i);
        }
        return sum / arr.size();
    }

    private int recognizeNote(double freq) {

        double numberOfTone = (Math.log(freq / etalon) / Math.log(2)) * 12;
        return (int) numberOfTone;
    }
}