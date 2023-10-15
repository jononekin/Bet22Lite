import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.junit.Test;
import org.mockito.Mockito;

import businessLogic.BLFacadeImplementation;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import domain.Quote;
import exceptions.EventNotFinished;

public class EmaitzakIpiniBLBMTest {
	DataAccess dataAccessMockito = Mockito.mock(DataAccess.class);

	BLFacadeImplementation sut = new BLFacadeImplementation(dataAccessMockito);

	@Test
	public void test1() {
		Mockito.doNothing().when(dataAccessMockito).open(Mockito.anyBoolean());
		Mockito.doNothing().when(dataAccessMockito).close();
		try {
			Mockito.doNothing().when(dataAccessMockito).EmaitzakIpini(Mockito.nullable(Quote.class));
		} catch (EventNotFinished e) {
			e.printStackTrace();
		}

		try {
			sut.EmaitzakIpini(null);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void test2() {
		Mockito.doNothing().when(dataAccessMockito).open(Mockito.anyBoolean());
		Mockito.doNothing().when(dataAccessMockito).close();
		try {
			Mockito.doNothing().when(dataAccessMockito).EmaitzakIpini(Mockito.any(Quote.class));
		} catch (EventNotFinished e) {
			e.printStackTrace();
		}

		try {
			sut.EmaitzakIpini(new Quote());
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void test3() {
		Mockito.doNothing().when(dataAccessMockito).open(Mockito.anyBoolean());
		Mockito.doNothing().when(dataAccessMockito).close();
		try {
			Mockito.doNothing().when(dataAccessMockito).EmaitzakIpini(Mockito.any(Quote.class));
		} catch (EventNotFinished e) {
			e.printStackTrace();
		}

		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH);
		int year = today.get(Calendar.YEAR);

		month += 1;
		if (month == 12) {
			month = 0;
			year += 1;
		}

		Event ev = new Event("Atletico-Athletic", UtilDate.newDate(year, month, 1), null, null);
		Question q = new Question("¿Quién ganará el partido?", 1.5, ev);
		Quote quote = new Quote(5.0, "1", q);

		try {
			sut.EmaitzakIpini(quote);
			fail();
		} catch (EventNotFinished e) {
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void test4() {
		Mockito.doNothing().when(dataAccessMockito).open(Mockito.anyBoolean());
		Mockito.doNothing().when(dataAccessMockito).close();
		try {
			Mockito.doNothing().when(dataAccessMockito).EmaitzakIpini(Mockito.any(Quote.class));
		} catch (EventNotFinished e) {
			e.printStackTrace();
		}

		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH);
		int year = today.get(Calendar.YEAR);
		int day = today.get(Calendar.DAY_OF_MONTH);

		Event ev = new Event("Atletico-Athletic", UtilDate.newDate(year, month, day), null, null);
		Question q = new Question("¿Quién ganará el partido?", 1.5, ev);
		Quote quote = new Quote(5.0, "1", q);

		try {
			sut.EmaitzakIpini(quote);
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void test5() {
		Mockito.doNothing().when(dataAccessMockito).open(Mockito.anyBoolean());
		Mockito.doNothing().when(dataAccessMockito).close();
		try {
			Mockito.doNothing().when(dataAccessMockito).EmaitzakIpini(Mockito.any(Quote.class));
		} catch (EventNotFinished e) {
			e.printStackTrace();
		}

		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH);
		int year = today.get(Calendar.YEAR);

		month -= 1;
		if (month == -1) {
			month = 11;
			year -= 1;
		}

		Event ev = new Event("Atletico-Athletic", UtilDate.newDate(year, month, 1), null, null);
		Question q = new Question("¿Quién ganará el partido?", 1.5, ev);
		Quote quote = new Quote(5.0, "1", q);

		try {
			sut.EmaitzakIpini(quote);
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}
}
