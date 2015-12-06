package by.bsu.core.models;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public final class ConvolutionIntegral {

    final static Logger logger = Logger.getLogger(ConvolutionIntegral.class);

    public static List<Double> getF(List<Double> tList, List<Double> gList, double channelWidth) {
        List<Double> fList = new ArrayList<Double>(tList.size());

        for(int i = tList.size(); i >= 0; i--) {
            double f = 0.5*(gList.get(0)*tList.get(i)+gList.get(i)*tList.get(0)) + 0.25*tList.get(i)*gList.get(0);
            for(int j=1;j<i;j++) {
                f += tList.get(j) * gList.get(i - j);
            }
            f *= channelWidth;
            fList.add(i, f);
        }

        fList.add(0, 0.25*tList.get(0)*gList.get(0)*channelWidth);

        return fList;
    }
}
