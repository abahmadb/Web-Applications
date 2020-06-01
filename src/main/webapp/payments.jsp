<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="images/logo.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="images/logo.ico" type="image/x-icon" />
    <title>RemyTutor</title>

    <!-- MAIN CSS -->
    <link rel="stylesheet" href="css/style.css">

    <!-- CONTROL PANEL HOME CSS -->
    <link rel="stylesheet" href="css/control.css">

    <link rel="stylesheet" href="css/payments.css">
    
    <!-- FONTAWESOME CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.1/css/all.min.css">

</head>

<body>

<input type="checkbox" id="toggle_menu">
<jsp:include page="include/menu.jsp"/>


<!-- CONTENT OF THE PAGE -->
<main>

    <div>

        <div class="box">

                <c:choose>
                    <c:when test="${empty payments}">
                        <p>No payments to display</p>
                    </c:when>
                    <c:otherwise>
                        <p>Payments history<span><i class="fas fa-arrow-circle-up outgoing"></i> Outgoing &nbsp; <i class="fas fa-arrow-circle-down incoming"></i> Incoming</span></p>
                        <table>
                            <tr>
                                <th>Date</th>
                                <th>Description</th>
                                <th>Amount</th>
                            </tr>
                        <c:forEach var="item" items="${payments}">
                            <tr>
                            <c:forTokens var="token" items="${item}" delims="," varStatus="loop">
                                <td>
                                    ${token}
                                    <c:if test="${loop.last}">
                                        <c:choose>
                                            <c:when test="${fn:contains(token, '-')}">
                                                <i class="fas fa-arrow-circle-up outgoing"></i>
                                            </c:when>
                                            <c:otherwise>
                                                <i class="fas fa-arrow-circle-down incoming"></i>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </td>
                            </c:forTokens>
                            </tr>
                        </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>

        </div>

        <div class="box totals">

            <div>
                Total earned
                <br>
                <p>
                    ${earned} &euro;
                </p>
            </div>

            <div>
                Total spent
                <br>
                <p>
                    ${spent} &euro;
                </p>
            </div>

        </div>

    </div>

</main>


</body>

</html>