
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Test;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import domain.Apustua;
import domain.Event;
import domain.Question;
import domain.Quote;
import domain.Registered;
import test.businessLogic.TestFacadeImplementation;


public class EmaitzakIpiniDAB {
    
    //sut:system under test
	static BLFacade sut=new BLFacadeImplementation();
	 
	 //additional operations needed to execute the test 
	static TestFacadeImplementation testDA=new TestFacadeImplementation();


	@Test
	public void test1(){
		try {
			sut.EmaitzakIpini(null);
			fail("No debería de ejecutar con parámetro null");
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void test2(){
		Event e =testDA.addEventWithQuestion("testEvent", new Date(), "testQuestion", 1);
		Quote q = testDA.addQuotesTo(e.getQuestions().get(0), 1, "testQuote");
		try {
			sut.EmaitzakIpini(q);
			Question question = sut.findQuestionFromQuote(q);
			assertNotNull(question);
			assertEquals(e.getQuestions().get(0).getQuestionNumber(), question.getQuestionNumber());
			assertEquals(q.getForecast(),question.getResult());
			
		} catch (Exception ex) {
			fail("No debería de dar error");
		} finally{
			testDA.removeEvent(e);
		}
	}

	@Test
	public void test3(){
		Event e =testDA.addEventWithQuestion("testEvent", new Date(), "testQuestion", 1);
		Quote q = testDA.addQuotesTo(e.getQuestions().get(0), 1, "testQuote");
		Registered u = testDA.addUser("testUser");
		Apustua ap = testDA.addApuestaTo(q, u, 1);
		

		try {
			sut.EmaitzakIpini(q);
			Question question = sut.findQuestionFromQuote(q);
			assertNotNull(question);
			Apustua apuesta = question.getQuotes().get(0).getApustuak().get(0);
			assertNotNull(apuesta);
			assertEquals(e.getQuestions().get(0).getQuestionNumber(), question.getQuestionNumber());
			assertEquals(q.getForecast(),question.getResult());
			assertEquals("irabazita",apuesta.getEgoera());
			
		} catch (Exception ex) {
			fail("No debería de dar error");
		} finally{
			
			assertTrue(testDA.removeUser(u.getUsername()));
			assertTrue(testDA.removeEvent(e));
		}
	}

	@Test
	public void test4(){
		Event e =testDA.addEventWithQuestion("testEvent", new Date(), "testQuestion", 1);
		Quote q = testDA.addQuotesTo(e.getQuestions().get(0), 1, "testQuote");
		Quote q2 = testDA.addQuotesTo(e.getQuestions().get(0), 2, "testQuote2");
		Registered u = testDA.addUser("testUser");
		Apustua ap = testDA.addApuestaTo(q, u, 1);
		Apustua ap2 = testDA.addApuestaTo(q2, u, 12);
		

		try {
			sut.EmaitzakIpini(q);
			Question question = sut.findQuestionFromQuote(q2);
			assertNotNull(question);
			Apustua apuesta = question.getQuotes().get(0).getApustuak().get(0);
			assertNotNull(apuesta);
			assertEquals(e.getQuestions().get(0).getQuestionNumber(), question.getQuestionNumber());
			assertNotEquals(q2.getForecast(),question.getResult());
			assertEquals("galduta",apuesta.getEgoera());
			
		} catch (Exception ex) {
			fail("No debería de dar error");
		} finally{
			
			// assertTrue(testDA.removeEvent(e));
			// assertTrue(testDA.removeUser(u.getUsername()));
		}
	}

}
