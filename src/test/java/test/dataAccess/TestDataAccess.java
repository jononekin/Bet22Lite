package test.dataAccess;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.ApustuAnitza;
import domain.Apustua;
import domain.Event;
import domain.Question;
import domain.Quote;
import domain.Registered;

public class TestDataAccess {
	protected EntityManager db;
	protected EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public TestDataAccess() {

		System.out.println("Creating TestDataAccess instance");

		open();

	}

	public void open() {

		System.out.println("Opening TestDataAccess instance ");

		String fileName = c.getDbFilename();

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}

	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean removeEvent(Event ev) {
		System.out.println(">> DataAccessTest: removeEvent");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e != null) {
			db.getTransaction().begin();
			db.remove(e);
			db.getTransaction().commit();
			return true;
		} else
			return false;
	}

	public boolean removeQuote(Quote quote) {
		System.out.println(">> DataAccessTest: removeQuote");
		Quote q = db.find(Quote.class, quote.getQuoteNumber());
		if (q != null) {
			db.getTransaction().begin();
			db.remove(q);
			db.getTransaction().commit();
			return true;
		} else
			return false;
	}

	public void removeRegistered(String username) {
		System.out.println(">> DataAccessTest: removeRegistered");
		Registered r = db.find(Registered.class, username);
		if (r != null) {
			db.getTransaction().begin();
			db.remove(r);
			db.getTransaction().commit();
		}
	}

	public Event addEventWithQuestion(String desc, Date d, String question, float qty) {
		System.out.println(">> DataAccessTest: addEvent");
		Event ev = null;
		db.getTransaction().begin();
		try {
			ev = new Event(desc, d, null, null);
			ev.addQuestion(question, qty);
			db.persist(ev);
			db.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ev;
	}

	public boolean existQuestion(Event ev, Question q) {
		System.out.println(">> DataAccessTest: existQuestion");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e != null) {
			return e.DoesQuestionExists(q.getQuestion());
		} else
			return false;

	}

	public Quote getQuote(Integer quoteNumber) {
		return db.find(Quote.class, quoteNumber);
	}

	public Quote addQuoteWithDate(Date date, String forecast) {
		System.out.println(">> DataAccessTest: addQuoteWithDate");
		db.getTransaction().begin();

		Event ev = new Event("Atletico-Athletic", date, null, null);
		Question q = ev.addQuestion("¿Quién ganará el partido?", 1);
		Quote quote = new Quote(1.3, forecast, q);

		db.persist(q);
		db.persist(ev);
		db.persist(quote);

		db.getTransaction().commit();
		return quote;
	}

	public Quote addQuoteApustua(Registered reg, ApustuAnitza apA, Boolean isIrabazita) {
		System.out.println(">> DataAccessTest: addQuoteApustua");

		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH);
		int year = today.get(Calendar.YEAR);

		month -= 1;
		if (month == -1) {
			month = 11;
			year -= 1;
		}

		db.getTransaction().begin();

		Event ev = new Event("Atletico-Athletic", UtilDate.newDate(year, month, 1), null, null);
		Question q = ev.addQuestion("¿Quién ganará el partido?", 1);
		Quote quote = new Quote(1.3, "1", q);
		Apustua ap = new Apustua(apA, quote);

		if (isIrabazita) {
			ap.setEgoera("irabazita");
		}

		apA.addApustua(ap);
		quote.addApustua(ap);
		reg.addApustuAnitza(apA);

		db.persist(apA);
		db.persist(ap);
		db.persist(q);
		db.persist(ev);
		db.persist(quote);
		db.persist(reg);

		db.getTransaction().commit();
		return quote;
	}

	public Quote addQuoteWithQuote() {
		System.out.println(">> DataAccessTest: addQuoteWithQuote");

		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH);
		int year = today.get(Calendar.YEAR);

		month -= 1;
		if (month == -1) {
			month = 11;
			year -= 1;
		}

		db.getTransaction().begin();

		Event ev = new Event("Atletico-Athletic", UtilDate.newDate(year, month, 1), null, null);
		Question q = ev.addQuestion("¿Quién ganará el partido?", 1);
		Quote quote = q.addQuote(1.3, "1", q);

		db.persist(q);
		db.persist(ev);
		db.persist(quote);

		db.getTransaction().commit();
		return quote;
	}

	public Quote addQuoteWithQuote(Registered reg) {
		System.out.println(">> DataAccessTest: addQuoteWithQuote");

		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH);
		int year = today.get(Calendar.YEAR);

		month -= 1;
		if (month == -1) {
			month = 11;
			year -= 1;
		}

		db.getTransaction().begin();

		Event ev = new Event("Atletico-Athletic", UtilDate.newDate(year, month, 1), null, null);
		Question q = ev.addQuestion("¿Quién ganará el partido?", 1);
		Quote quote = q.addQuote(1.3, "1", q);
		ApustuAnitza apA = new ApustuAnitza(reg, 5.0);
		Apustua ap = new Apustua(apA, quote);

		apA.addApustua(ap);
		quote.addApustua(ap);
		reg.addApustuAnitza(apA);

		db.persist(apA);
		db.persist(ap);
		db.persist(reg);
		db.persist(q);
		db.persist(ev);
		db.persist(quote);

		db.getTransaction().commit();
		return quote;
	}

	public Quote[] addQuoteWithMultiApustua(Registered reg) {
		System.out.println(">> DataAccessTest: addQuoteWithQuote");

		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH);
		int year = today.get(Calendar.YEAR);

		month -= 1;
		if (month == -1) {
			month = 11;
			year -= 1;
		}

		db.getTransaction().begin();

		Event ev = new Event("Atletico-Athletic", UtilDate.newDate(year, month, 1), null, null);
		Question q = ev.addQuestion("¿Quién ganará el partido?", 1);
		Quote quote1 = q.addQuote(1.3, "1", q);
		Quote quote2 = q.addQuote(1.5, "2", q);
		ApustuAnitza apA1 = new ApustuAnitza(reg, 5.0);
		Apustua ap1 = new Apustua(apA1, quote1);
		Apustua ap2 = new Apustua(apA1, quote1);

		apA1.addApustua(ap2);
		apA1.addApustua(ap1);
		quote1.addApustua(ap1);
		quote2.addApustua(ap2);
		reg.addApustuAnitza(apA1);

		db.persist(apA1);
		db.persist(ap1);
		db.persist(ap2);
		db.persist(reg);
		db.persist(q);
		db.persist(ev);
		db.persist(quote1);
		db.persist(quote2);

		db.getTransaction().commit();
		return new Quote[] { quote1, quote2 };
	}
}
