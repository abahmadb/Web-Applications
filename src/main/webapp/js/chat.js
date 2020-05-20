let current_chat;

// THIS FUNCTION OPENS (AND OPTIONALLY DOWNLOADS) THE CORRESPONDING CHAT ON THE RIGHT
function openChat(evt, otherid, am_i_teacher) {

    let tabid = evt.currentTarget.getAttribute("tabid");

    current_chat = tabid;

    // GET THE ELEMENT REPRESENTING THE CURRENT TAB
    let curtab = document.getElementById(tabid);

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
            let messages = "";

            // LOOP THROUGH ALL THE MESSAGES
            for(let i = 0; i < data.length; i++){

                // IF I AM THE SENDER
                if(data[i].SenderID == current_user)
                    messages += message_template('self', data[i].SenderID, data[i].Message, data[i].TS);

                // OTHERWISE, I AM THE RECEIVER SO
                else{

                    let str = "";

                    if(data[i].hasOwnProperty("LessonID"))
                        str = `<p><button onclick="confirmLesson(this, true, ${data[i].LessonID}, ${data[i].SenderID});">Accept</button><button onclick="confirmLesson(this, false, ${data[i].LessonID}, ${data[i].SenderID});">Reject</button></p>`;

                    messages += message_template('friend', data[i].SenderID, data[i].Message+str, data[i].TS);



                }


            }//for

            var lm = data[data.length-1];
            if(!lm.Message.startsWith("<h2"))
                document.querySelector('li[tabid="' + tabid + '"] .user_info p:last-child').innerHTML = lm.Message;


            // INSERT THEM INTO THE PAGE
            curtab.firstElementChild.innerHTML = messages;

            // KEEP THE SCROLL DIV AT THE BOTTOM
            updateScroll(curtab.firstElementChild);
        });
    }//if

    // NOW TIME TO HIGHLIGHT THE ITEM ON THE LEFT AND SHOW THE BOX ON THE RIGHT WITH THE MESSAGES

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

// PROGRAMMATICALLY TRIGGER A CLICK ON THE FIRST CHAT TO OPEN IT
let first_chat = document.querySelector(".chatlogs2 .tab li:first-child");
if(first_chat != null) first_chat.click();


function sendMessage(evt, otherid, tabid, am_i_teacher){

    var elem = evt.currentTarget;

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

    // PUSH THE REQUEST!
    $.post("chatpost", sending, function(data){

        // IF EVERYTHING WENT OK, INSERT THE NEW MESSAGE WITHOUT RELOADING THE PAGE
        if(data == 1){
            curtab.firstElementChild.innerHTML += message_template('self', current_user, sending["text_message"], get_timestamp());
            // EMPTY THE TEXTAREA
            elem.previousElementSibling.value = "";

            let lm = document.querySelector('li[tabid="' + tabid + '"] .user_info p:last-child');
            lm.innerHTML = sending["text_message"];
        }
        // SOMETHING WENT WRONG
        else
            alert("Oops, something went wrong and the message could not be sent");

        // KEEP THE SCROLL DIV AT THE BOTTOM
        updateScroll(curtab.firstElementChild);
    });
}//sendMessage

