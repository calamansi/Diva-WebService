
************************* Text line extraction using Gabor filters ******************

First of all, I assume that your system is 64-bit and IDE is Eclipse.
Because we will integrate Matlab into our server side, we need to do some configurations.

1. Windows

1.1 Install MATLAB Compiler Runtime (MCR)

Download it from:
http://www.mathworks.com/products/compiler/mcr/
Choose R2013b, and download the proper software according to your system. Default installation directory is recommended. Then you need to set environment variable PATH. See the MCR Installer documentation below the table for more information. 

1.2 Add jar files to the Eclipse

In the folder jars, four jar files should be added. They are: ij.jar, ijblob-1.3.jar, GaborClustering.jar and javabuilder.jar. The last two jars are in the subfolders according to your system.

In Project Explorer, right click the project name -> Build Path -> configure Build Path..., choose Libraries tab -> Add External JARs, then select the four jars.

We also need to add the jars to the webapp. In Project Explorer, select Deployed Resources -> webapp -> WEB-INF -> lib. If you don't see the lib subfolder, manually create one. Then copy and paste the four jars into the lib subfolder.

1.3 Set the working directory for Tomcat

In Project Explorer, right click the project name -> Run as -> Run Configurations. In the left, choose Tomcat vX.X Server at localhost. In the right, choose Arguments tab -> Working directory: -> Other, set it as the root directory of the project. Then click Apply.


2. Linux

2.1 Install MATLAB Compiler Runtime (MCR)

Download it from:
http://www.mathworks.com/products/compiler/mcr/
Choose R2013b, and download the proper software according to your system. Default installation directory is recommended. 

Then we need to set the environment variables for Tomcat. In Project Explorer, right click the project name -> Run as -> Run Configurations. In the left, choose Tomcat vX.X Server at localhost. In the right, choose Environment tab -> New... Set the following two variables and then click Apply. 

Name:
LD_LIBRARY_PATH
Value:
/usr/local/MATLAB/MATLAB_Compiler_Runtime/v82/runtime/glnxa64:/usr/local/MATLAB/MATLAB_Compiler_Runtime/v82/bin/glnxa64:/usr/local/MATLAB/MATLAB_Compiler_Runtime/v82/sys/os/glnxa64

Name:
XAPPLRESDIR
Value:
/usr/local/MATLAB/MATLAB_Compiler_Runtime/v82/X11/app-defaults

2.2 Add jar files to the Eclipse

In the folder jars, four jar files should be added. They are: ij.jar, ijblob-1.3.jar, GaborClustering.jar and javabuilder.jar. The last two jars are in the subfolders according to your system.

In Project Explorer, right click the project name -> Build Path -> configure Build Path..., choose Libraries tab -> Add External JARs, then select the four jars.

We also need to add the jars to the webapp. In Project Explorer, select Deployed Resources -> webapp -> WEB-INF -> lib. If you don't see the lib subfolder, manually create one. Then copy and paste the four jars into the lib subfolder.

2.3 Set the working directory for Tomcat

In Project Explorer, right click the project name -> Run as -> Run Configurations. In the left, choose Tomcat vX.X Server at localhost. In the right, choose Arguments tab -> Working directory: -> Other, set it as the root directory of the project. Then click Apply.

