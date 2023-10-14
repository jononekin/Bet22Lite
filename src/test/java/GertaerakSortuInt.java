import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import exceptions.EventFinished;

public class GertaerakSortuInt {
	DataAccess dataAccess = Mockito.mock((DataAccess.class));
	BLFacade sut=new BLFacadeImplementation(dataAccess);

	
	
	@Test
	//sut.createQuestion:  The event has one question with a queryText. 
	public void test1() {
		
	
		//define paramaters
		String deporte="fusbol";
		String des="Atletico-Athletic";
		boolean resp;
	
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate=null;
		try {
			oneDate = sdf.parse("05/10/2022");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		try {
			//configure Mock
			Mockito.doReturn(false).when(dataAccess).gertaerakSortu(Mockito.any(String.class), Mockito.any(Date.class), Mockito.any(String.class));
			
			//invoke System Under Test(sut)
			resp = sut.gertaerakSortu(des, oneDate, deporte);
			
			//verify the results
			assertFalse(resp);
			
			ArgumentCaptor<String> desCaptor = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<Date> oneDateCaptor = ArgumentCaptor.forClass(Date.class);
			ArgumentCaptor<String> deporteCaptor = ArgumentCaptor.forClass(String.class);
			
			Mockito.verify(dataAccess,Mockito.times(1)).gertaerakSortu(desCaptor.capture(), oneDateCaptor.capture(), deporteCaptor.capture());
			
			assertEquals(desCaptor.getValue(),des);
			assertEquals(oneDateCaptor.getValue(),oneDate);
			assertEquals(deporteCaptor.getValue(),deporte);
			
			
		} catch (EventFinished e) {
			fail();
		}
			
		
	}
	
			
	
	@Test
	//sut.createQuestion:  The event has NOT one question with a queryText. 
	public void test2() {
		
		//define paramaters
		String deporte="Futbol";
		String des="Atletico-Athletic";
		boolean resp;
	
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate=null;
		try {
			oneDate = sdf.parse("05/10/2022");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		try {
			//configure mock
			Mockito.doReturn(true).when(dataAccess).gertaerakSortu(Mockito.any(String.class), Mockito.any(Date.class), Mockito.any(String.class));
			
			//invoke System Under Test(sut)
			resp = sut.gertaerakSortu(des, oneDate, deporte);
			
			//verify results
			assertTrue(resp);
			
			ArgumentCaptor<String> desCaptor = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<Date> oneDateCaptor = ArgumentCaptor.forClass(Date.class);
			ArgumentCaptor<String> deporteCaptor = ArgumentCaptor.forClass(String.class);
			
			Mockito.verify(dataAccess,Mockito.times(1)).gertaerakSortu(desCaptor.capture(), oneDateCaptor.capture(), deporteCaptor.capture());
			
			assertEquals(desCaptor.getValue(),des);
			assertEquals(oneDateCaptor.getValue(),oneDate);
			assertEquals(deporteCaptor.getValue(),deporte);
			
		} catch (Exception e) {
			fail();
		}
			
	}
	
	@Test
	//sut.createQuestion:  The event has NOT one question with a queryText. 
	public void test3() {
		
			
		//define paramaters
		String deporte="Futbol";
		String des="Atletico-Athletic";
		
	
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate=null;
		try {
			oneDate = sdf.parse("25/10/2022");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		try {
			//configure mock
			Mockito.doReturn(true).when(dataAccess).gertaerakSortu(Mockito.any(String.class), Mockito.any(Date.class), Mockito.any(String.class));
			
			//invoke System Under Test(sut)
			boolean resp = sut.gertaerakSortu(des, oneDate, deporte);
			
			
			//verify results
			ArgumentCaptor<String> desCaptor = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<Date> oneDateCaptor = ArgumentCaptor.forClass(Date.class);
			ArgumentCaptor<String> deporteCaptor = ArgumentCaptor.forClass(String.class);
			
			Mockito.verify(dataAccess, Mockito.times(0)).gertaerakSortu(desCaptor.capture(), oneDateCaptor.capture(), deporteCaptor.capture());
			
			assertEquals(desCaptor.getValue(),des);
			assertEquals(oneDateCaptor.getValue(),oneDate);
			assertEquals(deporteCaptor.getValue(),deporte);
			
		} catch (EventFinished e) {
			assertTrue(true);
		}
			
	}
	
	@Test
	//sut.createQuestion:  The event has NOT one question with a queryText. 
	public void test4() {
		
		
		//define paramaters
		String deporte="fusbol";
		String des="Atletico-Athletic";
		boolean resp;
	
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate=null;
		try {
			oneDate = sdf.parse("05/10/2022");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		try {
			//configure Mock
			Mockito.doReturn(false).when(dataAccess).gertaerakSortu(Mockito.any(String.class), Mockito.any(Date.class), Mockito.any(String.class));
			
			//invoke System Under Test(sut)
			resp = sut.gertaerakSortu(des, oneDate, deporte);
			
			//verify the results
			assertFalse(resp);
			
			ArgumentCaptor<String> desCaptor = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<Date> oneDateCaptor = ArgumentCaptor.forClass(Date.class);
			ArgumentCaptor<String> deporteCaptor = ArgumentCaptor.forClass(String.class);
			
			Mockito.verify(dataAccess,Mockito.times(1)).gertaerakSortu(desCaptor.capture(), oneDateCaptor.capture(), deporteCaptor.capture());
			
			assertEquals(desCaptor.getValue(),des);
			assertEquals(oneDateCaptor.getValue(),oneDate);
			assertEquals(deporteCaptor.getValue(),deporte);
			
			
		} catch (EventFinished e) {
			fail();
		}
		
	}
}
