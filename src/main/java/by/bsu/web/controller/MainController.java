package by.bsu.web.controller;

import by.bsu.model.assay.ChiSquare;
import by.bsu.model.core.*;
import by.bsu.model.dao.Params;
import by.bsu.model.dao.Result;
import by.bsu.model.generator.GeneratorHelper;
import by.bsu.web.jsonview.Views;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    final static Logger logger = Logger.getLogger(MainController.class);

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    @ResponseBody
    public List<Double> generate(HttpSession httpSession,
                                 @RequestParam("W") final String inputW,
                                 @RequestParam("numOfPockets") final String inputNumOfPockets,
                                 @RequestParam("M") final String inputMu,
                                 @RequestParam("sigma") final String inputSigma,
                                 @RequestParam("tau") final String[] inputTauArray,
                                 @RequestParam("a") final String[] inputPefArray,
                                 @RequestParam("noise") final boolean noise,
                                 @RequestParam("normalization") final boolean norm) {

        //init params
        ConstData constData = ConstData.getInstance();
        constData.setW(Double.parseDouble(inputW));
        constData.setNumOfPockets(Integer.parseInt(inputNumOfPockets));
        constData.setSigma(Double.parseDouble(inputSigma));
        constData.setMu(Double.parseDouble(inputMu));
        constData.recreateTList();

        List<Double> tauList = new ArrayList<>(inputTauArray.length);
        for (String tau : inputTauArray) {
            tauList.add(Double.parseDouble(tau));
        }
        List<Double> pefList = new ArrayList<>(inputPefArray.length);
        for (String pef : inputPefArray) {
            pefList.add(Double.parseDouble(pef));
        }

        //generate arrays;
        List<Double> expResult = GeneratorHelper.generateData(tauList, pefList, noise, norm);

        //store to session;
        httpSession.setAttribute("expResult", expResult);
        httpSession.setAttribute("EquipmentResponse", constData.getGList());

        //return array
        return expResult;
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/getExperimentalData", method = RequestMethod.POST)
    @ResponseBody
    public List<Double> getGeneratedArray(HttpSession httpSession) {
        //return array from session
        return (List<Double>) (httpSession.getAttribute("expResult"));

    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/getEquipmentResponse", method = RequestMethod.POST)
    @ResponseBody
    public List<Double> getEquipmentResponse(HttpSession httpSession) {
        //return array from session
        return (List<Double>) (httpSession.getAttribute("EquipmentResponse"));

    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/analyze", method = RequestMethod.POST)
    @ResponseBody
    public String analyze(HttpSession httpSession,
                          @RequestParam("n") final String n,
                          @RequestParam("eps") final String eps,
                          @RequestParam("maxIt") final String maxIt,
                          @RequestParam("a") final String[] aArray,
                          @RequestParam("tau") final String[] tauArray,
                          @RequestParam("a_min") final String[] aMinArray,
                          @RequestParam("tau_min") final String[] tauMinArray,
                          @RequestParam("a_max") final String[] aMaxArray,
                          @RequestParam("tau_max") final String[] tauMaxArray) {

        ConstData constData = ConstData.getInstance();
        int nI = Integer.parseInt(n);
        double epsD = Double.parseDouble(eps);
        double maxItI = Double.parseDouble(maxIt);
        Params params = new Params(tauArray, aArray, tauMinArray, aMinArray, tauMaxArray, aMaxArray);

        List<Double> expResult = (List<Double>) (httpSession.getAttribute("expResult"));

        //tau then pef
        List<Double> tauAndPef = SolveIterator.calculateParam(constData.getTList(), expResult, nI * 2, epsD, maxItI, params);

//        for (Double aDouble : tauAndPef) {
//            logger.debug("param: " + aDouble);
//        }

        //generate analyze array
        List<Double> thResult = GeneratorHelper.generateData(tauAndPef.subList(0, nI), tauAndPef.subList(nI, tauAndPef.size()), false, false);

        //write to last element xi_2
//        double chi = ChiSquare.calculate(nI * 2, genResult, theoreticalResult);
        double chi = ChiSquare.calculate(nI * 2, expResult, thResult);

//        theoreticalResult.add(chi);
//        thResult.add(chi);

//        return theoreticalResult;
        List<Double> autoCor = AutoCor.calculateAC(expResult, thResult);
        Result res = new Result(thResult, expResult, constData.getGList(), tauAndPef.subList(0, nI), tauAndPef.subList(nI, tauAndPef.size()), chi, params.getLambda(), params.getEps(), autoCor);
        Gson gson = new Gson();
        return gson.toJson(res);
    }

}