function confirmRequest(action, otherid, tabid){

    // ASK THE USER IF HE IS SURE HE WANTS TO PROCEED
    let u_sure = confirm("Are you sure you want to proceed?");
    if(!u_sure) return;

    // WRAPPER WERE TO PUT THE SEND MESSAGE FORM
    var curtab = document.getElementById(tabid);

    let confirmation = {confirm_request: action};

    confirmation['teacher_id'] = current_user;
    confirmation['student_id'] = otherid;

    $.post("chatpost", confirmation, function(data){

        // IF EVERYTHING WAS OK
        if(data == 1){

            // AND THE TEACHER ANSWERED ACCEPT, THEN ENABLE THE CHAT
            if(action){
                curtab.lastElementChild.innerHTML = `<textarea></textarea>
<button class="button" onclick="sendMessage(event, ${otherid}, ${tabid}, true);">Send</button>
<button class="send_proposal" onclick="toggle_modal(event, ${otherid}, ${tabid})"><i class="far fa-paper-plane"></i></button>`;
                // REMOVE RED DOT FROM CONTACTS LIST
                let red_dot = document.querySelector('li[tabid="' + tabid + '"] .img_cont div');
                red_dot.parentElement.removeChild(red_dot);
            }
            // AND THE TEACHER ASWERED REJECT, THEN
            else{
                // REMOVE THE TABCONTENT WITH THE CHAT
                curtab.parentElement.removeChild(curtab);

                // REMOVE THE CONTACT FROM THE CONTACTS LIST
                let contact = document.querySelector('li[tabid="' + tabid + '"]');
                contact.parentElement.removeChild(contact);
            }
        }
        // SOMETHING WENT WRONG
        else
            alert("Oops, something went wrong and the request could not be processed");
    });

}//confirmRequest

function offer_lesson(){

    // GET ALL THE VALUES NEEDED
    let values_list = document.querySelectorAll(".lesson_proposal input");

    // ASSEBLE LESSON DETAILS TO SEND TO THE SERVER
    let lesson_proposal = {
        offer_lesson: "1",
        teacher_id: current_user,
        student_id: values_list.item(0).value,
        lesson_date: values_list.item(1).value,
        lesson_time: values_list.item(2).value,
        lesson_tariff: values_list.item(3).value,
        lesson_duration: values_list.item(4).value+":"+values_list.item(5).value
    }

    // PREPARE THE ELEMENT WHERE TO INSERT THE "SPECIAL" MESSAGE
    var curtab = document.getElementById(values_list.item(0).getAttribute("tabid"));

    $.post("chatpost", lesson_proposal, function(data){

        // INSERT THE NEW MESSAGE INTO THE BOX
        curtab.firstElementChild.innerHTML += message_template('self', current_user, data, get_timestamp());

        // KEEP THE SCROLL DIV AT THE BOTTOM
        updateScroll(curtab.firstElementChild);

        // MAKE MODAL DISAPPEAR
        toggle_modal(null, '', '');
    });

}//offer_lesson

function confirmLesson(elem, action, lessonid, teacherid){

    // ASK THE USER IF HE IS SURE HE WANTS TO PROCEED
    let u_sure = confirm("Are you sure you want to proceed?");
    if(!u_sure) return;


    let lesson = {
        confirm_lesson: action,
        lesson_id: lessonid,
        teacher_id: teacherid,
        student_id: current_user
    }

    $.post("chatpost", lesson, function(data){

        // CHANGE THE TITLE
        let h2 = elem.parentElement.parentElement.firstElementChild;
        h2.outerHTML = data;

        // REMOVE BOTH BUTTONS
        elem.parentElement.parentElement.removeChild(elem.parentElement);

    });

}

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

function toggle_modal(ev, studentid, tabid) {

    // SEVE THE REFERENCES FOR THE CHAT THIS OPERATION IS LINKED TO
    var h = document.querySelector('.lesson_proposal input[type="hidden"]');
    h.value = studentid;
    h.setAttribute("tabid", tabid);

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

function get_timestamp(){
    var d = new Date();
    return `${d.getDate()}-${d.getMonth()+1}-${d.getFullYear()} ${d.getHours()}:${d.getMinutes()}`;
}//get_timestamp

function message_template(self_friend, id_pic, message, ts){
    return `<div class="chat ${self_friend}"><div class="user-photo"><img src="/imageset/profile/${id_pic}.jpg"></div><div class="chat-message">${message}<span>${ts}</span></div></div>`;
}//message_template

function updateScroll(element){
    element.scrollTop = element.scrollHeight;
}//updateScroll

setInterval(function(){
    let chatbox = document.getElementById(current_chat);
    chatbox.firstElementChild.innerHTML = "";
    document.querySelector('li[tabid="' + current_chat + '"]').click();
}, 20000);
