package by.bsu.web.model;

import by.bsu.web.jsonview.Views;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

public class AjaxResponseBody {

    @JsonView(Views.Public.class)
    String msg;

    @JsonView(Views.Public.class)
    String code;

    @JsonView(Views.Public.class)
    Double[] arr;

    public Double[] getArr() {
        return arr;
    }

    public void setArr(Double[] arr) {
        this.arr = arr;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
