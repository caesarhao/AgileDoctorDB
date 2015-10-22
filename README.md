# AgileDoctorDB
AgileDoctor based on Relational Database and Java.

## Installation
### Installation of Derby Database.
Goto  http://db.apache.org/derby/derby_downloads.html and download *bin distribution*.
Extract the downloaded package.
Set environment variable *DERBY_HOME* to the Derby directory.
Put derby\*/bin in environment variable *PATH*.

## Start Derby Database
Open a new *cmd*, use *cd* command to change the current path into *CurrentProjectPath/db/* 
Execute command *startNetworkServer*.

## Folder definition:
* *src* : java source code here.
* *db*: database directory.
* *derby* : derby client jar file.
* *eclipselink* : jpa interface implemented with eclipselink.
* *doc* : documentation for the current project.

## Generate tables in the derby Database:
1. Make sure the derby server has been started.
2. Right-click the project folder, select *JPA Tools > Generate Tables from Entities* to generate tables, output mode (Database or Both)

## Package definition:
* *model* : Concepts/Classes, corresponding Tables in database.
* *view* : gui design.
* *control* : logic code, the main function is defined here.
