package by.bsu.model.generator;

import by.bsu.model.core.ConstData;
import by.bsu.model.core.ConvolutionIntegral;
import by.bsu.model.core.Normalization;
import by.bsu.model.core.TheSumOfExponents;
import org.apache.commons.math3.distribution.PoissonDistribution;

import java.util.Collections;
import java.util.List;

public final class GeneratorHelper {
    public static List<Double> generateData(List<Double> tauList, List<Double> pefList, boolean addNoise, boolean normalize) {
        ConstData constData = ConstData.getInstance();
        List<Double> gList = constData.getGList();
        List<Double> reactionSystem = TheSumOfExponents.getReactionSystem(constData.getTList(), tauList, pefList);
        List<Double> expResult = ConvolutionIntegral.getF(reactionSystem, gList, constData.getW());
        if (normalize) {
            Normalization.normalize(5000, expResult);
            Normalization.normalize(5000, gList);
            Collections.replaceAll(gList, 0d, Double.MIN_VALUE);
        }
        if (addNoise) {
            PoissonDistribution poissonDistribution;
            for (int i = 0; i < expResult.size(); i++) {
                poissonDistribution = new PoissonDistribution(expResult.get(i));
                expResult.set(i, (double) poissonDistribution.sample());
            }
            for (int i = 0; i < gList.size(); i++) {
                poissonDistribution = new PoissonDistribution(gList.get(i));
                gList.set(i, (double) poissonDistribution.sample());
            }
        }
        constData.setGList(gList);
        return expResult;
    }
}
