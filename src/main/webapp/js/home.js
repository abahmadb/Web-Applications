
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

$( function() {

    $("#search_box input").autocomplete({
        source: topics,
		select: function(e, ui,){
			e.preventDefault() // <--- Prevent the value from being inserted.
            $("#topic_id").val(ui.item.id);

            $(this).val(ui.item.value);
		}
    });

    $(".sign_in input[type='submit']").click(function(){

                
        // VALIDATE THE EMAIL
        
        var email = $(".sign_in input[type='email']")[0];
        
        if(!email.checkValidity() || email.value.trim() == ""){
            $("#login_error").html('Please input a correct e-mail address');
            return;
        }
            
        
        // VALIDATE PASSWORD
        
        var password = $(".sign_in input[type='password']")[0];
        
        // REMEMBER ME
        
        var rm = $(".sign_in input[type='checkbox']").prop("checked");
        
        var credentials = {
            uname: email.value,
            pword: password.value,
            remember_me: rm
        }
        
        /*$.post("login_mockup.json", function(data) {
        });*/
        
        var result = {
            response: 1,
            user_pic: "MarcoG.jpg"
        }
        
        if(result.response == 1){
            $("nav a:last-child").replaceWith(`<a href="control.html"><img src="images/${result.user_pic}">&nbsp;</a>`);
            toggle_modal(null);
        }
        else{
            $("#login_error").html('Incorrect e-mail and/or password.');
            $(".sign_up_in").effect("shake");
        }
        
        
    });
});

