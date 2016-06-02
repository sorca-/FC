package by.bsu.model.assay;

import org.apache.log4j.Logger;

import java.util.List;

import static java.lang.Math.*;

public final class ChiSquare {
    private final static Logger logger = Logger.getLogger(ChiSquare.class);

    public static double calculate(int m, List<Double> feList, List<Double> ftList) {
        int n = feList.size();
        double sum = 0;
        for (int i = 0; i < n; i++) {
            if (feList.get(i) == 0) {
                n--;
            } else {
                sum += (pow(feList.get(i) - (ftList.get(i)), 2) / feList.get(i));
            }
        }
        Double beforeTheSum = 1.0 / (n - m - 1);
        return beforeTheSum * sum;
    }
}
