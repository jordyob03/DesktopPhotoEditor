# Desktop Photo Editor 

Desktop application capable of making changes to saved images such as cropping, adding filters, modifying the lighting and drawing

# Requirements 

Java Development Kit (JDK) 20 or later
Apache Maven 
IntelliJ IDEA 



# How to Run the Application 

## Using IntelliJ


To install follow instructions [here](https://www.jetbrains.com/help/idea/installation-guide.html#requirements)
Maven will come bundled with IntelliJ 


Open the IntelliJ application and click "Get from VCS"
Enter https://github.com/jordyob03/DesktopPhotoEditor.git for the URL 
Hit clone


Go to settings and set JDK to version 20 or higher 


Use the run button to run the program, a new window should open featuring the application 



## Using Maven 

Clone the repository into the directory of your choice
..bash
git clone https://github.com/jordyob03/DesktopPhotoEditor.git
..


Build the application 
..bash 
mvn clean package
..


Run the application 
..bash
java -jar target/PhotoEditorFX-1.0-SNAPSHOT.jar
..

