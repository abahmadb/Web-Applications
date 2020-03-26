var modal = document.querySelector(".modal");
var modal_ctrl = false;

var registration = document.querySelector(".sign_up_in");
registration_ctrl = false;

function toggle_modal(){

        if(modal_ctrl){
            modal.style.display = "none";
            modal_ctrl = false;
        }
        else{
            modal.style.display = "block";
            modal_ctrl = true;
        }

}

function flip_registration(){

    if(registration_ctrl){
        registration.classList.remove("flipped");
        registration_ctrl = false;
    }
    else{
        registration.classList.add("flipped");
        registration_ctrl = true;
    }

    event.preventDefault();

}