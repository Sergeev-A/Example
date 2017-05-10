<!DOCTYPE html>
<html lang="es">
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<form method=get action="/">

    <c:out value=
                   "${requestScope.warpath}"/>
    <br>
    <c:out value=
                   "${requestScope.manifest}" escapeXml="false"/><br>

</body>
</html>
