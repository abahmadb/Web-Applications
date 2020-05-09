<%@ page contentType="text/html; charset=UTF-8" %>
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

        <!-- CALL THE SERVLET -->
        
        <!-- WEBSITE HEADER -->

        <header>

            <div id="logo_wrap">
                <a href="index.jsp"><img src="images/logo.png"></a>
            </div>

            <div id="menu_wrap">
                <nav>
                    <a href="" class="current_page" style="visibility: hidden">&nbsp;</a>
                    <a href="index.jsp">Home</a>
                    <a href="about.jsp">About us</a>
                    <a href="" onclick="toggle_modal(event);">Sign in</a>
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
                            <img class="photo_container" src="images/photo-member.jpg" alt="marco_dell'anna_photo">
                        </div>

                        <div>
                            <div class="header_name_box">
                                ${teacher_name}
                            </div>
                            <br>
                            <div class="header_rating_box">
                                <span id="teacher_rating_style">Rating: </span>
                                
                                <div class="score-wrap">                                  
                                    <div id="teacher_fullstar_style">                                 
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
                                
                            </div>
                            <br>
                            <div class="header_city_box">
                                City: Montebelluna
                            </div>
                        </div>

                    </div>

                    <!-- header's right box-->
                    <div class="header_right_box">
                        <div class="header_bookLesson_box">
                            <button id="teacher_bookLesson_style" class="button" href="chat.html">Book a lesson</button>               
                        </div>

                        <div class="header_price_box">
                            <p id="teacher_price_style">Price: 20€/h</p>
                        </div>

                        <div class="header_responseTime_box">
                            <p id="teacher_responseTime_style">Average response Time: 1 day</p>
                        </div>                    
                    </div>

                </div>


                <!-- second child: flexbox break -->

                <div class="break_flexbox"></div>

                <!-- third child: subjects -->

                <div class="flex_item">

                    <div class="box">
                        <h3>
                            Subjects offered
                        </h3>
                        <p>
                            <span>javascript</span>
                            <span>physic</span>
                            <span>java</span>
                        </p> 
                    </div>


                    <div class="box">
                        <h3>
                            Preferable tools
                        </h3>
                        <p>
                            <span>zoom</span>
                            <span>messenger</span>
                            <span>hangouts</span>
                            <span>skype</span>
                        </p> 
                    </div>

                    <div class="box">
                        <h3>
                            Level
                        </h3>
                        <p>
                            <span>university</span>
                            <span>high school</span>
                            <span>beginner</span>
                        </p> 
                    </div>
                </div>

                <!-- fourth child: tell about yourself -->

                <div class="flex_item">
                    <h2>
                        Profile
                    </h2>

                    <h3>
                        Career                
                    </h3>
                    <p>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sit amet pretium urna. Vivamus venenatis velit nec neque ultricies, eget elementum magna tristirci in velit. Praesent scelerisque tortor sed accumsan convallis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sit amet pretium urna. Vivamus venenatis velit nec neque ultricies, eget elementum magna tristique. Quisque vehicula, risus eget aliquam placerat, purus leo tincidunt eros, eget luctu
                    </p>  
                    <p>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sit amet pretium urna. Vivamus venenatis velit nec neque ultricies, eget elementum magna tristirci in velit. Praesent scelerisque tortor sed accumsan convallis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sit amet pretium urna. 
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sit amet pretium urna. Vivamus venenatis velit nec neque ultricies, eget elementum magna tristirci in velit. Praesent scelerisque tortor sed accumsan convallis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sit amet pretium urna. Vivamus venenatis velit nec neque ultricies, eget elementum magna tristique. Quisque vehicula, risus eget aliquam placerat, purus leo tincidunt eros, eget luctu
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sit amet pretium urna. Vivamus venenatis velit nec neque ultricies, eget elementum magna tristirci in velit. Praesent scelerisque tortor sed accumsan convallis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sit amet pretium urna. Vivamus venenatis velit nec neque ultricies, eget elementum magna tristique. Quisque vehicula, risus eget aliquam placerat, purus leo tincidunt eros, eget luctu
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sit amet pretium urna. Vivamus venenatis velit nec neque ultricies, eget elementum magna tristirci in velit. Praesent scelerisque tortor sed accumsan convallis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sit amet pretium urna. Vivamus venenatis velit nec neque ultricies, eget elementum magna tristique. Quisque vehicula, risus eget aliquam placerat, purus leo tincidunt eros, eget luctu
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sit amet pretium urna. Vivamus venenatis velit nec neque ultricies, eget elementum magna tristirci in velit. Praesent scelerisque tortor sed accumsan convallis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sit amet pretium urna. Vivamus venenatis velit nec neque ultricies, eget elementum magna tristique. Quisque vehicula, risus eget aliquam placerat, purus leo tincidunt eros, eget luctu
                    </p>
                    <h3>
                        Curriculum vitae                
                    </h3>
                    <p>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sit amet pretium urna. Vivamus venenatis velit nec neque ultricies, eget elementum magna tristirci in velit. Praesent scelerisque tortor sed accumsan convallis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sit amet pretium urna. Vivamus venenatis velit nec neque ultricies, eget elementum magna tristique. Quisque vehicula, risus eget aliquam placerat, purus leo tincidunt eros, eget luctu
                    </p> 
                </div>

                <!-- fifth child: comment -->

                <div class="flex_item">

                    <h2>
                        Feedbacks
                    </h2>

                    <div>

                        <table>
                            <tr>
                                <td>
                                    <p>
                                        <img class="profile_img" src="images/photo-member.jpg" alt="marco_dell'anna_photo">
                                        Matteo
                                    </p>
                                </td>
                                <td>
                                    aliquet sit amet mauris quis, vestibulum ullamcorper mi. Vestibulum in nunc quis neque mollis pellentesque ac id orci. Maecenas bibendum rhoncus mauris,
                                    aliquet sit amet mauris quis, vestibulum ullamcorper mi. Vestibulum in nunc quis neque mollis pellentesque ac id orci. Maecenas bibendum rhoncus mauris,
                                    aliquet sit amet mauris quis, vestibulum ullamcorper mi. Vestibulum in nunc quis neque mollis pellentesque ac id orci. Maecenas bibendum rhoncus mauris,
                                    aliquet sit amet mauris quis, vestibulum ullamcorper mi. Vestibulum in nunc quis neque mollis pellentesque ac id orci. Maecenas bibendum rhoncus mauris,
                                </td>
                                <td>
                                    <span class="fa fa-star checked"></span>
                                    <span class="fa fa-star checked"></span>
                                    <span class="fa fa-star checked"></span>
                                    <span class="fa fa-star checked"></span>
                                    <span class="fa fa-star"></span>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <p>
                                        <img class="profile_img" src="images/photo-member.jpg" alt="marco_dell'anna_photo"><br/>
                                        Francesco
                                    </p>
                                </td>
                                <td>
                                    non viverra est egestas id. Ut imperdiet rutrum urna, ac accumsan nibh cursus non. Ut elementum magna nec leo volutpat dapibus. Fusce sagittis dui sit amet lacus accumsan consectetur. Maecenas laoreet vestibulum lacinia. Cras ultricies turpis velit, eu imperdiet nibh bibendum vitae.
                                </td>
                                <td>
                                    <span class="fa fa-star checked"></span>
                                    <span class="fa fa-star checked"></span>
                                    <span class="fa fa-star checked"></span>
                                    <span class="fa fa-star"></span>
                                    <span class="fa fa-star"></span>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <p>
                                        <img class="profile_img" src="images/photo-member.jpg" alt="marco_dell'anna_photo"><br/>
                                        Lucia
                                    </p>
                                </td>
                                <td>
                                    Nulla nec massa diam. Aenean tempor metus in enim ultrices tempor. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                                </td>
                                <td>
                                    <span class="fa fa-star checked"></span>
                                    <span class="fa fa-star checked"></span>
                                    <span class="fa fa-star checked"></span>
                                    <span class="fa fa-star checked"></span>
                                    <span class="fa fa-star checked"></span>
                                </td>
                            </tr>
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

        <script>
            let avg = ${teacher_avgscore};
        </script>>
        
        <!-- MAIN JS -->

        <script src="js/home.js"></script>
        <script src="js/teacher.js"></script>

    </body>
</html>
