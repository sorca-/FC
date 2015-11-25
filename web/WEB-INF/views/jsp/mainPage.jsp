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
  <link rel="stylesheet" href="../../../css/common.css" type="text/css"/>
  <script type="text/javascript" src="../../../script/common.js"></script>
  <script type="text/javascript" src="../../../script/jquery-1.11.3.js"></script>

  <title><spring:message code="main.hello"/></title>

</head>
<body>
<div class="tabs">
  <ul>
    <li>Первая</li>
    <li>Вторая</li>
    <li>Третья</li>
  </ul>
  <div>
    <div>
      <form:form method="post" action="test" modelAttribute="testForm">
        <%--<form:input id="email" path="email" type="text"/>--%>
        <input type="submit"/>
      </form:form>

      <%--${email}--%>
    </div>
    <div>Второе содержимое</div>
    <div>Третье содержимое</div>
  </div>
</div>
</body>
</html>