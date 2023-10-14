package test.dataAccess;

import java.util.Date;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import domain.Event;
import domain.Question;

public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {
		
		System.out.println("Creating TestDataAccess instance");

		open();
		
	}

	
	public void open(){
		
		System.out.println("Opening TestDataAccess instance ");

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		
	}
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean removeEvent(Event ev) {
		System.out.println(">> DataAccessTest: removeEvent");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e!=null) {
			db.getTransaction().begin();
			db.remove(e);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
		
		public Event addEventWithQuestion(String desc, Date d, String question, float qty) {
			System.out.println(">> DataAccessTest: addEvent");
			Event ev=null;
				db.getTransaction().begin();
				try {
				    ev=new Event(desc,d);
				    ev.addQuestion(question, qty);
					db.persist(ev);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return ev;
	    }
		public boolean existQuestion(Event ev,Question q) {
			System.out.println(">> DataAccessTest: existQuestion");
			Event e = db.find(Event.class, ev.getEventNumber());
			if (e!=null) {
				return e.DoesQuestionExists(q.getQuestion());
			} else 
			return false;
			
		}
		
		public boolean exiteEvento(Date ed, String desc) {
			System.out.println(">> DataAccessTest: existeEvento");
			
			TypedQuery<Event> q= db.createQuery("select e from Event e where e.getEventDate()=?1", Event.class);
			 
			q.setParameter(1, ed);
			System.out.println(q.getResultList());
			for(Event ev: q.getResultList()) {
				if (ev.getDescription().equals(desc)) {
					return true;
				
				}
			}
			return false;
			
			//Event ev = new Event(desc, ed);
			
		}
		
		public boolean eliminateEvent (Date ed, String desc) {
			System.out.println(">> DataAccessTest: eliminateEvent");
			Event e=null;
			//Event ev = new Event(desc, ed);
			
			TypedQuery<Event>q=db.createQuery("SELECT e FROM Event e WHERE e.getEventDate() =?1 ", Event.class);
			q.setParameter(1,desc);
			System.out.println(q.getResultList());
			for (Event ev: q.getResultList()) {
				if (ev.getEventDate().equals(ed)) {
					e=ev;
				}
			}
			
			if (e!=null) {
				db.getTransaction().begin();
				db.remove(e);
				db.getTransaction().commit();
				return true;
			}
			return false;
		
		}
		
		public Event crearEvento(String des, Date de) {
			Event e = new Event(des, de);
			
			return e;
		}
		
		public Event getEvent(Date eventDate, String desc) {
			System.out.println(">> DataAccessTest: removeEvent");
			TypedQuery<Event> q = db.createQuery("SELECT e FROM Event e WHERE e.getEventDate() =?1 ",Event.class);
			q.setParameter(1, eventDate);
			System.out.println("getevent "+ q.getResultList());
			for(Event ev: q.getResultList()) {
				if(ev.getDescription().equals(desc)) {
					return ev;
				}
			}
			return null;
	    }
		
}

