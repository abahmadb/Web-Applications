
/* ========== SCRIPT OF SIGN-UP/SIGN-IN MODAL ========== */

// RETRIEVE THE POP-UP FOR SIGN-UP/SIGN-IN
var modal = document.querySelector(".modal");

// CONTROL VARIABLE TO TOGGLE THE SIGN-UP/SIGN-IN POP-UP
var modal_ctrl = false;

// RETRIEVE THE INNER CARD FOR SIGN-UP/SIGN-IN
var registration = modal.firstElementChild;

// CONTROL VARIABLE TO TOGGLE FLIP THE SIGN-UP/SIGN-IN CARD
var registration_ctrl = false;

function toggle_modal(ev) {

    // IF THE POP-UP IS VISIBLE
    if(modal_ctrl){

        // SWITCH THE CONTROL VARIABLE OFF 
        modal_ctrl = false;

        // MAKE IT DISAPPEAR WITH TRANSITION ON OPACITY
        modal.style.opacity = "0";

        // WAIT FOR THE OPACITY TO REACH ZERO THEN TAKE MODAL OUT THE WINDOW
        // YOU HAVE TO WAIT! OTHERWISE YOU ABRUPTLY SEE IT DISAPPEAR AND THE
        // TRANSITION HAPPENS OUT OF WINDOW WHERE IT CAN'T BE SEEN
        setTimeout(function(){
            modal.style.transform = "translateY(-99.99999999%)";
        }, 500);

    }//if

    // IF THE POP-UP IS INVISIBLE
    else{

        // SWITCH THE CONTROL VARIABLE ON
        modal_ctrl = true;

        // TAKE MODAL INSIDE THE WINDOW
        modal.style.transform = "translateY(0%)";

        // MAKE IT APPEAR WITH TRANSITION ON OPACITY
        modal.style.opacity = "1";

    }//else


    /* 

        SINCE THE EVENT IS ATTACHED TO A LINK YOU MUST PREVENT THE
        AUTOMATIC BEHAVIOUR, WHICH IS THE CHANGE OF THE LOCATION

    */ 
    if(ev != null) ev.preventDefault();

}//toggle_modal

function flip_registration(ev) {

    // IF THE POP-UP SHOWS SIGN-UP
    if(registration_ctrl){

        // SWITCH THE CONTROL VARIABLE OFF
        registration_ctrl = false;

        // FLIP THE CARD TO SHOW SIGN-IN
        registration.classList.remove("flipped");

    }

    // IF THE POP-UP SHOWS SIGN-IN
    else{

        // SWITCH THE CONTROL VARIABLE ON
        registration_ctrl = true;

        // FLIP THE CARD TO SHOW SIGN-UP
        registration.classList.add("flipped");

    }//else

    /* 

        SINCE THE EVENT IS ATTACHED TO A LINK YOU MUST PREVENT THE
        AUTOMATIC BEHAVIOUR, WHICH IS THE CHANGE OF THE LOCATION

    */
    ev.preventDefault();

}//flip_registration

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}


$( function() {

    // SET THE AUTOCOMPLETION FOR TOPICS
    $("#search_box input").autocomplete({
        source: topics,
        select: function(e, ui,){
            e.preventDefault() // <--- Prevent the value from being inserted.
            $("#topic_id").val(ui.item.id);

            $(this).val(ui.item.value);
        }
    });

    // SIGN-IN REQUEST
    $(".sign_in input[type='submit']").click(function(){

        // VALIDATE THE EMAIL

        var email = $(".sign_in input[type='email']")[0];

        if(!email.checkValidity() || email.value.trim() == ""){
            $("#login_error").html('Please input a correct e-mail address');
            return;
        }


        // DON'T VALIDATE PASSWORD ==> COULD DISCLOSE PASSWORD FORMAT?

        var password = $(".sign_in input[type='password']")[0];

        // REMEMBER ME

        var rm = $(".sign_in input[type='checkbox']").prop("checked");

        var credentials = {
            uname: email.value,
            pword: password.value,
        }

        $.post("indexpost", credentials, function(data) {

            var res = JSON.parse(data);
            if(res.response == 1){
                $("nav a:last-child").replaceWith(`<a href="control.html"><img src="images/imageset/profile/${res.userid}.jpg">&nbsp;</a>`);
                if(rm) setCookie("userid", res.userid, 5*24*60*60);
                toggle_modal(null);
            }
            else{
                $("#login_error").html('Incorrect e-mail and/or password.');
                $(".sign_up_in").effect("shake");
            }
        });

    });
    
    // SIGN-UP REQUEST
    $(".sign_up input[type='submit']").click(function(){

        // VALIDATE THE EMAIL

        var email = $(".sign_up input[type='email']")[0];

        if(!email.checkValidity() || email.value.trim() == ""){
            $("#register_error").html('Please input a correct e-mail address');
            return;
        }

    
        var pwords = $(".sign_up input[type='password']");
        
        if(pwords[0].value != pwords[1].value){
            $("#register_error").html('The password and its confirmation must be equal');
            return;
        }
        
        // TERMS AND CONDITIONS

        var rm = $(".sign_up input[type='checkbox']").prop("checked");

        if(!rm){
            $("#register_error").html('You have to accept the terms and conditions');
            return;
        }
    
    });
});

