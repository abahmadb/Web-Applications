
//This function to link the tab with the tab content
function openChat(evt, chatName) {
    // Declare all variables
    var i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    tabcontent= document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent [i].style.display = "none";
    }
    // Get all elements with class="tablinks" and remove the class "active"
    tablinks= document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks [i].className = tablinks[i].className.replace(" active", "");
    }
    // Show the current tab, and add an "active" class to the button that opened the tab
    document.getElementById(chatName).style.display = "block";
    evt.currentTarget.className += " active";
}

// Get the element with id="defaultOpen" and click on it
document.getElementById("defaultOpen").click();

var chatlist = document.querySelectorAll(".user_info");

function filter_chatlist(ev, elem){
    
    // EXTRACT VALUE FROM TEXTBOX
    let val = elem.previousElementSibling.value.toLowerCase();
    
    let li_element;
    
    // LOOP THROUGH ALL THE CONTACTS
    
    for(let i = 0; i < chatlist.length; i++){
        
        li_element = chatlist.item(i).parentElement.parentElement.parentElement;
        
        // IF THE ELEMENT DOESN'T MATCH THE SEARCH VALUE
        if(chatlist.item(i).firstElementChild.innerHTML.toLowerCase().indexOf(val) < 0)
            // HIDE IT
            li_element.style.display = "none";
        else
            // OTHERWISE, SHOW IT
            li_element.style.display = "";
        
    }//for
    
    ev.preventDefault();
}//filter_chatlist

// RETRIEVE THE POP-UP FOR LESSON PROPOSAL
var modal = document.querySelector(".lesson_proposal");

// CONTROL VARIABLE TO TOGGLE THE LESSON PROPOSAL POP-UP
var modal_ctrl = false;

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


