package by.bsu.core.models;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public final class TheSumOfExponents {

    final static Logger logger = Logger.getLogger(TheSumOfExponents.class);

    public static List<Double> getReactionSystem(List<Double> responseTimeList, List<Double> decayTimeList, List<Double> preExponentialFactors) {
        List<Double> reactionSystemList = new ArrayList<Double>(responseTimeList.size());
        int count = preExponentialFactors.size();

        for (Double responseTime : responseTimeList) {
            double reactionSystem = 0;
            for (int i = 0; i < count; i++) {
                reactionSystem += preExponentialFactors.get(i) * Math.exp(-1 * responseTime / decayTimeList.get(i));
            }
            reactionSystemList.add(reactionSystem);
        }
        return reactionSystemList;
    }

}