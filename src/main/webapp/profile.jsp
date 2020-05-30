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
        
        <!-- jQuery/jQuery UI -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.0/jquery.min.js"></script>
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
        
    </head>



    <body>

        <input type="checkbox" id="toggle_menu">
        
        <!-- SIDEBAR FOR THE DASHBOARD -->
        <%@ include file="include/menu.jsp" %>


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
                                        <c:when test="${person.gender == null || person.gender == ''}">
                                            Choose Gender
                                        </c:when>
                                        <c:when test="${person.gender == 'M'}">
                                            Male
                                        </c:when>
                                        <c:otherwise>
                                            Female
                                        </c:otherwise>
                                    </c:choose>
                                </option>
                                <option value="M">Male</option>
                                <option value="F">Female</option>
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
                        
                        <form action="profileServlet" method="post" enctype="multipart/form-data">
                            <p>
                                <label for="photo"> 
                                    <img src="/imageset/profile/${sessionScope.userid}.jpg" class="profile_img" alt="profile image" id="profile_img">
                                </label>
                            </p>
                            <input type="file" name="photo" id="photo" accept=".jpg .png" onchange="readFile(this);">
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
                            
                            <br>
                            <c:choose>
                                <c:when test="${sessionScope.passMessage == null}">          
                                </c:when>
                                <c:when test="${sessionScope.passMessage}">
                                    <p id="message" style="color: green;">
                                        Updated Successfully
                                    </p>
                                </c:when>
                                 <c:otherwise>
                                    <p id="message" style="color: red;">
                                        Update Failed
                                    </p>
                                </c:otherwise>
                            </c:choose>
                            <c:remove var="passMessage" scope="session" />
                            
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
             
                        <p>
                            <img src="images/id.svg" class="id_img" alt="id image">
                        </p>
                        <form action="profileServlet" method="post" enctype="multipart/form-data">
                            <p>
                                <label class="file_label" for="document_card">Upload ID</label>
                            </p>
                            <input type="file" name="document_card" id="document_card" accept=".jpg .png" onchange="this.form.submit()">
                        </form>
                        
                    </div>
                    <br>

                    <!-- QUALIFICATION -->
                    <div class="box">
                        
                        <h4>
                            Qualification
                        </h4>
            
                        <p>
                            <img src="images/diplome.svg" class="qualification_img" alt="qualification image">
                        </p>
                        <form action="profileServlet" method="post" enctype="multipart/form-data">
                            <p>
                                <label class="file_label" for="qualification">Upload qualification</label>
                            </p>
                            <input type="file" name="qualification" id="qualification" accept=".jpg .png" onchange="this.form.submit()">
                        </form>
                        
                    </div>

                </div>

                <!-- COLUMN FOUR -->
                <div>
                    <!-- TOPIC OFFER -->
                    <div class="box" style="width: 400px">

                        <h4>
                            Topic offer  
                        </h4>
                        <br>
                        
                        <form action="profileServlet" method="POST" id="topicForm">
                            <table id="topicTable">
                        
                                <tr>
                                    <th><img src="images/add.png" onclick="addfieldFunction(this)"></th>
                                    <th><label> Topic </label></th>
                                    <th id ="tariffLabel"><label> Tariff &euro;/h </label></th>
                                </tr>
                                
                                    <!--<tr>
                                        <td></td>
                                        <td><input type="text"></td>
                                        <input type="hidden" name="subject" value="${first_subject.topicName}">
                                        <td><input type="number"></td>
                                        <input type="hidden" name="tariff" value="${first_subject.tariff}">
                                    </tr-->
                                
                                <c:forEach var="t" items="${subject_list}">
                                    <tr>
                                    <td>
                                        <img src="images/del.png" onclick="remove_topic(this);">
                                    </td>
                                    <td>
                                        <input type="text" required  value="${t.topicName}">
                                        <input type="hidden" name="subject">
                                    </td>
                                    <td>
                                        <input type="hidden" name="tariff">
                                        <input type="number" required  value="${t.tariff}">
                                    </td>
                                    </tr>
                                </c:forEach>
                                
                            </table>     
                                <p>
                                    <input type="submit" value="Submit" name="formTopic">
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
                        <form id="description" action="profileServlet" method="post">

                            <label for="text-area"></label>
                            <input name="text" type="hidden" id=text>
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
            
        <!-- set quill contents -->
        <script>quill.root.innerHTML = ${person.description};</script>
        
        <script>
            var topics = [    
                <c:forEach var="t" items="${topics_list}" varStatus="status">   
                    {id: '${t.topicid}', value: '${t.label}'}${!status.last ? ',' : ''}
                </c:forEach>
	       ];    
            
        </script>
    </body>

</html>