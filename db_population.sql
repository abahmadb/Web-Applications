-- PERSON

INSERT INTO 
    person(Name, Surname, Gender, DoB, Email, Passwd, Phone, Description) 
VALUES 
    ("Xianwen", "Jin", "M", "1995-02-28", "jinxw@live.it", SHA2("Jinxw95"), "3333333336", "Fuck the world"), 
    ("Marco", "Grande", "M", "1996-02-10", "guess@live.it", SHA2("WantPass123"), "345678910", "don't fuck the world"),
    ("Marco", "Piccolo", "M", "1995-11-28", "marco@live.it", SHA2("Guseee123"), "3333332222", "Love the world"), 
    ("Victor", "Semencenco", "M", "1993-04-20", "sexy@live.it", SHA2("Gondus123"), "345678911", "Give a girl"),
    ("Ahmad", "TooLong", NULL, "1995-01-30", "dontknow@live.it", SHA2("Lol16153"), "3333333336", NULL), 
    ("Marco", "Grande", "F", NULL, "whoknows@live.it", SHA2("WantPass123"), NULL, NULL);



-- TOPIC
INSERT INTO topic VALUES (NULL, "3D animation"), (NULL, "Computer networks"), (NULL, "Network security"), (NULL, "Web applications"), (NULL, "Calculus"), (NULL, "Physiscs"), (NULL, "Automation"), (NULL, "Machine Learning"), (NULL, "Operating systems"), (NULL, "Databases");
INSERT INTO topic VALUES (NULL, "Big Data Computing"), (NULL, "Computer Vision"), (NULL, "Distributed Systems"), (NULL, "Photoshop"), (NULL, "C"), (NULL, "C++"), (NULL, "Java"), (NULL, "JavaScript"), (NULL, "Python"), (NULL, "PHP");
INSERT INTO topic VALUES (NULL, "JSP"), (NULL, "Ruby"), (NULL, "Perl"), (NULL, "CSS"), (NULL, "HTML"), (NULL, "C#"), (NULL, "Arduino"), (NULL, "WordPress"), (NULL, "MySQL"), (NULL, "PostgreSQL");