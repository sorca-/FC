package by.bsu.model.dao;

import java.util.Collections;
import java.util.List;

public class Result {
    private List<Double> thResult;
    private List<Double> expResult;
    private List<Double> eqResult;
    private List<Double> tauResult;
    private List<Double> pefResult;
    private double chiSq;
    private double lambda;
    private double eps;
    private List<Double> autoCor;

    public Result(List<Double> thResult, List<Double> expResult, List<Double> eqResult, List<Double> tauResult, List<Double> pefResult, double chiSq, double lambda, double eps, List<Double> autoCor) {

        this.thResult = thResult;
        this.expResult = expResult;
        this.eqResult = eqResult;
        this.tauResult = tauResult;
        this.pefResult = pefResult;
        this.chiSq = chiSq;
        if (Double.isNaN(eps)) {
            this.eps = 0;
        } else {
            this.eps = eps;
        }
        this.lambda = lambda;
        this.autoCor = autoCor;

//        double sumPef = pefResult.stream().mapToDouble(Double::doubleValue).sum();
//        for (int i = 0; i < tauResult.size(); i++) {
//            pefResult.set(i, pefResult.get(i)/sumPef);
//        }
    }

    public double getLambda() {
        return lambda;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public double getEps() {
        return eps;
    }

    public void setEps(double eps) {
        this.eps = eps;
    }

    public List<Double> getExpResult() {
        return expResult;
    }

    public void setExpResult(List<Double> expResult) {
        this.expResult = expResult;
    }

    public List<Double> getEqResult() {
        return eqResult;
    }

    public void setEqResult(List<Double> eqResult) {
        this.eqResult = eqResult;
    }

    public List<Double> getThResult() {
        return thResult;
    }

    public void setThResult(List<Double> thResult) {
        this.thResult = thResult;
    }

    public List<Double> getTauResult() {
        return tauResult;
    }

    public void setTauResult(List<Double> tauResult) {
        this.tauResult = tauResult;
    }

    public List<Double> getPefResult() {
        return pefResult;
    }

    public void setPefResult(List<Double> pefResult) {
        this.pefResult = pefResult;
    }

    public double getChiSq() {
        return chiSq;
    }

    public void setChiSq(double chiSq) {
        this.chiSq = chiSq;
    }
}
