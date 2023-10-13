import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import dataAccess.DataAccess;
import test.dataAccess.TestDataAccess;

public class GertaerakSortuDABTest {
		//sut:system under test
		 static DataAccess sut=new DataAccess();
		 
		 //additional operations needed to execute the test 
		 static TestDataAccess testDA=new TestDataAccess();
		 
		 @Test
			//sut.createQuestion:  Sport wrong, needed to be false. 
			public void test1() {
				//define paramaters
				String deporte="fusbol";
				String des="Real Madrid-Barcelona";
				boolean resp;
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date eventDate=null;
				
				
				try {
					eventDate = sdf.parse("30/10/2022");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				
				try {
					//invoke System Under Test (sut)  
					sut.open(true);
					resp=sut.gertaerakSortu(des, eventDate, deporte);
					sut.close();
					
					//verify the results
					assertFalse(resp);
					
					//the event is not in the DB
					testDA.open();
					boolean a = testDA.exiteEvento(eventDate, des);
					testDA.close();
					assertFalse(a);
				}
				catch (Exception e) {
					fail();
				
				} finally {
					//Remove the created objects in the database (cascade removing)   
					testDA.open();
			        boolean b=testDA.removeEvent(eventDate, des);
			        testDA.close();
			     // System.out.println("Finally "+b); 
				}
				          
			}
		 
			
				/*
				@Test
			//sut.createQuestion:  The event is null. The test fail
				public void test2() {
					try {
						
						//define paramaters
						String eventText="event1";
						String queryText="query1";
						Float betMinimum=new Float(2);
						
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						Date oneDate=null;;
						try {
							oneDate = sdf.parse("05/10/2022");
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
						
						//invoke System Under Test (sut)  
						Question q=sut.createQuestion(null, queryText, betMinimum);
						
						
						//verify the results
						assertTrue(q==null);
						
						
					   } catch (QuestionAlreadyExist e) {
						// TODO Auto-generated catch block
						// if the program goes to this point fail  
						fail();
						} 
					   }
			@Test
			//sut.createQuestion:  The question is null. The test fail
			public void test3() {
				try {
					
					//define paramaters
					String eventText="event1";
					String queryText=null;
					Float betMinimum=new Float(2);
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Date oneDate=null;;
					try {
						oneDate = sdf.parse("05/10/2022");
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					
					//configure the state of the system (create object in the dabatase)
					testDA.open();
					ev = testDA.addEventWithQuestion(eventText,oneDate,"query2", betMinimum);
					testDA.close();			
					
					//invoke System Under Test (sut)  
					Question q=sut.createQuestion(ev, queryText, betMinimum);
					
					
					//verify the results
					assertTrue(q==null);
					
					
					//q datubasean dago
					testDA.open();
					boolean exist = testDA.existQuestion(ev,q);
						
					assertTrue(!exist);
					testDA.close();
					
				   } catch (QuestionAlreadyExist e) {
					// TODO Auto-generated catch block
					// if the program goes to this point fail  
					fail();
					} finally {
						  //Remove the created objects in the database (cascade removing)   
						testDA.open();
				          boolean b=testDA.removeEvent(ev);
				          testDA.close();
				      //     System.out.println("Finally "+b);          
				        }
				   }
				   
				   */
}
