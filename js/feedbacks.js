let sum = 0;
let data = "";
let counters = [0, 0, 0, 0, 0];

//parse json data
//as of now, the structure of the json is an array of objects assigned to every user with keys 'user', 'rate' and 'comment'
let global = JSON.parse('{ '+
	'"Marco Dell\'Anna":[{"user":"Marco Dalla Mutta","rate":4.3,"comment":"Very good"},' +
						'{"user":"Xianwen Jin","rate":4.7,"comment":"Excellent"},' +
						'{"user":"Victor Semencenco","rate":5.0,"comment":"Excellent"}], ' +
    '"Marco Dalla Mutta":[{"user":"Marco Dell\'Anna","rate":4.3,"comment":"Very good"},' +
						'{"user":"Xianwen Jin","rate":4.7,"comment":"Excellent"},' +
						'{"user":"Victor Semencenco","rate":5.0,"comment":"Excellent"}]}');

//select only a group of feedbacks, relevant to the current user
let obj = global["Marco Dell'Anna"];

for (let i = 0; i < obj.length; i++) {

    //sum of the scores
    sum += obj[i].rate;

    //split the feedbacks count
    //every feedback can be 5,4,3,2 or 1 star
    //this will be used to fill the bars
    if (obj[i].rate < 1)
        counters[Math.ceil(obj[i].rate) - 1]++;
    else
        counters[Math.floor(obj[i].rate) - 1]++;

    //single feedback structure
    data += '<div class="rev">' +
                '<img src="images/user-photo2.png" alt=""> ' +
                '<span>' + obj[i].user + '</span>' +
                '<div>' + obj[i].rate.toFixed(1) + '</div>' +
                '<br>' +
                '<div>' + obj[i].comment + '</div>' +
            '</div>';
    //data will contain all the concatenated reviews
}

//insert all feedbacks into DOM
let rev = document.getElementById("rev-container");
rev.innerHTML = data;
rev.prepend(rev.childNodes[0]);
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
p.prepend(p.childNodes[0]);

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
