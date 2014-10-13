<h1>Diva-WebServices</h1>
This is a basic implementation of the Diva-WebServices, developed in the Diva group at University of Fribourg (http://diuf.unifr.ch/main/diva/)

<h2>Install Guide Windows:</h2>

1. Check out project from github
2. Let Maven download all dependencies (can take a minute or two)
3. Download and Install Tomcat 7 (I prefer the zip variation)
4. Configure Tomcat in your IDE
	- For IntelliJ (you need Ultimate version) see: http://www.jetbrains.com/idea/webhelp/defining-application-servers-in-intellij-idea.html
	- For Eclipse see: http://www.mulesoft.com/tcat/tomcat-eclipse


FOR INTELLIJ:

4. Install the Tomcat and the RESTClient plugin

5. Open Module Settings (F4)
	- Add the "Web Module Deployment Descritpor" (In "Deployment Descriptors " click the "+" and then select the web.xml)
	- Go to "Artifacts"
		 - Click "+" -> "WebApplication Exploded" -> "From Modules" and Select the Project
		 - In "Output Layout" -> Click "+" -> "Directory Content" and Select the "resources" folder

 6. Open "Run" -> "Edit Configurations" -> "+" -> "Tomcat Server" -> "local"
 	- Select "Deployment" -> "+" -> "Artifact" and Select the "*:war exploded"
 	- Hit "Apply" -> "OK"

 7. Hit Run
 	 - Your webbrowser should now open http://localhost:8080 and show a "Jersey" sample page

 8. Select "Tools" -> "Test RESTful WebService"
 	- Generate a GET request to: http://localhost:8080/webapi/segmentation
 	- You should receive a JSON response with some information

 9. You can start implementing your own webservice now

<h2>Add a new method WebService</h2>
For adding a new method to the WebService a few steps need to be performed:

<h3>1. Implement Webservice</h3>
The first step is to add your own class / or add your method to an existing class.
For an example you can have a look at the SampleService.java and SampleBean.java and expand from there.

<h3>2. Let your method be load</h3>
If your class is not already loaded in the DivaWebservices.java please add it so that Tomcat will load it on startup.

