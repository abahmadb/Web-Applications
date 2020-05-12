//variables data, counters and avg come directly from jsp + servlet

//fixing avg
avg = parseFloat(parseFloat(avg).toFixed(1));

//count the total number of feedbacks
let numFeed = counters.reduce((a, b) => a + b, 0);

if (numFeed > 0) {

    let rev = document.getElementById("rev-container");
    //graphical fix, applied only if there are reviews to load
    rev.style.paddingRight = "1px";
    rev.style.height = "auto";

}

//set stars width using avg
let stars = document.getElementsByClassName("rating-upper");
stars[0].style.width = ((avg / 5) * 100) + "%";

//insert first box inner paragraph into DOM
let p = document.getElementById("avg");
p.innerText = avg + ' average based on ' + numFeed + ' reviews.';

//fill correctly all first box bars using counters array
for (let i = 0; i < counters.length; i++) {

    //get the bar
    let bar = document.getElementsByClassName("bar-" + (i + 1));
    //fill it with the right percentage width
    bar[0].style.width = Math.round(counters[i] * 100 / numFeed) + "%";
    //write the bar counter on the right of the bar
    let side = document.getElementsByClassName("side right");
    side[counters.length - i - 1].innerText = counters[i].toString();

}

//get buttons used for giving feedbacks
let buttons = document.querySelectorAll("a.feedreq");
for(let i = 0; i < buttons.length; i++){

    //attach toggle event to selected buttons
    buttons[i].addEventListener("click", function () {

        toggle_modalfeed(event);

    });

}

//reuse variables from home.js to toggle the modal
modal = document.querySelectorAll("div")[0];
modal_ctrl = false;

//toggle modal function
function toggle_modalfeed(event){

    //execute only if we are showing the modal, not when hiding it
    if (!modal_ctrl){

        //set the heading text using the username field in the table with the give-feed buttons
        let heading = document.getElementById("feedform").querySelectorAll("div")[0];
        heading.innerHTML = "Give a feedback to " + event.target.parentElement.previousElementSibling.previousElementSibling.innerHTML + "!";

        //also update the input to be sent to the back-end
        let teacherinput = document.getElementsByName("teacher")[0];
        teacherinput.value = event.target.getAttribute("teacherid");

    }

    //use the toggle_modal from home.js to show or hide the modal
    toggle_modal(event);

}


//get the stars container in the modal
let modalstars = document.getElementsByClassName("modal")[0].getElementsByClassName("rating")[0];
//we need to modify the star filling in the modal every time we click on them
//so starting from the container we visit every grandchildren
for(let i = 0; i < modalstars.children.length; i++){

    for(let j = 0; j < modalstars.children[i].children.length; j++){

        //attach to every star the update event
        modalstars.children[i].children[j].addEventListener("click", function(){

            update_local_stars(j+1);

        });

    }

}

//function for updating stars inside the form modal whenever the user clicks on them
function update_local_stars(num){

    //stars in the html have two layers, they both work like buttons, but the upper one is the only one that is modified
    //get the stars to modify
    let stars = modalstars.children[0];

    //simply fill the stars
    stars.style.width = ((num / 5) * 100) + "%";

    //update the score description text
    let scoretag = document.getElementById("scoretag");
    scoretag.innerText = "Score: "+ num;

    //also update the value that will be sent to the back-end
    let scoreinput = document.getElementsByName("score")[0];
    scoreinput.value = num;

}