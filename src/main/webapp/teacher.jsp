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
        <title>RemyTutor</title>


        <!-- MAIN CSS -->
        <link rel="stylesheet" href="css/style.css">

        <!-- HOMEPAGE CSS -->
        <link rel="stylesheet" href="css/home.css">

        <!-- SIGN-UP/SIGN-IN POP-UP CSS -->
        <link rel="stylesheet" href="css/sign_up_in.css">

        <!-- TEACHER CSS -->
        <link rel="stylesheet" href="css/teacher.css">

        <!-- FONTAWESOME CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.1/css/all.min.css">

        <!-- jQuery/jQuery UI -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.0/jquery.min.js"></script>
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
        
    </head>

    <body>
        
        <!-- WEBSITE HEADER -->

        <header>

            <div id="logo_wrap">
                <a href="/remytutor"><img src="images/logo.png"></a>
            </div>

            <div id="menu_wrap">
                <nav>
                    <a href="" class="current_page" style="visibility: hidden">&nbsp;</a>
                    <a href="/remytutor">Home</a>
                    <a href="about.jsp">About us</a>
                    <c:choose>
                      <c:when test="${sessionScope.userid != null}">
                        <a href="/remytutor/dashboard"><img src="/imageset/profile/${sessionScope.userid}.jpg">&nbsp;</a>
                      </c:when>
                      <c:otherwise>
                        <a href="" onclick="toggle_modal(event);">Sign in</a>
                      </c:otherwise>
                    </c:choose>
                </nav>
            </div>

        </header>

        <!-- TEACHER MAIN -->

        <main>

            <div class="flex_container">

                <!-- first child: header-->

                <div class="flex_item">

                    <!-- header's left box -->

                    <div class="header_left_box">
                        <div>
                            <img class="photo_container" src="/imageset/profile/${teacher_id}.jpg" alt="teacher_photo">
                        </div>

                        <div>
                            <div class="header_name_box">
                                ${teacher_name}
                            </div>
                            <br>
                            <div class="header_rating_box">
                                
                                <div class="score-wrap">                                  
                                    <div id="teacher_fullstar_style" style="width: ${teacher_avgscore * 20}%">                                 
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star checked"></span> 
                                    </div>
                                    
                                    <div id="teacher_emptystar_style">
                                        <span class="fa fa-star"></span>
                                        <span class="fa fa-star"></span>
                                        <span class="fa fa-star"></span>
                                        <span class="fa fa-star"></span>
                                        <span class="fa fa-star"></span>      
                                    </div>
                                </div>
                                
                                <span>${teacher_avgscore}</span>
                                
                                <br>
                                
                                <p>${teacher_numfeed} feedbacks</p>
                            </div>
                            <br>
                            <div class="header_city_box">
                                <strong>City:</strong> ${teacher_city}
                            </div>
                        </div>

                    </div>

                    <!-- header's right box-->
                    <div class="header_right_box">
                        <div class="header_bookLesson_box">
                            <button id="teacher_bookLesson_style" class="button">Book a lesson</button>               
                        </div>

                        <div class="header_price_box">
                            <p id="teacher_price_style"><strong>Price:</strong> ${teacher_price}&euro;/h</p>
                        </div>
                        
                    </div>

                </div>


                <!-- second child: flexbox break -->

                <div class="break_flexbox"></div>

                <!-- third child: subjects -->

                <div class="flex_item">

                    <div class="box">
                        <h3>
                            Offered Subject
                        </h3>
                        <p>
                            <span>${teacher_subject}</span>
                        </p> 
                    </div>

                    <!-- check if teacher has other subjects besides the one it is offering-->
                    <c:if test="${not empty teacher_other_subjects}">
                        <div class="box">
                            <h3>
                                Other Subjects Taught
                            </h3>
                            <p>
                                <c:forEach var="t" items="${teacher_other_subjects}">
                                    <span>${t}</span>                              
                                </c:forEach>
                            </p>
                        </div>
                    </c:if>

                    <div class="box">
                        
                        <h3>
                            Verification status
                        </h3>
                        
                        <table class="verification">
                            
                            <tr>
                                <td>
                                    Identity 
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${teacher_identity}">
                                            <i class="fas fa-check-square" style="color: #33cc33"></i>
                                        </c:when>    
                                        <c:otherwise>
                                            <i class="fas fa-minus-square" style="color: #FFF100"></i> 
                                        </c:otherwise>
                                     </c:choose>     
                                </td>
                            </tr>
                            
                            <tr>
                                
                                <td>
                                    Certification
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${teacher_certificate}">
                                            <i class="fas fa-check-square" style="color: #33cc33"></i>
                                        </c:when>    
                                        <c:otherwise>
                                            <i class="fas fa-minus-square" style="color: #FFF100"></i> 
                                        </c:otherwise>
                                    </c:choose>  
                                </td>
                                
                            </tr>
                            
                        </table>

                    </div>
                    
                </div>

                <!-- fourth child: tell about yourself -->

                <div class="flex_item">
                    
                    <h2> Profile </h2>
                    
                    <!-- Here goes the jquill description that the user writes in his profile -->
                    ${teacher_description}
                    
                </div>

                <!-- STUDENT FEEDBACKS -->
                <div class="flex_item">

                    <h2>
                        Feedbacks
                    </h2>

                    <div>
                        
                        <table class="fixed">
                            <c:forEach var="t" items="${student_feedbacks}">
                                <tr>
                                    <td class="td1_feedbacks">
                                        <div>
                                            <img class="profile_img" src="/imageset/profile/${t.studentID}.jpg" alt="student_photo">
                                        </div>
                                        <div>
                                            ${t.name}
                                        </div>
                                    </td>
                                    <td class="td2_feedbacks">
                                        ${t.description} 
                                    </td>
                                    
                                    <td class="td3_feedbacks">
                                        <div class="score-wrap">                                  
                                            <div class="student_fullstar_style" style="width: ${t.score * 20}%">                                 
                                                <span class="fa fa-star checked"></span>
                                                <span class="fa fa-star checked"></span>
                                                <span class="fa fa-star checked"></span>
                                                <span class="fa fa-star checked"></span>
                                                <span class="fa fa-star checked"></span> 
                                            </div>
                                    
                                            <div class="student_emptystar_style">
                                                <span class="fa fa-star"></span>
                                                <span class="fa fa-star"></span>
                                                <span class="fa fa-star"></span>
                                                <span class="fa fa-star"></span>
                                                <span class="fa fa-star"></span>  
                                            </div>
                                        </div>
                                    </td>
                                   
                                </tr>
                            </c:forEach>
                         </table>
                        
                    </div>
                    
                </div>
            </div>
        </main>
        
        <!-- WEBSITE FOOTER -->

        <footer>

            <p>
                <img src="images/logo_hatonly.png">&copy; RemyTutor: sharing knowledge and passions &hearts;

                <i class="fab fa-facebook-square fa-2x" style="color: #3b5998"></i>
                <i class="fab fa-twitter-square fa-2x" style="color: #1DA1F2"></i>
                <i class="fab fa-instagram-square fa-2x" style="color: #833AB4"></i>
            </p>

        </footer>

