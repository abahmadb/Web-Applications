
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
    //alert(chatlist.length);
    
    let val = elem.previousElementSibling.value.toLowerCase();
    
    let contact_name;
    
    for(let i = 0; i < chatlist.length; i++){
        
        contact_name = chatlist.item(i).firstElementChild.innerHTML;
        
        if(contact_name.toLowerCase().indexOf(val) < 0)
            chatlist.item(i).parentElement.parentElement.parentElement.style.display = "none";        
    }
    
    ev.preventDefault();
}


