<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="icon" href="images/logo.ico" type="image/x-icon" />
        <link rel="shortcut icon" href="images/logo.ico" type="image/x-icon" />
        <title>Control Panel - RemyTutor</title>

        <!-- MAIN CSS -->
        <link rel="stylesheet" href="css/style.css">

        <!-- CONTROL PANEL HOME CSS -->
        <link rel="stylesheet" href="css/control.css">
        <link rel="stylesheet" href="css/control_main.css">

        <!-- FONTAWESOME CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.1/css/all.min.css">

    </head>



    <body>
        
        <!-- SIDEBAR FOR THE DASHBOARD -->
        <input type="checkbox" id="toggle_menu">
        <jsp:include page="include/menu.jsp"/>

        <!-- CONTENT OF THE PAGE -->
        <main>
            <div>

                <!-- FIRST COL -->

                <div>

                    <div class="box">

                        <h2>Personal info</h2>

                        <p>
                            <img src="/imageset/profile/${sessionScope.userid}.jpg" class="profile_img">
                        </p>

                        <p>
                            ${user_fullname}
                        </p>

                        <table>

                            <tr>

                                <td rowspan="2">
                                    <strong>Contacts</strong>
                                </td>

                                <td>
                                    ${user_email}
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    ${user_phone}
                                </td>    
                            </tr>

                            <tr>
                                <td>
                                    <strong>Average feedback</strong>
                                </td>
                                <td>
                                    ${user_feedaverage}
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <strong>Feedbacks amount</strong>
                                </td>
                                <td>
                                    ${user_feedamount}
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <strong>Lessons time</strong>
                                </td>
                                <td>
                                    ${total_time}
                                </td>
                            </tr>

                        </table>

                        <p>
                            <a href="/remytutor/profile">Show profile page</a>
                        </p>

                    </div>



                </div>

                <!-- SECOND COL -->

                <div>

                    <div class="box">

                        <h2>Chats</h2>

                        <div>

                            <table>
                                
                                <c:choose>
                                
                                    <c:when test="${not empty recent_chats}">
                                    
                                        <c:forEach var="chat" items="${recent_chats}">   
                                    
                                            <tr>

                                                <c:forEach var="c" items="${chat}">   

                                                    <td>
                                                        
                                                        <c:choose>
                                                            
                                                            <c:when test="${fn:startsWith(c, '<h2')}">
                                                            
                                                                You have a new lesson proposal!
                                                            
                                                            </c:when>
                                                            
                                                            <c:otherwise>
                                                            
                                                                ${c}
                                                            
                                                            </c:otherwise>
                                                            
                                                        </c:choose>

                                                    </td>

                                                </c:forEach>

                                            </tr>
                                        
                                        </c:forEach>
                                    
                                    </c:when>
                                    
                                    <c:otherwise>
                                    
                                        <tr>
                                            <td style="text-align: center;white-space: normal">
                                                Here you will see the most recent messages either sent to or received from other users on RemyTutor. Start interacting now!
                                            </td>
                                        </tr>
                                    
                                    </c:otherwise>
                                
                                </c:choose>
                                
                                
 
                            </table>

                        </div>

                        <p>
                            <a href="/remytutor/chat">Show all chats</a>
                        </p>
                    </div>

                    <br>

                    <div class="box">

                        <h2>Feedbacks</h2>


                        <div>

                            <table>
                                
                                
                                <c:choose>
                                
                                    <c:when test="${not empty recent_feed}">
                                    
                                        <c:forEach var="feed" items="${recent_feed}">   
                                    
                                            <tr>

                                                <td>
                                                    ${feed.name}
                                                </td>

                                                <td>
                                                    ${feed.description}
                                                </td>

                                                <td>
                                                    <c:forEach var="i" begin="1" end="${feed.score}">
                                                        <span class="fa fa-star checked"></span>
                                                    </c:forEach>
                                                    <c:forEach var="i" begin="1" end="${5 - feed.score}">
                                                        <span class="fa fa-star"></span>
                                                    </c:forEach>
                                                </td>


                                            </tr>

                                        </c:forEach>
                                    
                                    
                                    </c:when>
                                    
                                    <c:otherwise>
                                    
                                        <tr>
                                            <td style="text-align: center;white-space: normal">
                                                If you decided to share the knowledge on RemyTutor, here you will see the most recent feedbacks your students left about you! 
                                            </td>
                                        </tr>
                                    
                                    </c:otherwise>
                                
                                
                                </c:choose>
                                
                                

                                
                            </table>

                        </div>

                        <p>
                            <a href="/remytutor/feedbacks">Show all feedbacks</a>
                        </p>

                    </div>


                </div>

                <!-- THIRD COL -->

                <div>

                    <!-- PAYMENTS BOX -->
                    <div class="box">

                        <h2>Payments</h2>

                        <div>
                            
                            <table>
                                
                                <c:choose>
                                
                                    <c:when test="${not empty recent_payment}">
                                    
                                        <c:forEach var="payment" items="${recent_payment}">   
                                    
                                            <tr>

                                            <c:forEach var="p" items="${payment}">   

                                                <td>
                                                    ${p}
                                                </td>

                                            </c:forEach>

                                            </tr>

                                        </c:forEach>
                                    
                                    </c:when>
                                    
                                    <c:otherwise>
                                    
                                        <tr>
                                            <td style="text-align: center;white-space: normal">
                                                Wanna check how much you've been earning or spending? Here you will see the most recent money transfers, both incoming and outgoing.
                                            </td>
                                        </tr>
                                    
                                    </c:otherwise>
                                
                                
                                </c:choose>
                                
                            </table>

                        </div>

                        <p>
                            <a href="/remytutor/payments">Show all payments</a>
                        </p>
                    </div>

                </div>

            </div>

        </main>


    </body>

</html>