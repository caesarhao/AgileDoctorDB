# AgileDoctorDB
AgileDoctor based on Relational Database and Java.

## Installation
### Installation of Derby Database.
Goto  http://db.apache.org/derby/derby_downloads.html and download *bin distribution*.
Extract the downloaded package.
Set environment variable *DERBY_HOME* to the Derby directory.
Put derby\*/bin in environment variable *PATH*.

## Start Derby Database
Open a new cmd and execute command *startNetworkServer*.

## Folder definition:
* *src* : java source code here.
* *model* : class and property (TBox) saved here.
* *individual* : individual information (ABox) saved here.
* *LibsProtege* : *.jar copied from Protege, imported in project.

## Use Protege to generate Java code:
1. *Output folder* : **AgileDoctor/src**
2. *Java package* : **model**
3. *Factory class name* : **MyFactory**

## Package definition:
* *model* : Protege generating model code, can't be modified manually.
* *test* : write any test functions here during the development.
* *view* : gui design.
* *control* : logic code, the main function is defined here.
