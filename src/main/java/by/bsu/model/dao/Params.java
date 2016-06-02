package by.bsu.model.dao;


import java.util.List;

public class Params {
    private String[] defaultTau;
    private String[] defaultPef;
    private String[] minTau;
    private String[] minPef;
    private String[] maxTau;
    private String[] maxPef;

    double eps;
    double lambda;

    public Params(String[] defaultTau, String[] defaultPef, String[] minTau, String[] minPef, String[] maxTau, String[] maxPef) {
        this.defaultTau = defaultTau;
        this.defaultPef = defaultPef;
        this.minTau = minTau;
        this.minPef = minPef;
        this.maxTau = maxTau;
        this.maxPef = maxPef;
    }

    public String[] getDefaultTau() {

        return defaultTau;
    }

    public double getEps() {
        return eps;
    }

    public void setEps(double eps) {
        this.eps = eps;
    }

    public double getLambda() {
        return lambda;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public void setDefaultTau(String[] defaultTau) {
        this.defaultTau = defaultTau;
    }

    public String[] getDefaultPef() {
        return defaultPef;
    }

    public void setDefaultPef(String[] defaultPef) {
        this.defaultPef = defaultPef;
    }

    public String[] getMinTau() {
        return minTau;
    }

    public void setMinTau(String[] minTau) {
        this.minTau = minTau;
    }

    public String[] getMinPef() {
        return minPef;
    }

    public void setMinPef(String[] minPef) {
        this.minPef = minPef;
    }

    public String[] getMaxTau() {
        return maxTau;
    }

    public void setMaxTau(String[] maxTau) {
        this.maxTau = maxTau;
    }

    public String[] getMaxPef() {
        return maxPef;
    }

    public void setMaxPef(String[] maxPef) {
        this.maxPef = maxPef;
    }
}
