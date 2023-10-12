package test.dataAccess;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Event;
import domain.Sport;

public class gertaerakSortuDABTest {

	 //sut:system under test
	 static DataAccess sut=new DataAccess(false);
	 
	 //additional operations needed to execute the test 
	 static TestDataAccess testDA=new TestDataAccess();

	private Event ev;	
	
	@Test
	
	public void test1() {
		try {
			System.out.println("Test 1");
		String description="Real MadridGirona";
		Date eventDate=new Date(2023,11,21);
		String sport="Futbol";
		testDA.open();
		testDA.addSport("Futbol");
        testDA.close();

		boolean resp=sut.gertaerakSortu(description, eventDate, sport);
		
		fail();
		}catch(Exception e) {
		assertTrue(e.getClass()==ArrayIndexOutOfBoundsException.class);
		}finally {
			sut.close();
			testDA.open();
			testDA.removeSport("Futbol");
	        testDA.close();	
		}
	}
	@Test
	public void test2() {
		try {
			sut.open(false);
			System.out.println("Test 2");
			String description=null;
			Date eventDate=new Date(2023,11,21);
			String sport="Futbol";
			
			testDA.open();
			testDA.addSport("Futbol");
	        testDA.close();

	        boolean resp=sut.gertaerakSortu(description, eventDate, sport);
	        
	        fail();
		}catch(Exception e) {
			System.out.println(e);
			assertTrue(e.getClass()==NullPointerException.class);
		}finally {
			testDA.open();
			testDA.removeSport("Futbol");
			testDA.close();
			sut.close();
		}
	}
	@Test
	public void test3() {
			sut.open(false);
			String description="Real Madrid-Girona";
			Date eventDate=null;
			String sport="Futbol";
			
	        boolean resp=sut.gertaerakSortu(description, eventDate, sport);
			
	        testDA.open();
	        testDA.removeSport("Futbol");
	        testDA.close();
	        sut.close();

			assertFalse(resp);
		
	}
	@Test
	public void test4() {
		sut.open(false);
		String description="Real Madrid-Girona";
		Date eventDate=new Date(2023,11,21);
		String sport="Futbol";
		
		boolean resp=sut.gertaerakSortu(description, eventDate, sport);

		sut.close();
		
		assertFalse(resp);		
	}
	@Test

	public void test5() {
		try {
		sut.open(false);
		String description="Real Madrid-Girona";
		Date eventDate=new Date(2023,11,21);
		String sport=null;
		
		testDA.open();
		testDA.addSport("Futbol");
		testDA.close();
		
		boolean resp=sut.gertaerakSortu(description, eventDate, sport);
		
		}catch(Exception e) {
		
		assertTrue(true);
			
		}finally {
		testDA.open();
		testDA.removeSport("Futbol");
		testDA.close();
		
		sut.close();
		
		}
	}
	@Test

	public void test6() {
		sut.open(false);
		String description="Real Madrid-Girona";
		Date eventDate=new Date(0);
		String sport="Futbol";
		
		testDA.open();
		testDA.addSport("Futbol");
		Event ev=testDA.addEventWithQuestion(description, eventDate, "Gana real?", 1);
        testDA.close();
        
        boolean resp=sut.gertaerakSortu(description, eventDate, sport);

        testDA.open();
		testDA.removeSport("Futbol");
		testDA.removeEvent(ev);
		testDA.close();
		sut.close();
		
		assertFalse(resp);
	}
	@Test

	public void test7() {
		sut.open(false);
		String description="Real Madrid-Girona";
		Date eventDate=new Date(2023,11,21);
		String sport="Futbol";
		
		testDA.open();
		testDA.addSport("Futbol");
		testDA.close();
		
		boolean resp=sut.gertaerakSortu(description, eventDate, sport);
		
		testDA.open();
		testDA.removeSport("Futbol");
		testDA.close();
		sut.close();
		
		assertTrue(resp);
}

}
