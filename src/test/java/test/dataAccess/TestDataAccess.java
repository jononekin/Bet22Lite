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
import domain.ApustuAnitza;
import domain.Apustua;
import domain.Event;
import domain.Question;
import domain.Quote;
import domain.Registered;

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
		
	public Event addEventWithQuestion(String desc, Date d, String question, float minQty) {
		System.out.println(">> DataAccessTest: addEvent");
		Event ev=null;
			db.getTransaction().begin();
			try {
			    ev=new Event(desc,d);
			    ev.addQuestion(question, minQty);
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
	public Registered addUser(String nom){
		System.out.println(">> DataAccessTest: addUser");
		db.getTransaction().begin();
		Registered us = new Registered(nom, nom, 1234, false);
		us.setDirukop(99999.0);
		db.persist(us);
		db.getTransaction().commit();
		return us;
	}
	public boolean removeUser(String nom){
		System.out.println(">> DataAccessTest: removeUser");
		Registered u = db.find(Registered.class, nom);
		if (u!=null) {
			db.getTransaction().begin();
			db.remove(u);
			db.getTransaction().commit();
			return true;
		} return false;
	}


	public Apustua addApustua(double valor, Quote q) {
		System.out.println(">> DataAccessTest: addEvent");
		Apustua ap=null;
		Registered u = findUser("userTest");
			db.getTransaction().begin();
			try {
				ApustuAnitza apuA = new ApustuAnitza(u,valor);
				ap = new Apustua(apuA,q);
				q.addApustua(ap);
			    db.persist(apuA);
				db.persist(ap);
				db.getTransaction().commit();
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return ap;
	}


	public Registered findUser(String nom) {
		System.out.println(">> DataAccessTest: findUser");
		Registered u = db.find(Registered.class, nom);
		if (u!=null) {
			return u;
		} return null;
		
	}


	public Quote addQuoteTo(Question question, double val, String forecast) {
		System.out.println(">> DataAccessTest: addQuoteTo");
		Quote qu = null;
		try {
			db.getTransaction().begin();
			qu = new Quote(val, forecast);
			Question q = db.find(Question.class, question.getQuestionNumber());
			q.getQuotes().add(qu);
			qu.setQuestion(q);
			db.persist(qu);
			db.getTransaction().commit();
		} catch (Exception e) {
			db.getTransaction().rollback();
			e.printStackTrace();
		}
		
		return qu;
	}


	public Apustua addApuestaTo(Quote q, Registered u, double valor) {
		System.out.println(">> DataAccessTest: addApuestaTo");
		Apustua apu = null;
		try {
			db.getTransaction().begin();
			Quote quote = db.find(Quote.class, q.getQuoteNumber());
			Registered user = db.find(Registered.class, u.getUsername());
			ApustuAnitza apuA = new ApustuAnitza(user, valor);
			apu = new Apustua(apuA, quote);
			apuA.addApustua(apu);
			apu.setApustuAnitza(apuA);
			user.addApustuAnitza(apuA);
			quote.addApustua(apu);
			db.persist(apu);
			db.persist(apuA);
			db.getTransaction().commit();
		} catch (Exception e) {
			db.getTransaction().rollback();
		}
		return apu;
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

