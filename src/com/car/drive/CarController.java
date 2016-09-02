package com.car.drive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.car.config.Configuration;

/**
 * The Class CarController.
 *
 * @author 
 */

public class CarController {

	/** The isInitiated. */
	static boolean isInitiated = false;
	
	/** The Constant LOGGER. */
	static final Logger LOGGER = Logger.getLogger(CarController.class);
	
	/** The exit cmd. */
	static String exitCmd = null;
	
	/** The init cmd. */
	static String initCmd= null;
	
	/** The forward cmd. */
	static String forwardCmd = null;
	
	/** The turn left cmd. */
	static String turnLeftCmd = null;
	
	/** The turn right cmd. */
	static String turnRightCmd = null;
	
	/** The gps report cmd. */
	static String gpsReportCmd = null;
	
	/** The input file. */
	static String inputFile = "";
	
	/** The input data folder. */
	static String inputDataFolder = "";
	
	/** The root path. */
	static String rootPath = System.getProperty("user.dir");
	
	
	/** The directions. */
	ArrayList<String> directions = new ArrayList<String>();
	
	/**
	 * Instantiates a new car controller.
	 */
	public CarController() {
		Configuration config = new Configuration();
		try {
			config.loadLogProperties();
			exitCmd = config.getProperty("EXIT_COMMAND");
			initCmd = config.getProperty("INIT_COMMAND");
			forwardCmd = config.getProperty("FORWARD_COMMAND");
			turnLeftCmd = config.getProperty("TURN_LEFT_COMMAND");
			turnRightCmd = config.getProperty("TURN_RIGHT_COMMAND");
			gpsReportCmd = config.getProperty("GPS_REPORT_COMMAND");
			inputDataFolder = config.getProperty("INPUT_DATA_FOLDER");
			LOGGER.debug("EXIT Command---"+exitCmd);
			LOGGER.debug("INIT Command---"+initCmd);
			LOGGER.debug("FORWARD Command---"+forwardCmd);
			LOGGER.debug("TURN LEFT Command---"+turnLeftCmd);
			LOGGER.debug("TURN RIGHT Command---"+turnRightCmd);
			LOGGER.debug("GPS REPORT Command---"+gpsReportCmd);
			LOGGER.debug("INPUT DATA FOLDER---"+inputDataFolder);
			LOGGER.info("Configuration loaded successfully");
		} catch (IOException e) {
			LOGGER.error("Cannot read properties file" + e + "\n");
		}
		
			directions.add("NORTH");
			directions.add("SOUTH");
			directions.add("EAST");
			directions.add("WEST");
		
	}


	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		CarController carCtrl = new CarController();
		if(args.length==0){
			carCtrl.readCommandsFromCmdLine();
		}else if(args[0].equals("-f")){
			if(args.length==2){
				inputFile = rootPath+File.separator+inputDataFolder+File.separator+args[1];
				carCtrl.readCommandsFromFile();
			}
		}else{
			LOGGER.info("Please enter a valid option. Valid options are argument in the format '-f <FILENAME>'(for commands from a file) or without argument(for command line input)");
			System.out.println("Please enter a valid option. Valid options are argument in the format '-f <FILENAME>'(for commands from a file) or without argument(for command line input)");
		}
	}
	
	
	/**
	 * Read commands from command line and calls corresponding method to do the action.
	 */
	public void readCommandsFromCmdLine(){
		
		Scanner scannerCmd = new Scanner(System.in);
		String inputCmd = "";
		int xCoOrd = 0;
		int yCoOrd = 0;
		boolean isValidInit = false;
		Car car = new Car();
		try{
			System.out.print("Enter the command: ");
			while(true){
				System.out.print(">> ");
				inputCmd = scannerCmd.nextLine();
				LOGGER.debug("Input command"+inputCmd);
				if(inputCmd!=null){
					if(inputCmd.equals(exitCmd)){
						System.exit(0);
					}else{
						StringTokenizer stInput = new StringTokenizer(inputCmd);
						ArrayList<String> cmdList = new ArrayList<String>();
						 while (stInput.hasMoreTokens()) {
							 cmdList.add(stInput.nextToken());
					     }
						LOGGER.debug("Is car Initiated"+isInitiated);
						if(cmdList.get(0).equals(initCmd)){
							isValidInit = isValidInitCommand(cmdList);
							if(isValidInit){
								StringTokenizer stCmd = new StringTokenizer(cmdList.get(1),",");
								ArrayList<String> coordinateList = new ArrayList<String>();
								 while (stCmd.hasMoreTokens()) {
									 coordinateList.add(stCmd.nextToken());
							     }
								xCoOrd = Integer.parseInt(coordinateList.get(0));
								yCoOrd = Integer.parseInt(coordinateList.get(1));
								isInitiated = true;
								car.initCar(xCoOrd,yCoOrd,coordinateList.get(2));
							}else{
								continue;
							}
						}
						LOGGER.debug("Is car Initiated"+isInitiated);
						if(!isInitiated && !cmdList.get(0).equals(initCmd)){
							LOGGER.info("First command should be INIT.Please enter an INIT <X>,<Y>,<F> command");
							System.out.println("First command should be INIT.Please enter an INIT <X>,<Y>,<F> command");
						}
						if(isInitiated && !cmdList.get(0).equals(initCmd)){
							if(cmdList.get(0).equals(forwardCmd)){
								car.forwardCar();
							}else if(cmdList.get(0).equals(turnLeftCmd)){
								car.turnLeftCar();
							}else if(cmdList.get(0).equals(turnRightCmd)){
								car.turnRightCar();
							}else if(cmdList.get(0).equals(gpsReportCmd)){
								car.gpsReportCar();
							}else{
								LOGGER.info(inputCmd + " is not a valid command. Valid commands are 'INIT <X>,<Y>,<F>' , 'FORWARD', 'TURN_LEFT', 'TURN_RIGHT', 'GPS_REPORT' ");
								System.out.println(inputCmd + " is not a valid command. Valid commands are 'INIT <X>,<Y>,<F>' , 'FORWARD', 'TURN_LEFT', 'TURN_RIGHT', 'GPS_REPORT' ");
							}
						}
					}
					
				}
			}
		}finally{
			scannerCmd.close();
		}
	}
	
	
	/**
	 * Read commands from file and calls corresponding method to do the action.
	 */
	public void readCommandsFromFile(){
		BufferedReader bufferReader = null;
		String cmdLine = "";
		Car car = new Car();
		int xCoOrd = 0;
		int yCoOrd = 0;
		boolean isValidInit = false;
		try {
			LOGGER.debug("Input command file-- "+inputFile);
			bufferReader = new BufferedReader(new FileReader(inputFile));
			
			try {
				cmdLine = bufferReader.readLine();
				while (cmdLine != null) {
					LOGGER.debug("Input command-- "+cmdLine);
					StringTokenizer st = new StringTokenizer(cmdLine);
					ArrayList<String> cmdList = new ArrayList<String>();
					 while (st.hasMoreTokens()) {
						 cmdList.add(st.nextToken());
				     }
					 LOGGER.debug("Is car Initiated"+isInitiated);
					
					if(cmdList.get(0).equals(initCmd)){
						isValidInit = isValidInitCommand(cmdList);
						if(isValidInit){
							StringTokenizer stCmd = new StringTokenizer(cmdList.get(1),",");
							ArrayList<String> coordinateList = new ArrayList<String>();
							 while (stCmd.hasMoreTokens()) {
								 coordinateList.add(stCmd.nextToken());
						     }
							xCoOrd = Integer.parseInt(coordinateList.get(0));
							yCoOrd = Integer.parseInt(coordinateList.get(1));
							isInitiated = true;
							car.initCar(xCoOrd,yCoOrd,coordinateList.get(2));
						}else{
							cmdLine = bufferReader.readLine();
						    continue;
						}
					}
					if(!isInitiated && !cmdList.get(0).equals(initCmd)){
						LOGGER.info("First command should be INIT.Please enter an INIT <X>,<Y>,<F> command");
						System.out.println("First command should be INIT.Please enter an INIT <X>,<Y>,<F> command");
					}
					if(isInitiated && !cmdList.get(0).equals(initCmd)){
						if(cmdList.get(0).equals(forwardCmd)){
							car.forwardCar();
						}else if(cmdList.get(0).equals(turnLeftCmd)){
							car.turnLeftCar();
						}else if(cmdList.get(0).equals(turnRightCmd)){
							car.turnRightCar();
						}else if(cmdList.get(0).equals(gpsReportCmd)){
							car.gpsReportCar();
						}else{
							LOGGER.info(cmdList.get(0) + " is not a valid command.Valid commands are 'INIT <X>,<Y>,<F>' , 'FORWARD' ,'TURN_LEFT', 'TURN_RIGHT', 'GPS_REPORT'");
							System.out.println(cmdList.get(0) + " is not a valid command.Valid commands are 'INIT <X>,<Y>,<F>' , 'FORWARD' ,'TURN_LEFT', 'TURN_RIGHT', 'GPS_REPORT'");
						}
					}
					cmdLine = bufferReader.readLine();
				}
				LOGGER.info("Completed reading input file ");
			} catch (IOException e) {
				LOGGER.error("Cannot read input file "+inputFile+"\n" + e);
				System.exit(1);
			} finally {
				if (bufferReader != null) {
					try {
						bufferReader.close();
					} catch (IOException e) {
						LOGGER.error("Trying to close file reader that doesn't exists \n" + e);
					}
				}
			}
		} catch (FileNotFoundException e) {
			LOGGER.error("Input File Does not exist or Invalid input file name  "+inputFile+"\n" + e);
			System.exit(1);
		}
	}
	
	public boolean isValidInitCommand(ArrayList<String> cmdList){
		boolean isValidInit = false;
		int xCoOrd = 0;
		int yCoOrd = 0;
		if(cmdList.size()!=2){
			LOGGER.info("Not a valid INIT command.Please enter in INIT <X>,<Y>,<FACING> format");
			System.out.println("Not a valid INIT command.Please enter in INIT <X>,<Y>,<FACING> format");
			 return isValidInit;
		}
		StringTokenizer stCmd = new StringTokenizer(cmdList.get(1),",");
		ArrayList<String> coordinateList = new ArrayList<String>();
		 while (stCmd.hasMoreTokens()) {
			 coordinateList.add(stCmd.nextToken());
	     }
		 if(coordinateList.size()!=3){
			 LOGGER.info("Not a valid INIT position.Please enter in INIT <X>,<Y>,<FACING> format");
			 System.out.println("Not a valid INIT position.Please enter in INIT <X>,<Y>,<FACING> format");
			 return isValidInit;
		 }
		 if(!directions.contains(coordinateList.get(2))){
			 LOGGER.info("Not a valid direction.Please enter NORTH,SOUTH,EAST or WEST");
			 System.out.println("Not a valid direction.Please enter NORTH,SOUTH,EAST or WEST");
			 return isValidInit;
		 }
		 try {
			xCoOrd = Integer.parseInt(coordinateList.get(0));
			yCoOrd = Integer.parseInt(coordinateList.get(1));
		} catch (NumberFormatException e) {
			LOGGER.error("Invalid X or Y coordinates.Please enter INIT <X>,<Y>,<FACING> format");
		    System.out.println("Invalid X or Y coordinates.Please enter INIT <X>,<Y>,<FACING> format");
		    return isValidInit;
		}
		isValidInit = true;
		
		return isValidInit;
	}
}
