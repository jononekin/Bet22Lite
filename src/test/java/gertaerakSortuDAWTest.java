
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Date;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Event;
import test.dataAccess.TestDataAccess;

public class gertaerakSortuDAWTest {

	// sut:system under test
	static DataAccess sut = new DataAccess(false);

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	private Event ev;

	@Test
	public void test1() {
		sut.open(false);
		String description = "Real Madrid-Girona";
		Date eventDate = new Date(0);
		String sport = "Futbol";

		boolean resp = sut.gertaerakSortu(description, eventDate, sport);

		sut.close();

		assertFalse(resp);
	}

	@Test
	public void test2() {

		sut.open(false);

		String description = "Real Madrid-Girona";
		Date eventDate = new Date(0);
		String sport = "Futbol";

		testDA.open();
		testDA.addSport("Futbol");
		Event ev = testDA.addEventWithQuestion(description, eventDate, "Gana real?", 1);
		testDA.close();

		boolean resp = sut.gertaerakSortu(description, eventDate, sport);

		testDA.open();
		testDA.removeSport("Futbol");
		testDA.removeEvent(ev);
		testDA.close();
		sut.close();

		assertFalse(resp);
	}

	@Test
	public void test3() {

		sut.open(false);

		String description = "Real Madrid-Girona";
		Date eventDate = new Date(0);
		String sport = "Futbol";

		testDA.open();
		testDA.addSport("Futbol");
		Event ev = testDA.addEventWithQuestion("Barcelona-Sevilla", eventDate, "Gana barcelona?", 1);
		testDA.close();

		boolean resp = sut.gertaerakSortu(description, eventDate, sport);

		testDA.open();
		testDA.removeSport("Futbol");
		testDA.removeEvent(ev);
		testDA.close();
		sut.close();

		assertTrue(resp);
	}

	@Test
	public void test4() {

		sut.open(false);

		String description = "Real Madrid-Girona";
		Date eventDate = new Date(0);
		String sport = "Futbol";

		testDA.open();
		testDA.addSport("Futbol");
		testDA.close();

		boolean resp = sut.gertaerakSortu(description, eventDate, sport);

		testDA.open();
		testDA.removeSport("Futbol");
		testDA.close();
		sut.close();

		assertTrue(resp);
	}

}
