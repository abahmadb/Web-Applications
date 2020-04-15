/* ========== SCRIPT OF PROFILE PAGE ========== */

/* personal data default values */

function defaultValues() {
    document.getElementById("lname").defaultValue = "Xianwen";
} 

/* password check */

function validatePassword(){
    var password = document.getElementById("new_pw");
    var confirm_password = document.getElementById("confirm_pw");
    if(password.value != confirm_password.value) {
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

// prevent from resubmittion on refrash

if ( window.history.replaceState ) {
  window.history.replaceState( null, null, window.location.href );
}