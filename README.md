# Munvo-Summer2019-Internship
This repo contains a sample of the projects and modules I worked on while internship at Munvo as a Full-Stack Software Developer 
during the Summer of 2019. The folks over at Munvo were nice enough to let me keep some of the code for me to refer to
in my future projects #designpatterns. Both projects displayed here were created in the context of the CampaignQA application: 
an entreprise data quality assurance tool. 

*fileimport* 
This is a jar module that tracks a directory defined in a `config.properties` located in the same location as the built jar. 
Any it reads the names of the incoming files and makes a request to the main CampaignQA server, which returns the Hive table 
into which the data file needs to import the file. It then launches a thread to perform the import operation and records it 
in the main CampaignQA metadata database. The properties file is not included in this repo for confidentiality reasons. 

Concepts and Design Patterns applied: 
 - Concurrency: multiple import operations are performed in parallel.
 - Database Connection Pools: the import threads are using a pool of connections to avoid overhead costs involved in opening
   and closing connections.
 - OS/Filesystem operations: using java.nio library to watch for specific file system events.
 - Builder Pattern & Factory Pattern: Used in the creation of REST objects to be sent to CampainQA API and Runnable objects.
 - Singleton Pattern: Used in the creation of Configuration class to access to API URLs, directory information, and more. 
 
*frontend*
Contains projects page of UI of the CampainQA webapp. Update coming soon.  
