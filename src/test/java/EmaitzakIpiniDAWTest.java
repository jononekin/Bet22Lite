import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.junit.Test;

import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.ApustuAnitza;
import domain.Quote;
import domain.Registered;
import exceptions.EventNotFinished;
import test.dataAccess.TestDataAccess;

public class EmaitzakIpiniDAWTest {

	static DataAccess sut = new DataAccess();

	static TestDataAccess testDA = new TestDataAccess();

	private Quote quote;
	private Registered reg;

	@Test
	public void test1() {
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
	public void test2() {
		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH);
		int year = today.get(Calendar.YEAR);

		month -= 1;
		if (month == -1) {
			month = 11;
			year -= 1;
		}
		String forecast = "1";

		testDA.open();
		quote = testDA.addQuoteWithDate(UtilDate.newDate(year, month, 1), forecast);
		testDA.close();

		try {
			sut.EmaitzakIpini(quote);

			testDA.open();
			quote = testDA.getQuote(quote.getQuoteNumber());
			testDA.close();

			assertEquals(forecast, quote.getQuestion().getResult());
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
	public void test3() {
		testDA.open();
		testDA.removeRegistered("registered1");
		reg = new Registered("registered1", "1234", 12345);
		ApustuAnitza apA = new ApustuAnitza(reg, 5.0);
		quote = testDA.addQuoteApustua(reg, apA, false);
		testDA.close();

		try {
			sut.EmaitzakIpini(quote);

			testDA.open();
			quote = testDA.getQuote(quote.getQuoteNumber());
			testDA.close();

			assertEquals("jokoan", quote.getApustuak().get(0).getApustuAnitza().getEgoera());
		} catch (Exception e) {
			fail();
		} finally {
			testDA.open();
			boolean b = testDA.removeQuote(quote);
			testDA.removeRegistered("registered1");
			testDA.close();
			System.out.println("Finally " + b);
		}
	}

	@Test
	public void test4() {
		testDA.open();
		testDA.removeRegistered("registered2");
		reg = new Registered("registered2", "1234", 12345);
		ApustuAnitza apA = new ApustuAnitza(reg, 5.0);
		quote = testDA.addQuoteApustua(reg, apA, true);
		testDA.close();

		try {
			sut.EmaitzakIpini(quote);

			testDA.open();
			quote = testDA.getQuote(quote.getQuoteNumber());
			testDA.close();

			assertEquals("irabazita", quote.getApustuak().get(0).getApustuAnitza().getEgoera());
		} catch (Exception e) {
			fail();
		} finally {
			testDA.open();
			boolean b = testDA.removeQuote(quote);
			testDA.removeRegistered("registered2");
			testDA.close();
			System.out.println("Finally " + b);
		}
	}

	@Test
	public void test5() {
		testDA.open();
		quote = testDA.addQuoteWithQuote();
		testDA.close();

		try {
			sut.EmaitzakIpini(quote);
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
	public void test6() {
		testDA.open();
		testDA.removeRegistered("registered4");
		reg = new Registered("registered4", "1234", 12345);
		quote = testDA.addQuoteWithQuote(reg);
		testDA.close();

		try {
			sut.EmaitzakIpini(quote);

			testDA.open();
			quote = testDA.getQuote(quote.getQuoteNumber());
			testDA.close();

			assertEquals("irabazita", quote.getQuestion().getQuotes().get(0).getApustuak().get(0).getEgoera());
		} catch (Exception e) {
			fail();
		} finally {
			testDA.open();
			boolean b = testDA.removeQuote(quote);
			testDA.removeRegistered("registered4");
			testDA.close();
			System.out.println("Finally " + b);
		}
	}

	@Test
	public void test7() {
		testDA.open();
		testDA.removeRegistered("registered3");
		reg = new Registered("registered3", "1234", 12345);
		Quote[] quotes = testDA.addQuoteWithMultiApustua(reg);
		testDA.close();

		try {
			sut.EmaitzakIpini(quotes[0]);

			testDA.open();
			quote = testDA.getQuote(quotes[0].getQuoteNumber());
			testDA.close();

			assertEquals("galduta",
					quote.getQuestion().getQuotes().get(0).getApustuak().get(0).getApustuAnitza().getEgoera());
		} catch (Exception e) {
			fail();
		} finally {
			testDA.open();
			boolean b1 = testDA.removeQuote(quotes[0]);
			boolean b2 = testDA.removeQuote(quotes[1]);
			testDA.removeRegistered("registered3");
			testDA.close();
			System.out.println("Finally " + (b1 && b2));
		}
	}
}
