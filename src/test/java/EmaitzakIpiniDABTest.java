import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.junit.Test;

import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import domain.Quote;
import exceptions.EventNotFinished;
import test.dataAccess.TestDataAccess;

public class EmaitzakIpiniDABTest {

	static DataAccess sut = new DataAccess();

	static TestDataAccess testDA = new TestDataAccess();

	private Quote quote;

	@Test
	public void test1() {
		try {
			sut.EmaitzakIpini(null);
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void test2() {
		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH);
		int year = today.get(Calendar.YEAR);

		month -= 1;
		if (month == -1) {
			month = 11;
			year -= 1;
		}

		Event ev = new Event("Atletico-Athletic", UtilDate.newDate(year, month, 1), null, null);
		Question q = ev.addQuestion("¿Quién ganará el partido?", 1);
		Quote quote = new Quote(1.3, "1", q);

		try {
			sut.EmaitzakIpini(quote);
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void test3() {
		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH);
		int year = today.get(Calendar.YEAR);

		month += 1;
		if (month == 12) {
			month = 0;
			year += 1;
		}

		testDA.open();
		quote = testDA.addQuoteWithDate(UtilDate.newDate(year, month, 1), "1");
		testDA.close();

		try {
			sut.EmaitzakIpini(quote);
			fail();
		} catch (EventNotFinished e) {
			assertTrue(true);
		} catch (Exception e) {
			fail();
		} finally {
			testDA.open();
			boolean b = testDA.removeQuote(quote);
			testDA.close();
			System.out.println("Finally " + b);
		}
	}

	@Test
	public void test4() {
		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH);
		int year = today.get(Calendar.YEAR);
		int day = today.get(Calendar.DAY_OF_MONTH);

		testDA.open();
		quote = testDA.addQuoteWithDate(UtilDate.newDate(year, month, day), "1");
		testDA.close();

		try {
			sut.EmaitzakIpini(quote);
			assertTrue(true);
		} catch (EventNotFinished e) {
			fail();
		} catch (Exception e) {
			fail();
		} finally {
			testDA.open();
			boolean b = testDA.removeQuote(quote);
			testDA.close();
			System.out.println("Finally " + b);
		}
	}

	@Test
	public void test5() {
		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH);
		int year = today.get(Calendar.YEAR);

		month -= 1;
		if (month == -1) {
			month = 11;
			year -= 1;
		}

		testDA.open();
		quote = testDA.addQuoteWithDate(UtilDate.newDate(year, month, 1), "1");
		testDA.close();

		try {
			sut.EmaitzakIpini(quote);
			assertTrue(true);
		} catch (EventNotFinished e) {
			fail();
		} catch (Exception e) {
			fail();
		} finally {
			testDA.open();
			boolean b = testDA.removeQuote(quote);
			testDA.close();
			System.out.println("Finally " + b);
		}
	}
}
