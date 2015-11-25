package by.bsu.core.models;

import org.apache.log4j.Logger;

import java.util.List;

public final class ConvolutionIntegral {

    final static Logger logger = Logger.getLogger(ConvolutionIntegral.class);

    public static List<Double> getScatterConvolution(List<Double> responseTimeList, List<Double> responseEquipmentList,
                                                     int NumberOfChannels) {
        for (int i = NumberOfChannels - 1; i >= 1; i--) {
            double value = 0.5 * (responseEquipmentList.get(0) * responseTimeList.get(i) + responseEquipmentList.get(i) * responseTimeList.get(0))
                    + 0.25 * responseTimeList.get(i) * responseEquipmentList.get(0);
            for (int j = 1; j < i; j++) {
                value += responseTimeList.get(j) * responseEquipmentList.get(i - j);
            }
            responseTimeList.set(i, value);
        }
        responseTimeList.set(0, (0.25 * responseTimeList.get(0) * responseEquipmentList.get(0)));
        return responseTimeList;
    }
}
