/* ========== SCRIPT OF PROFILE PAGE ========== */

/* ID image change */

function readFile(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
        document.getElementById("profile_img").src = e.target.result;
        };
        reader.readAsDataURL( input.files[0] );
    }
}


/* password check */

function validatePassword(){
    var old_password = document.getElementById("old_pw");
    var password = document.getElementById("new_pw");
    var confirm_password = document.getElementById("confirm_pw");
    /*if (password.value == old_password.value){
        password.setCustomValidity("New password can't be equal to previous one");
        return false;
    }*/
    if (password.value != confirm_password.value) {
        confirm_password.setCustomValidity("Passwords Don't Match");
        return false;
    }
    else {
        confirm_password.setCustomValidity('');
        return true;
    }
}

/* text area for the Tell about yourself box */

var quill = new Quill('#personal_presentation', {
                theme: 'snow',
                placeholder: 'Introduce yourself to students!',
                modules: {
                    toolbar: [
                        ['bold', 'italic', 'underline'], 

                        [{ 'list': 'ordered'}, { 'list': 'bullet' }],
                        
                        [{ 'header': [1, 2, 3, 4, 5, 6, false] }],

                        [{ 'color': [] }],
                        
                        [{ 'align': [] }]
                    ]
                } 
            });

/* submit quill context using hidden input*/
form = document.getElementById("description");
form.onsubmit = function() {
  // Populate hidden input on submit
  var text = document.querySelector('input[name=text]');
  text.value = JSON.stringify(quill.root.innerHTML);
  return true;
};

// SET THE AUTOCOMPLETION FOR TOPICS
$( function () {
    
    $("#topicForm input[type='text']").autocomplete({
        source: topics,
        select: function(e, ui){
            e.preventDefault(); // <--- Prevent the value from being inserted.
            $("#topic_id").val(ui.item.id);
            $(this).val(ui.item.value);
        }
    });
});

// add inputs field on click

function addfieldFunction(icon) {
    
    /*var tableRef = document.getElementById("topicTable");
    var newRow = tableRef.insertRow(-1);
    var newCellDel = newRow.insertCell(0);
    var newCellSubject = newRow.insertCell(1);
    var newCellTariff = newRow.insertCell(2);
    // create input element
    var del = document.createElement("img");
    del.setAttribute("src", "images/del.png"); 
    del.setAttribute("onclick", "remove_topic(this);")
    var inputSubject = document.createElement("input");
    inputSubject.setAttribute("type", "text");
    var hiddenInput = document.createElement("input");
    hiddenInput.setAttribute("name", "topic_list");
    hiddenInput.setAttribute("type", "hidden");
    var inputTariff = document.createElement("input");
    inputTariff.setAttribute("type", "number");
    
    newCellDel.appendChild(del);
    <input type="hidden" name="topics_list" value="">
    newCellSubject.appendChild(inputSubject);
    newCellSubject.appendChild(hiddenInput);
    newCellTariff.appendChild(inputTariff);*/
    
     // take the table element
    var tbody = icon.parentElement.parentElement.parentElement;
    // insert new row
    tbody.innerHTML += `<tr>
                            <td>
                                <img src="images/del.png" onclick="remove_topic(this);">
                            </td>
                            <td>
                                <input type='text'>
                                <input type="hidden" name="subject" value="">
                            </td>
                            <td>
                                <input type="hidden" name="tariff" value="">
                                <input type="number">
                            </td>
                            </tr>`;
    
    $("#topicForm input[type='text']").autocomplete({
        source: topics,
        select: function(e, ui){
            e.preventDefault(); // <--- Prevent the value from being inserted.
            $("#topic_id").val(ui.item.id);
            //topics.push(ui.item.id);
            $(this).val(ui.item.value);
        }
    });

}

function remove_topic(icon) {
     icon.parentElement.parentElement.parentElement.removeChild(icon.parentElement.parentElement);   
}

/* populate hidden input of topic */
topicForm = document.getElementById("topicForm");
topicForm.onsubmit = function() {
    var topics = [];
    var tariffs = [];
    $("#topicForm input[type='text']").each(function() {
        topics.push($(this).val());
    });
    $("#topicForm input[type='number']").each(function() {
        tariffs.push($(this).val());
    });
    var i = 0;
    $("#topicForm input[name='subject']").each(function() {
        $(this).val(topics[i++]);
    });
    i = 0;
    $("#topicForm input[name='tariff']").each(function() {
        $(this).val(tariffs[i++]);
    });
    
    return true;
};

/* prevent from re-submission on reflash */

if ( window.history.replaceState ) {
    window.history.replaceState( null, null, window.location.href );
}