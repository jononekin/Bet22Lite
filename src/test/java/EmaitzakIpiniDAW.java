import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.mail.Quota;

import org.junit.Test;

import businessLogic.BLFacade;
import configuration.ConfigXML;
import test.dataAccess.*;
import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import domain.Quote;
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
        Event e = bl.addEventWithQuestion("test", d, "testq", 1);
        try{
            Quote q = sut.storeQuote("test", 2.0, e.getQuestions().get(0));
            sut.EmaitzakIpini(q);
            fail("Debería dar error");
        } catch(QuoteAlreadyExist ex){
            fail();
        } catch(EventNotFinished ex1){
            
            assertTrue(true);
        } finally{
            bl.removeEvent(e);
        } 
    }
    
    @Test
    public void test2(){
        
    }

}
