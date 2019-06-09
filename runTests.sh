#!/bin/sh

echo off
javac -d ./bin -cp ./bin;./lib/junit-4.12.jar:./lib/hamcrest-core-1.3.jar ./src/com/depas98/security/SecurityMonitor.java ./src/com/depas98/security/SecurityMonitorService.java ./src/com/depas98/security/SecurityMonitorServiceImpl.java ./src/com/depas98/security/SecurityFileReaderTask.java  ./src/com/depas98/security/SecurityStatsWriterTask.java  ./src/com/depas98/security/SecurityType.java ./test/com/depas98/security/SecurityMonitorServiceTest.java 

java -cp ./bin;./lib/junit-4.12.jar:./lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore com.depas98.security.SecurityMonitorServiceTest
