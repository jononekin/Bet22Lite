import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import configuration.ConfigXML;
import test.dataAccess.*;
import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import test.businessLogic.TestFacadeImplementation;
import test.dataAccess.TestDataAccess;

public class EmaitzakIpiniDAB {
    
    //sut:system under test
	static DataAccess sut=new DataAccess();
	 
	 //additional operations needed to execute the test 
	static TestDataAccess testDA=new TestDataAccess();

    private Event ev;

	@Test
	public void test1(){
		
	}
}
