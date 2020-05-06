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


        <!-- SLIDESHOW CSS -->
        <link rel="stylesheet" href="css/slideshow.css">

        <!-- FONTAWESOME CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.1/css/all.min.css">

        <!-- jQuery/jQuery UI -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.0/jquery.min.js"></script>
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

        <style>

            #contact_us{
                background: #ec5b53;
                padding: 27px 44px;
                color: #fff;
                font-size: 18px;
                border-radius: 5px;
                -moz-user-select: none;
                font-weight: bold;
                letter-spacing: 1px;
                line-height: 0;
                margin: 10px;
                cursor: pointer;
                transition: color .4s linear;
                border: none;
                overflow: hidden;
                text-decoration: none;
            }

        </style>

    </head>

    <body>

        <!-- WEBSITE HEADER -->

        <header>

            <div id="logo_wrap">
                <img src="images/logo.png">
            </div>

            <div id="menu_wrap">
                <nav>
                    <a href="" class="current_page">Home</a>
                    <a href="about.jsp">About us</a>
                    <c:choose>
                      <c:when test="${sessionScope.userid != null}">
                        <a href="control.html"><img src="/imageset/profile/${sessionScope.userid}.jpg">&nbsp;</a>
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

            <!-- FIRST SECTION: WEBSITE WELCOME AND SEARCH BAR FOR PROFESSORS -->
            <section>

                <img src="images/main.jpg">

                <div>

                    <div>

                        <div>
                            <h1>What do you want to learn?</h1>
                        </div>

                        <div id="search_box">
                            <input type="text" placeholder="What would you like to learn?" size="30"><form action="search.jsp" method="get"><input type="hidden" id="topic_id" name="topic_id" value="">
                            <input type="submit" class="button" value="SEARCH"></form>
                        </div>

                    </div>
                </div>

            </section>

            <section>

                <h1>Learn everything you want!</h1>

                <!-- SLIDESHOW -->

                <div id="slideshow">

                    <div id="riquadro"><div>

                        <img src="images/slideshow/1.jpg">
                        <p>Explore the mysteries of mathematics</p>

                        </div><div>

                        <img src="images/slideshow/2.jpg">
                        <p>Learn how to play an instrument</p>

                        </div><div>

                        <img src="images/slideshow/3.jpg">
                        <p>Become a great chef!</p>

                        </div><div>

                        <img src="images/slideshow/4.jpg">
                        <p>Relax and meditate with Yoga!</p>

                        </div><div>

                        <img src="images/slideshow/5.jpg">
                        <p>Seek help for school or university</p>

                        </div><div>

                        <img src="images/slideshow/6.jpg">
                        <p>You are going to love RemyTutor!</p>

                        </div></div>

                    <span id="avanti" onClick="cambia_immagine(1)">&#10095;</span>
                    <span id="indietro" onClick="cambia_immagine(-1)">&#10094;</span>
                </div>

            </section>

            <section>

                <p>If you have any doubts feel free to</p>

                <a id="contact_us" href="about.html">Contact Us</a>

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
        
        <script>
            
	var topics = [    
        ${fn:replace(topics_list, "&#039;", "'")}
	];

	</script>

        <!-- MAIN JS -->

        <script src="js/home.js"></script>

        <!-- SHLIDESHOW JS -->

        <script src="js/slideshow.js"></script>
    </body>

</html>