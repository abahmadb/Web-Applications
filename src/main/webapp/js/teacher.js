//fixing avg to 1 decimal 
avg = parseFloat(parseFloat(avg).toFixed(1));

//trasform avg to percentage
avg = avg * 20;
//set stars width according to the teacher score
document.getElementById("teacher_fullstar_style").setAttribute("style", "width: " + avg + "%");

//need these for the 3 modals, "send a message" modal, "request has been sent" modal and "log in first" modal
var modal_bookLessonCenter = document.querySelectorAll("center")[0];
var modal_quitBookLessonCenter = document.querySelectorAll("center")[1];
var modal_logInBookLessonCenter = document.querySelectorAll("center")[2];

//MODAL FOR BOOK A LESSON BUTTON start

//select book a lesson button
var button = document.querySelector(".button");
//attach toggle event to selected button
button.addEventListener("click", function () {

    if (modal_bookLessonCenter.style.display=='none' && modal_logInBookLessonCenter.style.display=="none") {
        //set visibility off for "request has been sent" modal
        modal_quitBookLessonCenter.style.display='none';
        //set visibility on for "send a message to the teacher" modal and resize the modal accordingly
        modal_bookLessonCenter.parentElement.style.cssText = "width: 600px; height: 400px; margin: -200px 0 0 -300px; padding: 30px";  
        modal_bookLessonCenter.style.display='block';
    }
    
    else if (modal_bookLessonCenter.style.display=='none' && modal_quitBookLessonCenter.style.display=="none"){
        //set visibility off for "log in first" modal
        modal_logInBookLessonCenter.style.display='none';
        //set visibility on for "send a message to the teacher" modal and resize the modal accordingly
        modal_bookLessonCenter.parentElement.style.cssText = "width: 600px; height: 400px; margin: -200px 0 0 -300px; padding: 30px";
        modal_bookLessonCenter.style.display='block';
    }
        
    toggle_modalteacher(event);

});

// RETRIEVE THE POP-UP FOR BOOK A LESSON BUTTON
var modal_bookLesson = document.querySelectorAll(".modal")[1];

// CONTROL VARIABLE TO TOGGLE THE BOOK A LESSON BUTTON
var modal_ctrl_bookLesson = false;

function toggle_modalteacher(event) {

    // IF THE POP-UP IS VISIBLE
    if(modal_ctrl_bookLesson){

        // SWITCH THE CONTROL VARIABLE OFF 
        modal_ctrl_bookLesson = false;

        // MAKE IT DISAPPEAR WITH TRANSITION ON OPACITY
        modal_bookLesson.style.opacity = "0";

        // WAIT FOR THE OPACITY TO REACH ZERO THEN TAKE MODAL OUT THE WINDOW
        // YOU HAVE TO WAIT! OTHERWISE YOU ABRUPTLY SEE IT DISAPPEAR AND THE
        // TRANSITION HAPPENS OUT OF WINDOW WHERE IT CAN'T BE SEEN
        setTimeout(function(){
            modal_bookLesson.style.transform = "translateY(-99.99999999%)";
        }, 500);

    }//if

    // IF THE POP-UP IS INVISIBLE
    else{

        // SWITCH THE CONTROL VARIABLE ON
        modal_ctrl_bookLesson = true;

        // TAKE MODAL INSIDE THE WINDOW
        modal_bookLesson.style.transform = "translateY(0%)";

        // MAKE IT APPEAR WITH TRANSITION ON OPACITY
        modal_bookLesson.style.opacity = "1";

    }

    /* 
    SINCE THE EVENT IS ATTACHED TO A LINK YOU MUST PREVENT THE
    AUTOMATIC BEHAVIOUR, WHICH IS THE CHANGE OF THE LOCATION */
    if(event != null) event.preventDefault();

}//toggle_modal

//MODAL FOR BOOK A LESSON BUTTON end

//CALL TEACHERSERVLET USING AJAX CALL (POST) start

function call_teacherServlet() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
      
    if (this.readyState == 4 && this.status == 200) {
        
        //set visibility off for "send a message to the teacher" modal and "log in first" modal
        modal_bookLessonCenter.style.display='none';
        modal_logInBookLessonCenter.style.display='none';
        //set visibility on for "request has been sent" modal and resize the modal accordingly
        modal_quitBookLessonCenter.parentElement.style.cssText = "width: 460px; height: 150px; margin: -75px 0 0 -230px; padding: 10px";     
        modal_quitBookLessonCenter.style.display='block';
    }
      
    //there was an error when sending the request because user was not logged in (I set status to 500 in servlet) 
    else if (this.status == 500) {
        modal_bookLessonCenter.style.display='none';
        modal_quitBookLessonCenter.style.display='none';
        //set visibility on for "log in first" modal and resize the modal accordingly
        modal_logInBookLessonCenter.parentElement.style.cssText = "width: 550px; height: 150px; margin: -75px 0 0 -275px; padding: 10px"; 
        modal_logInBookLessonCenter.style.display='block';
    }
  };
  xhttp.open("POST", document.getElementById("teacher_bookLesson_modal_style").getAttribute("context")+"/teacher", true);
  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhttp.send("teacherID=" + teacher_ID + "&comment=" + document.getElementsByTagName("textarea")[0].value);
}

//CALL TEACHERSERVLET USING AJAX CALL (POST) end