<!-- SIGN-UP/SIGN-IN POP-UP -->

        <div class="modal">

            <div class="sign_up_in">

                <div class="sign_up">

                    <div>

                        <p><img src="images/signup-image.png"><br><br><a href="" onclick="flip_registration(event)">I am already a member</a></p>


                    </div>

                    <div>

                        <h2>Sign up</h2>

                        <form action="indexpost" method="POST" autocomplete="off">
                            
                        <span><i class="fas fa-user"></i><input type="text" placeholder="First name" name="firstname" autocomplete="off" required></span>
                            
                        <span><i class="fas fa-user"></i><input type="text" placeholder="Last name" name="lastname" autocomplete="off" required></span>

                        <span><i class="fas fa-envelope"></i><input type="email" placeholder="E-mail address" name="email" autocomplete="off"></span>

                        <span><i class="fas fa-lock"></i><input type="password" placeholder="Password" name="passwd" autocomplete="off" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}" required  oninvalid="this.setCustomValidity('The password must contain both upper and lower case characters plus a number')"
                        oninput="this.setCustomValidity('')"></span>

                        <span><i class="fas fa-unlock"></i><input type="password" placeholder="Repeat password" name="passwd_confirm" autocomplete="off" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}" required></span>

                        <span><label class="custom_checkbox">
                            <input type="checkbox" required>
                            <span class="checkmark"></span>
                            </label> I agree to terms and conditions and the privacy policy</span>

                        <br><input type="submit" value="Register" class="button" name="register">

                            <span id="register_error"></span>
                        </form>
                    </div>

                </div>

                <div class="sign_in">

                    <div>

                        <p><img src="images/signin-image.jpg"><br><br><a href="" onclick="flip_registration(event)">Create an account</a></p>

                    </div>

                    <div>

                        <h2>Sign in</h2>

                        <span><i class="fas fa-envelope"></i><input type="email" placeholder="E-mail address"></span>

                        <span><i class="fas fa-unlock"></i><input type="password" placeholder="Password"></span>

                        <span><label class="custom_checkbox">
                            <input type="checkbox" checked>
                            <span class="checkmark"></span>
                            </label> Remember me</span>

                        <br><input type="submit" value="Log in" class="button">
                        
                        <span id="login_error"></span>

                    </div>

                </div>

            </div>

            <div onclick="toggle_modal(event)" class="close_modal">X</div>

        </div>
        
        <script>
            
	var topics = [];
    
        </script>
        
                
        <!-- BOOK A LESSON MODAL -->

        <div class="modal">

            <div class="sign_up_in" id="teacherform">

                    <!-- Section for sending a message to the teacher -->
                    <center>
                        <label for="chat" class="chat_label">Send a message to your teacher:</label>
                        
                        <textarea name="chat"></textarea>
                        
                        <button class="button" id="teacher_bookLesson_modal_style" onclick="call_teacherServlet(event, this)">Send the request!</button>
                    </center>            
            </div>
            
            <div onclick="toggle_modalteacher(event)" class="close_modal">X</div>
        </div>

        <script>            
            //I need it when doing the ajax call to send it (POST) to the teacherServlet
            let teacher_ID = ${teacher_id};
            
            // IS THE USER LOGGED IN?
            let logged_in = ${sessionScope.userid != null};
        </script>
        
        <!-- MAIN JS -->

        <script src="js/home.js"></script>
        <script src="js/teacher.js"></script>

    </body>
</html>
