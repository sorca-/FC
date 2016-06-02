package by.bsu.model.core;

import by.bsu.model.generator.GeneratorHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Derivative {
    private static final Double delta = 0.05;

    public static Map<Integer, List<Double>> getDerivative(List<Double> tList, List<Double> tauList, List<Double> pefList) {
        Map<Integer, List<Double>> resultMap = new HashMap<>(tList.size());
        List<Double> sumOfExponent = GeneratorHelper.generateData(tauList, pefList, false, false);
        int numOfParam = 0;

        for (int i = 0; i < tauList.size(); i++) {
            List<Double> tauModList = new ArrayList<>(tauList);
            List<Double> pefModList = new ArrayList<>(pefList);
            Double tauH = delta * tauList.get(i);
            Double pefH = delta * pefList.get(i);

            tauModList.set(i, tauModList.get(i)+tauH);
            List<Double> tauSOEModList = GeneratorHelper.generateData(tauModList, pefList, false, false);

            pefModList.set(i, pefModList.get(i)+pefH);
            List<Double> pefSOEModList = GeneratorHelper.generateData(tauList, pefModList, false, false);

            resultMap.put(numOfParam++, getDifference(tauSOEModList, sumOfExponent, tauH));
            resultMap.put(numOfParam++, getDifference(pefSOEModList, sumOfExponent, pefH));
        }
        return resultMap;
    }

    public static List<Double> getChiSquarePartDiff(int m, List<Double> feList, List<Double> tList, List<Double> tauTheorList, List<Double> peTheorList) {
        int size = tList.size();
        List<Double> result = new ArrayList<>(m);
        Map<Integer, List<Double>> partDivOfSumOfExp = getDerivative(tList, tauTheorList, peTheorList);
        List<Double> ftList = GeneratorHelper.generateData(tauTheorList, peTheorList, false, false);
        for (int i = 0; i < m; i++) {
            double sum = 0;
            List<Double> param = partDivOfSumOfExp.get(i);             //Частная производная по i параметру во всех точках t.
            for (int j = 0; j < size; j++) {
                if (feList.get(j) == 0) {
                    continue;
                }
                sum += (feList.get(j) - ftList.get(j)) * param.get(j) / feList.get(j);
            }
            result.add(sum);
        }
        return result;
    }

    private static List<Double> getDifference(List<Double> minuend, List<Double> subtrahend, Double h) {
        int size = minuend.size();
        List<Double> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add((minuend.get(i) - subtrahend.get(i)) / h);
        }
        return result;
    }
}
