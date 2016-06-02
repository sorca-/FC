package by.bsu.model.core;

import Jama.Matrix;
import by.bsu.model.dao.Params;
import by.bsu.model.generator.GeneratorHelper;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static by.bsu.model.assay.ChiSquare.*;
import static by.bsu.model.core.TheSystemOfEquations.*;
import static java.lang.Math.*;

public final class SolveIterator {
    private final static Logger logger = Logger.getLogger(SolveIterator.class);

    public static List<Double> calculateParam(List<Double> tList, List<Double> feList, int m, Double eps, Double lambdaMax, Params params) {
        double lambda = 0.001;
        List<Double> startTauList = new ArrayList<>(m);
        List<Double> startPefList = new ArrayList<>(m);

        for (int i = 0; i < m / 2; i++) {
            startPefList.add(Double.parseDouble(params.getDefaultPef()[i]));
            startTauList.add(Double.parseDouble(params.getDefaultTau()[i]));
        }
        List<Double> currentTauList = new ArrayList<>(startTauList);
        List<Double> currentPefList = new ArrayList<>(startPefList);

        List<Double> startFList = GeneratorHelper.generateData(startTauList, startPefList, false, false);
        double startChiSq = calculate(m, feList, startFList);
        double exitChi = Double.MAX_VALUE;
        while (lambda < lambdaMax && exitChi >= eps) {
            Matrix deltaParam = solveSystem(lambda, m, feList, tList, startTauList, startPefList);

            int j = 0;
            for (int i = 0; i < m / 2; i++) {
                double newTau = startTauList.get(i) + deltaParam.get(j++, 0);
                double newPef = startPefList.get(i) + deltaParam.get(j++, 0);

                currentTauList.set(i, checkValue(params.getMaxTau()[i], params.getMinTau()[i], newTau));
                currentPefList.set(i, checkValue(params.getMaxPef()[i], params.getMinPef()[i], newPef));

            }

            List<Double> endFList = GeneratorHelper.generateData(currentTauList, currentPefList, false, false);
            double endChiSq = calculate(m, feList, endFList);
            exitChi = abs(endChiSq - startChiSq) / startChiSq;
            if (startChiSq >= endChiSq) {
                startChiSq = endChiSq;
                lambda *= 0.1;
                startTauList = new ArrayList<>(currentTauList);
                startPefList = new ArrayList<>(currentPefList);
            } else {
                lambda *= 10;
            }
        }
        currentTauList.addAll(currentPefList);
        params.setEps(exitChi);
        params.setLambda(lambda);
        return currentTauList;
    }

    private final static double checkValue(String max, String min, Double value) {
        if (!StringUtils.isEmpty(max) && value > Double.parseDouble(max)) {
            return Double.parseDouble(max);
        }
        if (!StringUtils.isEmpty(min) && value < Double.parseDouble(min)) {
            return Double.parseDouble(min);
        }
        return value;
    }

}
