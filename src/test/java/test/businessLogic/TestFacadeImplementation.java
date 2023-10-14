package test.businessLogic;
import java.util.Date;
import configuration.ConfigXML;
import domain.Apustua;
import domain.Event;
import domain.Question;
import domain.Quote;
import domain.Registered;
import test.dataAccess.TestDataAccess;

public class TestFacadeImplementation {
	TestDataAccess dbManagerTest;
 	
    
	   public TestFacadeImplementation()  {
			
			System.out.println("Creating TestFacadeImplementation instance");
			ConfigXML c=ConfigXML.getInstance();
			dbManagerTest=new TestDataAccess(); 
			dbManagerTest.close();
		}
		public void close(){
			dbManagerTest.close();
		}
		 
		public boolean removeEvent(Event ev) {
			dbManagerTest.open();
			boolean b=dbManagerTest.removeEvent(ev);
			dbManagerTest.close();
			return b;

		}
		
		public Event addEventWithQuestion(String desc, Date d, String q, float qty) {
			dbManagerTest.open();
			Event o=dbManagerTest.addEventWithQuestion(desc,d,q, qty);
			dbManagerTest.close();
			return o;
		}
		

		public Registered addUser(String user){
			Registered us = findUser(user);
			if(us == null){
				dbManagerTest.open();
				us = dbManagerTest.addUser(user);
				dbManagerTest.close();
			}

			return us;
		}
		public Registered findUser(String user){
			Registered us;
			dbManagerTest.open();
			us = dbManagerTest.findUser(user);
			dbManagerTest.close();
			return us;
		}
		public boolean removeUser(String user){
			boolean remove = false;
			dbManagerTest.open();
			remove = dbManagerTest.removeUser(user);
			dbManagerTest.close();
			return remove;
		}
		public Apustua addApustua(double valor, Quote q){
			Apustua ap = null;
			dbManagerTest.open();
			ap = dbManagerTest.addApustua(valor, q);
			dbManagerTest.close();
			return ap;
		}
		

		public Quote addQuotesTo(Question question, double val, String forecast) {
			Quote quo = null;
			dbManagerTest.open();
			quo = dbManagerTest.addQuoteTo(question, val, forecast);
			dbManagerTest.close();
			return quo;
		}

		
		public Apustua addApuestaTo(Quote q, Registered u, double valor) {
			Apustua apu = null;
			dbManagerTest.open();
			apu = dbManagerTest.addApuestaTo(q,u,valor);
			dbManagerTest.close();
			return apu;
		}
}
