<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        
        <!-- CONTROL PANEL CSS -->
        <link rel="stylesheet" href="css/control.css">
        <link rel="stylesheet" href="css/control_main.css">

        <!-- SIGN UP IN HOME CSS FOR MODAL STYLE -->
        <link rel="stylesheet" href="css/sign_up_in.css">
        
        <!-- FEEDBACKS CSS -->
        <link rel="stylesheet" href="css/feedbacks.css">
        
        <!-- FONTAWESOME CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.1/css/all.min.css">

    </head>

    <body>

        <input type="checkbox" id="toggle_menu">
        <jsp:include page="include/menu.jsp"/>

        <!-- CONTENT OF THE PAGE -->
        
        <main>

            <div>

                <div class="flex-container">

                    <div>

                        <!-- here there will be box with information about the current user score -->
                        <!-- everything is dynamically updated by js script -->

                        <span class="heading">User Rating</span>

                        <div class="rating">
                            <div class="rating-upper">
                                <span class="fa fa-star checked"></span>
                                <span class="fa fa-star checked"></span>
                                <span class="fa fa-star checked"></span>
                                <span class="fa fa-star checked"></span>
                                <span class="fa fa-star checked"></span>
                            </div>
                            <div class="rating-lower">
                                <span class="fa fa-star"></span>
                                <span class="fa fa-star"></span>
                                <span class="fa fa-star"></span>
                                <span class="fa fa-star"></span>
                                <span class="fa fa-star"></span>
                            </div>
                        </div>

                        <center>
                        <p id="avg">No reviews found</p>
                        </center>

                        <div class="row">
                            <div class="side">
                                <div>5 star</div>
                            </div>
                            <div class="middle">
                                <div class="bar-container">
                                    <div class="bar-5"></div>
                                </div>
                            </div>
                            <div class="side right">
                                <div>0</div>
                            </div>
                            <div class="side">
                                <div>4 star</div>
                            </div>
                            <div class="middle">
                                <div class="bar-container">
                                    <div class="bar-4"></div>
                                </div>
                            </div>
                            <div class="side right">
                                <div>0</div>
                            </div>
                            <div class="side">
                                <div>3 star</div>
                            </div>
                            <div class="middle">
                                <div class="bar-container">
                                    <div class="bar-3"></div>
                                </div>
                            </div>
                            <div class="side right">
                                <div>0</div>
                            </div>
                            <div class="side">
                                <div>2 star</div>
                            </div>
                            <div class="middle">
                                <div class="bar-container">
                                    <div class="bar-2"></div>
                                </div>
                            </div>
                            <div class="side right">
                                <div>0</div>
                            </div>
                            <div class="side">
                                <div>1 star</div>
                            </div>
                            <div class="middle">
                                <div class="bar-container">
                                    <div class="bar-1"></div>
                                </div>
                            </div>
                            <div class="side right">
                                <div>0</div>
                            </div>

                        </div>

                    </div>

                </div>

                <div class="flex-container" id="rev-container">

                    <!-- feedbacks will be placed here -->

                    <c:forEach var="t" items="${feedbacks}">
                    <div class="rev">
                        <img src="/imageset/profile/${t.studentID}.jpg" alt="/imageset/profile/profile.jpg">
                        <div>
                            ${t.name} ${t.surname}
                        </div>
                        <div>
                            Score: ${t.score}
                        </div>
                        <br>
                        <div>
                            ${t.description}
                        </div>
                    </div>
                    </c:forEach>

                </div>

                <div>

                    <div class="box hoverable_rows" id="give-feed">

                        <!-- here there will be a table that lists teachers to whom give feedbacks -->
                        <!-- and buttons to open a modal form and actually give the feedback -->

                        <c:choose>
                            <c:when test="${empty userfeedlist}">
                                <h2>Here you can give feedbacks, have a lecture with someone to start giving feedbacks!</h2>
                            </c:when>
                            <c:otherwise>
                                <h2>Evaluate your teachers!</h2>
                                <c:forEach var="t" items="${userfeedlist}">
                                    <c:forTokens items="${t}" delims="," var="item" varStatus ="loop">
                                        <c:choose>
                                            <c:when test="${loop.first}">
                                                <div>
                                                    <table>
                                                        <tr>
                                                            <td>
                                                                ${item}
                                                            </td>
                                                            <td></td>
                                            </c:when>
                                            <c:otherwise>
                                                            <td>
                                                                <a class= "feedreq" teacherid = ${item}>Give feedback</a>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forTokens>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>


                    </div>

                </div>

                </div>

            </main>

        <!-- this is the modal invoked by the table buttons, it will be dynamically updated by js script -->

        <div class="modal">

            <div class="sign_up_in" id="feedform">

                <form method="post" action="${pageContext.request.contextPath}/feedbacks">

                    <center>

                        <div></div>
                        <input type="hidden" name="teacher" value="">
                        <br>
                        <div id="scoretag">Score: 0</div>
                        <input type="hidden" name="score" value="0">

                        <div class="rating">
                            <div class="rating-upper">
                                <span class="fa fa-star checked"></span>
                                <span class="fa fa-star checked"></span>
                                <span class="fa fa-star checked"></span>
                                <span class="fa fa-star checked"></span>
                                <span class="fa fa-star checked"></span>
                            </div>
                            <div class="rating-lower">
                                <span class="fa fa-star"></span>
                                <span class="fa fa-star"></span>
                                <span class="fa fa-star"></span>
                                <span class="fa fa-star"></span>
                                <span class="fa fa-star"></span>
                            </div>
                        </div>

                        <br>
                        <label for="comment">Comment:</label>
                        <br>
                        <textarea id="comment" name="comment" rows="5" cols="33"></textarea>
                        <br>
                        <input class="feedreq" type="submit" value="Submit feedback">

                    </center>

                </form>

            </div>

            <div onclick="toggle_modalfeed(event)" class="close_modal">X</div>

        </div>

        <!-- here will be placed some variables needed by feedbacks js script-->
        <script>
            let counters = ${counters};
            let avg = ${avg};
        </script>

        <!-- FEEDBACKS JS -->
        <script src="js/feedbacks.js"></script>
        <!-- HOME JS FOR REUSING TOGGLE MODAL FUNCTION -->
        <script src="js/home.js"></script>

    </body>

</html>