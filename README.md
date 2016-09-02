# Application Description

This application will read commands to move the car from either command line or a file. By default this application will read commands from command line. If the application is run in format <MAIN_CLASS with package> -f <INPUT_FILENAME>, commands will be read from the given filename
Main class is com.car.drive.CarController
eg : java com.car.drive.CarController -f input.txt

Commands are expected to be entered in Upper Case.

'EXIT' command is for exiting the application from command line

# External libraries

1. log4j-1.2.17.jar for logging
2. junit-4.12.jar, hamcrest-core-1.3.jar for jUnit

# Points to note
1. The logs folder should be modified in the log4j.properties file accordingly
2. Input file needs to be placed in 'inputData' folder. This can be changed config.properties file













