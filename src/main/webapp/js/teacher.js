//fixing avg to 1 decimal 
avg = parseFloat(parseFloat(avg).toFixed(1));

//trasform avg to percentage
avg = avg * 20;
//set stars width according to the teacher score
document.getElementById("teacher_fullstar_style").setAttribute("style", "width: " + avg + "%");

//MODAL FOR BOOK A LESSON BUTTON start
//select book a lesson button
var button = document.querySelector(".button");
//attach toggle event to selected button
button.addEventListener("click", function () {

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
      var data = xhttp.responseText;
        alert(data);
    }
  };
  xhttp.open("POST", document.getElementById("teacher_bookLesson_modal_style").getAttribute("context")+"/teacher", true);
  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhttp.send("teacherID=1&studentID=4&comment=" + document.getElementsByTagName("textarea")[0].value);
}
