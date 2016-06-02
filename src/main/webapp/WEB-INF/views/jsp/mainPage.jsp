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

    <%--<title><spring:message code="all.main.hello"/></title>--%>

</head>
<body>
<div id="parent">
    <div id="chart">

        <div id="chartContainer"></div>
        <div id="chartContainer_autocor"></div>
        <table>
            <tr id="xi_2" style="display: none;">
                <td><img src="../../../resources/core/image/xi_2.png"></td>
                <td><label> = </label><label id="xi_2_val"> </label></td>
            </tr>
            <tr id="lambda" style="display: none;">
                <td>"<spring:message code="results.lambda"/>"</td>
                <td><label> = </label><label id="lambda_val"> </label></td>
            </tr>
            <tr id="eps" style="display: none;">
                <td>"<spring:message code="results.eps"/>"</td>
                <td><label> = </label><label id="eps_val"> </label></td>
            </tr>
        </table>
    </div>
    <div id="model">
        <table>
            <tr>
                <td><label><spring:message code="all.label.model"/>:</label></td>
                <td><img src="../../../resources/core/image/model_exp.png"/></td>
            </tr>
        </table>
    </div>

    <div id="params_div">
        <div class="tabbable">
            <ul class="tabs">
                <li><a href="#generateTab">Generate</a></li>
                <li><a href="#analyzeTab">Analyze</a></li>
            </ul>
            <div class="tabcontent">
                <div id="generateTab" class="tab">
                    <table>
                        <tr>
                            <td><label><spring:message code="generate.label.parameters.mu"/>: </label></td>
                            <td><input type="text" id="m_param"
                                       placeholder="<spring:message code="generate.placeholder.parameters.mu"/>"
                                       class="param">
                            </td>
                        </tr>
                        <tr>
                            <td><label><spring:message code="generate.label.parameters.sigma"/>: </label></td>
                            <td><input type="text" id="sigma_param" placeholder="<spring:message code="generate.placeholder.parameters.sigma"/>" class="param">
                            </td>
                        </tr>
                        <tr>
                            <td><label>Number of pockets: </label></td>
                            <td><input type="text" id="N_param" placeholder="N" class="param">
                            </td>
                        </tr>
                        <tr>
                            <td><label><spring:message code="parameters.pocket"/>: </label></td>
                            <td><input type="text" id="w_param" placeholder="W" class="param">
                            </td>
                        </tr>
                        <tr>
                            <td><label>Normalization: </label></td>
                            <td><input type="checkbox" id="norm" class="param" checked="checked">
                            </td>
                        </tr>
                        <tr>
                            <td><label>Noise: </label></td>
                            <td><input type="checkbox" id="noise" class="param" checked="checked">
                            </td>
                        </tr>
                        <tr>
                            <td><label><spring:message code="parameters.number"/>: </label></td>
                            <td><input type="text" id="paramNum"
                                       placeholder="<spring:message code="placeholder.parameters.number"/>"
                                       class="param">
                            </td>
                            <td><input type="button" value="<spring:message code="buttons.showTable"/>" id="fillParams">
                            </td>
                        </tr>
                        <%--</table>--%>

                        <%--<div>--%>
                        <tr>
                            <td></td>
                            <td>
                                <div class="param_div">
                                    <table id="params_ex" style="display: none">
                                        <thead>
                                        <tr>
                                            <th></th>
                                            <th>p</th>
                                            <th></th>
                                            <th></th>
                                            <th><spring:message code="generate.columnName.tau"/></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </div>
                            </td>
                            <td valign="top">
                                <input type="button" value="<spring:message code="buttons.generate"/>" id="generate">
                                <label id="warn" style="display: none"><spring:message
                                        code="warn.input.all.values"/></label>
                            </td>
                        </tr>
                        <%--</div>--%>
                    </table>
                </div>
                <div id="analyzeTab" class="tab">
                    <table>
                        <tr>
                            <td><label><spring:message code="analyze.label.parameters.epsilon"/>: </label></td>
                            <td><input type="text" id="epsilon_param"
                                       placeholder="<spring:message code="analyze.placeholder.parameters.epsilon"/>"
                                       class="param">
                            </td>
                        </tr>
                        <tr>
                            <td><label><spring:message code="analyze.label.parameters.maxLambda"/>: </label></td>
                            <td><input type="text" id="maxIteration_param"
                                       placeholder="<spring:message code="analyze.placeholder.parameters.maxLambda"/>"
                                       class="param">
                            </td>
                        </tr>
                        <tr>
                            <td><label><spring:message code="parameters.number"/>: </label></td>
                            <td><input type="text" id="paramNumTh"
                                       placeholder="<spring:message code="placeholder.parameters.number"/>"
                                       class="param">
                            </td>
                            <td><input type="button" value="<spring:message code="buttons.showTable"/>"
                                       id="fillParamsTh">
                            </td>
                        </tr>

                        <tr>
                            <td></td>
                            <td></td>
                            <td><input type="button" value="<spring:message code="analyze.buttons.analyze"/>"
                                       id="analyze"
                                       disabled>
                                <label id="warnTh" style="display: none"><spring:message
                                        code="warn.input.all.values"/></label></td>

                        </tr>
                    </table>
                    <div>

                        <div class="param_div">
                                    <table id="params_th" style="display: none">
                                        <thead>
                                        <tr>
                                            <th></th>
                                            <th>p_min</th>
                                            <th></th>
                                            <th>p</th>
                                            <th></th>
                                            <th>p_max</th>
                                            <th></th>
                                            <th></th>
                                            <th><spring:message code="analyze.columnName.tau_min"/></th>
                                            <th></th>
                                            <th><spring:message code="analyze.columnName.tau"/></th>
                                            <th></th>
                                            <th><spring:message code="analyze.columnName.tau_max"/></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </div>
                            <%--</td>--%>
                            <%--<td valign="top">--%>

                            <%--</td>--%>
                        <%--</tr>--%>
                        </div>
                    <%--</table>--%>
                </div>
            </div>
        </div>

    </div>
</div>
</body>
</html>