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

// prevent from re-submission on reflash

if ( window.history.replaceState ) {
  window.history.replaceState( null, null, window.location.href );
}