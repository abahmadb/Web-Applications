let modal_book = document.querySelector("#teacherform center");


var button = document.querySelector(".header_bookLesson_box .button");


button.addEventListener("click", function () {

    if(logged_in)
        toggle_modalteacher(event);
    else
        document.querySelector("nav a:last-child").click();

    event.preventDefault();

});

var modal_bookLesson = modal_book.parentElement.parentElement;
var modal_ctrl_bookLesson = false;

function toggle_modalteacher(ev) {

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
    if(ev != null) event.preventDefault();

}//toggle_modal


function call_teacherServlet(ev, elem) {

    modal_book.parentElement.style.width = "auto";
    modal_book.parentElement.style.height = "auto";
    modal_book.parentElement.style.margin = "auto";
    modal_book.parentElement.style.transform = "translate(-50%,-50%)";
    
    let values = {
        teacherID: teacher_ID,
        comment: elem.previousElementSibling.value
    };

    $.post("teacher", values, function(data){

        if(data == 1)
            modal_book.innerHTML = `<br>
                        <div class="quit_chat_label">The request has been sent! The teacher will soon evaluate it, thank you</div>
                        <br>
                        <button class="button" onclick="toggle_modalteacher(event)" class="close_modal">Exit</button>`;
        
        else
            modal_book.innerHTML = `<br>
                        <div class="quit_chat_label">An errore occurred trying to send the request. Make sure you are logged in in before booking the lesson.</div>
                        <br>
                        <button class="button" onclick="location.reload()" class="close_modal">Exit</button>`;
        
    });


}
