package by.bsu.core.assay;

import org.apache.log4j.Logger;

import java.util.List;

import static java.lang.Math.*;

public final class ChiSquare {
    final static Logger logger = Logger.getLogger(ChiSquare.class);

    public static double calculate(int m, List<Double> feList, List<Double> ftList) {
        int n = feList.size();
        double beforeTheSum = 1 / (n - m - 1);
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += (pow(feList.get(i) - (ftList.get(i)), 2) / feList.get(i));
        }
        return beforeTheSum * sum;
    }
}
