package by.bsu.model.core;

import java.util.Collections;
import java.util.List;

public final class Normalization {

    @SafeVarargs
    public final static void normalize(int cof, List<Double>... listToNormalize) {
        Double maxValue = Collections.max(listToNormalize[0]);
        for (List<Double> itList : listToNormalize) {
            for (int i = 0; i < itList.size(); i++) {
                itList.set(i, (itList.get(i)/maxValue)*cof);
            }
        }
    }
}
