<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="icon" href="images/logo.ico" type="image/x-icon" /> 
        <link rel="shortcut icon" href="images/logo.ico" type="image/x-icon" />
        <title>Chat - Remytutor</title> 


        <!------------------------ THE MAIN CSS --------->

        <link rel="stylesheet" href="css/style.css">

        <!-- CONTROL PANEL HOME CSS -->

        <link rel="stylesheet" href="css/control.css">

        <!-- THE CHAT CSS -->

        <link rel="stylesheet" href="css/chat.css" />

        <!-- FONTAWESOME CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.1/css/all.min.css">

        <!-- jQuery/jQuery UI -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.0/jquery.min.js"></script>
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

    </head>

    <body>

        <input type="checkbox" id="toggle_menu">
        <jsp:include page="include/menu.jsp"/>


        <div class="lesson_proposal">

            <div>
                <h1>Propose a lesson to MEMEN</h1>

                <input type="hidden" value="">
                
                <table>

                    <tr>

                        <td>
                            Date:
                        </td>

                        <td>
                            <input type="date">
                        </td>

                    </tr>

                    <tr>

                        <td>
                            Time: 
                        </td>

                        <td>
                            <input type="time">
                        </td>

                    </tr>

                    <tr>

                        <td>
                            Tariff:
                        </td>

                        <td>
                            <input type="number" min="1">€
                        </td>

                    </tr>

                    <tr>

                        <td>
                            Duration:
                        </td>

                        <td>
                            <input type='number' min='0' max='24' value="0">h&nbsp;&nbsp;
                            <input type='number' min='0' max='59' value="0" step="15">m
                        </td>

                    </tr>
                </table>

                <p style="text-align: center">
                    <button class="button" onclick="offer_lesson()">Send</button>
                </p>
            </div>

            <span onclick="toggle_modal(event)" class="close_modal">X</span>
        </div>
        <main>

            <div>


                <!-- LEFT BOX -->
                
                <div class="contacts_body">
                    
                    <!-- SEARCH BAR FOR CONTACTS -->
                    <form>
                        <input type="text" name="text" class="search"  placeholder="Search contact..." autocomplete="off" oninput="filter_chatlist(event, this);">
                    </form>

                    <!-- CONTACTS LIST CONTAINER -->
                    <div class="chatlogs2">

                        <ul class="tab">
                                                                                    
                            <c:forEach var="contact" items="${contactlist}" varStatus="loop">   
                                                                
                                
                                <li onclick="openChat(event, ${contact.userID}, ${contact.teacher})" class="tablinks ${contact.teacher ? 'teacher' : 'student'}" tabid="${loop.index}">
                                        <div>
                                            <div class="img_cont">
                                                <img src="/imageset/profile/${contact.userID}.jpg">
                                                <c:if test="${!contact.requestConfirmed}">
                                                    <div></div>
                                                </c:if>
                                            </div>
                                            
                                            <div class="user_info">
                                                <p><strong>${contact.name} ${contact.surname}</strong></p>
                                                <p>
                                                    <c:if test="${!fn:startsWith(contact.lastMessage, '<h2')}">
                                                        ${contact.lastMessage}
                                                    </c:if>
                                                    
                                                </p>
                                            </div>
                                        </div>
                                </li>
                                
                            </c:forEach>
                        
                        </ul>
                        
                        
                    </div>

                    <p><span style="background-color: #9ecae1">Student</span><span style="background-color: #deebf7">Teacher</span></p>
                </div> 

                <!-- RIGHT BOX -->
                
                <div class="chatbox">

                    <!-- FOR EACH CONTACT IN THE CONTACT LIST WE GET THE MESSAGES CONTAINER READY, IN CASE THE USER CLICKS IT -->
                    <c:forEach var="contact" items="${contactlist}" varStatus="loop">   
                                    
                        <div id="${loop.index}" class="tabcontent">

                            <!-- THIS WILL GET FILLED BY JS, ONLY UPON REQUEST/CLICK BY THE USER -->
                            <div class="chatlogs"></div>

                            <!-- WE GET THE TEXTAREA+BUTTON READY TO GO, SO WE ONLY NEED TO DOWNLOAD THE MESSAGES -->
                            <div class="chat-form">
                                
                                <!-- HERE WE CHECK IF THE TEACHER CONFIRMED THE STUDENT REQUEST -->
                                <c:choose>
                                    
                                    <c:when test="${contact.requestConfirmed}">
                                        <!-- IF HE DID, WE CAN JUST SHOW THE FORM FOR SENDING MESSAGES -->
                                        <textarea></textarea>
                                        <button class="button" onclick="sendMessage(event, ${contact.userID}, ${loop.index}, ${contact.teacher});">Send</button>
                                        <c:if test="${contact.teacher}"><button class="send_proposal" onclick="toggle_modal(event, ${contact.userID}, ${loop.index})"><i class="far fa-paper-plane"></i></button></c:if>
                                    </c:when>
                                    
                                    <c:otherwise>
                                        <!-- IF HE DID NOT -->
                                        <p class="wait_confirm">
                                            <c:choose>
                                                
                                                <c:when test="${contact.teacher}">
                                                    <!-- AND HE IS THE TEACHER, HE NEEDS TO SEE 2 BUTTONS TO EITHER ACCEPT OR REJECT THE REQUEST -->
                                                        You received a lesson request from ${contact.name}! What do you choose? &nbsp;
                                                        <button class="button" onclick="confirmRequest(true, ${contact.userID},${loop.index})">Accept</button>
                                                        &nbsp;&nbsp;
                                                        <button class="button" onclick="confirmRequest(false, ${contact.userID},${loop.index})">Reject</button>
                                                </c:when>
                                                
                                                <c:otherwise>
                                                    <!-- AND HE IS THE STUDENT, HE JUST NEEDS TO SEE A MESSAGE TELLING HIM TO WAIT FOR TEACHER'S CONFIRMATION -->
                                                        Your request has been sent to <strong>${contact.name}</strong> for confirmation. In case <strong>${contact.name}</strong> confirmes, you will be able to communicate with him/her and book lessons through this chat.
                                                </c:otherwise>
                                            
                                            </c:choose>
                                        </p>
                                    </c:otherwise>
                                    
                                </c:choose>
                            </div>

                        </div>

                    </c:forEach>

                </div>


            </div>

        </main>

        <!-- CHAT JS -->

        <script>
            var current_user = ${sessionScope.userid};
        </script>
        <script src="js/chat.js"></script>

    </body>


</html>
