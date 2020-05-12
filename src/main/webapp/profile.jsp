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
        <title>Personal Profile - RemyTutor</title>

        <!-- MAIN CSS -->
        <link rel="stylesheet" href="css/style.css">

        <!-- CONTROL PANEL HOME CSS -->
        <link rel="stylesheet" href="css/control.css">

        <!-- CONTROL PANEL PROFILE CSS -->
        <link rel="stylesheet" href="css/profile.css">

        <!-- FONTAWESOME CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.1/css/all.min.css">

        <!-- Quill.js script for creating the personal presentation -->
        <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
        <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
        
    </head>



    <body>

        <input type="checkbox" id="toggle_menu">
        <%@ include file="include/menu.jsp" %>
        <!-- SIDEBAR FOR THE DASHBOARD
        <aside>
            <center>
                <a href="index.html">
                    <img src="images/logo_transparent.png" alt="">
                </a>
            </center>

            <p><label for="toggle_menu"><i class="fas fa-bars"></i></label></p>
            <a href="control.jsp"><i class="fas fa-desktop"></i><span>Control Panel</span></a>
            <a href="chat.jsp"><i class="fas fa-comment-dots"></i><span>Chat</span></a>
            <a href="feedbacks.jsp"><i class="far fa-thumbs-up"></i><span>Feedbacks</span></a>
            <a href="payments.jsp"><i class="fas fa-money-check-alt"></i><span>Payments</span></a>
            <a href="profile.jsp"  class="current_section"><i class="fas fa-user-alt"></i><span>Profile</span></a>
            <a href="index.jsp"><i class="fas fa-info-circle"></i><span>Sign out</span></a>

        </aside -->


        <!-- CONTENT OF THE PAGE -->
        <main>
            <div>


                <!-- EACH OF THE FOLLOWING DIVS REPRESENT A COLUMN IN THE LAYOUT -->

                <!-- COLUMN ONE -->
                <div>

                    <!-- PERSONAL INFORMATION -->
                    <div class="box">

                        <h4>
                            General information 
                        </h4>
                        <br>
                        <form action="profileServlet" method="POST">
                            <label for="fname">First Name</label><br>
                            <input type="text" id="fname" name="firstname" placeholder="Name.." required autocomplete="off" value="${person.name}"><br>

                            <label for="lname">Last Name</label><br>
                            <input type="text" id="lname" name="lastname" placeholder="Surname.." required autocomplete="off" value="${person.surname}"><br>

                            <label for="gender">Gender</label><br>
                            <select class="select-css" name="gender">
                                <option selected hidden>
                                    <c:choose>
                                        <c:when test="${person.gender == null || person.gender.equals(\"\")}">
                                            Choose Gender
                                        </c:when>
                                        <c:otherwise>
                                            ${person.gender}
                                        </c:otherwise>
                                    </c:choose>
                                </option>
                                <option value="M">M</option>
                                <option value="F">F</option>
                            </select>

                            <label for="birthday">Birthday</label><br>
                            <input type="date" id="birth" name="birth" placeholder="yyyy-mm-dd" value="${person.dob}"><br>

                            <label for="email">E-mail</label><br>
                            <input type="email" id="email" name="email" placeholder="E-mail.." required autocomplete="off" value="${person.email}"><br>

                            <label for="phone_nr">Phone number</label><br>
                            <input type="tel" id="phone_nr" name="phone_nr" placeholder="Phone number.." pattern="[0-9]{3}[ ]*[0-9]{3}[ ]*[0-9]{4}"
                                   title="the phone number should have 10 numbers" autocomplete="off" value="${person.phone}"><br>
                            
                            <label for="city">City</label><br>
                            <input type="text" id="city" name="city" placeholder="City.."
                                    autocomplete="off" value="${person.city}"><br>
                            
                            <p>
                                <input type="submit" value="Update" name="personForm">
                            </p>
                        </form>

                    </div>
                </div>


                <!-- COLUMN TWO -->
                <div>

                    <!-- PROFILE PICTURE -->
                    <div class="box">

                        <h4>
                            Profile photo 
                        </h4>

                         <form action="uploadServlet" method="post" enctype="multipart/form-data">
                            <p>
                                <label for="photo"> 
                                    <img src="/imageset/profile/${sessionScope.userid}.jpg" class="profile_img" alt="profile image" id="profile_img">
                                </label>
                            </p>
                            <input type="file" name="photo" id="photo" accept=".jpg, .png" onchange="readFile(this);">
                            <p>
                                <input type="submit" value="Upload photo">
                            </p>
                        </form>
                        
                    </div>

                    <br>

                    <!-- CHANGE PASSWORD -->
                    <div class="box">

                        <h4>
                            Change password
                        </h4>
                        <br>
                        <form action="profileServlet" method="post" onsubmit="return validatePassword()">

                            <input type="password" id="old_pw" name="old_pw" placeholder="Old password.." required><br>
                            <input type="password" id="new_pw" name="new_pw" placeholder="New password.." required
                                   title="Password must contain at least 6 characters, including UPPER/lowercase and numbers."
                                   pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}" onkeyup='validatePassword();'
                                   autocomplete="off"><br>
                            <input type="password" required id="confirm_pw" name="confirm_pw" placeholder="Confirm password.."
                                   onkeyup='validatePassword();' autocomplete="off"><br>
                            
                            <p>
                                <input type="submit" value="Change password" name="passForm">
                            </p>
                            <p id="message">
                                <br>
                                ${passMessage}
                            </p>
                        </form>
                    </div>

                </div>


                <!-- COLUMN THREE -->
                <div>

                    <!-- IDENTITY -->
                    <div class="box">
                        
                        <h4>
                            Identity card 
                        </h4>
             
                        <form action="uploadServlet" method="post" enctype="multipart/form-data">
                            <p>
                                <label for="document_card"> 
                                    <img src="/imageset/identity/${sessionScope.userid}.jpg" class="id_img" alt="id image" id="id_img">
                                </label>
                            </p>
                            <input type="file" name="document_card" id="document_card" accept=".jpg, .png" onchange="readFile2(this);">
                            <p>
                                <input type="submit" value="Upload ID">
                            </p>
                        </form>
                        
                    </div>
                    <br>

                    <!-- QUALIFICATION -->
                    <div class="box">
                        
                        <h4>
                            Qualification
                        </h4>
            
                        <form action="uploadServlet" method="post" enctype="multipart/form-data">
                            <p>
                                <label for="qualification"> 
                                    <img src="/imageset/certificate/${sessionScope.userid}.jpg" class="qualification_img" id="qualification_img" alt="qualification image">
                                </label>
                            </p>
                            <input type="file" name="qualification" id="qualification" accept=".jpg, .png" onchange="readFile3(this);">
                            <p>
                                <input type="submit" value="Upload Qualification">
                            </p>
                        </form>

                    </div>

                </div>

                <!-- COLUMN FOUR -->
                <div>
                    <!-- TOPIC OFFER -->
                    <div class="box">

                        <h4>
                            Topic offer  
                        </h4>
                        <br>
                        <form action="profileServlet" method="POST">
                            <label for="topic">Topic</label><br>
                            <input type="text" id="topic" name="topic" placeholder="Subject.." required autocomplete="off"><br>

                            <label for="tariff">Tariff &euro;/h</label><br>
                            <input type="text" id="tariff" name="tariff" placeholder="Tariff.." required autocomplete="off"><br>

                            <p>
                                <input type="submit" value="Submit" name="topicForm">
                            </p>
                        </form>
                    </div>
                </div>
                
                
                <!-- COLUMN FIVE -->
                <div>
                    <!-- TELL ABOUT YOURSELF -->
                    <div class="box" style="width: auto">

                        <h4>
                            Tell about yourself 
                        </h4>
                        <br>
                        <form action="profileServlet" method="post">

                            <label for="text-area"></label>

                            <div style="background-color: #FFF">
                                <div id="personal_presentation"></div>
                            </div>

                            <p>
                                <input type="submit" value="Submit" name="descriptionForm">
                            </p>

                        </form>
                    </div>
                </div>
                
                
            </div>

        </main>
        
        <!-- CONTROL PANEL PROFILE CSS JS -->

        <script src="js/profile.js"></script>
        
        <!-- person object from response -->
        
        <script>var person = ${person}</script>
        
    </body>

</html>