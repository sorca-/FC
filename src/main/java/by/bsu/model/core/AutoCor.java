package by.bsu.model.core;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class AutoCor {
    public static final List<Double> calculateWR(List<Double> exp, List<Double> th) {
        List<Double> result = new ArrayList<>(exp.size());
        for (int i = 0; i < exp.size(); i++) {
            double del = exp.get(i);
            double raz = exp.get(i) - th.get(i);
            double res = del == 0 ? raz : raz / sqrt(del);
            result.add(res);
        }
        return result;
    }

    public static final List<Double> calculateAC(List<Double> exp, List<Double> th) {
        int k = exp.size();
        List<Double> result = new ArrayList<>(exp.size());
        List<Double> r = calculateWR(exp, th);

        double divider = 0d;
        for (int i = 1; i < k; i++) {
            divider += r.get(i)*r.get(i);
        }
        divider /= k;

        for (int j = 1; j < k/2; j++) {
            double sum = 0d;
            for (int i = 1; i < k - j + 1; i++) {
                sum += r.get(i)*r.get(i+j-1);
            }

            sum /= (k-j+1);

//            result.add(sum / divider);
            result.add(divider == 0 ? sum : sum/divider);

        }

        return result;
    }
}
