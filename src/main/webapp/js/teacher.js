//fixing avg to 1 decimal 
avg = parseFloat(parseFloat(avg).toFixed(1));

//trasform avg to percentage
avg = avg * 20;
//set stars width according to the teacher score
document.getElementById("teacher_fullstar_style").setAttribute("style", "width: " + avg + "%");
//trasform student_score to percentage
//student_score = student_score * 20;
//document.getElementById("student_fullstar_style").setAttribute("style", "width: " + student_score + "%");
