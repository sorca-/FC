package by.bsu.core.models;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public final class TheSumOfExponents {

    final static Logger logger = Logger.getLogger(TheSumOfExponents.class);

    public static List<Double> getReactionSystem(List<Double> tList, List<Double> tauList, List<Double> pefList) {
        List<Double> iList = new ArrayList<Double>(tList.size());

        for (Double responseTime : tList) {
            double reactionSystem = 0;
            for (int i = 0; i < pefList.size(); i++) {
                reactionSystem += pefList.get(i) * exp(-1 * responseTime / tauList.get(i));
            }
            iList.add(reactionSystem);
        }
        return iList;
    }
}