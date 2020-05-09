//fixing avg to 1 decimal 
avg = parseFloat(parseFloat(avg).toFixed(1));

//trasform avg to percentage
avg = avg * 20;
//set stars width according to the teacher score
document.getElementById("teacher_fullstar_style").setAttribute("style", "width: " + avg + "%");
//let stars = document.getElementById("teacher_star_style");
//stars.style.width = avg + "%";
