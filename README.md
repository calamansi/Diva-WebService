<h1>Diva-WebServices</h1>
This is a basic implementation of the Diva-WebServices, developed in the Diva group at University of Fribourg (http://diuf.unifr.ch/main/diva/)

<h2>Add a new method WebService</h2>
For adding a new method to the WebService a few steps need to be performed:

<h3>1. Implement Webservice</h3>
The first step is to add your own class / or add your method to an existing class.
For an example you can have a look at the SampleService.java and SampleBean.java and expand from there.

<h3>2. Let your method be load</h3>
If your class is not already loaded in the DivaWebservices.java please add it so that Tomcat will load it on startup.

