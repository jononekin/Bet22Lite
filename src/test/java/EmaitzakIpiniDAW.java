import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.mail.Quota;
import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import javax.xml.crypto.Data;

import org.junit.Test;
import org.mockito.Mockito;

import businessLogic.BLFacade;
import configuration.ConfigXML;
import test.dataAccess.*;
import dataAccess.DataAccess;
import domain.ApustuAnitza;
import domain.Apustua;
import domain.Event;
import domain.Question;
import domain.Quote;
import domain.Registered;
import exceptions.EventFinished;
import exceptions.EventNotFinished;
import exceptions.QuestionAlreadyExist;
import exceptions.QuoteAlreadyExist;
import test.businessLogic.TestFacadeImplementation;
import test.dataAccess.TestDataAccess;

public class EmaitzakIpiniDAW {
    //sut:system under test
	static DataAccess sut=new DataAccess();
	 
    //additional operations needed to execute the test 
    static TestDataAccess testDA=new TestDataAccess();
    static TestFacadeImplementation bl = new TestFacadeImplementation();
    private Event ev;
    
   @Test
    public void test1(){
        Date d = new Date(2024, 11, 12);
        
        Event e = bl.addEventWithQuestion("test", d, "testq", 1); //crea un evento test y le asigna una pregunta testq en la BD
        Quote q = null;
        try{
            q =sut.storeQuote("test", 2.0, e.getQuestions().get(0));
            sut.EmaitzakIpini(q);
            fail("Debería dar error");
        } catch(QuoteAlreadyExist ex){
            fail();
        } catch(EventNotFinished ex1){
            
            assertTrue(true);
        } finally{
            assertTrue(bl.removeEvent(e));
            
        } 
    }
    
    @Test
    public void test2(){
        Event e = bl.addEventWithQuestion("testEvent", new Date(), "testQuestion", 1);
        //La pregunta creada del evento
        Question q = e.getQuestions().get(0);
        
        Registered u = sut.findUser(new Registered("admin", "123", 1234, true));
        
        //Crea el pronostico
        Quote quote = new Quote();
        quote.setQuestion(q);
        quote.setQuote(2.0);
        quote.setForecast("testQuote");
        //añade el pronostico a la pregunta
        
        ApustuAnitza apa = new ApustuAnitza(u, 2.0);
        Apustua ap = new Apustua(apa, quote);
        
        quote.addApustua(ap);
        
        q.addQuote(quote.getQuote(), quote.getForecast(), q);
        
        EntityManager emTest = Mockito.mock(EntityManager.class);
        
        Mockito.doReturn(quote).when(emTest.find(Quote.class, quote));
        Mockito.doReturn(q).when(emTest.find(Question.class, quote.getQuestion()));
        Mockito.verify(null)
        
        try {
            Quote q = sut.storeQuote("test", 2.0, e.getQuestions().get(0));
            
            //Vector de apuestas del pronostico
            Vector<Quote> quotes = new Vector<>();
            quotes.addElement(q);
            //añado apuestas al pronostico para testear la linea 22
            assertTrue(sut.ApustuaEgin(u, quotes, 2.0, 2));
            
            sut.EmaitzakIpini(q);
            Question qdb = sut.findQuestionFromQuote(q);
            assertEquals(q.getForecast(), qdb.getResult());
        } catch (QuoteAlreadyExist | EventNotFinished e1) {
            fail("No debería de dar error");
        } finally{
            assertTrue(bl.removeEvent(e));
            
        }
    }

    @Test
    public void test3(){

    }


}
