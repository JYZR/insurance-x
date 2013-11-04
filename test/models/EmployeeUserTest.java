package models;

import play.test.*;
import static play.test.Helpers.*;
import org.junit.*;
import static org.junit.Assert.*;

public class EmployeeUserTest extends WithApplication {
	@Before
	public void setUp() {
		start(fakeApplication(inMemoryDatabase()));
	}

	@Test
	public void createAndFetch() {
		new EmployeeUser("Bobby", "bobby", "pwd").save();
		EmployeeUser bobby = EmployeeUser.fetch("bobby");
		assertNotNull(bobby);
		assertEquals("Bobby", bobby.name);
		assertEquals("bobby", bobby.username);
		assertEquals("pwd", bobby.password);
	}

	@Test
	public void authenticate() {
		new EmployeeUser("Bobby", "bobby", "pwd").save();
		assertNotNull(EmployeeUser.authenticate("bobby", "pwd"));
		assertNull(EmployeeUser.authenticate("bobby", "asdf"));
		assertNull(EmployeeUser.authenticate("tommy", "pwd"));
	}

	@Test
	public void countEmployeeUsers() {
		new EmployeeUser("Bobby", "bobby", "pwd").save();
		new EmployeeUser("Jonny", "jonny", "pwd").save();
		assertEquals(2, EmployeeUser.all().size());
	}
	
	@Test
	public void employeeUserVsCustomerUser() {
		new EmployeeUser("Bobby", "bobby", "pwd").save();
		new CustomerUser("Jimmy", "jimmy", "pwd").save();
		assertEquals(1, EmployeeUser.allEmployees().size());
	}
}
