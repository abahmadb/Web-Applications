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
        <link rel="stylesheet" href="css/search.css">

        <!-- SIGN-UP/SIGN-IN POP-UP CSS -->
        <link rel="stylesheet" href="css/sign_up_in.css">

        <!-- FONTAWESOME CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.1/css/all.min.css">


        <!-- jQuery/jQuery UI -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.0/jquery.min.js"></script>
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

        <script>
            $( function() {
                var topics = [
                    "Math",
                    "Calculus",
                    "C++",
                    "Java",
                    "JavaScript",
                    "HTML",
                    "CSS",
                    "Yoga",
                    "Guitar",
                    "English",
                    "Photoshop",
                    "Dancing",
                    "Singing",
                    "French",
                    "Spanish"
                ];

                $("#search_box input").autocomplete({
                    source: topics
                });
            });
        </script>

    </head>

    <body>

        <!-- WEBSITE HEADER -->

        <header>

            <div id="logo_wrap">
                <a href="index.html"><img src="images/logo.png"></a>
            </div>

            <div id="menu_wrap">
                <nav>
                    <a href="" class="current_page" style="visibility: hidden">&nbsp;</a>
                    <a href="index.html">Home</a>
                    <a href="about.html">About us</a>
                    <a href="" onclick="toggle_modal(event);">Sign in</a>
                </nav>
            </div>

        </header>

        <!-- WEBSITE MAIN BODY -->

        <main>

            <section>

                <div id="search_box">
                    <input type="text" placeholder="What would you like to learn?" size="30">
                    <select class="select-css" style="width:auto">
                        <option>Sort by</option>
                        <option>Highest feedbacks</option>
                        <option>Ascending tariff</option>
                        <option>Descending tariff</option>
                    </select>
                    <button class="button">SEARCH</button>
                </div>

            </section>


            <section>

                <div class="result">
                    
                    <a href="teacher.html">
                        <div class="photobox" style="background-image: url('images/users_profile/1.jpg')">                        
                            <p>
                                Katie
                            </p>
                        </div>
                    </a>
                    
                    <div class="cost_feedback">
                        <p>
                            15&euro;/h
                        </p>
                        <p>
                            4.1 <i class="fa fa-star"></i>11 feedbacks
                        </p>
                    </div>
                </div>

                <div class="result">

                    <a href="teacher.html">
                        <div class="photobox" style="background-image: url('images/users_profile/2.jpg');">
                            <p>
                                Jack
                            </p>
                        </div>
                    </a>

                    <div class="cost_feedback">
                        <p>
                            14&euro;/h
                        </p>
                        <p>
                            4.2 <i class="fa fa-star"></i>17 feedbacks
                        </p>
                    </div>
                </div>

                <div class="result">

                    <a href="teacher.html">
                        <div class="photobox" style="background-image: url('images/users_profile/3.jpg');">

                            <p>
                                George
                            </p>
                        </div>
                    </a>

                    <div class="cost_feedback">
                        <p>
                            16&euro;/h
                        </p>
                        <p>
                            4.3 <i class="fa fa-star"></i>12 feedbacks
                        </p>
                    </div>
                </div>

                <div class="result">

                    <a href="teacher.html">
                        <div class="photobox" style="background-image: url('images/users_profile/4.jpg');">

                            <p>
                                Sanders
                            </p>
                        </div>
                    </a>
                    
                    <div class="cost_feedback">
                        <p>
                            13&euro;/h
                        </p>
                        <p>
                            4.4 <i class="fa fa-star"></i>4 feedbacks
                        </p>
                    </div>
                </div>

                <div class="result">

                    <a href="teacher.html">
                        <div class="photobox" style="background-image: url('images/users_profile/5.jpg')">                        
                            <p>
                                Roy
                            </p>
                        </div>
                    </a>

                    <div class="cost_feedback">
                        <p>
                            18&euro;/h
                        </p>
                        <p>
                            4.1 <i class="fa fa-star"></i>20 feedbacks
                        </p>
                    </div>
                </div>

                <div class="result">

                    <a href="teacher.html">
                        <div class="photobox" style="background-image: url('images/users_profile/6.jpg');">
                            <p>
                                Steve
                            </p>
                        </div>
                    </a>

                    <div class="cost_feedback">
                        <p>
                            17&euro;/h
                        </p>
                        <p>
                            4.2 <i class="fa fa-star"></i>3 feedbacks
                        </p>
                    </div>
                </div>

                <div class="result">

                    <a href="teacher.html">
                        <div class="photobox" style="background-image: url('images/users_profile/7.jpg');">

                            <p>
                                Teresa
                            </p>
                        </div>
                    </a>

                    <div class="cost_feedback">
                        <p>
                            14&euro;/h
                        </p>
                        <p>
                            4.3 <i class="fa fa-star"></i>9 feedbacks
                        </p>
                    </div>
                </div>

                <div class="result">

                    <a href="teacher.html">
                        <div class="photobox" style="background-image: url('images/users_profile/8.jpg');">

                            <p>
                                Logan
                            </p>
                        </div>
                    </a>

                    <div class="cost_feedback">
                        <p>
                            15&euro;/h
                        </p>
                        <p>
                            4.4 <i class="fa fa-star"></i>5 feedbacks
                        </p>
                    </div>
                </div>

                <div class="result">

                    <a href="teacher.html">
                        <div class="photobox" style="background-image: url('images/users_profile/9.jpg')">                        
                            <p>
                                Lawrence
                            </p>
                        </div>
                    </a>

                    <div class="cost_feedback">
                        <p>
                            15&euro;/h
                        </p>
                        <p>
                            4.1 <i class="fa fa-star"></i>17 feedbacks
                        </p>
                    </div>
                </div>

                <div class="result">

                    <a href="teacher.html">
                        <div class="photobox" style="background-image: url('images/users_profile/10.jpg');">
                            <p>
                                Beatrice
                            </p>
                        </div>
                    </a>

                    <div class="cost_feedback">
                        <p>
                            16&euro;/h
                        </p>
                        <p>
                            4.2 <i class="fa fa-star"></i>15 feedbacks
                        </p>
                    </div>
                </div>

                <div class="result">

                    <a href="teacher.html">
                        <div class="photobox" style="background-image: url('images/users_profile/11.jpg');">

                            <p>
                                Robbie
                            </p>
                        </div>
                    </a>

                    <div class="cost_feedback">
                        <p>
                            13&euro;/h
                        </p>
                        <p>
                            4.3 <i class="fa fa-star"></i>2 feedbacks
                        </p>
                    </div>
                </div>

                <div class="result">

                    <a href="teacher.html">
                        <div class="photobox" style="background-image: url('images/users_profile/12.jpg');">

                            <p>
                                Maggie
                            </p>
                        </div>
                    </a>

                    <div class="cost_feedback">
                        <p>
                            12&euro;/h
                        </p>
                        <p>
                            4.4 <i class="fa fa-star"></i>1 feedbacks
                        </p>
                    </div>
                </div>

                <div class="result">

                    <a href="teacher.html">
                        <div class="photobox" style="background-image: url('images/users_profile/13.jpg')">                        
                            <p>
                                Ruby
                            </p>
                        </div>
                    </a>

                    <div class="cost_feedback">
                        <p>
                            15&euro;/h
                        </p>
                        <p>
                            4.1 <i class="fa fa-star"></i>10 feedbacks
                        </p>
                    </div>
                </div>

                <div class="result">

                    <a href="teacher.html">
                        <div class="photobox" style="background-image: url('images/users_profile/14.jpg');">
                            <p>
                                Nina
                            </p>
                        </div>
                    </a>

                    <div class="cost_feedback">
                        <p>
                            19&euro;/h
                        </p>
                        <p>
                            4.2 <i class="fa fa-star"></i>20 feedbacks
                        </p>
                    </div>
                </div>

                <div class="result">

                    <a href="teacher.html">
                        <div class="photobox" style="background-image: url('images/users_profile/15.jpg');">

                            <p>
                                Sabrina
                            </p>
                        </div>
                    </a>

                    <div class="cost_feedback">
                        <p>
                            15&euro;/h
                        </p>
                        <p>
                            4.3 <i class="fa fa-star"></i>6 feedbacks
                        </p>
                    </div>
                </div>

                <div class="result">

                    <a href="teacher.html">
                        <div class="photobox" style="background-image: url('images/users_profile/16.jpg');">

                            <p>
                                Sarah
                            </p>
                        </div>
                    </a>

                    <div class="cost_feedback">
                        <p>
                            14&euro;/h
                        </p>
                        <p>
                            4.4 <i class="fa fa-star"></i>4 feedbacks
                        </p>
                    </div>
                </div>

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

                        <span><img src="images/account.png"><input type="text" placeholder="Full name"></span>

                        <span><img src="images/email.png"><input type="text" placeholder="E-mail address"></span>

                        <span><img src="images/password.png"><input type="password" placeholder="Password"></span>

                        <span><img src="images/password.png"><input type="password" placeholder="Repeat password"></span>

                        <span><label class="custom_checkbox">
                            <input type="checkbox">
                            <span class="checkmark"></span>
                            </label> I agree to terms and conditions and the privacy policy</span>

                        <br><input type="submit" value="Register" class="button">

                    </div>

                </div>

                <div class="sign_in">

                    <div>

                        <p><img src="images/signin-image.jpg"><br><br><a href="" onclick="flip_registration(event)">Create an account</a></p>

                    </div>

                    <div>

                        <h2>Sign in</h2>

                        <span><img src="images/account.png"><input type="email" placeholder="E-mail address"></span>

                        <span><img src="images/password.png"><input type="password" placeholder="Password"></span>

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

        <script src="js/home.js"></script>
        
        <script>

            document.querySelector("#search_box").firstElementChild.value = new URLSearchParams(window.location.search).get("topic");

        </script>
    </body>

</html>