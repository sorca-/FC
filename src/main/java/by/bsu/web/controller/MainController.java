package by.bsu.web.controller;

import by.bsu.web.jsonview.Views;
import by.bsu.web.model.AjaxResponseBody;
import com.fasterxml.jackson.annotation.JsonView;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class MainController {

//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String printWelcome(ModelMap model) {
//
//		model.addAttribute("message", "Hello World");
//		return "hello";
//
//	}
//
//	@RequestMapping(value = "/hello/{name:.+}", method = RequestMethod.GET)
//	public ModelAndView hello(@PathVariable("name") String name) {
//
//		ModelAndView model = new ModelAndView();
//		model.setViewName("hello");
//		model.addObject("msg", name);
//
//		return model;
//
//	}

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseBody generate(@RequestParam("W") final String w, @RequestParam("T") final String t,
                                     @RequestParam("M") final String m, @RequestParam("sigma") final String sigma,
                                     @RequestParam("n") final String n, @RequestParam("tau") final String[] tauArray,
                                     @RequestParam("a") final String[] aArray) {
        AjaxResponseBody result = new AjaxResponseBody();
        Double[] arr = {1.0, 3.0, 7.0, 15.0, 11.0, 10.0, 3.0, 0.0};
        result.setCode("200");
        result.setMsg("");
        result.setArr(arr);
        return result;
    }

}