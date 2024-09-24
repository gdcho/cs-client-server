1. Install Tomcat following the instructions in TomcatInstallationInstructions.txt.  The midp project referred in the attachments is attached as midp.zip.  Tomcat is a lightweight web server that allows you to develop web applications using Java.  You will find that developing web apps using Tomcat and Java Servlets is very similar to node.js or PHP. 

2. Modify the HitServlet.java of the midp web app by replacing the message variable as follows; recompile the servlet (as per the above instructions), shutdown and startup tomcat and access the servlet again from the browser.

String message = "Hits: " + ++mCount + " :" + request.getHeader("User-Agent");

3. Try out the attached DirServlet.java for convenience, that lists contents of the folder specified by adding the path parameter to the URL as follows: 

?path=windows

check out the web.xml file in midp/WEB-INF folder.

4. Self study the following topics from the indicated resources:

HTML, JavaScript, AJAX
https://www.w3schools.com/html/

https://www.w3schools.com/js/

https://www.w3schools.com/xml/ajax_examples.asp

Get, Post and Session Variables with Tomcat/Servlet
http://www.tutorialspoint.com/servlets/servlets-form-data.htm

http://www.tutorialspoint.com/servlets/servlets-session-tracking.htm

 

5. Install Oracle server or Express Edition on your laptops, or any other relational database such as SQL Server or MySQL would be fine too..  An older version Oracle11gXE which is fairly easy to install and less probalamatic is in comp3940 shareout folder.  You can copy and install.

 

6.  Run SQL Command Line of your Oracle installation by going into apps on your windows computer.

type "connect" on the sql prompt.

enter userid and password when prompted. 

type the following sql command to create a test table.


create table staff(name char(10), address char(10));

commit;


Try out testJDBC and JDBC_Insert java programs as follows (assuming all files including the jar file, are in the c:\temp folder):

 

javac  -classpath .;c:\temp\ojdbc6.jar JDBC_Insert.java

java -classpath .;c:\temp\ojdbc6.jar JDBC_Insert

 

The above code assumes Oracle 11g XE with "system" and "oracle1" as the userid and password of the oracle account.  The ojdbc6.jar is attached.

If you are using Oracle 18c XE then just replace ojbc6.jar with ojdbc8.jar in the above commands when compiling and running the code.  ojdbc8.jar is also attached.  

 

7. Self study the following from the indicated online resources.

 

SQL, Oracle, JDBC and integration with Tomcat/Servlet

https://www.w3schools.com/sql/

https://www.tutorialspoint.com/sql

https://docs.oracle.com/javase/tutorial/jdbc/index.html

8.  You will be working on most of the take home assignments in small teams.  Your 1st assignment would require to follow software engineering methodologies and tools.  In particular a CI pipeline built using Git and Github Actions (or Jenkins etc), that builds the software when appropriate changes to your source code repository e.g. Git take place, and runs UI testing tool e.g. Selenium and unit testing tools such as JUnit.  Study the following blogs and as team try to set up the CI pipeline.

https://faun.pub/continuous-integration-of-java-project-with-github-actions-7a8a0e8246ef

https://www.lambdatest.com/blog/selenium-github-actions-example/