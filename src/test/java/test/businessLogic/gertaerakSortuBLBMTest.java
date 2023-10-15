package test.businessLogic;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Event;
import exceptions.EventFinished;
import test.dataAccess.TestDataAccess;

public class gertaerakSortuBLBMTest {

	private BLFacadeImplementation businessLogic;
    private DataAccess dbManager;

    @Before
    public void setUp() {
        dbManager = mock(DataAccess.class);
        businessLogic = new BLFacadeImplementation(dbManager);
    }

    @Test
    public void test1() {
        // Mock the behavior of DataAccess methods
        when(dbManager.gertaerakSortu(null, new Date(2023, 11, 21), "Futbol"))
            .thenThrow(new NullPointerException());

        // Test your method
        try {
            boolean result = businessLogic.gertaerakSortu(null, new Date(2023, 11, 21), "Futbol");
            fail("Should have thrown an exception");
        } catch (NullPointerException e) {
            assertTrue(true);
        } catch (EventFinished e) {
            fail("Should have thrown an exception");
		}

        // Verify that the required methods are called
        verify(dbManager).gertaerakSortu(null, new Date(2023, 11, 21), "Futbol");
    }
    
    @Test
    public void test2() {
        // Mock the behavior of DataAccess methods
        when(dbManager.gertaerakSortu("Real Madrid-Girona", null, "Futbol"))
            .thenThrow(new NullPointerException());

        // Test your method
        try {
            boolean result = businessLogic.gertaerakSortu("Real Madrid-Girona", null, "Futbol");
            fail("Should have thrown an exception");
        } catch (NullPointerException e) {
            assertTrue(true);
        } catch (EventFinished e) {
            fail("Should have thrown an exception");
		}

        // Verify that the required methods are called
    }
    
    @Test
    public void test3() {
        // Mock the behavior of DataAccess method
    	Date today=new Date();
        when(dbManager.gertaerakSortu("Real Madrid-Girona", today, "Futbol"))
            .thenReturn(false);

        // Test your method
        try {
            boolean result = businessLogic.gertaerakSortu("Real Madrid-Girona",today, "Futbol");
            fail("Should have thrown an exception");
        } catch (EventFinished e) {
            assertTrue(true);
		}

        // Verify that the required methods are called
    }
    @Test
    public void test4() {
        // Mock the behavior of DataAccess method
    	String string = "January 2, 2024";
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
    	Date date = Date.from(LocalDate.parse(string, formatter).atStartOfDay()
    		      .atZone(ZoneId.systemDefault())
    		      .toInstant());
    	when(dbManager.gertaerakSortu("Real Madrid-Girona", date, "Futbol"))
            .thenReturn(true);
    	boolean result=false;	
    	try {
    			result = businessLogic.gertaerakSortu("Real Madrid-Girona",date, "Futbol");
    			
    		}catch(EventFinished e) {
    			fail();
    		}
            assertTrue(result);

        // Verify that the required methods are called
        verify(dbManager).gertaerakSortu("Real Madrid-Girona", date, "Futbol");
    }
    @Test
    public void test5() {
        // Mock the behavior of DataAccess method
    	String string = "January 2, 2024";
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
    	Date date = Date.from(LocalDate.parse(string, formatter).atStartOfDay()
    		      .atZone(ZoneId.systemDefault())
    		      .toInstant());
    	when(dbManager.gertaerakSortu("Real Madrid-Girona", date, "Futbol"))
            .thenReturn(false);
    	boolean result=true;	
    	try {
    			result = businessLogic.gertaerakSortu("Real Madrid-Girona",date, "Futbol");
    			
    		}catch(EventFinished e) {
    			fail();
    		}
            assertFalse(result);

        // Verify that the required methods are called
        verify(dbManager).gertaerakSortu("Real Madrid-Girona", date, "Futbol");
    }
}
