# cs-client-server

## Setting up tomcat
1.  Install JDK 11. 
    Download appache tomcat 10 e.g. go to Apache Tomcat homepage and download 64-bit Windows zip file for tomcat 10.
    Unzip the folder and perhaps rename the unzipped folder to something smaller such as just "tomcat".
    Move the tomcat folder to some suitable location on your laptop.  In the following it is assumed that it is moved to the root "C" drive  

2.  In the file c:\tomcat\conf\server.xml replace 
<Server port="8005" shutdown="SHUTDOWN"> with <Server port="8006" shutdown="SHUTDOWN">, and 
<Connector port="8080" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" /> with
<Connector port="8081" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" />.

3.  set the following environment variables
CATALINA_HOME C:\tomcat
JAVA_HOME to C:\Program Files\Java\jdk-11 (assuming this where your jdk was installed)


4.  unzip midp.zip amd copy the folder midp into C:\tomcat\webapps

5. start a windows command prompt from "Windows System" (where you found Control Panel previously)
cd c:\tomcat\bin and then type startup.bat
Allow network connection if such permissions window show up.
If everything was configured properly, a new command window will be launched that will display log messages,
 none of these should be an error or a warning.

6.  Open Internet Explorer and type http://localhost:8081
You should see a tomcat manager web page.  

type http://localhost:8081/midp/hits and you should see the following on the web page.
Hits: 1
refreshing the page will increment the count.

7. type shutdown.bat on the window where you typed startup.bat


You have basically configured apache tomcat and deployed a simple web app that counts number of hits.
Check out C:\tomcat\webapps\midp\WEB-INF\web.xml and C:\tomcat\webapps\midp\WEB-INF\classes\HitServlet.java files.

Although HitServlet.class file already exists.  You can compile the java file to create the class file as follows:
on a windows command prompt type the following
cd C:\tomcat\webapps\midp\WEB-INF\classes
javac -classpath .;c:\tomcat\lib\servlet-api.jar HitServlet.java