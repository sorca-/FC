<%--
  Created by IntelliJ IDEA.
  User: sorca
  Date: 10/4/2015
  Time: 7:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <link rel="stylesheet" href="../../../resources/core/css/common.css" type="text/css"/>
    <script type="text/javascript" src="../../../resources/core/js/jquery-1.11.3.js"></script>
    <script type="text/javascript" src="../../../resources/core/js/jquery.canvasjs.min.js"></script>
    <script type="text/javascript" src="../../../resources/core/js/common.js"></script>

    <title><spring:message code="main.hello"/></title>

</head>
<body>
<div id="parent">
    <div id="chart">
        <input type="button" value="<spring:message code="buttons.generate"/>" id="generate" disabled>
        <input type="button" value="<spring:message code="buttons.analyze"/>" id="analyze" disabled>
        <div id="chartContainer"></div>
        <table id="xi_2" style="display: none;">
            <tr>
                <td><img src="../../../resources/core/image/xi_2.png"></td>
                <td><label> = 0</label></td>
            </tr>
        </table>
    </div>

    <div id="params_div">
        <table>
            <tr>
                <td><label><spring:message code="label.model"/>:</label></td>
                <td><img src="../../../resources/core/image/model_exp.png"/></td>
            </tr>
            <tr>
                <td><label>M: </label></td>
                <td><input type="text" id="m_param"
                           placeholder="M">
                </td>
            </tr>
            <tr>
                <td><label>Sigma </label></td>
                <td><input type="text" id="sigma_param"
                           placeholder="sigma">
                </td>
            </tr>
            <tr>
                <td><label>T: </label></td>
                <td><input type="text" id="t_param"
                           placeholder="T">
                </td>
            </tr>
            <tr>
                <td><label><spring:message code="parameters.pocket"/>: </label></td>
                <td><input type="text" id="w_param"
                           placeholder="W">
                </td>
            </tr>
            <tr>
                <td><label><spring:message code="parameters.number"/>: </label></td>
                <td><input type="text" id="paramNum"
                           placeholder="<spring:message code="parameters.number.placeholder"/>">
                </td>
            </tr>
        </table>

        <div>
            <table id="params" style="display: none">
                <thead>
                <tr>
                    <th>a</th>
                    <th>t</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>