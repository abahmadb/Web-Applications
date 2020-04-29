
CREATE DATABASE remytutor;

CREATE TABLE person(
    Name        VARCHAR(50)     NOT NULL,
    Surname     VARCHAR(50)     NOT NULL,
    Gender      CHAR(1)         NOT NULL,
    DoB         DATE            NOT NULL,
    Email       VARCHAR(50)     PRIMARY KEY,
    Passwd      CHAR(41)        NOT NULL,
    Phone       CHAR(12),
    Description TEXT
)engine=InnoDB;


CREATE TABLE feedback (

    TeacherID
    StudentID
    Score
    Description
    PRIMARY KEY(TeacherID, StudentID)
    
)engine=InnoDB;

CREATE TABLE lesson (

    TeacherID
    StudentID
    LessonDate
    LessonTime
    LessonDuration
    
)engine=InnoDB;

CREATE TABLE chat (

    
    
)engine=InnoDB;

CREATE TABLE topic (

    IDTopic INT AUTO_INCREMENT PRIMARY KEY,
    Label VARCHAR(50)
    
)engine=InnoDB;

INSERT INTO topic VALUES (NULL, "3D animation"), (NULL, "Computer networks"), (NULL, "Network security"), (NULL, "Web applications"), (NULL, "Calculus"), (NULL, "Physiscs"), (NULL, "Automation"), (NULL, "Machine Learning"), (NULL, "Operating systems"), (NULL, "Databases");
INSERT INTO topic VALUES (NULL, "Big Data Computing"), (NULL, "Computer Vision"), (NULL, "Distributed Systems"), (NULL, "Photoshop"), (NULL, "C"), (NULL, "C++"), (NULL, "Java"), (NULL, "JavaScript"), (NULL, "Python"), (NULL, "PHP");
INSERT INTO topic VALUES (NULL, "JSP"), (NULL, "Ruby"), (NULL, "Perl"), (NULL, "CSS"), (NULL, "HTML"), (NULL, "C#"), (NULL, "Arduino"), (NULL, "WordPress"), (NULL, "MySQL"), (NULL, "PostgreSQL");

CREATE TABLE (

)engine=InnoDB;

