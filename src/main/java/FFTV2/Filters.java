package FFTV2;

import noteRecognizer.Complex;

import java.util.ArrayList;
import java.util.List;

public class Filters {
    public static double SinglePi = Math.PI;
    public static double DoublePi = 2*Math.PI;


    public static List<Pair> Antialiasing(List<Pair> spectrum)
    {
        List<Pair> result = new ArrayList<Pair>();
        List<Pair> data = new ArrayList<>(spectrum);
        //data.
        for (int j = 0; j < spectrum.size() - 4; j++)
        {
            int i = j;
            double x0 = data.get(i).first;
            double x1 = data.get(i+1).first;
            double y0 = data.get(i).second;
            double y1 = data.get(i+1).second;

            double a = (y1 - y0)/(x1 - x0);
            double b = y0 - a*x0;

            i += 2;
            double u0 = data.get(i).first;
            double u1 = data.get(i+1).first;
            double v0 = data.get(i).second;
            double v1 = data.get(i+1).second;

            double c = (v1 - v0)/(u1 - u0);
            double d = v0 - c*u0;

            double x = (d - b)/(a - c);
            double y = (a*d - b*c)/(a - c);

            if (y > y0 && y > y1 && y > v0 && y > v1 &&
                    x > x0 && x > x1 && x < u0 && x < u1)
            {
                result.add(new Pair(x1, y1));
                result.add(new Pair(x, y));
            }
            else
            {
                result.add(new Pair(x1, y1));
            }
        }

        return result;
    }

    public static List<Pair> GetJoinedSpectrum(
            ArrayList<Complex> spectrum0, ArrayList<Complex> spectrum1,
            double shiftsPerFrame, double sampleRate)
    {
        int frameSize = spectrum0.size();
        double frameTime = frameSize/sampleRate;
        double shiftTime = frameTime/shiftsPerFrame;
        double binToFrequancy = sampleRate/frameSize;
       List<Pair> dictionary=new ArrayList<>();
        for (int bin = 0; bin < frameSize; bin++)
        {
            double omegaExpected = DoublePi*(bin*binToFrequancy); // ω=2πf
            double omegaActual = (spectrum1.get(bin).phase() - spectrum0.get(bin).phase())/shiftTime; // ω=∂φ/∂t
            double omegaDelta = Align(omegaActual - omegaExpected, DoublePi); // Δω=(∂ω + π)%2π - π
            double binDelta = omegaDelta/(DoublePi*binToFrequancy);
            double frequancyActual = (bin + binDelta)*binToFrequancy;
            double magnitude = spectrum1.get(bin).abs() + spectrum0.get(bin).abs();
            dictionary.add(new Pair(frequancyActual, magnitude*(0.5 + Math.abs(binDelta))));
        }

        return dictionary;
    }

    public static double Align(double angle, double period)
    {
        int qpd = (int) (angle/period);
        if (qpd >= 0) qpd += qpd & 1;
        else qpd -= qpd & 1;
        angle -= period*qpd;
        return angle;
    }
}
