echo off
javac -d ./bin -cp ./lib/jackson-core-2.9.7.jar;./lib/jackson-databind-2.9.7.jar;./lib/jackson-annotations-2.9.7.jar ./src/com/depas98/assignment/data/Action.java ./src/com/depas98/assignment/data/ActionStat.java  ./src/com/depas98/assignment/data/ActionTime.java ./src/com/depas98/assignment/ActionStatsService.java

java -cp ./bin;./lib/jackson-core-2.9.7.jar;./lib/jackson-databind-2.9.7.jar;./lib/jackson-annotations-2.9.7.jar com.depas98.assignment.ActionStatsService