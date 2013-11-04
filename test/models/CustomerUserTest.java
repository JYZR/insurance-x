package models;

import play.test.*;
import static play.test.Helpers.*;
import org.junit.*;
import static org.junit.Assert.*;

public class CustomerUserTest extends WithApplication {
	@Before
	public void setUp() {
		start(fakeApplication(inMemoryDatabase()));
	}

	@Test
	public void createAndFetch() {
		new CustomerUser("Bobby", "12345", "bobby", "pwd").save();
		CustomerUser bobby = CustomerUser.fetch("bobby");
		assertNotNull(bobby);
		assertEquals("Bobby", bobby.name);
		assertEquals("12345", bobby.phone);
		assertEquals("bobby", bobby.username);
		assertEquals("pwd", bobby.password);
	}

	@Test
	public void authenticate() {
		new CustomerUser("Bobby", "12345", "bobby", "pwd").save();
		assertNotNull(CustomerUser.authenticate("bobby", "pwd"));
		assertNull(CustomerUser.authenticate("bobby", "asdf"));
		assertNull(CustomerUser.authenticate("tommy", "pwd"));
	}

	@Test
	public void countCustomerUsers() {
		new CustomerUser("Bobby", "12345", "bobby", "pwd").save();
		new CustomerUser("Jonny", "12345", "jonny", "pwd").save();
		assertEquals(2, CustomerUser.all().size());
	}

	@Test
	public void customerUserVsEmployeeUser() {
		new CustomerUser("Bobby", "12345", "bobby", "pwd").save();
		new EmployeeUser("Jimmy", "jimmy", "pwd").save();
		assertEquals(1, CustomerUser.allCustomers().size());
	}
}
