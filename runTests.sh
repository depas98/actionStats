#!/bin/sh

javac -d ./bin  -cp ./lib/jackson-core-2.9.7.jar;./lib/jackson-databind-2.9.7.jar;./lib/jackson-annotations-2.9.7.jar;./bin;./lib/junit-4.12.jar;./lib/hamcrest-core-1.3.jar  ./src/com/depas98/assignment/data/Action.java ./src/com/depas98/assignment/data/ActionStat.java ./src/com/depas98/assignment/data/ActionTime.java ./src/com/depas98/assignment/ActionStatsService.java ./test/com/depas98/assignment/ActionStatServiceTest.java 

java -cp ./bin;./lib/junit-4.12.jar;./lib/jackson-core-2.9.7.jar;./lib/jackson-databind-2.9.7.jar;./lib/jackson-annotations-2.9.7.jar;./lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore com.depas98.assignment.ActionStatServiceTest
