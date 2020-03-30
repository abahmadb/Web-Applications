var main_img = document.querySelector("main > section:first-child > img");
main_img.setAttribute("src", `images/imageset/${Math.floor(Math.random()*17+1)}.jpg`);



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
    ev.preventDefault();

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

