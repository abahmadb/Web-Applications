-- PERSON

INSERT INTO 
    person(Name, Surname, Gender, DoB, Email, Passwd, Phone, Description) 
VALUES 
    ("Xianwen", "Jin", "M", "1995-02-28", "jinxw@live.it", SHA2("Jinxw95",256), "3333333336", "Fuck the world"), 
    ("Marco", "Grande", "M", "1996-02-10", "guess@live.it", SHA2("WantPass123",256), "345678910", "don't fuck the world"),
    ("Marco", "Piccolo", "M", "1995-11-28", "marco@live.it", SHA2("Guseee123",256), "3333332222", "Love the world"), 
    ("Victor", "Semencenco", "M", "1993-04-20", "sexy@live.it", SHA2("Gondus123",256), "345678911", "Give a girl"),
    ("Ahmad", "TooLong", NULL, "1995-01-30", "dontknow@live.it", SHA2("Lol16153",256), "3333333336", NULL), 
    ("Marco", "Grande", "F", NULL, "whoknows@live.it", SHA2("WantPass123",256), NULL, NULL);


-- TOPIC
INSERT INTO topic VALUES (NULL, "3D animation"), (NULL, "Computer networks"), (NULL, "Network security"), (NULL, "Web applications"), (NULL, "Calculus"), (NULL, "Physiscs"), (NULL, "Automation"), (NULL, "Machine Learning"), (NULL, "Operating systems"), (NULL, "Databases");
INSERT INTO topic VALUES (NULL, "Big Data Computing"), (NULL, "Computer Vision"), (NULL, "Distributed Systems"), (NULL, "Photoshop"), (NULL, "C"), (NULL, "C++"), (NULL, "Java"), (NULL, "JavaScript"), (NULL, "Python"), (NULL, "PHP");
INSERT INTO topic VALUES (NULL, "JSP"), (NULL, "Ruby"), (NULL, "Perl"), (NULL, "CSS"), (NULL, "HTML"), (NULL, "C#"), (NULL, "Arduino"), (NULL, "WordPress"), (NULL, "MySQL"), (NULL, "PostgreSQL");



-- LESSON
INSERT INTO
    lesson(TeacherID, StudentID, LessonDate, LessonTime, LessonDuration, LessonTariff, Payment)
VALUES
    (202, 1237119, '2020-05-15', '08:30', '2:00', 30, '2020-05-13 23:59:59'),
    (505, 1210174, '2020-05-30', '12:00', '1:00', 20, '2020-05-28 23:59:59'),
    (101, 1205721, '2020-05-15', '08:30', '2:00', 40, '2020-05-13 23:59:59'),
    (303, 1216114, '2020-06-02', '11:00', '0:30', 10, '2020-05-30 23:59:59'),
    (808, 1190245, '2020-06-12', '08:30', '2:00', 30, '2020-06-10 23:59:59'),
    (404, 1205378, '2020-06-20', '14:00', '2:30', 50, '2020-06-18 23:59:59');
	
	
INSERT INTO
	feedback
VALUES
	(2, 3, 4, "Very good", '2020-05-13 23:59:59'),
	(2, 1, 4, "Excellent", '2020-05-13 23:59:59'),
	(2, 4, 5, "Excellent", '2020-05-13 23:59:59');	
	