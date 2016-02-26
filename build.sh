#!/usr/bin/bash
mkdir -p build/classes/
javac -classpath .:eclipselink/eclipselink.jar:eclipselink/javax.persistence.jar:sqlite/sqlite-jdbc-3.8.11.2.jar -d build/classes -sourcepath src/ src/*/*.java

