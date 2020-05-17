
//This function to link the tab with the tab content
function openChat(evt, otherid, tabid, am_i_teacher) {

    // GET THE ELEMENT REPRESENTING THE CURRENT TAB
    var curtab = document.getElementById(tabid);

    // DOWNLOAD THE MESSAGES FOR THIS CHAT ONLY IF YOU DID NOT DO IT ALREADY
    if(curtab.firstElementChild.firstElementChild == null){

        // LET'S SEND THE PAIR (TEACHERID, STUDENTID) TO THE SERVER TO ASK FOR THE MESSAGES
        let chat_id = {load_messages: "1"};

        if(am_i_teacher){
            chat_id['teacher_id'] = current_user;
            chat_id['student_id'] = otherid;
        }
        else{
            chat_id['teacher_id'] = otherid;
            chat_id['student_id'] = current_user;
        }

        // GET THE CHAT MESSAGES CORRESPONDING TO THE SELECTED PAIR
        $.post("chatpost", chat_id, function(data){

            // PARSE THE RECEIVED MESSAGES AND SHOW THEM TO THE USER
            let chat_container = curtab.firstElementChild;
            let messages = "";

            // LOOP THROUGH ALL THE MESSAGES
            for(let i = 0; i < data.length; i++){

                // IF I AM THE SENDER
                if(data[i].SenderID == current_user)
                    messages += '<div class="chat self">';

                // OTHERWISE, I AM THE RECEIVER SO
                else
                    messages += '<div class="chat friend">';


                messages += `   <div class="user-photo">
                                    <img src="/imageset/profile/${data[i].SenderID}.jpg">
                                </div>
                                <p class="chat-message">${data[i].Message}<span>${data[i].TS}</span></p>
                            </div>`;

            }//for

            // INSERT THEM INTO THE PAGE
            chat_container.innerHTML = messages;
        });
    }//if

    // Get all elements with class="tabcontent" and hide them
    let tabcontent = document.querySelectorAll(".tabcontent");
    for (let i = 0; i < tabcontent.length; i++)
        tabcontent[i].style.display = "none";

    // Get all elements with class="tablinks" and remove the class "active"
    let tablinks= document.querySelectorAll(".tablinks");
    for (let i = 0; i < tablinks.length; i++)
        tablinks[i].classList.remove("active");

    // Show the current tab, and add an "active" class to the button that opened the tab
    curtab.style.display = "block";
    evt.currentTarget.classList.add("active");
}//openChat

// Get the element with id="defaultOpen" and click on it
document.getElementById("defaultOpen").click();


function sendMessage(ev, elem, otherid, tabid, am_i_teacher){
    
    // WRAPPER WERE TO PUT THE NEWLY GENERATED MESSAGE
    var curtab = document.getElementById(tabid);
    
    // CONTENT OF THE MESSAGE
    var text = elem.previousElementSibling.value;

    // WE SIGNAL TO THE SERVLET THAT THE OPERATION WE WANT TO PERFORM IS THE SENDING OF A MESSAGE
    var sending = {send_message: "1"};

    // SO WE GIVE THE SERVLET THE MESSAGE, THE SENDERID AND THE IDENTIFIER (TEACHERID, STUDENTID)
    sending["text_message"] = text.trim();
    sending["sender"] = current_user;

    if(am_i_teacher){
        sending['teacher_id'] = current_user;
        sending['student_id'] = otherid;
    }
    else{
        sending['teacher_id'] = otherid;
        sending['student_id'] = current_user;
    }
    
    var d = new Date();
    
    var datestring = `${d.getDate()}-${d.getMonth()}-${d.getFullYear()} ${d.getHours()}:${d.getMinutes()}`;

    // PUSH THE REQUEST!
    $.post("chatpost", sending, function(data){
        
        // IF EVERYTHING WENT OK, INSERT THE NEW MESSAGE WITHOUT RELOADING THE PAGE
        if(data == 1){
            curtab.firstElementChild.innerHTML += `<div class="chat self">
                                                        <div class="user-photo">
                                                            <img src="/imageset/profile/${current_user}.jpg">
                                                        </div>
                                                        <p class="chat-message">${sending["text_message"]}<span>${datestring}</span></p>
                                                    </div>`;
            elem.previousElementSibling.value = "";
        }
        // SOMETHING WENT WRONG
        else
            alert("Oops, something went wrong and the message could not be sent");
    });
}//sendMessage



var chatlist = document.querySelectorAll(".user_info p strong");

function filter_chatlist(ev, elem){

    // EXTRACT VALUE FROM TEXTBOX
    let val = elem.value.toLowerCase();

    let li_element;

    // LOOP THROUGH ALL THE CONTACTS

    for(let i = 0; i < chatlist.length; i++){

        li_element = chatlist.item(i).parentElement.parentElement.parentElement.parentElement;

        // IF THE ELEMENT DOESN'T MATCH THE SEARCH VALUE
        if(chatlist.item(i).innerHTML.toLowerCase().indexOf(val) < 0)
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


