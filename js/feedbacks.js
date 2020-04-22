let sum = 0;
let data = "";
let counters = [0, 0, 0, 0, 0];

//parse json data
//as of now, the structure of the json is an array of objects assigned to every user with keys 'user', 'rating' and 'comment'
let global = JSON.parse('{ '+
	'"Marco Dell\'Anna":[{"user":"Marco Dalla Mutta","rating":4,"comment":"Very good"},' +
						'{"user":"Xianwen Jin","rating":4,"comment":"Excellent"},' +
						'{"user":"Victor Semencenco","rating":5,"comment":"Excellent"}], ' +
    '"Marco Dalla Mutta":[{"user":"Marco Dell\'Anna","rating":4,"comment":"Very good"},' +
						'{"user":"Xianwen Jin","rating":4,"comment":"Excellent"},' +
						'{"user":"Victor Semencenco","rating":5,"comment":"Excellent"}]}');

//we can select only a group of feedbacks, relevant to the current user
let obj = global["Marco Dell'Anna"];

for (let i = 0; i < obj.length; i++) {

    //sum of the scores
    sum += obj[i].rating;

    //split the feedbacks count
    //every feedback can be 5,4,3,2 or 1 star
    //this will be used to fill the bars
    counters[obj[i].rating - 1]++;

    //single feedback structure
    data += '<div class="rev">' +
                '<img src="images/user-photo2.png" alt=""> ' +
                '<div>' + obj[i].user + '</div>' +
                '<div>Score: ' + obj[i].rating + '</div>' +
                '<br><div>\"' + obj[i].comment + '\"</div>' +
            '</div>';
    //data will contain all the concatenated reviews
}

//insert all feedbacks into DOM
let rev = document.getElementById("rev-container");
rev.innerHTML = data;
//graphical fix, it's placed here because it's applied only if there are reviews to load
rev.style.paddingRight = "1px";
rev.style.height = "auto";

//compute the average score
let avg = parseFloat((sum / obj.length).toFixed(1));

//set stars width using avg
let stars = document.getElementsByClassName("rating-upper");
stars[0].style.width = ((avg / 5) * 100) + "%";

//insert first box inner paragraph into DOM
let p = document.getElementById("avg");
p.innerText = avg + ' average based on ' + obj.length + ' reviews.';

//fill correctly all first box bars using counters array
for (let i = 0; i < counters.length; i++) {

    //get the bar
    let bar = document.getElementsByClassName("bar-" + (i + 1));
    //fill it with the right percentage width
    bar[0].style.width = Math.round(counters[i] * 100 / obj.length) + "%";
    //write the bar counter on the right of the bar
    let side = document.getElementsByClassName("side right");
    side[counters.length - i - 1].innerText = counters[i].toString();

}

//let's say we have a list of users from who we have already had lectures
let userfeedlist = ["Matteo", "Francesco", "Lucia"];
let feeddata = "<h2>" + "Evaluate your teachers!" + "</h2>";

//we can list them and have an option to give a feedback to each of them
for (let i = 0; i < userfeedlist.length; i++) {

   feeddata += "<div>" +
                    "<table>" +
                        "<tr>" +
                            "<td>" +
                                userfeedlist[i] +
                            "</td>" +
                            "<td></td>" +
                            "<td>" +
                                "<a class= \"feedreq\">Give feedback</a>\n" +
                            "</td>" +
                        "</tr>" +
                    "</table>" +
                "</div>";

}

//insert the data into DOM
let feeddiv = document.getElementById("give-feed");
feeddiv.innerHTML = feeddata;

//get buttons used for giving feedbacks
let buttons = document.getElementsByClassName("feedreq");
for(let i = 0; i < buttons.length; i++){

    //exclude the button inside the modal
    //i want only the ones outside, that are basically an "a" html element, while the one inside is an "input" element
    if(buttons[i].nodeName === "A") {

        //attach toggle event to selected buttons
        buttons[i].addEventListener("click", function () {

            toggle_modalfeed(event);

        });

    }

}

//reuse variables from home.js to toggle the modal
modal = document.querySelectorAll("div")[0];
modal_ctrl = false;

//toggle modal function
function toggle_modalfeed(event){

    //execute only if we are showing the modal, not when hiding it
    if (!modal_ctrl){

        //using linear search to find the name of the user related to the button we pressed
        //there are other ways to do it, like binary search or using indexOf()
        //i decided to keep it simple

        let user = "";
        //take the closest div ancestor of the pressed button
        let closest = event.target.closest("div");

        //find its relative position wrt all the divs in #give-feed
        for(let i = 0; i < userfeedlist.length; i++){

            let possibleclosest = feeddiv.querySelectorAll("div")[i];
            if(possibleclosest === closest){

                //the relative position is used to retrieve the user related to the button
                //that is the user we want to give the feedback to
                user = userfeedlist[i];
                break;

            }

        }

        //set the heading text using the retrieved info
        let heading = document.getElementById("feedform").querySelectorAll("div")[0];
        heading.innerHTML = "Give a feedback to " + user + "!";

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

}