package by.bsu.model.core;

import org.apache.commons.math3.distribution.*;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sorca on 5/25/2016.
 */
public class ConstData {
    private static ConstData ourInstance = new ConstData();
    private static double sigma;
    private static double mu;
    private static double w;
    private static int numOfPockets;
    private static List<Double> tList;
    private static List<Double> gList;

    public static ConstData getInstance() {
        return ourInstance;
    }

    private ConstData() {
    }

    public double getSigma() {
        return sigma;
    }

    public int getNumOfPockets() {
        return numOfPockets;
    }

    public void setNumOfPockets(int numOfPockets) {
        ConstData.numOfPockets = numOfPockets;
    }

    public void setSigma(double sigma) {
        ConstData.sigma = sigma;
    }

    public double getMu() {
        return mu;
    }

    public void setMu(double mu) {
        ConstData.mu = mu;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        ConstData.w = w;
    }

    public List<Double> getTList() {
        return tList.isEmpty() ? recreateTList() : tList;
    }

    public List<Double> recreateTList() {
        tList = new ArrayList<>(numOfPockets);
        double prev = 0;
        for (int i = 0; i < numOfPockets; i++) {
            tList.add(i, prev);
            prev += w;
        }
        gList = recreateGList();
        return tList;
    }

    public List<Double> getGList() {
        return gList.isEmpty() ? recreateGList() : gList;
    }

    public void setGList(List<Double> value) {
        gList = new ArrayList<>(value);
    }

    private List<Double> recreateGList() {
        gList = new ArrayList<>(tList.size());
        NormalDistribution normalDistribution = new NormalDistribution(mu, sigma);
        gList.addAll(tList.stream().map(normalDistribution::density).collect(Collectors.toList()));
        Collections.replaceAll(gList, 0d, Double.MIN_VALUE);
        return gList;
    }
}
