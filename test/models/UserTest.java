package models;

import play.test.*;
import static play.test.Helpers.*;

import org.junit.*;

import static org.junit.Assert.*;

public class UserTest extends WithApplication {
	@Before
	public void setUp() {
		start(fakeApplication(inMemoryDatabase()));
	}

	@Test
	public void createAndFetch() {
		new User("Bobby", "bobby", "pwd").save();
		User bobby = User.fetch("bobby");
		assertNotNull(bobby);
		assertEquals("Bobby", bobby.name);
		assertEquals("bobby", bobby.username);
		assertEquals("pwd", bobby.password);
	}

	@Test
	public void authenticate() {
		new User("Bobby", "bobby", "pwd").save();
		assertNotNull(User.authenticate("bobby", "pwd"));
		assertNull(User.authenticate("bobby", "asdf"));
		assertNull(User.authenticate("tommy", "pwd"));
	}

	@Test
	public void countUsers() {
		new User("Bobby", "bobby", "pwd").save();
		new User("Jonny", "jonny", "pwd").save();
		assertEquals(2, User.all().size());
	}
}
