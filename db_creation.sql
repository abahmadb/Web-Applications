
CREATE DATABASE remytutor;

use remytutor;

CREATE TABLE person (
    IDUser      INT             AUTO_INCREMENT PRIMARY KEY,
    Name        VARCHAR(50)     NOT NULL,
    Surname     VARCHAR(50)     NOT NULL,
    Gender      CHAR(1),
    DoB         DATE,
    Email       VARCHAR(50)     UNIQUE NOT NULL,
    Passwd      CHAR(41)        NOT NULL,
    Phone       CHAR(12),
    Description TEXT
)engine=InnoDB;


CREATE TABLE feedback (
    TeacherID       INT,
    StudentID       INT,
    Score           INT     NOT NULL,
    Description     TEXT    NOT NULL,
    ReviewDate      DATE    NOT NULL,
    PRIMARY KEY(TeacherID, StudentID),
    FOREIGN KEY (TeacherID) REFERENCES person(IDUser),
    FOREIGN KEY (StudentID) REFERENCES person(IDUser),
    CONSTRAINT Score CHECK (Score BETWEEN 1 AND 5)
)engine=InnoDB;

CREATE TABLE lesson (
    IDLesson            INT     AUTO_INCREMENT PRIMARY KEY,
    TeacherID           INT,
    StudentID           INT,
    LessonDate          DATE    NOT NULL,
    LessonTime          TIME    NOT NULL,
    LessonDuration      TIME    NOT NULL,
    LessonTariff        INT     NOT NULL,
    Payment             DATETIME,
    FOREIGN KEY (TeacherID) REFERENCES person(IDUser),
    FOREIGN KEY (StudentID) REFERENCES person(IDUser)
)engine=InnoDB;

CREATE TABLE chat (
    TeacherID           INT,
    StudentID           INT,
    Confirmed           BOOLEAN     NOT NULL,
    Messages            JSON        NOT NULL,
    LastMessage         DATETIME,
    PRIMARY KEY(TeacherID, StudentID),
    FOREIGN KEY (TeacherID) REFERENCES person(IDUser),
    FOREIGN KEY (StudentID) REFERENCES person(IDUser)
)engine=InnoDB;


-- TOPICS: YOGA, MATH, ....

CREATE TABLE topic (

    IDTopic INT AUTO_INCREMENT PRIMARY KEY,
    Label VARCHAR(50)   NOT NULL
    
)engine=InnoDB;

INSERT INTO topic VALUES (NULL, "3D animation"), (NULL, "Computer networks"), (NULL, "Network security"), (NULL, "Web applications"), (NULL, "Calculus"), (NULL, "Physiscs"), (NULL, "Automation"), (NULL, "Machine Learning"), (NULL, "Operating systems"), (NULL, "Databases");
INSERT INTO topic VALUES (NULL, "Big Data Computing"), (NULL, "Computer Vision"), (NULL, "Distributed Systems"), (NULL, "Photoshop"), (NULL, "C"), (NULL, "C++"), (NULL, "Java"), (NULL, "JavaScript"), (NULL, "Python"), (NULL, "PHP");
INSERT INTO topic VALUES (NULL, "JSP"), (NULL, "Ruby"), (NULL, "Perl"), (NULL, "CSS"), (NULL, "HTML"), (NULL, "C#"), (NULL, "Arduino"), (NULL, "WordPress"), (NULL, "MySQL"), (NULL, "PostgreSQL");

-- CREATES CORRESPONDENCE BETWEEN TEACHER AND TOPICS HE WISHES TO TEACH, AKA IN WHICH SEARCHING TOPICS SHOULD HE APPEAR?

CREATE TABLE teacher_topic(
    TeacherID           INT,
    TopicID             INT,
    FOREIGN KEY (TeacherID) REFERENCES person(IDUser),
    FOREIGN KEY (TopicID) REFERENCES topic(IDTopic)
)engine=InnoDB;

