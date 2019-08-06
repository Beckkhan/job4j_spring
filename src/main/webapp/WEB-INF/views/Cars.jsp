<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Car List</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        <%@include file="/resource/css/main.css"%>
    </style>
    <script>
        $(document).on('click', 'input[name=checkSold]', function () {
            var carId = $(this).attr('id');
            $.ajax({
                url: 'changeStatus',
                type: 'get',
                contentType: 'text/html',
                data: ({carId: carId, sold: $(this).is(':checked')})
            });
        });
    </script>
</head>
<body>
<h2>ALL CARS</h2>
<table id="carTable">
    <tr>
        <th>Car Details</th>
        <th>Pictures</th>
    </tr>
    <c:forEach var="car" items="${cars}">
        <tr>
            <td>
                <ul>
                    <li>Creation Date: ${car.created}</li>
                    <li>Car Id: ${car.id}</li>
                    <li>Car name: ${car.brand}</li>
                    <li>Body: ${car.bodytype.name}</li>
                    <li>Engine: ${car.engine.name}</li>
                    <li>Transmission: ${car.transmission.name}</li>
                    <li>Price: ${car.price}</li>
                    <li>Location: ${car.location}</li>
                    <li>Owner: ${car.user.name}</li>
                    <li>Sold Status:
                        <c:choose>
                            <c:when test="${car.sold == true && sessionScope.login == car.user.login}">
                                <input type="checkbox" name="checkSold" id="${car.id}" checked >
                            </c:when>
                            <c:when test="${car.sold == true && sessionScope.login != car.user.login}">
                                <input type="checkbox" name="checkSold" id="${car.id}" checked disabled>
                            </c:when>
                            <c:when test="${car.sold == false && sessionScope.login == car.user.login}">
                                <input type="checkbox" name="checkSold" id="${car.id}" >
                            </c:when>
                            <c:when test="${car.sold == false && sessionScope.login != car.user.login}">
                                <input type="checkbox" name="checkSold" id="${car.id}" disabled>
                            </c:when>
                        </c:choose>
                    </li>
                    <li>
                        <form method="get" action="${pageContext.servletContext.contextPath}/imageUpload">
                            <input type="hidden" name="carIdForImage"  value="${car.id}">
                            <input type="submit" value="Upload Images">
                        </form>
                    </li>
                    <li>
                        <form method="post" action="${pageContext.servletContext.contextPath}/updateDelete">
                            <input type="hidden" name="carId"  value="${car.id}">
                            <input type="submit" onclick="if(!(confirm('Are you sure you want to delete this customer?'))) return false" value="Delete Car">
                        </form>
                        <c:if test="${not empty error}">
                            <c:out value='${error}'></c:out>
                        </c:if>
                    </li>
                </ul>
            </td>
            <td>
                <c:forEach var="picture" items="${car.pictures}">
                    <img src="data:image; base64,${picture.base64picture}" height="300"/>
                </c:forEach>
            </td>
        </tr>
    </c:forEach>
</table>
<form method="get" action="${pageContext.servletContext.contextPath}/filter">
    <br>
    <label>Show for the last day:</label>
    <input type="checkbox" name="filterDate" value="date">
    <br>
    <label>With Images:</label>
    <input type="checkbox" name="filterImage" value="image">
    <br>
    <label>Only cars fo sale:</label>
    <input type="checkbox" name="filterSold" value="sold">
    <br>
    <label>With Car Name:</label>
    <input type="text" name="filterName">
    <br>
    <input type="submit" value="Filter">
</form>
<br>
<form method="get" action="${pageContext.servletContext.contextPath}/addCarForm">
    <input type='submit' value='Add New Car'>
</form>
<br>
</body>
</html>