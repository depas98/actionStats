# ActionStatsService
The ActionStatsService has two functions one is to add Actions items and the other is to return stats on those items.:

### Add Action
	 
   addAction(String) returning error

This function accepts a JSON serialized string with the following format and will store each entry:
			
			{"action":"jump", "time":100}
			{"action":"run", "time":75}
			{"action":"jump", "time":200}

### Statistics
	
  getStats() return string
	
	Returns the a JSON string for the average tie for each action, with the following format:
			
			[{"action":"jump", "avg":10.0}, {"action":"run", "avg":15.0}]

#### The program makes the following assumptions:
	1. Action can only be "jump" or "run" and these values are case sensitive
	2. Time is an integer value
	3. End User can make concurrent calls
	4. It is assumed the action entries are not very large (i.e. < 10,000) so the 
	   service will store all the entries and calculate the average when getStats is 
	   called (Note: If this was not the case then the program would have not stored 
	   the individual times and would have only stored a running average and an entry 
	   count to better calculate the average)
    
### Instructions For Running Program

### Setup
Install Open JDK 11.x, downloads can be found here
https://jdk.java.net/archive/

Make sure java is in your environment PATH 
#### Linux
	export JAVA_HOME="path where java is installed"
	export PATH=$JAVA_HOME/bin:$PATH

#### Windows
See the following:
https://javatutorial.net/set-java-home-windows-10

#### Testing Java Install
You should be able to run  both of these from the command line:
	
	java -version
	javac -version

Output should be something like the following:

	java -version
	openjdk version "11.0.2" 2019-01-15
	OpenJDK Runtime Environment 18.9 (build 11.0.2+9)
	OpenJDK 64-Bit Server VM 18.9 (build 11.0.2+9, mixed mode)

	javac -version
	javac 11.0.2

### Running
#### Service Main Method
You can test the service by running the run.sh or run.bat scripts, this will input four action items concurrently and then prints out the stats.  Output will be similar to the following:

	Adding action: {"action":"run", "time":150}
	Adding action: {"action":"jump", "time":100}
	Adding action: {"action":"run", "time":200}
	Adding action: {"action":"jump", "time":150}
	
	Stats for added actions
	[{"action":"run","avg":175.0},{"action":"jump","avg":125.0}]

#### Unit Test
You can run all the unit test for this service, output will be like the following:

	JUnit version 4.12
	............
	Time: 0.264
	
	OK (12 tests)


#### Linux
In the "actionStats" directory

Give the script files exec permission
chmod 755 *.sh

Running Main Program execute the script below:
./run.sh
	
or you can execute the following individually
	to compile 
	javac -d ./bin -cp ./lib/jackson-core-2.9.7.jar:./lib/jackson-databind-2.9.7.jar:./lib/jackson-annotations-2.9.7.jar ./src/com/depas98/assignment/data/Action.java ./src/com/depas98/assignment/data/ActionStat.java ./src/com/depas98/assignment/data/ActionTime.java ./src/com/depas98/assignment/ActionStatsService.java
	
	to run
	java -cp ./bin:./lib/jackson-core-2.9.7.jar:./lib/jackson-databind-2.9.7.jar:./lib/jackson-annotations-2.9.7.jar com.depas98.assignment.ActionStatsService

Running Unit Tests execute the script below
	./runTests.sh

or you can execute the following individually
	to compile 
	javac -d ./bin -cp ./lib/jackson-core-2.9.7.jar:./lib/jackson-databind-2.9.7.jar:./lib/jackson-annotations-2.9.7.jar:./bin:./lib/junit-4.12.jar:./lib/hamcrest-core-1.3.jar ./src/com/depas98/assignment/data/Action.java ./src/com/depas98/assignment/data/ActionStat.java ./src/com/depas98/assignment/data/ActionTime.java ./src/com/depas98/assignment/ActionStatsService.java ./test/com/depas98/assignment/ActionStatServiceTest.java 
	
	to run
	java -cp ./bin:./lib/junit-4.12.jar:./lib/jackson-core-2.9.7.jar:./lib/jackson-databind-2.9.7.jar:./lib/jackson-annotations-2.9.7.jar:./lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore com.depas98.assignment.ActionStatServiceTest

#### Windows
In the "actionStats" directory

Running Main Program execute the script below:
  run.bat

or you can execute the following individually
	to compile
	javac -d ./bin -cp ./lib/jackson-core-2.9.7.jar;./lib/jackson-databind-2.9.7.jar;./lib/jackson-annotations-2.9.7.jar ./src/com/depas98/assignment/data/Action.java ./src/com/depas98/assignment/data/ActionStat.java  ./src/com/depas98/assignment/data/ActionTime.java ./src/com/depas98/assignment/ActionStatsService.java
	
	to run
	java -cp ./bin;./lib/jackson-core-2.9.7.jar;./lib/jackson-databind-2.9.7.jar;./lib/jackson-annotations-2.9.7.jar com.depas98.assignment.ActionStatsService

Running Unit Tests execute the script below
  runTests.bat

or you can execute the following individually
	to compile 
	javac -d ./bin -cp ./lib/jackson-core-2.9.7.jar;./lib/jackson-databind-2.9.7.jar;./lib/jackson-annotations-2.9.7.jar;./bin;./lib/junit-4.12.jar;./lib/hamcrest-core-1.3.jar ./src/com/depas98/assignment/data/Action.java ./src/com/depas98/assignment/data/ActionStat.java ./src/com/depas98/assignment/data/ActionTime.java ./src/com/depas98/assignment/ActionStatsService.java ./test/com/depas98/assignment/ActionStatServiceTest.java 
	
	to run
	java -cp ./bin;./lib/junit-4.12.jar;./lib/jackson-core-2.9.7.jar;./lib/jackson-databind-2.9.7.jar;./lib/jackson-annotations-2.9.7.jar;./lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore com.depas98.assignment.ActionStatServiceTest
