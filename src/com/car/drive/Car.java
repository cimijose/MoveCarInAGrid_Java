
package com.car.drive;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.car.config.Configuration;



/**
 * The Class Car.
 */
public class Car {
	
	/** The Constant LOGGER. */
	static final Logger LOGGER = Logger.getLogger(Car.class);
	
	/** The grid max x. */
	int gridMaxX;
	
	/** The grid max y. */
	int gridMaxY;
	
	/** The grid min x. */
	int gridMinX;
	
	/** The grid min y. */
	int gridMinY;
	
	int oneUnit;
	
	/** The current X. */
	private int currentX;
	
	/** The current Y. */
	private int currentY;
	
	/** The current facing. */
	private String currentFacing;

	private String gpsReport;
	
	/**
	 * Gets the current X.
	 *
	 * @return the current X
	 */
	public int getCurrentX() {
		return currentX;
	}

	/**
	 * Sets the current X.
	 *
	 * @param currentX the new current X
	 */
	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}

	/**
	 * Gets the current Y.
	 *
	 * @return the current Y
	 */
	public int getCurrentY() {
		return currentY;
	}

	/**
	 * Sets the current Y.
	 *
	 * @param currentY the new current Y
	 */
	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}

	/**
	 * Gets the current facing.
	 *
	 * @return the current facing
	 */
	public String getCurrentFacing() {
		return currentFacing;
	}

	/**
	 * Sets the current facing.
	 *
	 * @param currentFacing the new current facing
	 */
	public void setCurrentFacing(String currentFacing) {
		this.currentFacing = currentFacing;
	}
	
	

	public String getGpsReport() {
		return gpsReport;
	}

	public void setGpsReport(String gpsReport) {
		this.gpsReport = gpsReport;
	}

	/**
	 * Instantiates a new car.
	 */
	public Car() {
		Configuration config = new Configuration();
		try {
			config.loadLogProperties();
			gridMaxX = Integer.parseInt(config.getProperty("GRID_MAX_X_COORD"));
			gridMaxY = Integer.parseInt(config.getProperty("GRID_MAX_Y_COORD"));
			gridMinX = Integer.parseInt(config.getProperty("GRID_MIN_X_COORD"));
			gridMinY = Integer.parseInt(config.getProperty("GRID_MIN_Y_COORD"));
			oneUnit = Integer.parseInt(config.getProperty("ONE_UNIT"));
			LOGGER.debug("Grid maximum x coordinate "+gridMaxX);
			LOGGER.debug("Grid maximum y coordinate "+gridMaxY);
			LOGGER.debug("Grid minimum x coordinate "+gridMinX);
			LOGGER.debug("Grid minimum y coordinate "+gridMinY);
			LOGGER.info("Configuration loaded successfully");
		} catch (IOException e) {
			LOGGER.error("Cannot read properties file" + e + "\n");
		}
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param facing
	 */
	public void initCar(int x,int y,String facing) {
		
		 LOGGER.debug("Input X "+x);
		 LOGGER.debug("Input Y "+y);
		 LOGGER.debug("Input Facing "+facing);
		 if(x>=gridMinX && x<=gridMaxX && y>=gridMinY && y<=gridMaxY){
			 this.setCurrentX(x);
			 this.setCurrentY(y);
			 this.setCurrentFacing(facing);
		 }
		 LOGGER.info("Current Position after INIT "+ this.getCurrentX()+ ","+this.getCurrentY()+","+this.getCurrentFacing());
	}

	/**
	 *This method will move the car one unit forward in the direction it is currently facing.
	 */
	public void forwardCar() {
		LOGGER.info("Current Position before forward"+ this.getCurrentX()+ ","+this.getCurrentY()+","+this.getCurrentFacing());
		if(this.getCurrentFacing()!=null){
			if(this.getCurrentFacing().equals("NORTH") && this.getCurrentY()<gridMaxY){
				this.setCurrentY(this.getCurrentY()+oneUnit);
			}else if(this.getCurrentFacing().equals("EAST") && this.getCurrentX()<gridMaxX){
				this.setCurrentX(this.getCurrentX()+oneUnit);
			}else if(this.getCurrentFacing().equals("SOUTH") && this.getCurrentY()>gridMinY) {
				this.setCurrentY(this.getCurrentY()-oneUnit);
			}else if(this.getCurrentFacing().equals("WEST")&& this.getCurrentX()>gridMinX){
				this.setCurrentX(this.getCurrentX()-oneUnit);
			}
		}
		LOGGER.info("Current Position after FORWARD"+ this.getCurrentX()+ ","+this.getCurrentY()+","+this.getCurrentFacing());
	}

	/**
	 * This method will rotate the car to left without changing its position on the grid.
	 */
	public void turnLeftCar() {
		LOGGER.info("Current Position before turn left"+ this.getCurrentX()+ ","+this.getCurrentY()+","+this.getCurrentFacing());
		if(this.getCurrentFacing()!=null){
			if(this.getCurrentFacing().equals("NORTH")){
				this.setCurrentFacing("WEST");
			}else if(this.getCurrentFacing().equals("WEST")){
				this.setCurrentFacing("SOUTH");
			}else if(this.getCurrentFacing().equals("SOUTH")) {
				this.setCurrentFacing("EAST");
			}else if(this.getCurrentFacing().equals("EAST")){
				this.setCurrentFacing("NORTH");
			}
		}
		LOGGER.info("Current Position after turn left"+ this.getCurrentX()+ ","+this.getCurrentY()+","+this.getCurrentFacing());
	}

	/**
	 * This method will rotate the car into right without changing its position on the grid.
	 */
	public void turnRightCar() {
		
		LOGGER.info("Current Position before turn right"+ this.getCurrentX()+ ","+this.getCurrentY()+","+this.getCurrentFacing());
		if(this.getCurrentFacing()!=null){
			if(this.getCurrentFacing().equals("NORTH")){
				this.setCurrentFacing("EAST");
			}else if(this.getCurrentFacing().equals("EAST")){
				this.setCurrentFacing("SOUTH");
			}else if(this.getCurrentFacing().equals("SOUTH")) {
				this.setCurrentFacing("WEST");
			}else if(this.getCurrentFacing().equals("WEST")){
				this.setCurrentFacing("NORTH");
			}
		}
		LOGGER.info("Current Position after turn right"+ this.getCurrentX()+ ","+this.getCurrentY()+","+this.getCurrentFacing());
	}

	/**
	 * This method prints the current position of the car.
	 */
	public void gpsReportCar() {
		LOGGER.info("Current Position gpsReport"+ this.getCurrentX()+ ","+this.getCurrentY()+","+this.getCurrentFacing());
		
		if(this.getCurrentFacing()!=null){
			this.setGpsReport("Output: "+ this.getCurrentX()+ ","+this.getCurrentY()+","+this.getCurrentFacing());
		}else{
			this.setGpsReport("Car is not initiated properly");
		}
		System.out.println(this.getGpsReport());
	}

	

}
