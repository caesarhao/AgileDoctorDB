# AgileDoctorDB
AgileDoctor based on Relational Database and Java.

## Installation
<del>### Installation of Derby Database.
Goto  http://db.apache.org/derby/derby_downloads.html and download *bin distribution*.
Extract the downloaded package.
Set environment variable *DERBY_HOME* to the Derby directory.
Put derby\*/bin in environment variable *PATH*.</del>

<del>## Start Derby Database
Open a new *cmd*, use *cd* command to change the current path into *CurrentProjectPath/db/* 
Execute command *startNetworkServer*.</del>

## Folder definition:
* *src* : java source code here.
* *dbs.db*: sqlite database.
* *sqlite* : sqlite-jdbc jar file.
* *eclipselink* : jpa interface implemented with eclipselink.
* *doc* : documentation for the current project.

## Generate tables in the sqlite Database:
1. Right-click the project folder, select *JPA Tools > Generate Tables from Entities* to generate tables, output mode (Database or Both)

## Package definition:
* *model* : Concepts/Classes, corresponding Tables in database.
* *jpa* : All manipulations with the database, CRUD.
* *view* : gui design.
* *control* : logic code, the main function is defined here.

## Bug logging:
* A bug in EclipseLink: 2 OneToMany with same element type are mapped to a same relation table with a_b_b. This bug is corrected by introducing 2 new sub classes.

