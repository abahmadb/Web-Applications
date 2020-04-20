//This function to filter the tab

function myFunction() {
  // Declare variables
  var input, filter, ul, button, tablinks, i, txtValue;
  input = document.getElementsByClassName('search');
  filter = input.value.toUpperCase();
  ul = document.getElementByClassName("tab");
  button = ul.getElementsByTagName('button'); 
  
  
  // Loop through all list items, and hide those who don't match the search query
  for (i = 0; i < button.length; i++) {
    tablinks = button[i].getElementsByClassName("tablinks")[0];
    txtValue = tablinks.textContent || tablinks.innerText;
    if (txtValue.toUpperCase().indexOf(filter) > -1) {
      button[i].style.display = "";
    } else {
      button[i].style.display = "none";
    }
  }
} 

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



