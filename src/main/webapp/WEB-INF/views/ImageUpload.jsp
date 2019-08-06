<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload</title>
    <style>
        <%@include file="/resource/css/main.css"%>
    </style>
</head>
<body>
<h2>Upload Car Picture</h2>
<br>
<form method="post" action="${pageContext.servletContext.contextPath}/imageUpload" enctype="multipart/form-data">
    <input type="file" name="image"  accept="image/gif, image/jpeg, image/png"><br><br>
    <input type="submit" value="Upload">
</form>
<label>&nbsp;</label>
<form method='get' action='${pageContext.servletContext.contextPath}/'>
    <input type='submit' value='Return To Car List'>
</form>
</body>
</html>