package by.bsu.core.models;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public final class NormalDistribution {
    final static Logger logger = Logger.getLogger(TheSumOfExponents.class);

    public static List<Double> getG (double sigma, double m, List<Double> tList)
    {
        List<Double> gList = new ArrayList<Double>(tList.size());

        for (Double t : tList) {
            gList.add((1/(sqrt(PI)*sigma))*exp(-1*((t-m)/(2*sigma))));
        }
        return gList;
    }
}
