package FFTV2;

import java.util.ArrayList;

public class AnalizerV2 {
    public static  double getPitch(ArrayList<Double> amp,ArrayList<Double> pitch){
        return amp.get(getIndexOfMax(pitch));
    }
    private static int getIndexOfMax(ArrayList<Double> arr){
        double max=Double.MIN_VALUE;
        int index=0;
        for (int i=0;i<arr.size()/2;i++){
            if(arr.get(i)>max){
                max=arr.get(i);
                index=i;
            }
        }
        return index;
    }
}
