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
        <%@ include file="include/menu.jsp" %>

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

                    <div class="box hoverable_rows">

                        <h2>Chats</h2>

                        <div>

                            <table>
                                <tr>
                                    <td>
                                        Matteo
                                    </td>
                                    <td>
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam lacinia vehicula auctor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. In sollicitudin leo in volutpat cursus.
                                    </td>
                                    <td>
                                        09/04/2020 19:05
                                    </td>
                                </tr>

                                <tr>
                                    <td>
                                        Francesco
                                    </td>
                                    <td>
                                        Praesent venenatis, risus id ornare interdum, neque quam sollicitudin odio, non convallis neque urna at libero. Donec ac libero malesuada, blandit enim at, ornare orci. Integer et nisi condimentum, aliquam mi in, porta est. Morbi in mollis risus.
                                    </td>
                                    <td>
                                        09/04/2020 19:05
                                    </td>
                                </tr>

                                <tr>
                                    <td>
                                        Lucia
                                    </td>
                                    <td>
                                        Phasellus congue hendrerit lectus accumsan pulvinar. Phasellus sodales sem non neque pharetra, eget vulputate nulla aliquam. Praesent eu malesuada leo. Nulla nisi nibh,
                                    </td>
                                    <td>
                                        09/04/2020 19:05
                                    </td>
                                </tr>
                            </table>

                        </div>

                        <p>
                            <a href="/remytutor/chat">Show all chats</a>
                        </p>
                    </div>

                    <br>

                    <div class="box hoverable_rows">

                        <h2>Feedbacks</h2>


                        <div>

                            <table>
                                <tr>
                                    <td>
                                        Matteo
                                    </td>
                                    <td>
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
                                        Francesco
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
                                        Lucia
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

                                <tr>

                                    <td>
                                        09/04/2020 19:26
                                    </td>
                                    <td>
                                        Incoming deposit from Matteo
                                    </td>
                                    <td>
                                        +30&euro;
                                    </td>
                                </tr>

                                <tr>

                                    <td>
                                        09/04/2020 19:26
                                    </td>
                                    <td>
                                        Incoming deposit from Lucia
                                    </td>
                                    <td>
                                        +26&euro;
                                    </td>
                                </tr>

                                <tr>

                                    <td>
                                        09/04/2020 19:26
                                    </td>
                                    <td>
                                        Incoming deposit from Luca
                                    </td>
                                    <td>
                                        +28&euro;
                                    </td>
                                </tr>

                                <tr>

                                    <td>
                                        09/04/2020 19:26
                                    </td>
                                    <td>
                                        Outgoing payment to Francesco
                                    </td>
                                    <td>
                                        -20&euro;
                                    </td>
                                </tr>

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