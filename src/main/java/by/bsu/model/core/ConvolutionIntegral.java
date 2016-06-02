package by.bsu.model.core;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public final class ConvolutionIntegral {

    final static Logger logger = Logger.getLogger(ConvolutionIntegral.class);

    public static List<Double> getF(List<Double> tList, List<Double> gList, double channelWidth) {
        ArrayList<Double> fList = new ArrayList<Double>(tList.size()+1);

        fList.add(0.25*tList.get(0)*gList.get(0)*channelWidth);
        for(int i = 1; i < tList.size(); i++) {
            double f = 0.5*(gList.get(i)*tList.get(0)+gList.get(0)*tList.get(i)) + 0.25*tList.get(0)*gList.get(i);
            for(int j=1;j<i;j++) {
                f += tList.get(j) * gList.get(i - j);
            }
            f *= channelWidth;
            fList.add(f);
        }
        return fList;
    }
}
