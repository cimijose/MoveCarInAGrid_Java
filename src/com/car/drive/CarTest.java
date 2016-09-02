package com.car.drive;

import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Test;

public class CarTest {

	@Test
	/*
	 * Test an INIT 0,0,NORTH and FORWARD,GPS_REPORT
	 */
	public void testCar1() {
		Car car = new Car();
		car.initCar(0,0,"NORTH");
		car.forwardCar();
		car.gpsReportCar();
		assertEquals("Output: 0,1,NORTH", car.getGpsReport());
	}
	
	@Test
	/*
	 * Test an INIT 0,0,NORTH and TURN_LEFT,GPS_REPORT
	 * 
	 */
	public void testCar2() {
		
		Car car = new Car();
		car.initCar(0,0,"NORTH");
		car.turnLeftCar();
		car.gpsReportCar();
		assertEquals("Output: 0,0,WEST", car.getGpsReport());
	}
	
	@Test
	/*
	 * Test an INIT 1,2,EAST and FORWARD,FORWARD,TURN_LEFT,FORWARD,GPS_REPORT
	 */
	public void testCar3() {
		
		Car car = new Car();
		car.initCar(1,2,"EAST");
		car.forwardCar();
		car.forwardCar();
		car.turnLeftCar();
		car.forwardCar();
		car.gpsReportCar();
		assertEquals("Output: 3,3,NORTH", car.getGpsReport());
	}
	
	@Test
	/*
	 * Test a command other than INIT as first command .Result : any action before an INIT should be ignored 
	 */
	public void testCar4() {
		ArrayList <String> initCmd = new ArrayList<String>();
		initCmd.add("FORWARD");
		initCmd.add("");
		Car car = new Car();
		car.forwardCar();
		car.gpsReportCar();
		assertEquals("Car is not initiated properly",car.getGpsReport());		
	}
	
	@Test
	/*
	 * Test a INIT more than once. Result : should allow multiple init
	 * 
	 */
	public void testCar5() {
		
		Car car = new Car();
		car.initCar(1,3,"EAST");
		car.forwardCar();
		car.initCar(2,1,"SOUTH");
		car.gpsReportCar();
		assertEquals("Output: 2,1,SOUTH", car.getGpsReport());		
	}
	
	@Test
	/*
	 * Test crossing boundary on WEST .Result : action to cross boundary should be ignored. car should in the last position
	 */
	public void testCar6() {
		
		Car car = new Car();
		car.initCar(1,2,"NORTH");
		car.turnLeftCar();
		car.forwardCar();
		car.forwardCar();
		car.gpsReportCar();
		assertEquals("Output: 0,2,WEST", car.getGpsReport());		
	}

	@Test
	/*
	 * Test crossing boundary on NORTH with INIT. Result : action should be ignored
	 */
	public void testCar7() {
		
		Car car = new Car();
		car.initCar(3,6,"NORTH");
		car.gpsReportCar();
		assertEquals("Car is not initiated properly",car.getGpsReport());		
	}

	@Test
	/*
	 * Test crossing boundary on NORTH . Result : action should be ignored and position of the car will be last position
	 */
	public void testCar8() {
		
		Car car = new Car();
		car.initCar(2,3,"NORTH");
		car.forwardCar();
		car.forwardCar();
		car.gpsReportCar();
		assertEquals("Output: 2,4,NORTH", car.getGpsReport());			
	}
	
	@Test
	/*
	 * Test crossing boundary on NORTH . Result : action should be ignored and position of the car will be last position
	 */
	public void testCar9() {
		
		Car car = new Car();
		car.initCar(2,0,"EAST");
		car.turnRightCar();
		car.forwardCar();
		car.forwardCar();
		car.gpsReportCar();
		assertEquals("Output: 2,0,SOUTH", car.getGpsReport());			
	}
	
	@Test
	/*
	 * Test crossing boundary on EAST . Result : action should be ignored and position of the car will be last position
	 */
	public void testCar10() {
		Car car = new Car();
		car.initCar(3,2,"WEST");
		car.turnLeftCar();
		car.turnLeftCar();
		car.forwardCar();
		car.forwardCar();
		car.gpsReportCar();
		assertEquals("Output: 4,2,EAST", car.getGpsReport());			
	}

}
