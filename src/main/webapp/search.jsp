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
        <title>Search - RemyTutor</title>


        <!-- MAIN CSS -->
        <link rel="stylesheet" href="css/style.css">

        <!-- HOMEPAGE CSS -->
        <link rel="stylesheet" href="css/search.css">

        <!-- SIGN-UP/SIGN-IN POP-UP CSS -->
        <link rel="stylesheet" href="css/sign_up_in.css">

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
                    <a href="/remytutor" class="current_page">Home</a>
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

        <!-- WEBSITE MAIN BODY -->

        <main>

            <section>

                <div id="search_box">
                    <input type="text" placeholder="What would you like to learn?" size="30" value="${cur_topic}">
                    <form action="search" method="get" style="white-space: nowrap">
                        <input type="hidden" name="topic_id" id="topic_id" value="${param.topic_id}">
                        <select class="select-css" style="width:auto; display: inline-block" name="sorting_by">
                            <option value="0"${param.sorting_by == '0' ? 'selected' : ''}>Sort by</option>
                            <option value="1"${param.sorting_by == '1' ? 'selected' : ''}>Highest feedbacks</option>
                            <option value="2"${param.sorting_by == '2' ? 'selected' : ''}>Ascending tariff</option>
                            <option value="3"${param.sorting_by == '3' ? 'selected' : ''}>Descending tariff</option>
                        </select>
                        <input type="submit" class="button" value="SEARCH">
                    </form>
                    
                </div>

            </section>


            <section>

                <c:forEach var="t" items="${search_items}">
                    
                    <div class="result">
                    
                        <a href="${pageContext.request.contextPath}/teacher?teacher_id=${t.userid}">
                            <div class="photobox" style="background-image: url('/imageset/profile/${t.userid}.jpg')">                        
                                <p>
                                    ${t.name}
                                </p>
                            </div>
                        </a>

                        <div class="cost_feedback">
                            <p>
                                ${t.tariff}&euro;/h
                            </p>
                            <p>
                                ${t.avgscore} <i class="fa fa-star"></i>${t.counter} feedbacks
                            </p>
                        </div>
                    </div>
                    
                </c:forEach>
                
            </section>


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

        <!-- MAIN JS -->
        <script>
            
	var topics = [    
        <c:forEach var="t" items="${topics_list}" varStatus="status">   
            {id: '${t.topicid}', value: '${t.label}'}${!status.last ? ',' : ''}
        </c:forEach>
	];

	</script>
        <script src="js/home.js"></script>
        
    </body>

</html>