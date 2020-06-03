# EDUCATION-WA1920

                                 University of Padova
                 Master Degree in ICT for Internet and Multimedia 
                       Master Degree in Computer Engineering
                                 Web Applications 
                                      2019/2020


This directory contains the source code of the project developed for **RemyTutor**, a remote teaching website.

## Prerequisites:

- [Git](https://git-scm.com/) -  Version Control System
- [JDK 8](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html) - Java Development Kit
- [Maven](https://maven.apache.org/) -  Software project management and comprehension tool
- [MySQL](https://www.mysql.com/) - Relational Database Management System
- [Apache Tomcat](http://tomcat.apache.org/) - Java Servlet, JavaServer Pages, Java Expression Language and Java WebSocket technologies

## Usage

Simply clone this repo with Git and compile with Maven:

	mvn clean package
	
This will generate a 'target' folder with a WAR package named education.X.X.X.war, where X.X.X is the version of the project.

To create and populate the MySQL database simply take the sql scripts in src/main/database and *source* them in a MySQL console:

    mysql>source path/to/sql/db_creation.sql
    ..
    mysql>source path/to/sql/db_population.sql

We also require to place the 'imageset' folder present in this repo to be placed inside the $CATALINA_HOME$\webapps folder
(e.g. in Windows C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps).
This is required because images are not stored in the database, but in this case when a redeploy occurs all images changes will be lost;
storing them in a path relative to the Tomcat folder solves redeploy problems. 

If you are experiencing problems on image loading, be sure to set the right Tomcat CATALINA_HOME environment variable before starting the server, 
especially if you use MacOS/Unix systems; using the terminal you would do:

	export CATALINA_HOME = path/to/tomcat

The WAR package can then be deployed in Tomcat and the website should be now accessible and usable from the Tomcat Manager.
Testing the website is fairly easy, you can create an account or use credentials from the already default populated 
database (credentials in clear text are of course outside of the database, in db_population.sql)

## Features

See HW1-RemyTutor-2019-2020.pdf and HW1-RemyTutor-2019-2020.ppt for a detailed description of this project

## Contributors:
Students who made this Project possible

* [Marco Dell'Anna](https://bitbucket.org/%7Bd8df9b14-ec57-443e-ba56-802d4f5483c9%7D/) - Computer Engineering
* [Marco Dalla Mutta](https://bitbucket.org/%7Bf5e243b3-d5ce-4baf-aa57-1f6a7bd87650%7D/) - Computer Engineering
* [Jin Xianwen](https://bitbucket.org/%7Bdd398f8c-f491-4e3f-ba61-0ebed3bb2845%7D/) - Computer Engineering
* [Semencenco Victor](https://bitbucket.org/%7Bdd4c42d8-0e76-4c93-adda-11149e82ee40%7D/) - Computer Engineering
* [Memen Salihi](https://bitbucket.org/account/user/%7B618b397f-36e0-4f88-b39c-454c3a297ab6%7D/) - ICT for Internet & Multimedia
* [Ahmad Bashir Usman](https://bitbucket.org/%7Bd6a6bf8a-962e-4161-ab75-07c273cb7b16%7D/) - ICT for Internet & Multimedia
 
## License:

 * This project is released under the MIT License.
 * Further information can be found in the [LICENSE](LICENSE.md) file.


## Acknowledgments:

We extend our appreciation to Professor Nicola Ferro for his invaluable insights throughout our project.








