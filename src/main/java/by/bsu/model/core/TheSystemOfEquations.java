package by.bsu.model.core;

import Jama.Matrix;

import java.util.List;
import java.util.Map;

import static by.bsu.model.core.Derivative.*;

public final class TheSystemOfEquations {
    public static Matrix solveSystem(double lambda, int m, List<Double> feList, List<Double> tList, List<Double> tauList, List<Double> pefList) {
        Map<Integer, List<Double>> derivative = getDerivative(tList, tauList, pefList);
        List<Double> chiSquarePartDiff = getChiSquarePartDiff(m, feList, tList, tauList, pefList);
        Matrix A = new Matrix(m, m);

        for (int j = 0; j < m; j++) {
            List<Double> paramListJ = derivative.get(j);
            for (int p = 0; p < m; p++) {
                List<Double> paramListP = derivative.get(p);
                double sum = 0;
                for (int i = 0; i < tList.size(); i++) {
                    if (feList.get(i) == 0) {
                        continue;
                    }
                    sum += paramListJ.get(i) * paramListP.get(i) / feList.get(i);
                }
                if (j == p) {
                    sum *= (1 + lambda);
                }
                A.set(j, p, sum);
            }
        }
        double[] chiSquarePartDiffArr = chiSquarePartDiff.stream().mapToDouble(d -> d).toArray();
        Matrix B = new Matrix(chiSquarePartDiffArr, m);
        return A.solve(B);
    }
}
