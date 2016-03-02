# AgileDoctorDB

[![Code Climate](https://codeclimate.com/github/caesarhao/AgileDoctorDB/badges/gpa.svg)](https://codeclimate.com/github/caesarhao/AgileDoctorDB)
[![Test Coverage](https://codeclimate.com/github/caesarhao/AgileDoctorDB/badges/coverage.svg)](https://codeclimate.com/github/caesarhao/AgileDoctorDB/coverage)
[![Issue Count](https://codeclimate.com/github/caesarhao/AgileDoctorDB/badges/issue_count.svg)](https://codeclimate.com/github/caesarhao/AgileDoctorDB)
[![Build Status](https://travis-ci.org/caesarhao/AgileDoctorDB.svg?branch=master)](https://travis-ci.org/caesarhao/AgileDoctorDB)

AgileDoctor based on Relational Database and Java.

## Requirement
* JDK >= 1.8.0

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
* *engine* : game engine implementation.

## Bug logging:
* A bug in EclipseLink: 2 OneToMany with same element type are mapped to a same relation table with a_b_b. This bug is corrected by introducing 2 new sub classes.

##Update:
Major modifications:
<del>* 1:Add session state conditions. Now the engine can decide which session to enter
* 2:Add default abnormal states phrases (by adding a new construction function in PatientPhrase)
* 3:Running: If Session State is not Normal, which means patient's phrase primitif type is not "AnswerWithInfo", the corresponding AInformation can't be got.
Other modifications
* 1 APhrase add primitive type: Questioning
* 2 model: PatientPhrase add findByType  but not implementing (the idea is to add query which can get patient's phrases by primitive type</del>
## TODO:
* <del>information query method should be changed.</del>
* Debug system parameter and algorithm.
* add ms3 and test all
* implement engine.DChar.calcFromDoctorPhrase
* implementengine.DChar.calcDropFlag
* implementengine.PActChar.calcFromPatientPhrase

## Comments for essay:
* A new state "END" has been added into DialogueSession state.

## Hints for JPA Inheritance:
* https://en.wikibooks.org/wiki/Java_Persistence/Inheritance
There are 4 different types of inheritance in the persistance of Object -> Table.
1. Superclass just defines the common properties, superclass won't be persisted. Put an annotation * @MappedSuperclass * on the superclass.
2. Superclass and childrenclass are created in a single table. Put an annotation * @Inheritance(strategy=InheritanceType.SINGLE_TABLE) * on the superclass.
3. Different tables are created for Superclass and childrenclass. Put an annotation * @Inheritance(strategy=InheritanceType.TABLE_PER_CLASS) * on the superclass.
4. One table is created for the common data and additional tables are created childrenclass. Put an annotation * @Inheritance(strategy=InheritanceType.JOINED) * on the superclass.
