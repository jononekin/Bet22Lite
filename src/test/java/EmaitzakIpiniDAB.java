
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import test.businessLogic.TestFacadeImplementation;


public class EmaitzakIpiniDAB {
    
    //sut:system under test
	static BLFacade sut=new BLFacadeImplementation();
	 
	 //additional operations needed to execute the test 
	static TestFacadeImplementation testDA=new TestFacadeImplementation();


	@Test
	public void test1(){
		try {
			sut.EmaitzakIpini(null);
			fail("No debería de ejecutar con parámetro null");
		} catch (Exception e) {
			assertTrue(true);
		}
	}

}
